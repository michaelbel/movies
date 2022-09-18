package org.michaelbel.movies.settings

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.settings.model.Theme

@Composable
fun SettingsContent(
    navController: NavController
) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    var backHandlingEnabled: Boolean by remember { mutableStateOf(false) }

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
                ThemeContent(
                    viewModel = viewModel,
                    onThemeSelected = ::hideThemeModalBottomSheet
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
            .systemBarsPadding(),
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

@Composable
private fun ThemeContent(
    viewModel: SettingsViewModel,
    onThemeSelected: () -> Unit
) {
    val currentTheme: Theme = viewModel.currentTheme
        .collectAsState(Theme(Theme.THEME_DEFAULT)).value

    Column(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            )
    ) {
        viewModel.themes.forEach { theme ->
            val textId = when (theme.value) {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> R.string.settings_theme_system
                AppCompatDelegate.MODE_NIGHT_NO -> R.string.settings_theme_light
                AppCompatDelegate.MODE_NIGHT_YES -> R.string.settings_theme_dark
                else -> throw Exception()
            }
            val selected: Boolean = currentTheme == theme

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onThemeSelected()
                        viewModel.selectTheme(theme)
                    }
            ) {
                RadioButton(
                    selected = selected,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.secondary,
                        unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)
                    ),
                    onClick = {
                        onThemeSelected()
                        viewModel.selectTheme(theme)
                    }
                )

                Spacer(
                    modifier = Modifier
                        .size(4.dp)
                )

                Text(
                    text = stringResource(textId)
                )
            }
        }
    }
}