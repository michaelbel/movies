@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.detailsweb

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.koin.compose.koinInject
import org.michaelbel.movies.domain.usecase.MovieDetailsUseCase
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.config.formatBackdropImage
import org.michaelbel.movies.network.config.formatPosterImage
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

@Composable
fun DetailsWebScreen(
    movieId: MovieId,
    pagingKey: PagingKey,
    onBackClick: () -> Unit,
    interactor: Interactor = koinInject(),
    movieDetailsUseCase: MovieDetailsUseCase = koinInject()
) {
    val state by produceState<DetailsWebState>(
        initialValue = DetailsWebState.Loading,
        key1 = interactor,
        key2 = movieId,
        key3 = pagingKey
    ) {
        val params = MovieDetailsUseCase.Params(
            pagingKey = pagingKey,
            language = interactor.language,
            movieId = movieId
        )
        value = movieDetailsUseCase(params).fold(
            onSuccess = { movie -> DetailsWebState.Ready(movie = movie) },
            onFailure = { throwable -> DetailsWebState.Error(throwable.toUserMessage()) }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when (val currentState = state) {
            DetailsWebState.Loading -> {
                DetailsContent(
                    movie = MoviePojo.Empty,
                    placeholder = true,
                    onBackClick = onBackClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is DetailsWebState.Ready -> {
                DetailsContent(
                    movie = currentState.movie,
                    onBackClick = onBackClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is DetailsWebState.Error -> {
                ErrorContent(
                    message = currentState.message,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun DetailsContent(
    movie: MoviePojo,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false,
    onBackClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val isHorizontal = maxWidth >= maxHeight
        val imageData = when {
            placeholder -> null
            isHorizontal && movie.backdropPath.isNotBlank() -> movie.backdropPath.formatBackdropImage
            !isHorizontal && movie.posterPath.isNotBlank() -> movie.posterPath.formatPosterImage
            movie.backdropPath.isNotBlank() -> movie.backdropPath.formatBackdropImage
            movie.posterPath.isNotBlank() -> movie.posterPath.formatPosterImage
            else -> null
        }
        val imageRequest: ImageRequest? = imageData?.let { data ->
            ImageRequest.Builder(LocalPlatformContext.current)
                .data(data)
                .crossfade(true)
                .build()
        }
        val imagePainter = ColorPainter(MaterialTheme.colorScheme.primaryContainer)

        AsyncImage(
            model = imageRequest,
            contentDescription = movie.title,
            placeholder = imagePainter,
            error = imagePainter,
            fallback = imagePainter,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.34F))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.62F),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.76F)
                        )
                    )
                )
        )

        TopAppBar(
            title = {
                Text(
                    text = movie.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White
            ),
            windowInsets = WindowInsets(0, 0, 0, 0)
        )

        SelectionContainer(
            modifier = Modifier
                .align(Alignment.TopStart)
                .widthIn(max = 860.dp)
                .fillMaxWidth()
                .padding(
                    start = 18.dp,
                    top = 88.dp,
                    end = 32.dp,
                    bottom = 40.dp
                )
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = movie.overview.ifBlank { "Overview is not available." },
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        lineHeight = MaterialTheme.typography.headlineSmall.lineHeight * 1.15F
                    )
                )
            }
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary
            )
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(24.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

private fun Throwable.toUserMessage(): String {
    return message.orEmpty().ifBlank { "Movie details are unavailable." }
}

private sealed interface DetailsWebState {
    data object Loading: DetailsWebState
    data class Ready(
        val movie: MoviePojo
    ): DetailsWebState
    data class Error(
        val message: String
    ): DetailsWebState
}
