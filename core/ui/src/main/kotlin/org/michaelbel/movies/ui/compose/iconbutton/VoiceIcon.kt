package org.michaelbel.movies.ui.compose.iconbutton

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun VoiceIcon(
    onInputText: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val speechRecognizeContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        val data: Intent? = activityResult.data
        val spokenText: String? = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { results ->
            results[0]
        }
        onInputText(spokenText.orEmpty())
    }

    val onStartSpeechRecognize: () -> Unit = {
        Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }.also { intent: Intent ->
            speechRecognizeContract.launch(intent)
        }
    }

    IconButton(
        onClick = onStartSpeechRecognize,
        modifier = modifier
    ) {
        Image(
            imageVector = MoviesIcons.KeyboardVoice,
            contentDescription = stringResource(MoviesContentDescription.VoiceIcon),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Composable
@DevicePreviews
private fun VoiceIconPreview() {
    MoviesTheme {
        VoiceIcon(
            onInputText = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun VoiceIconAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        VoiceIcon(
            onInputText = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}