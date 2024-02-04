package org.michaelbel.movies.settings.ui

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import org.michaelbel.movies.common.ktx.notificationManager
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.compose.SwitchCheckIcon
import org.michaelbel.movies.ui.ktx.appNotificationSettingsIntent
import org.michaelbel.movies.ui.lifecycle.OnResume

@Composable
fun SettingsPostNotificationsBox(
    onShowPermissionSnackbar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val notificationManager = context.notificationManager
    var areNotificationsEnabled by remember { mutableStateOf(notificationManager.areNotificationsEnabled()) }

    val resultContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

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
                    resultContract.launch(context.appNotificationSettingsIntent)
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
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
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
                .testTag("Switch"),
            thumbContent = if (areNotificationsEnabled) { { SwitchCheckIcon() } } else null,
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.surfaceTint,
                checkedIconColor = MaterialTheme.colorScheme.surfaceTint
            )
        )
    }
}