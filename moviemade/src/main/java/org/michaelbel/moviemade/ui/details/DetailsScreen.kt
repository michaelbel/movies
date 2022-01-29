package org.michaelbel.moviemade.ui.details

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun DetailsScreen(
    navController: NavController,
    movieTitle: String
) {
    Scaffold(
        topBar = { Toolbar(navController, movieTitle) }
    ) {
        Content(onClick = {})
    }
}

@Composable
private fun Toolbar(
    navController: NavController,
    movieTitle: String
) {
    SmallTopAppBar(
        title = { Text(text = movieTitle) },
        modifier = Modifier.systemBarsPadding(),
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun Content(
    onClick: (String) -> Unit
) {

}