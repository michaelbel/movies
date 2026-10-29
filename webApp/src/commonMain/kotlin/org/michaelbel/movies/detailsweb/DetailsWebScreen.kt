@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)

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
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.michaelbel.movies.domain.usecase.AccountPojoFlowUseCase
import org.michaelbel.movies.domain.usecase.MovieDetailsUseCase
import org.michaelbel.movies.domain.usecase.MovieFlowUseCase
import org.michaelbel.movies.domain.usecase.UpdateFavoriteUseCase
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.network.config.formatBackdropImage
import org.michaelbel.movies.network.config.formatPosterImage
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.isNotEmpty
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
fun DetailsWebScreen(
    movieId: MovieId,
    pagingKey: PagingKey,
    onBackClick: () -> Unit,
    onAuthRequired: () -> Unit,
    onFavoriteError: (String) -> Unit,
    localeInteractor: LocaleInteractor = koinInject(),
    uiInteractor: UiInteractor = koinInject(),
    movieDetailsUseCase: MovieDetailsUseCase = koinInject(),
    accountPojoFlowUseCase: AccountPojoFlowUseCase = koinInject(),
    movieFlowUseCase: MovieFlowUseCase = koinInject(),
    updateFavoriteUseCase: UpdateFavoriteUseCase = koinInject()
) {
    val scope = rememberCoroutineScope()
    val accountPojo by remember(accountPojoFlowUseCase) {
        accountPojoFlowUseCase(Unit)
    }.collectAsState(initial = AccountPojo.Empty)
    val favoriteMovie by remember(movieFlowUseCase, movieId) {
        movieFlowUseCase(
            MovieFlowUseCase.Params(
                pagingKey = Movie.FAVORITE,
                movieId = movieId
            )
        )
    }.collectAsState(initial = null)
    var favoriteJob by remember(movieId) { mutableStateOf<Job?>(null) }

    val isAuthorized = accountPojo.isNotEmpty
    val isFavorite = isAuthorized && favoriteMovie != null
    val isFavoriteJobActive = favoriteJob != null
    val onFavoriteClick: () -> Unit = {
        when {
            !isAuthorized -> {
                onAuthRequired()
            }
            !isFavoriteJobActive -> {
                val params = UpdateFavoriteUseCase.Params(
                    movieId = movieId,
                    favorite = favoriteMovie == null
                )
                val job = scope.launch {
                    try {
                        updateFavoriteUseCase(params).getOrThrow()
                    } catch (exception: CancellationException) {
                        throw exception
                    } catch (exception: Exception) {
                        onFavoriteError(exception.message.orEmpty().ifBlank { "Failed to update favorites." })
                    }
                }
                favoriteJob = job
                job.invokeOnCompletion { favoriteJob = null }
            }
        }
    }

    val state by produceState<DetailsWebState>(
        initialValue = DetailsWebState.Loading,
        key1 = localeInteractor,
        key2 = movieId,
        key3 = pagingKey
    ) {
        val params = MovieDetailsUseCase.Params(
            pagingKey = pagingKey,
            language = localeInteractor.language,
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
                    isFavoriteVisible = uiInteractor.isDetailsFavoriteFeatureEnabled,
                    isFavorite = isFavorite,
                    isFavoriteJobActive = isFavoriteJobActive,
                    onFavoriteClick = onFavoriteClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is DetailsWebState.Ready -> {
                DetailsContent(
                    movie = currentState.movie,
                    onBackClick = onBackClick,
                    isFavoriteVisible = uiInteractor.isDetailsFavoriteFeatureEnabled,
                    isFavorite = isFavorite,
                    isFavoriteJobActive = isFavoriteJobActive,
                    onFavoriteClick = onFavoriteClick,
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
    onBackClick: () -> Unit,
    isFavoriteVisible: Boolean,
    isFavorite: Boolean,
    isFavoriteJobActive: Boolean,
    onFavoriteClick: () -> Unit
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
            actions = {
                if (isFavoriteVisible) {
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                            positioning = TooltipAnchorPosition.Below
                        ),
                        tooltip = {
                            PlainTooltip {
                                Text(
                                    text = if (isFavorite) "Remove from favorites" else "Add to favorites"
                                )
                            }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(
                            onClick = onFavoriteClick,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .size(IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform))
                                .pointerHoverIcon(PointerIcon.Hand),
                            enabled = !isFavoriteJobActive,
                            shape = IconButtonDefaults.extraSmallSquareShape
                        ) {
                            Icon(
                                imageVector = if (isFavorite) MoviesIcons.Favorite else MoviesIcons.FavoriteBorder,
                                contentDescription = "Favorite",
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                tint = Color.White
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
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
