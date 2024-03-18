@file:SuppressLint("StartActivityAndCollapseDeprecated")
@file:Suppress(
    "DEPRECATION",
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)

package org.michaelbel.movies.ui.tile

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.service.quicksettings.TileService
import android.widget.Toast
import androidx.annotation.RequiresApi
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui_kmp.R

@RequiresApi(24)
actual class MoviesTileService: TileService() {

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
                startActivityAndCollapse(intent)
            }
        }
    }
}