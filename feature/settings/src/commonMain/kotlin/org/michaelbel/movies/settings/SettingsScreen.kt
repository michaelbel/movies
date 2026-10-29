@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)

package org.michaelbel.movies.settings

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
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.common.MOVIES_GITHUB_URL
import org.michaelbel.movies.common.MOVIES_GOOGLE_PLAY_URL
import org.michaelbel.movies.common.MOVIES_TELEGRAM_URL
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.browser.navigateToUrl
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.interactor.entity.AppLanguage
import org.michaelbel.movies.settings.event.SettingsEvent
import org.michaelbel.movies.settings.event.SettingsEventManager
import org.michaelbel.movies.settings.intent.SettingsIntent
import org.michaelbel.movies.settings.ktx.stringText
import org.michaelbel.movies.settings.model.SettingsModel
import org.michaelbel.movies.settings.ui.SettingsAppIconsBox
import org.michaelbel.movies.settings.ui.SettingsDialog
import org.michaelbel.movies.settings.ui.SettingsPaletteColorsBox
import org.michaelbel.movies.settings.ui.SettingsResetDialog
import org.michaelbel.movies.ui.ObserveAsEvents
import org.michaelbel.movies.ui.OnResume
import org.michaelbel.movies.ui.SettingsGenderText
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.clickableWithoutRipple
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.icons.Cat
import org.michaelbel.movies.ui.icons.FrameBug
import org.michaelbel.movies.ui.icons.Github
import org.michaelbel.movies.ui.icons.GooglePlay
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.icons.SettingsReset
import org.michaelbel.movies.ui.icons.StarShine
import org.michaelbel.movies.ui.icons.Telegram
import org.michaelbel.movies.ui.icons.ThemeLightDark
import org.michaelbel.movies.ui.icons.TileSmall
import org.michaelbel.movies.ui.isDebug
import org.michaelbel.movies.ui.isNavigationRail
import org.michaelbel.movies.ui.requestTileService
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.widget.ktx.rememberAndPinAppWidgetProvider

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    uiInteractor: UiInteractor = koinInject()
) {
    val state by viewModel.stateFlow.collectAsStateCommon()
    val openAppNotificationSettings = uiInteractor.navigateToAppNotificationSettings()
    val openDoNotDisturbSettings = uiInteractor.navigateToDoNotDisturbSettings()
    val openAppOpenByDefaultSettings = uiInteractor.navigateToAppOpenByDefaultSettings()
    val requestIgnoreBatteryOptimizations = uiInteractor.requestIgnoreBatteryOptimizations()
    val openBatteryOptimizationSettings = uiInteractor.navigateToBatteryOptimizationSettings()
    val navigateToGithubUrl = navigateToUrl(MOVIES_GITHUB_URL)
    val navigateToTelegramUrl = navigateToUrl(MOVIES_TELEGRAM_URL)
    val navigateToMoviemadeUrl = navigateToUrl(MOVIES_GOOGLE_PLAY_URL)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()
    val permissionMessage = stringResource(MoviesStrings.settings_post_notifications_should_request)
    val permissionAction = stringResource(MoviesStrings.settings_action_go)
    val onRequestPostNotificationsPermission = uiInteractor.rememberPostNotificationsPermissionHandler(
        enabled = state.areNotificationsEnabled,
        onPermissionGranted = { viewModel.dispatch(SettingsIntent.CollectNotificationsEnabled) },
        onPermissionDenied = { viewModel.dispatch(SettingsIntent.ShowPermissionSnackbar(permissionMessage, permissionAction)) }
    )
    val onRequestTileService = requestTileService { message -> viewModel.dispatch(SettingsIntent.ShowSnackbar(message)) }

    ObserveAsEvents(
        flow = viewModel.eventFlow,
        key1 = snackbarHostState,
        key2 = lazyListState
    ) { event ->
        when (event) {
            is SettingsEvent.PinWidget -> {}
            is SettingsEvent.RequestPostNotificationsPermission -> onRequestPostNotificationsPermission()
            is SettingsEvent.RequestDoNotDisturbAccess -> openDoNotDisturbSettings()
            is SettingsEvent.RequestIgnoreBatteryOptimizations -> {
                when {
                    state.isIgnoringBatteryOptimizations -> openBatteryOptimizationSettings()
                    else -> requestIgnoreBatteryOptimizations()
                }
            }
            is SettingsEvent.RequestTileService -> onRequestTileService()
            is SettingsEvent.RequestGithub -> navigateToGithubUrl()
            is SettingsEvent.RequestTelegram -> navigateToTelegramUrl()
            is SettingsEvent.RequestGooglePlay -> navigateToMoviemadeUrl()
            is SettingsEvent.ScrollToTop -> scope.launch { lazyListState.animateScrollToItem(0) }
            is SettingsEvent.ReselectSettings -> Unit
            is SettingsEvent.ShowSnackbar -> {
                snackbarHostState.currentSnackbarData?.dismiss()
                scope.launch { snackbarHostState.showSnackbar(message = event.message, duration = SnackbarDuration.Short) }
            }
            is SettingsEvent.ShowPermissionSnackbar -> {
                snackbarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    val result = snackbarHostState.showSnackbar(message = event.message, actionLabel = event.actionLabel, duration = SnackbarDuration.Long)
                    if (result == SnackbarResult.ActionPerformed) {
                        openAppNotificationSettings()
                    }
                }
            }
        }
    }

    ObserveAsEvents(flow = SettingsEventManager.eventFlow) { event ->
        when (event) {
            SettingsEvent.ReselectSettings -> viewModel.dispatch(SettingsIntent.ScrollToTop)
            else -> Unit
        }
    }

    SettingsScreenContent(
        state = state,
        dispatch = viewModel::dispatch,
        snackbarHostState = snackbarHostState,
        lazyListState = lazyListState,
        onNavigateToAppOpenByDefaultSettings = openAppOpenByDefaultSettings
    )

    OnResume {
        viewModel.dispatch(SettingsIntent.CollectNotificationsEnabled)
        viewModel.dispatch(SettingsIntent.CollectDoNotDisturbState)
        viewModel.dispatch(SettingsIntent.CollectIgnoringBatteryOptimizations)
    }
}

@Composable
private fun SettingsScreenContent(
    state: SettingsModel,
    dispatch: (SettingsIntent) -> Unit,
    snackbarHostState: SnackbarHostState,
    lazyListState: LazyListState,
    onNavigateToAppOpenByDefaultSettings: () -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        canScroll = { true }
    )
    val iconChangedMessages: Map<IconAlias, String> = when {
        state.isAppIconFeatureEnabled -> {
            IconAlias.VALUES.associateWith { iconAlias ->
                stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, iconAlias.title)
            }
        }
        else -> emptyMap()
    }
    val updateNotAvailableMessage = stringResource(MoviesStrings.settings_update_not_available)
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            LargeFlexibleTopAppBar(
                title = {
                    Text(
                        text = stringResource(MoviesStrings.settings_title)
                    )
                },
                modifier = Modifier.clickableWithoutRipple { dispatch(SettingsIntent.ScrollToTop) },
                actions = {
                    if (state.isSettingsResetFeatureEnabled) {
                        TooltipBox(
                            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                                positioning = TooltipAnchorPosition.Below
                            ),
                            tooltip = {
                                PlainTooltip {
                                    Text(
                                        text = stringResource(MoviesStrings.settings_reset)
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
                                        dispatch(SettingsIntent.ResetSettings)
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
                                    contentDescription = stringResource(MoviesContentDescription.ResetSettingsIcon),
                                    modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                                )
                            }
                        }
                    }
                    if (state.isDebugDialogFeatureEnabled) {
                        IconButton(
                            onClick = { dispatch(SettingsIntent.OpenDebugDialog) },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .size(IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform)),
                            shape = IconButtonDefaults.extraSmallSquareShape
                        ) {
                            Icon(
                                imageVector = MoviesIcons.FrameBug,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
                ),
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(
                    bottom = if (isNavigationRail) WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() else 0.dp
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Horizontal)
    ) { innerPadding ->
        val localizationVisible = listOf(
            state.isLanguageFeatureEnabled,
            state.isGenderFeatureEnabled
        )
        val localizationCount = localizationVisible.count { it }
        val appearanceVisible = listOf(
            state.isThemeFeatureEnabled,
            state.isDynamicColorsFeatureEnabled,
            state.isPaletteColorsFeatureEnabled,
            state.isAppIconFeatureEnabled
        )
        val appearanceCount = appearanceVisible.count { it }
        val feedVisible = listOf(
            state.isMovieListFeatureEnabled,
            state.isFeedViewFeatureEnabled
        )
        val feedCount = feedVisible.count { it }
        val systemVisible = listOf(
            state.isAppOpenByDefaultFeatureEnabled,
            state.isNotificationsFeatureEnabled,
            state.isDoNotDisturbFeatureEnabled,
            state.isBatteryOptimizationFeatureEnabled
        )
        val systemCount = systemVisible.count { it }
        val integrationsVisible = listOf(
            state.isWidgetFeatureEnabled,
            state.isTileFeatureEnabled
        )
        val integrationsCount = integrationsVisible.count { it }
        val privacyVisible = listOf(
            state.isBiometricFeatureEnabled && state.isBiometricAvailable,
            state.isScreenshotFeatureEnabled
        )
        val privacyCount = privacyVisible.count { it }
        val aboutVisible = listOf(
            state.isGithubFeatureEnabled,
            state.isTelegramFeatureEnabled,
            state.isGooglePlayFeatureEnabled,
            state.isReviewAppFeatureEnabled && state.isReviewFeatureEnabled,
            state.isUpdateAppFeatureEnabled && state.isUpdateFeatureEnabled
        )
        val aboutCount = aboutVisible.count { it }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = innerPadding + PaddingValues(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = if (isNavigationRail) WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() else 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
        ) {
            if (state.isLanguageFeatureEnabled) {
                item {
                    var languageDialog by remember { mutableStateOf(false) }
                    if (languageDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.Language,
                            title = stringResource(MoviesStrings.settings_language),
                            items = AppLanguage.VALUES,
                            currentItem = AppLanguage.transform(stringResource(MoviesStrings.language_code)),
                            onItemSelect = { dispatch(SettingsIntent.SelectLanguage(it)) },
                            onDismissRequest = { languageDialog = false }
                        )
                    }

                    SegmentedListItem(
                        onClick = { languageDialog = true },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = 0,
                            count = localizationCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.Language,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = AppLanguage.transform(stringResource(MoviesStrings.language_code)).stringText,
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
                            text = stringResource(MoviesStrings.settings_language),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isGenderFeatureEnabled) {
                item {
                    var genderDialog by remember { mutableStateOf(false) }
                    if (genderDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.Cat,
                            title = SettingsGenderText,
                            items = GrammaticalGender.VALUES,
                            currentItem = state.grammaticalGender,
                            onItemSelect = { dispatch(SettingsIntent.SetGrammaticalGender(GrammaticalGender.value(it))) },
                            onDismissRequest = { genderDialog = false }
                        )
                    }

                    SegmentedListItem(
                        onClick = { genderDialog = true },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = localizationVisible.take(1).count { it },
                            count = localizationCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.Cat,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = state.grammaticalGender.stringText,
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
                            text = SettingsGenderText,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (localizationCount > 0 && appearanceCount > 0) {
                item {
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
            if (state.isThemeFeatureEnabled) {
                item {
                    var themeDialog by remember { mutableStateOf(false) }
                    if (themeDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.ThemeLightDark,
                            title = stringResource(MoviesStrings.settings_theme),
                            items = AppTheme.VALUES,
                            currentItem = state.themeData.appTheme,
                            onItemSelect = { dispatch(SettingsIntent.SelectTheme(it)) },
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
                                text = state.themeData.appTheme.stringText,
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
                            text = stringResource(MoviesStrings.settings_theme),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isDynamicColorsFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = {
                            val enabled = !state.themeData.dynamicColors
                            dispatch(SettingsIntent.SetDynamicColors(enabled))
                            if (enabled) {
                                dispatch(SettingsIntent.SetPaletteColors(false))
                            }
                        },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = appearanceVisible.take(1).count { it },
                            count = appearanceCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.FormatPaint,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.themeData.dynamicColors,
                                onCheckedChange = null,
                                thumbContent = if (state.themeData.dynamicColors) {
                                    {
                                        Icon(
                                            imageVector = MoviesIcons.Check,
                                            contentDescription = MoviesContentDescription.None,
                                            modifier = Modifier.size(SwitchDefaults.IconSize)
                                        )
                                    }
                                } else null
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(MoviesStrings.settings_dynamic_colors_description),
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
                            text = stringResource(MoviesStrings.settings_dynamic_colors),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isPaletteColorsFeatureEnabled) {
                item {
                    val itemShapes = ListItemDefaults.segmentedShapes(
                        index = appearanceVisible.take(2).count { it },
                        count = appearanceCount
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(itemShapes.shape)
                            .background(MaterialTheme.colorScheme.inversePrimary)
                    ) {
                        SegmentedListItem(
                            onClick = {
                                val enabled = !state.themeData.paletteColors
                                dispatch(SettingsIntent.SetPaletteColors(enabled))
                                if (enabled) {
                                    dispatch(SettingsIntent.SetDynamicColors(false))
                                }
                            },
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
                                    checked = state.themeData.paletteColors,
                                    onCheckedChange = null,
                                    thumbContent = if (state.themeData.paletteColors) {
                                        {
                                            Icon(
                                                imageVector = MoviesIcons.Check,
                                                contentDescription = MoviesContentDescription.None,
                                                modifier = Modifier.size(SwitchDefaults.IconSize)
                                            )
                                        }
                                    } else null
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = stringResource(MoviesStrings.settings_palette_colors_description),
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
                                text = stringResource(MoviesStrings.settings_palette_colors),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        AnimatedVisibility(
                            visible = state.themeData.paletteColors,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            SettingsPaletteColorsBox(
                                paletteKey = state.themeData.paletteKey,
                                seedColor = state.themeData.seedColor,
                                onChange = { localPaletteKey, localSeedColor ->
                                    dispatch(SettingsIntent.SetPaletteKey(localPaletteKey))
                                    dispatch(SettingsIntent.SetSeedColor(localSeedColor))
                                }
                            )
                        }
                    }
                }
            }
            if (state.isAppIconFeatureEnabled) {
                item {
                    val itemShapes = ListItemDefaults.segmentedShapes(
                        index = appearanceVisible.take(3).count { it },
                        count = appearanceCount
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(itemShapes.shape)
                            .background(MaterialTheme.colorScheme.inversePrimary)
                    ) {
                        SegmentedListItem(
                            onClick = { dispatch(SettingsIntent.ShowSnackbar("fdhjkfhsdjkf dfjdsfjkdshkjfhsdjkfhkds dfsdsf")) },
                            shapes = itemShapes,
                            leadingContent = {
                                Icon(
                                    imageVector = MoviesIcons.Apps,
                                    contentDescription = null,
                                    modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = stringResource(MoviesStrings.settings_app_launcher_icon_description),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            colors = ListItemDefaults.segmentedColors(
                                containerColor = MaterialTheme.colorScheme.inversePrimary,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                supportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        ) {
                            Text(
                                text = stringResource(MoviesStrings.settings_app_launcher_icon),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        SettingsAppIconsBox(
                            enabledIcon = state.enabledIcon,
                            onChange = { iconAlias ->
                                iconChangedMessages[iconAlias]?.let { message ->
                                    dispatch(SettingsIntent.ShowSnackbar(message))
                                }
                                dispatch(SettingsIntent.SetAppIcon(iconAlias))
                            }
                        )
                    }
                }
            }
            if (localizationCount + appearanceCount > 0 && feedCount > 0) {
                item {
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
            if (state.isMovieListFeatureEnabled) {
                item {
                    var movieListDialog by remember { mutableStateOf(false) }
                    if (movieListDialog) {
                        SettingsDialog(
                            icon = MoviesIcons.LocalMovies,
                            title = stringResource(MoviesStrings.settings_movie_list),
                            items = MovieList.VALUES,
                            currentItem = state.movieList,
                            onItemSelect = { dispatch(SettingsIntent.SelectMovieList(it)) },
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
                                text = state.movieList.stringText,
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
                            text = stringResource(MoviesStrings.settings_movie_list),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isFeedViewFeatureEnabled) {
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
                                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                            ) {
                                ToggleButton(
                                    checked = state.feedView == FeedView.FeedList,
                                    onCheckedChange = { dispatch(SettingsIntent.SelectFeedView(FeedView.FeedList)) },
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
                                        text = stringResource(MoviesStrings.settings_appearance_list)
                                    )
                                }

                                ToggleButton(
                                    checked = state.feedView == FeedView.FeedGrid,
                                    onCheckedChange = { dispatch(SettingsIntent.SelectFeedView(FeedView.FeedGrid)) },
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
                                        text = stringResource(MoviesStrings.settings_appearance_grid)
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
                            text = stringResource(MoviesStrings.settings_appearance),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (localizationCount + appearanceCount + feedCount > 0 && systemCount > 0) {
                item {
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
            if (state.isAppOpenByDefaultFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = onNavigateToAppOpenByDefaultSettings,
                        shapes = ListItemDefaults.segmentedShapes(
                            index = 0,
                            count = systemCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.OpenInNew,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(MoviesStrings.settings_open_by_default_description),
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
                            text = stringResource(MoviesStrings.settings_open_by_default),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isNotificationsFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = { dispatch(SettingsIntent.RequestPostNotificationsPermission) },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = systemVisible.take(1).count { it },
                            count = systemCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.Notifications,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.areNotificationsEnabled,
                                onCheckedChange = null,
                                thumbContent = if (state.areNotificationsEnabled) {
                                    {
                                        Icon(
                                            imageVector = MoviesIcons.Check,
                                            contentDescription = MoviesContentDescription.None,
                                            modifier = Modifier.size(SwitchDefaults.IconSize)
                                        )
                                    }
                                } else null
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(if (state.areNotificationsEnabled) MoviesStrings.settings_post_notifications_granted else MoviesStrings.settings_post_notifications_denied),
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
                            text = stringResource(MoviesStrings.settings_post_notifications),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isDoNotDisturbFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = {
                            when {
                                state.isDoNotDisturbAccessGranted -> dispatch(SettingsIntent.SetDoNotDisturbEnabled(!state.isDoNotDisturbEnabled))
                                else -> dispatch(SettingsIntent.RequestDoNotDisturbAccess)
                            }
                        },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = systemVisible.take(2).count { it },
                            count = systemCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.DoNotDisturbOn,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.isDoNotDisturbAccessGranted && state.isDoNotDisturbEnabled,
                                onCheckedChange = null,
                                thumbContent = if (state.isDoNotDisturbAccessGranted && state.isDoNotDisturbEnabled) {
                                    {
                                        Icon(
                                            imageVector = MoviesIcons.Check,
                                            contentDescription = MoviesContentDescription.None,
                                            modifier = Modifier.size(SwitchDefaults.IconSize)
                                        )
                                    }
                                } else null
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(
                                    when {
                                        !state.isDoNotDisturbAccessGranted -> MoviesStrings.settings_do_not_disturb_not_granted
                                        state.isDoNotDisturbEnabled -> MoviesStrings.settings_do_not_disturb_enabled
                                        else -> MoviesStrings.settings_do_not_disturb_disabled
                                    }
                                ),
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
                            text = stringResource(MoviesStrings.settings_do_not_disturb),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isBatteryOptimizationFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = { dispatch(SettingsIntent.RequestIgnoreBatteryOptimizations) },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = systemVisible.take(3).count { it },
                            count = systemCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.BatterySaver,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.isIgnoringBatteryOptimizations,
                                onCheckedChange = null,
                                thumbContent = if (state.isIgnoringBatteryOptimizations) {
                                    {
                                        Icon(
                                            imageVector = MoviesIcons.Check,
                                            contentDescription = MoviesContentDescription.None,
                                            modifier = Modifier.size(SwitchDefaults.IconSize)
                                        )
                                    }
                                } else null
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(if (state.isIgnoringBatteryOptimizations) MoviesStrings.settings_battery_optimization_ignored else MoviesStrings.settings_battery_optimization_optimized),
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
                            text = stringResource(MoviesStrings.settings_battery_optimization),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (localizationCount + appearanceCount + feedCount + systemCount > 0 && integrationsCount > 0) {
                item {
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
            if (state.isWidgetFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = rememberAndPinAppWidgetProvider(),
                        shapes = ListItemDefaults.segmentedShapes(
                            index = 0,
                            count = integrationsCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.Widgets,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(MoviesStrings.settings_app_widget_description, stringResource(MoviesStrings.appwidget_description)),
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
                            text = stringResource(MoviesStrings.settings_app_widget),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isTileFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = { dispatch(SettingsIntent.RequestTileService) },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = integrationsVisible.take(1).count { it },
                            count = integrationsCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.TileSmall,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(MoviesStrings.settings_tile_description),
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
                            text = stringResource(MoviesStrings.settings_tile),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (localizationCount + appearanceCount + feedCount + systemCount + integrationsCount > 0 && privacyCount > 0) {
                item {
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
            if (state.isBiometricFeatureEnabled && state.isBiometricAvailable) {
                item {
                    SegmentedListItem(
                        onClick = { dispatch(SettingsIntent.SetBiometricEnabled(!state.isBiometricEnabled)) },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = 0,
                            count = privacyCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.Fingerprint,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.isBiometricEnabled,
                                onCheckedChange = null,
                                thumbContent = if (state.isBiometricEnabled) {
                                    {
                                        Icon(
                                            imageVector = MoviesIcons.Check,
                                            contentDescription = MoviesContentDescription.None,
                                            modifier = Modifier.size(SwitchDefaults.IconSize)
                                        )
                                    }
                                } else null
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(if (state.isBiometricEnabled) MoviesStrings.settings_biometric_added else MoviesStrings.settings_biometric_not_added),
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
                            text = stringResource(MoviesStrings.settings_lock_app),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isScreenshotFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = { dispatch(SettingsIntent.SetScreenshotBlockEnabled(!state.isScreenshotBlockEnabled)) },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = privacyVisible.take(1).count { it },
                            count = privacyCount
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.Screenshot,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = state.isScreenshotBlockEnabled,
                                onCheckedChange = null,
                                thumbContent = if (state.isScreenshotBlockEnabled) {
                                    {
                                        Icon(
                                            imageVector = MoviesIcons.Check,
                                            contentDescription = MoviesContentDescription.None,
                                            modifier = Modifier.size(SwitchDefaults.IconSize)
                                        )
                                    }
                                } else null
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(MoviesStrings.settings_screenshots_description),
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
                            text = stringResource(MoviesStrings.settings_screenshots),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (localizationCount + appearanceCount + feedCount + systemCount + integrationsCount + privacyCount > 0 && (aboutCount > 0 || state.isAboutFeatureEnabled)) {
                item {
                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }
            if (state.isGithubFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = { dispatch(SettingsIntent.RequestGithub) },
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
                                text = stringResource(MoviesStrings.settings_github_description),
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
                            text = stringResource(MoviesStrings.settings_github),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isTelegramFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = { dispatch(SettingsIntent.RequestTelegram) },
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
                                text = stringResource(MoviesStrings.settings_telegram_description),
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
                            text = stringResource(MoviesStrings.settings_telegram),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isGooglePlayFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = { dispatch(SettingsIntent.RequestGooglePlay) },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = aboutVisible.take(2).count { it },
                            count = aboutCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.GooglePlay,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(MoviesStrings.settings_google_play_description),
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
                            text = stringResource(MoviesStrings.settings_google_play),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isReviewAppFeatureEnabled && state.isReviewFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = { dispatch(SettingsIntent.ReviewClick) },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = aboutVisible.take(3).count { it },
                            count = aboutCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.StarShine,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(MoviesStrings.settings_review_description),
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
                            text = stringResource(MoviesStrings.settings_review),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isUpdateAppFeatureEnabled && state.isUpdateFeatureEnabled) {
                item {
                    SegmentedListItem(
                        onClick = {
                            if (state.isUpdateAvailable) {
                                dispatch(SettingsIntent.UpdateClick)
                            } else {
                                dispatch(SettingsIntent.ShowSnackbar(updateNotAvailableMessage))
                            }
                        },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = aboutVisible.take(4).count { it },
                            count = aboutCount
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = MoviesIcons.SystemUpdate,
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.smallIconSize)
                            )
                        },
                        supportingContent = {
                            Text(
                                text = stringResource(MoviesStrings.settings_update_description),
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
                            text = stringResource(MoviesStrings.settings_update),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            if (state.isAboutFeatureEnabled) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = MoviesIcons.MovieFilter,
                            contentDescription = MoviesContentDescription.None,
                            modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = stringResource(MoviesStrings.settings_app_version_name, state.versionName),
                            modifier = Modifier.padding(start = 2.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )

                        Text(
                            text = stringResource(MoviesStrings.settings_app_version_code, state.versionCode),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )

                        Text(
                            text = state.appVersionData.flavor,
                            modifier = Modifier.padding(start = 2.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )

                        if (isDebug) {
                            Text(
                                text = stringResource(MoviesStrings.settings_app_debug),
                                modifier = Modifier.padding(start = 2.dp),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
