@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)

package org.michaelbel.movies.settingsweb

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.LocalMovies
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TopAppBarDefaults
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
import org.michaelbel.movies.domain.usecase.SelectFeedViewUseCase
import org.michaelbel.movies.domain.usecase.SelectMovieListUseCase
import org.michaelbel.movies.domain.usecase.SelectThemeUseCase
import org.michaelbel.movies.domain.usecase.ThemeDataFlowUseCase
import org.michaelbel.movies.interactor.AboutInteractor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.settingsweb.ktx.stringText
import org.michaelbel.movies.settingsweb.ui.AboutItem
import org.michaelbel.movies.settingsweb.ui.SettingsDialog
import org.michaelbel.movies.settingsweb.ui.settingsListItemShape
import org.michaelbel.movies.ui.clickableWithoutRipple
import org.michaelbel.movies.ui.compose.plus

@Composable
fun SettingsScreen(
    uiInteractor: UiInteractor = koinInject(),
    aboutInteractor: AboutInteractor = koinInject(),
    themeDataFlowUseCase: ThemeDataFlowUseCase = koinInject(),
    currentFeedViewFlowUseCase: CurrentFeedViewFlowUseCase = koinInject(),
    currentMovieListFlowUseCase: CurrentMovieListFlowUseCase = koinInject(),
    selectThemeUseCase: SelectThemeUseCase = koinInject(),
    selectFeedViewUseCase: SelectFeedViewUseCase = koinInject(),
    selectMovieListUseCase: SelectMovieListUseCase = koinInject()
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
        isFeedViewFeatureEnabled = uiInteractor.isFeedViewFeatureEnabled,
        isMovieListFeatureEnabled = uiInteractor.isMovieListFeatureEnabled,
        isGithubFeatureEnabled = uiInteractor.isGithubFeatureEnabled,
        isTelegramFeatureEnabled = uiInteractor.isTelegramFeatureEnabled,
        isAboutFeatureEnabled = uiInteractor.isAboutFeatureEnabled,
        onSelectTheme = { theme ->
            scope.launch { selectThemeUseCase(theme).getOrThrow() }
        },
        onSelectFeedView = { localFeedView ->
            scope.launch { selectFeedViewUseCase(localFeedView).getOrThrow() }
        },
        onSelectMovieList = { localMovieList ->
            scope.launch { selectMovieListUseCase(localMovieList).getOrThrow() }
        },
        onGithubClick = { uriHandler.openUri(MOVIES_GITHUB_URL) },
        onTelegramClick = { uriHandler.openUri(MOVIES_TELEGRAM_URL) }
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
    isFeedViewFeatureEnabled: Boolean,
    isMovieListFeatureEnabled: Boolean,
    isGithubFeatureEnabled: Boolean,
    isTelegramFeatureEnabled: Boolean,
    isAboutFeatureEnabled: Boolean,
    onSelectTheme: (AppTheme) -> Unit,
    onSelectFeedView: (FeedView) -> Unit,
    onSelectMovieList: (MovieList) -> Unit,
    onGithubClick: () -> Unit,
    onTelegramClick: () -> Unit
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = innerPadding + PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if (isThemeFeatureEnabled) {
                item {
                    var themeDialog by remember { mutableStateOf(false) }
                    if (themeDialog) {
                        SettingsDialog(
                            icon = Icons.Outlined.Palette,
                            title = "Theme",
                            items = AppTheme.VALUES,
                            currentItem = themeData.appTheme,
                            onItemSelect = onSelectTheme,
                            onDismissRequest = { themeDialog = false }
                        )
                    }

                    ListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                settingsListItemShape(
                                    isFirst = true,
                                    isLast = !isMovieListFeatureEnabled &&
                                        !isFeedViewFeatureEnabled &&
                                        !isGithubFeatureEnabled &&
                                        !isTelegramFeatureEnabled
                                )
                            )
                            .clickable { themeDialog = true },
                        headlineContent = {
                            Text(
                                text = "Theme",
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        supportingContent = {
                            Text(
                                text = themeData.appTheme.stringText,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Palette,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary
                        )
                    )
                }
            }
            if (isMovieListFeatureEnabled) {
                item {
                    var movieListDialog by remember { mutableStateOf(false) }
                    if (movieListDialog) {
                        SettingsDialog(
                            icon = Icons.Outlined.LocalMovies,
                            title = "List",
                            items = MovieList.VALUES,
                            currentItem = movieList,
                            onItemSelect = onSelectMovieList,
                            onDismissRequest = { movieListDialog = false }
                        )
                    }

                    ListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                settingsListItemShape(
                                    isFirst = !isThemeFeatureEnabled,
                                    isLast = !isFeedViewFeatureEnabled &&
                                        !isGithubFeatureEnabled &&
                                        !isTelegramFeatureEnabled
                                )
                            )
                            .clickable { movieListDialog = true },
                        headlineContent = {
                            Text(
                                text = "List",
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        supportingContent = {
                            Text(
                                text = movieList.stringText,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.LocalMovies,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary
                        ),
                    )
                }
            }
            if (isFeedViewFeatureEnabled) {
                item {
                    ListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                settingsListItemShape(
                                    isFirst = !isThemeFeatureEnabled &&
                                        !isMovieListFeatureEnabled,
                                    isLast = !isGithubFeatureEnabled &&
                                        !isTelegramFeatureEnabled
                                )
                            ),

                        headlineContent = {
                            Text(
                                text = "Appearance",
                                style = MaterialTheme.typography.titleLarge
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
                                        imageVector = Icons.Outlined.ViewAgenda,
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
                                        imageVector = Icons.Outlined.Dashboard,
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
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.GridView,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary
                        )
                    )
                }
            }
            if (isGithubFeatureEnabled) {
                item {
                    ListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                settingsListItemShape(
                                    isFirst = !isThemeFeatureEnabled &&
                                        !isMovieListFeatureEnabled &&
                                        !isFeedViewFeatureEnabled,
                                    isLast = !isTelegramFeatureEnabled
                                )
                            )
                            .clickable(onClick = onGithubClick),
                        headlineContent = {
                            Text(
                                text = "GitHub",
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "Check the Repository",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Outlined.Code,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary
                        )
                    )
                }
            }
            if (isTelegramFeatureEnabled) {
                item {
                    ListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                settingsListItemShape(
                                    isFirst = !isThemeFeatureEnabled &&
                                        !isMovieListFeatureEnabled &&
                                        !isFeedViewFeatureEnabled &&
                                        !isGithubFeatureEnabled,
                                    isLast = true
                                )
                            )
                            .clickable(onClick = onTelegramClick),
                        headlineContent = {
                            Text(
                                text = "Telegram",
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "Subscribe to Сhannel",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Send,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary
                        )
                    )
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
