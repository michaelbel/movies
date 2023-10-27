package org.michaelbel.movies.ui.icons

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.SystemUpdate
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.ui.graphics.vector.ImageVector
import org.michaelbel.movies.ui.R

/**
 * Movies icons. Material icons are [ImageVector]s, custom icons are drawable resource IDs.
 */
object MoviesIcons {
    @DrawableRes val TmdbLogo: Int = R.drawable.ic_tmdb_logo
    @DrawableRes val ThemeLightDark: Int = R.drawable.ic_theme_light_dark_24
    @DrawableRes val NotificationSmallIconMovieFilter = R.drawable.ic_movie_filter_24
    @DrawableRes val AdultOutline = R.drawable.ic_18_up_rating_outline_24
    @DrawableRes val ShortcutSettingsOutline: Int = R.drawable.ic_shortcut_settings_outline_48

    val Account: ImageVector = Icons.Outlined.AccountCircle
    val ArrowBack: ImageVector = Icons.Outlined.ArrowBack
    val Close: ImageVector = Icons.Outlined.Close
    val Info: ImageVector = Icons.Outlined.Info
    val GridView: ImageVector = Icons.Outlined.GridView
    val Language: ImageVector = Icons.Outlined.Language
    val LocationOn: ImageVector = Icons.Outlined.LocationOn
    val MovieFilter: ImageVector = Icons.Filled.MovieFilter
    val Notifications: ImageVector = Icons.Outlined.Notifications
    val Settings: ImageVector = Icons.Outlined.Settings
    val Share: ImageVector = Icons.Outlined.Share
    val SystemUpdate: ImageVector = Icons.Outlined.SystemUpdate
    val Visibility: ImageVector = Icons.Outlined.Visibility
    val VisibilityOff = Icons.Outlined.VisibilityOff
}