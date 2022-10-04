package org.michaelbel.movies.feed.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.feed.R
import org.michaelbel.movies.ui.MoviesTheme

@Composable
fun FeedFailure(
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, text) = createRefs()

        Icon(
            imageVector = Icons.Filled.Info,
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
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FeedFailurePreview() {
    MoviesTheme {
        FeedFailure(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}