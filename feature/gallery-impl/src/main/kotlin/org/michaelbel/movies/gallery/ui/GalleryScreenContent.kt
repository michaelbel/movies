package org.michaelbel.movies.gallery.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.michaelbel.movies.gallery.GalleryViewModel
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.context

@Composable
fun GalleryRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val movieImage: String by viewModel.imageFlow.collectAsStateWithLifecycle()

    GalleryScreenContent(
        movieImage = movieImage,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun GalleryScreenContent(
    movieImage: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val (image, backIcon) = createRefs()

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(movieImage)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.constrainAs(image) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                contentScale = ContentScale.Fit
            )

            IconButton(
                onClick = onBackClick,
                modifier = Modifier.constrainAs(backIcon) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 4.dp)
                    top.linkTo(parent.top, 8.dp)
                }
            ) {
                Image(
                    imageVector = MoviesIcons.ArrowBack,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                )
            }
        }
    }
}