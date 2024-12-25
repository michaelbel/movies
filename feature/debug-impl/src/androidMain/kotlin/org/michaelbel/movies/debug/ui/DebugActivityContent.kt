@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.debug.ui

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.debug.DebugViewModel
import org.michaelbel.movies.debug_impl.R
import org.michaelbel.movies.ui.icons.MoviesAndroidIcons
import org.michaelbel.movies.ui.ktx.collectAsStateCommon
import org.michaelbel.movies.ui.ktx.rememberNavigateToAppSettings
import org.michaelbel.movies.ui.ktx.rememberNavigateToDeveloperSettings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun DebugActivityContent(
    viewModel: DebugViewModel = koinViewModel(),
    enableEdgeToEdge: (Any, Any) -> Unit
) {
    val themeData by viewModel.themeDataFlow.collectAsStateCommon()
    val firebaseToken by viewModel.firebaseTokenFlow.collectAsStateCommon()

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val navigateToAppSettings = rememberNavigateToAppSettings()
    val navigateToDeveloperSettings = rememberNavigateToDeveloperSettings()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        canScroll = { true }
    )

    MoviesTheme(
        themeData = themeData,
        enableEdgeToEdge = enableEdgeToEdge
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            topBar = {
                DebugToolbar(
                    onNavigationIconClick = { (context as Activity).finish() },
                    modifier = Modifier.fillMaxWidth(),
                    topAppBarScrollBehavior = topAppBarScrollBehavior
                )
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.navigationBarsPadding(),
                state = rememberLazyListState(),
                contentPadding = innerPadding
            ) {
                item {
                    SettingItem(
                        title = stringResource(R.string.debug_app_settings),
                        description = "",
                        icon = painterResource(MoviesAndroidIcons.SettingsCinematicBlur24),
                        onClick = navigateToAppSettings
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        thickness = .1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                item {
                    SettingItem(
                        title = stringResource(R.string.debug_developer_settings),
                        description = "",
                        icon = painterResource(MoviesAndroidIcons.SettingsAccountBox24),
                        onClick = navigateToDeveloperSettings
                    )
                }
                if (viewModel.isFirebaseTokenFeatureEnabled) {
                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            thickness = .1.dp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    item {
                        SettingItem(
                            title = stringResource(R.string.debug_firebase_token),
                            description = stringResource(R.string.debug_firebase_token_copy),
                            icon = painterResource(MoviesAndroidIcons.Firebase24),
                            onClick = { clipboardManager.setText(AnnotatedString(firebaseToken)) }
                        )
                    }
                }
            }
        }
    }
}