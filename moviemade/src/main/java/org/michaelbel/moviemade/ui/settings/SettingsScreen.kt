package org.michaelbel.moviemade.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ui.ROUTE_ABOUT

@Composable
fun SettingsScreen(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar(navController) }
    ) {
        Content(navController)
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
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun Content(
    navController: NavController
) {
    Column {
        SettingsListItem(
            title = {
                Text(
                    text = stringResource(R.string.title_about)
                )
            },
            onClick = {
                navController.navigate(ROUTE_ABOUT)
            }
        )
    }
}