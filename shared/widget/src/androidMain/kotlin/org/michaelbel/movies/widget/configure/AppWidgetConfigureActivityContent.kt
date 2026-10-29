package org.michaelbel.movies.widget.configure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.displayCutoutWindowInsets
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.theme.AppTheme
import org.michaelbel.movies.widget.R

@Composable
fun AppWidgetConfigureActivityContent(
    viewModel: AppWidgetConfigureViewModel = koinViewModel(),
    enableEdgeToEdge: (Any, Any) -> Unit,
    onBackClick: () -> Unit
) {
    val themeData by viewModel.themeData.collectAsStateCommon()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    AppTheme(
        themeData = themeData,
        enableEdgeToEdge = enableEdgeToEdge
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            topBar = {
                Toolbar(
                    onNavigationIconClick = onBackClick,
                    topAppBarScrollBehavior = topAppBarScrollBehavior,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.appwidget_configure_soon),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}

@Composable
private fun Toolbar(
    onNavigationIconClick: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.appwidget_configure),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onNavigationIconClick,
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
            ) {
                Image(
                    imageVector = MoviesIcons.Close,
                    contentDescription = stringResource(MoviesContentDescription.CloseIcon),
                    modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}
