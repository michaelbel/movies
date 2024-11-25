package org.michaelbel.movies.settings.ktx

import android.app.StatusBarManager
import android.content.ComponentName
import android.graphics.drawable.Icon
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.ui.icons.MoviesAndroidIcons
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.tile.MoviesTileService

@Composable
actual fun requestTileService(onSnackbarShow: (String) -> Unit): () -> Unit {
    val context = LocalContext.current
    val tileTitleLabel = stringResource(MoviesStrings.tile_title)
    val tileMessage = stringResource(MoviesStrings.settings_tile_error_already_added)
    return {
        if (Build.VERSION.SDK_INT >= 33) {
            val statusBarManager = ContextCompat.getSystemService(context, StatusBarManager::class.java)
            statusBarManager?.requestAddTileService(
                ComponentName(context, MoviesTileService::class.java),
                tileTitleLabel,
                Icon.createWithResource(context, MoviesAndroidIcons.MovieFilter24),
                context.mainExecutor
            ) { result ->
                when (result) {
                    StatusBarManager.TILE_ADD_REQUEST_RESULT_TILE_ALREADY_ADDED -> {
                        onSnackbarShow(tileMessage)
                    }
                }
            }
        }
    }
}