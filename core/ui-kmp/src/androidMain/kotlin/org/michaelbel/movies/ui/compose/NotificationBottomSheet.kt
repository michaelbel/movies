package org.michaelbel.movies.ui.compose

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.appNotificationSettingsIntent
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme
import org.michaelbel.movies.ui_kmp.R

@Composable
actual fun NotificationBottomSheet(
    modifier: Modifier,
    onBottomSheetHide: () -> Unit
) {
    val context = LocalContext.current

    val activityContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    val permissionContract = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        val shouldRequest = (context as Activity).shouldShowRequestPermissionRationale(
            Manifest.permission.POST_NOTIFICATIONS
        )
        if (!granted && !shouldRequest) {
            activityContract.launch(context.appNotificationSettingsIntent)
        }
    }

    val value by rememberInfiniteTransition(label = "")
        .animateFloat(
            initialValue = 15f,
            targetValue = -15f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = MoviesIcons.Notifications,
                contentDescription = MoviesContentDescription.None,
                modifier = Modifier.graphicsLayer(
                    transformOrigin = TransformOrigin(
                        pivotFractionX = 0.5F,
                        pivotFractionY = 0.0F,
                    ),
                    rotationZ = value
                ),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }

        Text(
            text = stringResource(R.string.notification_enable_title),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        Text(
            text = stringResource(R.string.notification_enable_subtitle),
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        Button(
            onClick = {
                onBottomSheetHide()
                when {
                    Build.VERSION.SDK_INT >= 33 -> {
                        permissionContract.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    else -> {
                        activityContract.launch(context.appNotificationSettingsIntent)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint
            ),
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
        ) {
            Text(
                text = stringResource(if (Build.VERSION.SDK_INT >= 33) R.string.notification_continue else R.string.notification_go_to_settings)
            )
        }
    }
}

@Composable
@DevicePreviews
private fun NotificationBottomSheetPreview() {
    MoviesTheme {
        NotificationBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            onBottomSheetHide = {}
        )
    }
}

@Composable
@Preview
private fun NotificationBottomSheetAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        NotificationBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            onBottomSheetHide = {}
        )
    }
}