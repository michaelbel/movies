package org.michaelbel.movies.ui.tile

import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.service.quicksettings.TileService
import android.widget.Toast
import org.michaelbel.movies.ui.R
import org.michaelbel.movies.ui.icons.MoviesIcons

class MoviesTileService: TileService() {

    override fun onTileAdded() {
        super.onTileAdded()
        Toast.makeText(this, R.string.tile_added, Toast.LENGTH_SHORT).show()
    }

    override fun onStartListening() {
        super.onStartListening()
        val tile = qsTile
        tile.label = getString(R.string.tile_title)
        tile.icon = Icon.createWithResource(this, MoviesIcons.MovieFilter24)
        tile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        runCatching {
            val intent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            if (Build.VERSION.SDK_INT >= 34) {
                startActivityAndCollapse(pendingIntent)
            } else {
                @Suppress("deprecation")
                startActivityAndCollapse(intent)
            }
        }
    }
}