package org.michaelbel.movies.work

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import net.i2p.android.router.util.ConnectivityAndInternetAccess
import org.michaelbel.movies.common.ktx.currentDateTime
import org.michaelbel.movies.interactor.AppNotificationInteractor
import org.michaelbel.movies.network.connectivity.RemoteConnectivityPolicy
import org.michaelbel.movies.network.connectivity.impl.MoviesConnectivityMonitor

class DownloadImageWorker(
    private val context: Context,
    workerParams: WorkerParameters,
    private val appNotificationInteractor: AppNotificationInteractor,
    private val connectivityMonitor: MoviesConnectivityMonitor
): CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val imageUrl = inputData.getString(KEY_IMAGE_URL).orEmpty()
        val contentTitleRes = inputData.getInt(KEY_CONTENT_TITLE, 0)
        val contentTextRes = inputData.getInt(KEY_CONTENT_TEXT, 0)
        val notificationId = imageUrl.hashCode()

        if (imageUrl.isEmpty()) {
            return Result.failure(workDataOf(KEY_IMAGE_URL to FAILURE_RESULT))
        }

        // WorkManager also has a CONNECTED constraint, but this local guard closes the
        // race between constraint evaluation and the actual URL.openStream() call.
        if (!RemoteConnectivityPolicy.canStartRemoteRequest(
                isConnected = ConnectivityAndInternetAccess.isConnected(applicationContext),
                hasPhysicalNetwork = ConnectivityAndInternetAccess.hasPhysicalNetwork(applicationContext)
            )
        ) {
            return Result.retry()
        }

        appNotificationInteractor.sendDownloadImageNotification(
            notificationId = notificationId,
            contentTitleRes = contentTitleRes,
            contentTextRes = contentTextRes
        )

        val uri = try {
            saveImageToDownloads(
                url = imageUrl,
                name = "$currentDateTime.jpg"
            )
        } catch (failure: IOException) {
            connectivityMonitor.onBackendTransportFailure(
                failedHost = runCatching { URL(imageUrl).host }.getOrDefault(imageUrl),
                failure = failure
            )
            appNotificationInteractor.cancelDownloadImageNotification(notificationId)
            return Result.retry()
        }

        appNotificationInteractor.cancelDownloadImageNotification(notificationId)

        return if (uri != null) {
            Result.success(workDataOf(KEY_IMAGE_URL to uri.toString()))
        } else {
            Result.failure(workDataOf(KEY_IMAGE_URL to FAILURE_RESULT))
        }
    }

    private fun saveImageToDownloads(url: String, name: String): Uri? {
        if (Build.VERSION.SDK_INT >= 29) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                put(MediaStore.MediaColumns.MIME_TYPE, IMAGE_MIME_TYPE)
                put(MediaStore.MediaColumns.RELATIVE_PATH, IMAGE_RELATIVE_PATH)
            }
            val contentResolver = context.contentResolver
            val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            return if (uri != null) {
                URL(url).openStream().use { input ->
                    contentResolver.openOutputStream(uri).use { output ->
                        input.copyTo(requireNotNull(output), DEFAULT_BUFFER_SIZE)
                    }
                }
                uri
            } else {
                null
            }
        } else {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                name
            )
            URL(url).openStream().use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            return file.toUri()
        }
    }

    companion object {
        const val KEY_IMAGE_URL = "IMAGE_URL"
        const val KEY_CONTENT_TITLE = "CONTENT_TITLE"
        const val KEY_CONTENT_TEXT = "CONTENT_TEXT"
        const val FAILURE_RESULT = "FAILURE_RESULT"
        const val IMAGE_MIME_TYPE = "JPG"
        const val IMAGE_RELATIVE_PATH = "Download"
        const val DOWNLOAD_IMAGE_WORKER_TAG = "downloadImageWorker"
    }
}
