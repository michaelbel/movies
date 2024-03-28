package org.michaelbel.movies.common.ktx

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.net.toUri

val Context.appSettingsIntent: Intent
    get() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            "package:$packageName".toUri()
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return intent
    }