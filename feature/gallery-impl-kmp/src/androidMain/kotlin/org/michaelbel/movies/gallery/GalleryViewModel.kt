@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.ktx.require
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.gallery.ktx.nameRes
import org.michaelbel.movies.gallery_impl_kmp.R
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.persistence.database.entity.ImageDb
import org.michaelbel.movies.persistence.database.ktx.original
import org.michaelbel.movies.work.DownloadImageWorker

@HiltViewModel
actual class GalleryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val interactor: Interactor,
    private val workManager: WorkManager
): BaseViewModel() {

    private val movieId: String = savedStateHandle.require("movieId")

    val movieImagesFlow: StateFlow<List<ImageDb>> = interactor.imagesFlow(movieId.toInt())
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    private val _workInfoFlow: MutableStateFlow<WorkInfo?> = MutableStateFlow(null)
    val workInfoFlow: StateFlow<WorkInfo?> = _workInfoFlow.asStateFlow()

    init {
        loadMovieImages(movieId.toInt())
    }

    fun downloadImage(imageDb: ImageDb) = launch {
        val workData = Data.Builder()
            .putString(DownloadImageWorker.KEY_IMAGE_URL, imageDb.original)
            .putInt(DownloadImageWorker.KEY_CONTENT_TITLE, R.string.gallery_downloading_image)
            .putInt(DownloadImageWorker.KEY_CONTENT_TEXT, imageDb.type.nameRes)
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
            enqueueUniqueWork(imageDb.toString(), ExistingWorkPolicy.KEEP, downloadImageWorker)
            getWorkInfoByIdFlow(downloadImageWorker.id).collect { workInfo ->
                _workInfoFlow.emit(workInfo)
            }
        }
    }

    private fun loadMovieImages(movieId: Int) = launch {
        interactor.images(movieId)
    }
}