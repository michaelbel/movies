package org.michaelbel.movies.ui.ktx

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

@Composable
fun rememberSpeechRecognitionLauncher(onInputText: (String) -> Unit): () -> Unit {
    val speechRecognizeContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        val data = activityResult.data
        val spokenText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { results ->
            results[0]
        }
        if (!spokenText.isNullOrEmpty()) {
            onInputText(spokenText)
        }
    }

    return {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        speechRecognizeContract.launch(intent)
    }
}

@Composable
fun rememberConnectivityClickHandler(): () -> Unit {
    if (Build.VERSION.SDK_INT >= 29) {
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
        return {
            val intent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
            launcher.launch(intent)
        }
    } else {
        return {}
    }
}

@Composable
fun rememberNavigateToAppSettings(): () -> Unit {
    val context = LocalContext.current
    val appSettingsContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        "package:${context.packageName}".toUri()
    ).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    return remember { { appSettingsContract.launch(intent) } }
}

@Composable
fun rememberRequestNotificationPermission(
    onGranted: () -> Unit = {}
): () -> Unit {
    if (Build.VERSION.SDK_INT >= 33) {
        val context = LocalContext.current
        val navigateToAppSettings = rememberNavigateToAppSettings()
        val cameraPermissionContract = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            val shouldRequest = (context as Activity).shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
            when {
                granted -> onGranted()
                !granted && !shouldRequest -> navigateToAppSettings()
            }
        }
        return remember {
            {
                when {
                    ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED -> {
                        cameraPermissionContract.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    else -> onGranted()
                }
            }
        }
    } else {
        return {}
    }
}