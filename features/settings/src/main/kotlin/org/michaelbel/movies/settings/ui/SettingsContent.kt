package org.michaelbel.movies.settings.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.settings.R
import org.michaelbel.movies.settings.SettingsViewModel
import org.michaelbel.movies.ui.SystemTheme

@Composable
fun SettingsContent(
    navController: NavController
) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val currentTheme: SystemTheme = viewModel.currentTheme
        .collectAsState(SystemTheme.FollowSystem).value
    var backHandlingEnabled: Boolean by remember { mutableStateOf(false) }
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { value: ModalBottomSheetValue ->
            if (value == ModalBottomSheetValue.Hidden) {
                backHandlingEnabled = false
            }
            true
        }
    )
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    fun showThemeModalBottomSheet() = coroutineScope.launch {
        modalBottomSheetState.show()
        backHandlingEnabled = true
    }

    fun hideThemeModalBottomSheet() = coroutineScope.launch {
        modalBottomSheetState.hide()
        backHandlingEnabled = false
    }

    BackHandler(backHandlingEnabled) {
        if (modalBottomSheetState.isVisible) {
            hideThemeModalBottomSheet()
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                navController = navController
            )
        }
    ) { paddingValues: PaddingValues ->
        ModalBottomSheetLayout(
            sheetContent = {
                ThemeSetupBox(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp
                        ),
                    themes = viewModel.themes,
                    currentTheme = currentTheme,
                    onThemeSelected = { systemTheme ->
                        viewModel.selectTheme(systemTheme)
                        hideThemeModalBottomSheet()
                    }
                )
            },
            sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(8.dp),
            sheetBackgroundColor = MaterialTheme.colorScheme.surface
        ) {
            Content(
                paddingValues = paddingValues,
                onShowThemeBottomSheet = ::showThemeModalBottomSheet
            )
        }
    }
}

@Composable
private fun Toolbar(
    navController: NavController
) {
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.settings_title)
            )
        },
        modifier = Modifier
            .statusBarsPadding(),
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Image(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    )
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    onShowThemeBottomSheet: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        SettingsBox(
            title = {
                Text(
                    text = stringResource(R.string.settings_theme)
                )
            },
            onClick = onShowThemeBottomSheet
        )
    }
}