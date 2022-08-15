package org.michaelbel.movies.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import org.michaelbel.moviemade.R
import org.michaelbel.movies.ui.NavGraph

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val viewModel: SettingsViewModel = hiltViewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar(navController) }
    ) {
        Content(navController, viewModel)
    }
}

@Composable
private fun Toolbar(
    navController: NavController
) {
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.title_settings)
            )
        },
        modifier = Modifier.systemBarsPadding(),
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
    navController: NavController,
    viewModel: SettingsViewModel
) {
    var themeDialog by remember { mutableStateOf(false) }
    val currentTheme: Int = viewModel.currentTheme.collectAsState(viewModel.themes.first()).value

    if (themeDialog) {
        AlertDialog(
            onDismissRequest = { themeDialog = false },
            confirmButton = {},
            title = {
                Text(
                    text = stringResource(R.string.theme)
                )
            },
            text = {
                Column {
                    viewModel.themes.forEach { theme ->
                        val textId = when (theme) {
                            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> R.string.theme_system
                            AppCompatDelegate.MODE_NIGHT_NO -> R.string.theme_light
                            AppCompatDelegate.MODE_NIGHT_YES -> R.string.theme_dark
                            else -> throw Exception()
                        }
                        val selected: Boolean = currentTheme == theme
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                themeDialog = false
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
                                    themeDialog = false
                                    viewModel.selectTheme(theme)
                                }
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = stringResource(id = textId))
                        }
                    }
                }
            }
        )
    }

    Column {
        SettingsListItem(
            title = {
                Text(
                    text = stringResource(R.string.theme)
                )
            },
            onClick = {
                themeDialog = true
            }
        )
        SettingsListItem(
            title = {
                Text(
                    text = stringResource(R.string.title_about)
                )
            },
            onClick = {
                navController.navigate(NavGraph.About.route)
            }
        )
    }
}