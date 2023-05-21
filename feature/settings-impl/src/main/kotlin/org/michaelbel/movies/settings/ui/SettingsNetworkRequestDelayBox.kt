package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlin.math.roundToInt
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.SliderPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsNetworkRequestDelayBox(
    delay: Int,
    onDelayChangeFinished: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var sliderPosition: Float by remember { mutableStateOf(delay.toFloat()) }
    sliderPosition = delay.toFloat()

    ConstraintLayout(
        modifier = modifier.testTag("ConstraintLayout")
    ) {
        val (title, value, slider) = createRefs()

        Text(
            text = stringResource(R.string.settings_network_request_delay),
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 8.dp)
                }
                .testTag("TitleText"),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        Text(
            text = stringResource(R.string.settings_network_request_delay_ms, sliderPosition.roundToInt()),
            modifier = Modifier
                .constrainAs(value) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .testTag("ValueText"),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )

        Slider(
            value = sliderPosition,
            onValueChange = { position ->
                sliderPosition = position
            },
            modifier = Modifier
                .constrainAs(slider) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(title.bottom, 4.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                }
                .testTag("Slider"),
            valueRange = 0F..10000F,
            steps = 9,
            onValueChangeFinished = {
                onDelayChangeFinished(sliderPosition.roundToInt())
            },
            colors = SliderDefaults.colors(
                inactiveTrackColor = MaterialTheme.colorScheme.outline,
                inactiveTickColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsNetworkRequestDelayBoxPreview(
    @PreviewParameter(SliderPreviewParameterProvider::class) delay: Int
) {
    MoviesTheme {
        SettingsNetworkRequestDelayBox(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            delay = delay,
            onDelayChangeFinished = {}
        )
    }
}