package org.michaelbel.movies.common.inappupdate.di

import android.app.Activity
import android.util.Log
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.clientVersionStalenessDays
import com.google.android.play.core.ktx.totalBytesToDownload
import com.google.android.play.core.tasks.Task
import org.michaelbel.movies.common.googleapi.GoogleApi
import timber.log.Timber
import javax.inject.Inject

class InAppUpdate @Inject constructor(
    private val appUpdateManager: AppUpdateManager,
    googleApi: GoogleApi
) {

    var onUpdateAvailableListener: (Boolean) -> Unit = {}

    private val appUpdateInfo: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo
    private val appUpdateType: Int = AppUpdateType.IMMEDIATE

    init {
        Log.e("2", "InAppUpdate init googleApi isPlayServicesAvailable = ${googleApi.isPlayServicesAvailable}")
        if (googleApi.isPlayServicesAvailable) {
            appUpdateInfo.addOnSuccessListener(::onSuccessAppUpdate)
            appUpdateInfo.addOnFailureListener(::onFailureAppUpdate)
        }
    }

    fun startUpdateFlow(activity: Activity) {
        Log.e("2", "InAppUpdate startUpdateFlow")
        appUpdateManager.startUpdateFlow(
            appUpdateInfo.result,
            activity,
            AppUpdateOptions.defaultOptions(appUpdateType)
        )
    }

    private fun onSuccessAppUpdate(appUpdateInfo: AppUpdateInfo) {
        Log.e("2", "onSuccessAppUpdate appUpdateInfo updateAvailability=${appUpdateInfo.updateAvailability()}")
        Log.e("2", "onSuccessAppUpdate appUpdateInfo updatePriority=${appUpdateInfo.updatePriority()}")
        Log.e("2", "onSuccessAppUpdate appUpdateInfo availableVersionCode=${appUpdateInfo.availableVersionCode()}")
        Log.e("2", "onSuccessAppUpdate appUpdateInfo packageName=${appUpdateInfo.packageName()}")
        Log.e("2", "onSuccessAppUpdate appUpdateInfo totalBytesToDownload=${appUpdateInfo.totalBytesToDownload()}")
        Log.e("2", "onSuccessAppUpdate appUpdateInfo clientVersionStalenessDays=${appUpdateInfo.clientVersionStalenessDays}")
        Log.e("2", "onSuccessAppUpdate appUpdateInfo totalBytesToDownload=${appUpdateInfo.totalBytesToDownload}")
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
            Log.e("2", "onSuccessAppUpdate onUpdateAvailableListener true")
            onUpdateAvailableListener(true)
        }
    }

    private fun onFailureAppUpdate(throwable: Throwable) {
        Log.e("2", "onFailureAppUpdate $throwable")
        Timber.e(throwable)
    }
}