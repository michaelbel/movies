package org.michaelbel.movies.feed.ui

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.feed_impl.R
import org.michaelbel.movies.ui.icon.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun FeedFailure(
    modifier: Modifier = Modifier,
    onCheckConnectivityClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, text, button) = createRefs()

        Icon(
            imageVector = MoviesIcons.Info,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(image) {
                    width = Dimension.value(36.dp)
                    height = Dimension.value(36.dp)
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 8.dp)
                },
            tint = MaterialTheme.colorScheme.error
        )

        Text(
            text = stringResource(R.string.feed_error_loading),
            modifier = Modifier
                .constrainAs(text) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(image.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                },
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )

        if (Build.VERSION.SDK_INT >= 29) {
            OutlinedButton(
                onClick = onCheckConnectivityClick,
                modifier = Modifier
                    .constrainAs(button) {
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(text.bottom, 8.dp)
                        end.linkTo(parent.end, 16.dp)
                    }
            ) {
                Text(
                    text = stringResource(R.string.feed_error_check_internet_connectivity)
                )
            }
        }
    }
}

@Composable
@DevicePreviews
private fun FeedFailurePreview() {
    MoviesTheme {
        FeedFailure(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            onCheckConnectivityClick = {}
        )
    }
}