package org.michaelbel.movies.details.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import org.michaelbel.movies.ui.MoviesTheme

@Composable
fun DetailsToolbar(
    modifier: Modifier = Modifier,
    navController: NavController,
    movieTitle: String
) {
    SmallTopAppBar(
        title = {
            Text(
                text = movieTitle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailsToolbarPreview() {
    MoviesTheme {
        DetailsToolbar(
            modifier = Modifier
                .statusBarsPadding(),
            navController = NavController(LocalContext.current),
            movieTitle = "How to train your dragon"
        )
    }
}