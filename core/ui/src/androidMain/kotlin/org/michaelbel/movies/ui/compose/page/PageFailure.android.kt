package org.michaelbel.movies.ui.compose.page

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme
import org.michaelbel.movies.ui.R

@Composable
fun PageFailure(
    modifier: Modifier
) {
    val settingsPanelContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = MoviesIcons.Info,
            contentDescription = MoviesContentDescription.None,
            modifier = Modifier.size(36.dp),
            tint = MaterialTheme.colorScheme.error
        )

        Text(
            text = stringResource(R.string.error_loading),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        if (Build.VERSION.SDK_INT >= 29) {
            val onCheckConnectivityClick: () -> Unit = {
                settingsPanelContract.launch(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
            }

            OutlinedButton(
                onClick = onCheckConnectivityClick,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.error_check_internet_connectivity)
                )
            }
        }
    }
}

@Composable
@DevicePreviews
private fun PageFailurePreview() {
    MoviesTheme {
        PageFailure(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun PageFailureAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        PageFailure(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}