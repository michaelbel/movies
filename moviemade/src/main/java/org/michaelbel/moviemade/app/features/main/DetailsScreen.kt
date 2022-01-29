package org.michaelbel.moviemade.app.features.main

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun DetailsScreen(
    navController: NavController
) {
    val context: Context = LocalContext.current

    Scaffold(
        topBar = { Toolbar(navController) }
    ) {
        Content(onClick = {})
    }
}

@Composable
private fun Toolbar(
    navController: NavController
) {
    SmallTopAppBar(
        title = { Text(text = "details") },
        modifier = Modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "content desc"
                )
            }
        }
    )
}

@Composable
private fun Content(
    onClick: (String) -> Unit
) {
    LazyColumn {
        item {
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 4.dp)
            ) { Text(text = "text") }
        }
    }
}