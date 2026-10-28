@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.michaelbel.movies.details.intent.DetailsIntent
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.details.ktx.movieUrl
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.details.model.DetailsModel
import org.michaelbel.movies.details.preview.DetailsModelPreviewParameterProvider
import org.michaelbel.movies.details.ui.DetailsContent
import org.michaelbel.movies.details.ui.DetailsFailure
import org.michaelbel.movies.network.config.ScreenState
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.navigation.DetailsDestination
import org.michaelbel.movies.ui.preview.wrapper.ThemeWrapper
import org.michaelbel.movies.ui.shareText
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
fun DetailsScreen(
    destination: DetailsDestination,
    viewModel: DetailsViewModel = koinViewModel { parametersOf(destination) }
) {
    val state by viewModel.stateFlow.collectAsStateCommon()

    DetailsScreenContent(
        state = state,
        dispatch = viewModel::dispatch
    )
}

@Composable
private fun DetailsScreenContent(
    state: DetailsModel,
    dispatch: (DetailsIntent) -> Unit
) {
    val exitUntilCollapsedScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val pinnedScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val onContainerColor = MaterialTheme.colorScheme.onPrimaryContainer

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val isWide = maxWidth >= 840.dp
        val topAppBarScrollBehavior = when {
            isWide -> pinnedScrollBehavior
            else -> exitUntilCollapsedScrollBehavior
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            topBar = {
                when {
                    isWide -> {
                        TopAppBar(
                            title = {
                                Text(
                                    text = state.detailsState.toolbarTitle
                                )
                            },
                            navigationIcon = {
                                IconButton(
                                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                    onClick = { dispatch(DetailsIntent.BackClick) }
                                ) {
                                    Image(
                                        imageVector = MoviesIcons.ArrowBack,
                                        contentDescription = stringResource(MoviesContentDescription.BackIcon),
                                        modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                        colorFilter = ColorFilter.tint(onContainerColor)
                                    )
                                }
                            },
                            actions = {
                                AnimatedVisibility(
                                    visible = state.isDetailsFavoriteFeatureEnabled,
                                    enter = fadeIn()
                                ) {
                                    TooltipBox(
                                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                                            positioning = TooltipAnchorPosition.Below
                                        ),
                                        tooltip = {
                                            PlainTooltip {
                                                Text(
                                                    text = stringResource(if (state.isAuthorized && state.isFavorite) MoviesStrings.remove_from_favorites else MoviesStrings.add_to_favorites)
                                                )
                                            }
                                        },
                                        state = rememberTooltipState()
                                    ) {
                                        IconButton(
                                            onClick = { dispatch(DetailsIntent.FavoriteClick) },
                                            enabled = !state.isFavoriteJobActive,
                                            modifier = Modifier
                                                .minimumInteractiveComponentSize()
                                                .size(IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform))
                                                .pointerHoverIcon(PointerIcon.Hand),
                                            shape = IconButtonDefaults.extraSmallSquareShape
                                        ) {
                                            Image(
                                                imageVector = if (state.isAuthorized && state.isFavorite) MoviesIcons.Favorite else MoviesIcons.FavoriteBorder,
                                                contentDescription = stringResource(MoviesContentDescription.FavoriteIcon),
                                                modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                                colorFilter = ColorFilter.tint(onContainerColor)
                                            )
                                        }
                                    }
                                }

                                AnimatedVisibility(
                                    visible = state.isDetailsShareFeatureEnabled && state.detailsState.movieUrl != null,
                                    enter = fadeIn()
                                ) {
                                    state.detailsState.movieUrl?.let { url ->
                                        TooltipBox(
                                            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                                                positioning = TooltipAnchorPosition.Below
                                            ),
                                            tooltip = {
                                                PlainTooltip {
                                                    Text(
                                                        text = stringResource(MoviesStrings.share)
                                                    )
                                                }
                                            },
                                            state = rememberTooltipState()
                                        ) {
                                            IconButton(
                                                onClick = shareText(url, stringResource(MoviesStrings.share_via)),
                                                modifier = Modifier
                                                    .minimumInteractiveComponentSize()
                                                    .size(IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform)),
                                                shape = IconButtonDefaults.extraSmallSquareShape
                                            ) {
                                                Image(
                                                    imageVector = MoviesIcons.Share,
                                                    contentDescription = stringResource(MoviesContentDescription.ShareIcon),
                                                    modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                                    colorFilter = ColorFilter.tint(onContainerColor)
                                                )
                                            }
                                        }
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
                            ),
                            scrollBehavior = topAppBarScrollBehavior
                        )
                    }
                    else -> {
                        LargeTopAppBar(
                            title = {
                                Text(
                                    text = state.detailsState.toolbarTitle
                                )
                            },
                            actions = {
                                AnimatedVisibility(
                                    visible = state.isDetailsFavoriteFeatureEnabled,
                                    enter = fadeIn()
                                ) {
                                    TooltipBox(
                                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                                            positioning = TooltipAnchorPosition.Below
                                        ),
                                        tooltip = {
                                            PlainTooltip {
                                                Text(
                                                    text = stringResource(if (state.isAuthorized && state.isFavorite) MoviesStrings.remove_from_favorites else MoviesStrings.add_to_favorites)
                                                )
                                            }
                                        },
                                        state = rememberTooltipState()
                                    ) {
                                        IconButton(
                                            onClick = { dispatch(DetailsIntent.FavoriteClick) },
                                            enabled = !state.isFavoriteJobActive,
                                            modifier = Modifier
                                                .minimumInteractiveComponentSize()
                                                .size(IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform))
                                                .pointerHoverIcon(PointerIcon.Hand),
                                            shape = IconButtonDefaults.extraSmallSquareShape
                                        ) {
                                            Image(
                                                imageVector = if (state.isAuthorized && state.isFavorite) MoviesIcons.Favorite else MoviesIcons.FavoriteBorder,
                                                contentDescription = stringResource(MoviesContentDescription.FavoriteIcon),
                                                modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                                colorFilter = ColorFilter.tint(onContainerColor)
                                            )
                                        }
                                    }
                                }

                                AnimatedVisibility(
                                    visible = state.isDetailsShareFeatureEnabled && state.detailsState.movieUrl != null,
                                    enter = fadeIn()
                                ) {
                                    state.detailsState.movieUrl?.let { url ->
                                        TooltipBox(
                                            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                                                positioning = TooltipAnchorPosition.Below
                                            ),
                                            tooltip = {
                                                PlainTooltip {
                                                    Text(
                                                        text = stringResource(MoviesStrings.share)
                                                    )
                                                }
                                            },
                                            state = rememberTooltipState()
                                        ) {
                                            IconButton(
                                                onClick = shareText(url, stringResource(MoviesStrings.share_via)),
                                                modifier = Modifier
                                                    .minimumInteractiveComponentSize()
                                                    .size(IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform)),
                                                shape = IconButtonDefaults.extraSmallSquareShape
                                            ) {
                                                Image(
                                                    imageVector = MoviesIcons.Share,
                                                    contentDescription = stringResource(MoviesContentDescription.ShareIcon),
                                                    modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                                    colorFilter = ColorFilter.tint(onContainerColor)
                                                )
                                            }
                                        }
                                    }
                                }
                            },
                            navigationIcon = {
                                IconButton(
                                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                    onClick = { dispatch(DetailsIntent.BackClick) }
                                ) {
                                    Image(
                                        imageVector = MoviesIcons.ArrowBack,
                                        contentDescription = stringResource(MoviesContentDescription.BackIcon),
                                        modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                                        colorFilter = ColorFilter.tint(onContainerColor)
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
                            ),
                            scrollBehavior = topAppBarScrollBehavior
                        )
                    }
                }
            },
        ) { innerPadding ->
            when (val detailsState = state.detailsState) {
                is ScreenState.Loading -> {
                    DetailsContent(
                        movie = MoviePojo.Empty,
                        isWide = isWide,
                        placeholder = true,
                        contentPadding = innerPadding
                    )
                }
                is ScreenState.Content<*> -> {
                    DetailsContent(
                        contentPadding = innerPadding,
                        movie = detailsState.movie,
                        isWide = isWide,
                        isDetailsGalleryFeatureEnabled = state.isDetailsGalleryFeatureEnabled,
                        onNavigateToGallery = { dispatch(DetailsIntent.GalleryClick) }
                    )
                }
                is ScreenState.Failure -> {
                    DetailsFailure(
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }
}

@PreviewWrapper(ThemeWrapper::class)
@Preview
@Composable
private fun DetailsScreenContentPreview(
    @PreviewParameter(DetailsModelPreviewParameterProvider::class) state: DetailsModel
) {
    DetailsScreenContent(
        state = state,
        dispatch = {}
    )
}
