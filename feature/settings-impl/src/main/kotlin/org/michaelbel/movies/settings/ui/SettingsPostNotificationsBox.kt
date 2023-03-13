package org.michaelbel.movies.settings.ui

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.net.toUri
import org.michaelbel.movies.common.ktx.notificationManager
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.lifecycle.OnResume
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsPostNotificationsBox(
    modifier: Modifier = Modifier,
    onShowPermissionSnackbar: () -> Unit
) {
    val context: Context = LocalContext.current
    val notificationManager: NotificationManager = context.notificationManager
    var areNotificationsEnabled: Boolean by remember { mutableStateOf(notificationManager.areNotificationsEnabled()) }

    val resultContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    val onStartAppSettingsIntent: () -> Unit = {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            "package:${context.packageName}".toUri()
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { intent: Intent ->
            resultContract.launch(intent)
        }
    }

    val postNotificationsPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            areNotificationsEnabled = notificationManager.areNotificationsEnabled()
        } else {
            val shouldRequest: Boolean = (context as Activity).shouldShowRequestPermissionRationale(
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (!shouldRequest) {
                onShowPermissionSnackbar()
            }
        }
    }

    OnResume {
        areNotificationsEnabled = notificationManager.areNotificationsEnabled()
    }

    ConstraintLayout(
        modifier = modifier
            .clickable {
                if (areNotificationsEnabled) {
                    onStartAppSettingsIntent()
                } else {
                    postNotificationsPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .testTag("ConstraintLayout")
    ) {
        val (title, value) = createRefs()

        Text(
            text = stringResource(R.string.settings_post_notifications),
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("Text"),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.bodyLarge
        )

        Switch(
            checked = areNotificationsEnabled,
            onCheckedChange = null,
            modifier = Modifier
                .constrainAs(value) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("Switch")
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsPostNotificationsBoxPreview() {
    MoviesTheme {
        SettingsPostNotificationsBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            onShowPermissionSnackbar = {}
        )
    }
}