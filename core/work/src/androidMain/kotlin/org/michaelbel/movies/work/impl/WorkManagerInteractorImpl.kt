package org.michaelbel.movies.work.impl

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.michaelbel.movies.persistence.database.entity.pojo.ImagePojo
import org.michaelbel.movies.persistence.database.ktx.original
import org.michaelbel.movies.work.AccountUpdateWorker
import org.michaelbel.movies.work.DownloadImageWorker
import org.michaelbel.movies.work.MoviesDatabaseWorker
import org.michaelbel.movies.work.R
import org.michaelbel.movies.work.WorkInfoState
import org.michaelbel.movies.work.WorkManagerInteractor
import org.michaelbel.movies.work.ktx.nameRes

class WorkManagerInteractorImpl(
    private val workManager: WorkManager
): WorkManagerInteractor {

    override fun downloadImage(image: ImagePojo): Flow<WorkInfoState> {
        return flow {
            val workData = Data.Builder()
                .putString(DownloadImageWorker.KEY_IMAGE_URL, image.original)
                .putInt(DownloadImageWorker.KEY_CONTENT_TITLE,  R.string.gallery_downloading_image)
                .putInt(DownloadImageWorker.KEY_CONTENT_TEXT, image.type.nameRes)
                .build()
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresStorageNotLow(true)
                .setRequiresBatteryNotLow(true)
                .build()
            val downloadImageWorker = OneTimeWorkRequestBuilder<DownloadImageWorker>()
                .addTag(DownloadImageWorker.DOWNLOAD_IMAGE_WORKER_TAG)
                .setConstraints(constraints)
                .setInputData(workData)
                .build()
            workManager.run {
                enqueueUniqueWork(image.toString(), ExistingWorkPolicy.KEEP, downloadImageWorker)
                getWorkInfoByIdFlow(downloadImageWorker.id).collect { workInfo ->
                    when (workInfo?.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            val result = workInfo.outputData.getString(DownloadImageWorker.KEY_IMAGE_URL).orEmpty()
                            emit(WorkInfoState.Success(result))
                        }
                        WorkInfo.State.FAILED -> {
                            val result = workInfo.outputData.getString(DownloadImageWorker.KEY_IMAGE_URL).orEmpty()
                            emit(WorkInfoState.Failure(result))
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    override fun prepopulateDatabase() {
        val request = OneTimeWorkRequestBuilder<MoviesDatabaseWorker>()
            .setInputData(workDataOf(MoviesDatabaseWorker.KEY_FILENAME to MOVIES_DATA_FILENAME))
            .build()
        workManager.enqueue(request)
    }

    override fun updateAccountDetails() {
        val request = OneTimeWorkRequestBuilder<AccountUpdateWorker>()
            .build()
        workManager.enqueue(request)
    }

    private companion object {
        private const val MOVIES_DATA_FILENAME = "movies.json"
    }
}