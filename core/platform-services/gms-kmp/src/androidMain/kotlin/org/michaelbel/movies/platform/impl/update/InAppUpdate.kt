package org.michaelbel.movies.platform.impl.update

import android.app.Activity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallException
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallErrorCode
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import org.michaelbel.movies.platform.app.AppService

class InAppUpdate(
    private val appUpdateManager: AppUpdateManager,
    appService: AppService
) {

    var onUpdateAvailableListener: (Boolean) -> Unit = {}

    private val appUpdateInfo: Task<AppUpdateInfo> = appUpdateManager.appUpdateInfo
    private val appUpdateType: Int = AppUpdateType.IMMEDIATE

    init {
        if (appService.isPlayServicesAvailable) {
            appUpdateInfo.addOnSuccessListener(::onSuccessAppUpdate)
            appUpdateInfo.addOnFailureListener(::onFailureAppUpdate)
        }
    }

    fun startUpdateFlow(activity: Activity) {
        appUpdateManager.startUpdateFlow(
            appUpdateInfo.result,
            activity,
            AppUpdateOptions.defaultOptions(appUpdateType)
        )
    }

    private fun onSuccessAppUpdate(appUpdateInfo: AppUpdateInfo) {
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
            onUpdateAvailableListener(true)
        }
    }

    private fun onFailureAppUpdate(throwable: Throwable) {
        if (throwable is InstallException && throwable.errorCode == InstallErrorCode.ERROR_APP_NOT_OWNED) return
    }
}