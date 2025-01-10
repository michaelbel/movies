package org.michaelbel.movies.common.ktx

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings

val Context.appNotificationSettingsIntent: Intent
    get() {
        val intent = Intent()
        when {
            Build.VERSION.SDK_INT >= 26 -> {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
            else -> {
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("app_package", packageName)
                intent.putExtra("app_uid", applicationInfo.uid)
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return intent
    }