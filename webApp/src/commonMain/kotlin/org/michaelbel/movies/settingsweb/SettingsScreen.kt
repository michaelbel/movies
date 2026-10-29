@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)

package org.michaelbel.movies.settingsweb

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberTooltipState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.michaelbel.movies.common.MOVIES_GITHUB_URL
import org.michaelbel.movies.common.MOVIES_TELEGRAM_URL
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.domain.usecase.CurrentFeedViewFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentMovieListFlowUseCase
import org.michaelbel.movies.domain.usecase.ResetSettingsUseCase
import org.michaelbel.movies.domain.usecase.SelectFeedViewUseCase
import org.michaelbel.movies.domain.usecase.SelectMovieListUseCase
import org.michaelbel.movies.domain.usecase.SelectThemeUseCase
import org.michaelbel.movies.domain.usecase.SetPaletteColorsUseCase
import org.michaelbel.movies.domain.usecase.SetPaletteKeyUseCase
import org.michaelbel.movies.domain.usecase.SetSeedColorUseCase
import org.michaelbel.movies.domain.usecase.ThemeDataFlowUseCase
import org.michaelbel.movies.interactor.AboutInteractor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.settingsweb.ktx.stringText
import org.michaelbel.movies.settingsweb.ui.AboutItem
import org.michaelbel.movies.settingsweb.ui.SettingsDialog
import org.michaelbel.movies.settingsweb.ui.SettingsPaletteColorsBox
import org.michaelbel.movies.settingsweb.ui.SettingsResetDialog
import org.michaelbel.movies.ui.clickableWithoutRipple
import org.michaelbel.movies.ui.compose.plus
import org.michaelbel.movies.ui.icons.Github
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.icons.SettingsReset
import org.michaelbel.movies.ui.icons.Telegram
import org.michaelbel.movies.ui.icons.ThemeLightDark

@Composable
fun SettingsScreen(
    uiInteractor: UiInteractor = koinInject(),
    aboutInteractor: AboutInteractor = koinInject(),
    themeDataFlowUseCase: ThemeDataFlowUseCase = koinInject(),
    currentFeedViewFlowUseCase: CurrentFeedViewFlowUseCase = koinInject(),
    currentMovieListFlowUseCase: CurrentMovieListFlowUseCase = koinInject(),
    selectThemeUseCase: SelectThemeUseCase = koinInject(),
    selectFeedViewUseCase: SelectFeedViewUseCase = koinInject(),
    selectMovieListUseCase: SelectMovieListUseCase = koinInject(),
    setPaletteColorsUseCase: SetPaletteColorsUseCase = koinInject(),
    setPaletteKeyUseCase: SetPaletteKeyUseCase = koinInject(),
    setSeedColorUseCase: SetSeedColorUseCase = koinInject(),
    resetSettingsUseCase: ResetSettingsUseCase = koinInject()
) {
    val themeData by remember(themeDataFlowUseCase, uiInteractor) {
        themeDataFlowUseCase(uiInteractor.defaultDynamicColorsEnabled)
    }.collectAsState(initial = ThemeData.Default)
    val feedView by remember(currentFeedViewFlowUseCase) {
        currentFeedViewFlowUseCase(Unit)
    }.collectAsState(initial = FeedView.FeedList)
    val movieList by remember(currentMovieListFlowUseCase) {
        currentMovieListFlowUseCase(Unit)
    }.collectAsState(initial = MovieList.NowPlaying())
    val scope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current

    SettingsScreenContent(
        modifier = Modifier.fillMaxSize(),
        themeData = themeData,
        feedView = feedView,
        movieList = movieList,
        versionName = aboutInteractor.versionName,
        versionCode = aboutInteractor.versionCode,
        isThemeFeatureEnabled = uiInteractor.isThemeFeatureEnabled,
        isPaletteColorsFeatureEnabled = uiInteractor.isPaletteColorsFeatureEnabled,
        isFeedViewFeatureEnabled = uiInteractor.isFeedViewFeatureEnabled,
        isMovieListFeatureEnabled = uiInteractor.isMovieListFeatureEnabled,
        isGithubFeatureEnabled = uiInteractor.isGithubFeatureEnabled,
        isTelegramFeatureEnabled = uiInteractor.isTelegramFeatureEnabled,
        isAboutFeatureEnabled = uiInteractor.isAboutFeatureEnabled,
        isSettingsResetFeatureEnabled = uiInteractor.isSettingsResetFeatureEnabled,
        onSelectTheme = { theme ->
            scope.launch { selectThemeUseCase(theme).getOrThrow() }
        },
        onSetPaletteColors = { enabled ->
            scope.launch { setPaletteColorsUseCase(enabled).getOrThrow() }
        },
        onSetPalette = { paletteKey, seedColor ->
            scope.launch {
                setPaletteKeyUseCase(paletteKey).getOrThrow()
                setSeedColorUseCase(seedColor).getOrThrow()
            }
        },
        onSelectFeedView = { localFeedView ->
            scope.launch { selectFeedViewUseCase(localFeedView).getOrThrow() }
        },
        onSelectMovieList = { localMovieList ->
            scope.launch { selectMovieListUseCase(localMovieList).getOrThrow() }
        },
        onGithubClick = { uriHandler.openUri(MOVIES_GITHUB_URL) },
        onTelegramClick = { uriHandler.openUri(MOVIES_TELEGRAM_URL) },
        onResetSettings = {
            scope.launch { resetSettingsUseCase(Unit).getOrThrow() }
        }
    )
}

@Composable
private fun SettingsScreenContent(
    modifier: Modifier,
    themeData: ThemeData,
    feedView: FeedView,
    movieList: MovieList,
    versionName: String,
    versionCode: Long,
    isThemeFeatureEnabled: Boolean,
    isPaletteColorsFeatureEnabled: Boolean,
    isFeedViewFeatureEnabled: Boolean,
    isMovieListFeatureEnabled: Boolean,
    isGithubFeatureEnabled: Boolean,
    isTelegramFeatureEnabled: Boolean,
    isAboutFeatureEnabled: Boolean,
    isSettingsResetFeatureEnabled: Boolean,
    onSelectTheme: (AppTheme) -> Unit,
    onSetPaletteColors: (Boolean) -> Unit,
    onSetPalette: (Int, Int) -> Unit,
    onSelectFeedView: (FeedView) -> Unit,
    onSelectMovieList: (MovieList) -> Unit,
    onGithubClick: () -> Unit,
    onTelegramClick: () -> Unit,
    onResetSettings: () -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        canScroll = { true }
    )
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier
            .fillMaxWidth()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            LargeFlexibleTopAppBar(
                title = {
                    Text(
                        text = "Settings"
                    )
                },
                modifier = Modifier.clickableWithoutRipple {
                    scope.launch { listState.animateScrollToItem(0) }
                },
                actions = {
                    if (isSettingsResetFeatureEnabled) {
                        TooltipBox(
                            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                                positioning = TooltipAnchorPosition.Below
                            ),
                            tooltip = {
                                PlainTooltip {
                                    Text(
                                        text = "Reset settings"
                                    )
                                }
                            },
                            state = rememberTooltipState()
                        ) {
                            var resetSettingsDialog by remember { mutableStateOf(false) }
                            if (resetSettingsDialog) {
                                SettingsResetDialog(
                                    onDismissRequest = { resetSettingsDialog = false },
                                    onResetClick = {
                                        onResetSettings()
                                        resetSettingsDialog = false
                                    }
                                )
                            }

                            IconButton(
                                onClick = { resetSettingsDialog = true },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .size(IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform)),
                                shape = IconButtonDefaults.extraSmallSquareShape
                            ) {
                                Icon(
                                    imageVector = MoviesIcons.SettingsReset,
                                    contentDescription = "Reset settings",
                                    modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
                ),
                windowInsets = WindowInsets(0, 0, 0, 0),
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Horizontal)
    ) { innerPadding ->
        val appearanceVisible = listOf(
            isThemeFeatureEnabled,
            isPaletteColorsFeatureEnabled
        )
        val appearanceCount = appearanceVisible.count { it }
        val feedVisible = listOf(
            isMovieListFeatureEnabled,
            isFeedViewFeatureEnabled
        )
        val feedCount = feedVisible.count { it }
        val aboutVisible = listOf(
            isGithubFeatureEnabled,
            isTelegramFeatureEnabled
        )
        val aboutCount = aboutVisible.count { it }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = innerPadding + PaddingValues(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
        ) {
            if (isThemeFeatureEnabled) {
                item {
                    var themeDialog by remember { mutableStateOf(false) }
                    if (themeDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.ThemeLightDark,
                            title = "Theme",
                            items = AppTheme.VALUES,
                            currentItem = themeData.appTheme,
                            onItemSelect = onSelectTheme,
                            onDismissRequest = { themeDialog = false }
                        )
                    }

                    SegmentedListItem(
                        onClick = { themeDialog = true },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = 0,
                            count = appearanceCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.ThemeLightDark,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = themeData.appTheme.stringText,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        colors = ListItemDefaults.segmentedColors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            supportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            trailingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = "Theme",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (isPaletteColorsFeatureEnabled) {
                item {
                    val itemShapes = ListItemDefaults.segmentedShapes(
                        index = appearanceVisible.take(1).count { it },
                        count = appearanceCount
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(itemShapes.shape)
                            .background(MaterialTheme.colorScheme.inversePrimary)
                    ) {
                        SegmentedListItem(
                            onClick = { onSetPaletteColors(!themeData.paletteColors) },
                            shapes = itemShapes,
                            leadingContent = {
                                Icon(
                                    imageVector = MoviesIcons.Palette,
                                    contentDescription = null,
                                    modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                                )
                            },
                            trailingContent = {
                                Switch(
                                    checked = themeData.paletteColors,
                                    onCheckedChange = null,
                                    thumbContent = if (themeData.paletteColors) {
                                        {
                                            Icon(
                                                imageVector = MoviesIcons.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(SwitchDefaults.IconSize)
                                            )
                                        }
                                    } else null
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = "Apply colors from Palette",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = MaterialTheme.colorScheme.inversePrimary,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                supportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                trailingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        ) {
                            Text(
                                text = "Palette Colors",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        AnimatedVisibility(
                            visible = themeData.paletteColors,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            SettingsPaletteColorsBox(
                                paletteKey = themeData.paletteKey,
                                seedColor = themeData.seedColor,
                                onChange = onSetPalette
                            )
                        }
                    }
                }
            }
            if (appearanceCount > 0 && feedCount > 0) {
                item {
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
            if (isMovieListFeatureEnabled) {
                item {
                    var movieListDialog by remember { mutableStateOf(false) }
                    if (movieListDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.LocalMovies,
                            title = "List",
                            items = MovieList.VALUES,
                            currentItem = movieList,
                            onItemSelect = onSelectMovieList,
                            onDismissRequest = { movieListDialog = false }
                        )
                    }

                    SegmentedListItem(
                        onClick = { movieListDialog = true },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = 0,
                            count = feedCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.LocalMovies,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = movieList.stringText,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        colors = ListItemDefaults.segmentedColors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            supportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            trailingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = "List",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (isFeedViewFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = {},
                        shapes = ListItemDefaults.segmentedShapes(
                            index = feedVisible.take(1).count { it },
                            count = feedCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.GridView,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)
                            ) {
                                ToggleButton(
                                    checked = feedView == FeedView.FeedList,
                                    onCheckedChange = { onSelectFeedView(FeedView.FeedList) },
                                    modifier = Modifier
                                        .weight(1F)
                                        .height(40.dp),
                                    shapes = ButtonGroupDefaults.connectedLeadingButtonShapes()
                                ) {
                                    Icon(
                                        imageVector = MoviesIcons.ViewAgenda,
                                        contentDescription = null,
                                        modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                                    )

                                    Spacer(
                                        modifier = Modifier.size(ToggleButtonDefaults.IconSpacing)
                                    )

                                    Text(
                                        text = "List"
                                    )
                                }

                                ToggleButton(
                                    checked = feedView == FeedView.FeedGrid,
                                    onCheckedChange = { onSelectFeedView(FeedView.FeedGrid) },
                                    modifier = Modifier
                                        .weight(1F)
                                        .height(40.dp),
                                    shapes = ButtonGroupDefaults.connectedTrailingButtonShapes()
                                ) {
                                    Icon(
                                        imageVector = MoviesIcons.Dashboard,
                                        contentDescription = null,
                                        modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                                    )

                                    Spacer(
                                        modifier = Modifier.size(ToggleButtonDefaults.IconSpacing)
                                    )

                                    Text(
                                        text = "Grid"
                                    )
                                }
                            }
                        },
                        colors = ListItemDefaults.segmentedColors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            supportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = "Appearance",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (appearanceCount + feedCount > 0 && (aboutCount > 0 || isAboutFeatureEnabled)) {
                item {
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
            if (isGithubFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = onGithubClick,
                        shapes = ListItemDefaults.segmentedShapes(
                            index = 0,
                            count = aboutCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.Github,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "Check the Repository",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        colors = ListItemDefaults.segmentedColors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            supportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            trailingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = "GitHub",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (isTelegramFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = onTelegramClick,
                        shapes = ListItemDefaults.segmentedShapes(
                            index = aboutVisible.take(1).count { it },
                            count = aboutCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.Telegram,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "Subscribe to Сhannel",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        colors = ListItemDefaults.segmentedColors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            supportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            trailingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = "Telegram",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (isAboutFeatureEnabled) {
                item {
                    AboutItem(
                        versionName = versionName,
                        versionCode = versionCode
                    )
                }
            }
        }
    }
}
