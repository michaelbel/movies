package org.michaelbel.movies.settings.ktx

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.michaelbel.movies.ui.ktx.appNotificationSettingsIntent

@Composable
actual fun rememberPostNotificationsPermissionHandler(
    areNotificationsEnabled: Boolean,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
): () -> Unit {
    val context = LocalContext.current
    val postNotificationsPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> onPermissionGranted()
            else -> {
                if (Build.VERSION.SDK_INT >= 33) {
                    val shouldRequest = (context as Activity).shouldShowRequestPermissionRationale(
                        Manifest.permission.POST_NOTIFICATIONS)
                    if (!shouldRequest) {
                        onPermissionDenied()
                    }
                }
            }
        }
    }
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    return {
        if (areNotificationsEnabled) {
            val intent = context.appNotificationSettingsIntent
            resultContract.launch(intent)
        } else if (Build.VERSION.SDK_INT >= 33) {
            postNotificationsPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}