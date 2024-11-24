package org.michaelbel.movies.settings.ktx

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.michaelbel.movies.ui.ktx.appNotificationSettingsIntent

@Composable
actual fun openAppNotificationSettings(): () -> Unit {
    val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    val context = LocalContext.current
    return { resultContract.launch(context.appNotificationSettingsIntent) }
}