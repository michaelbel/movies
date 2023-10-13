package org.michaelbel.movies.notifications.ktx

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri

val Context.appNotificationSettingsIntent: Intent
    get() {
        val intent = Intent()
        when {
            Build.VERSION.SDK_INT >= 26 -> {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
            Build.VERSION.SDK_INT >= 21 -> {
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS")
                intent.putExtra("app_package", packageName)
                intent.putExtra("app_uid", applicationInfo.uid)
            }
            else -> {
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.setData("package:$packageName".toUri())
            }
        }
        return intent
    }