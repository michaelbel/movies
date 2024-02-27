package org.michaelbel.movies.ui.icons

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.KeyboardVoice
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LocalMovies
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.SystemUpdate
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.ui.graphics.vector.ImageVector
import org.michaelbel.movies.ui.R

/**
 * Movies icons. Material icons are [ImageVector]s, custom icons are drawable resource IDs.
 */
object MoviesIcons {
    @DrawableRes val TmdbLogo = R.drawable.ic_tmdb_logo
    @DrawableRes val ThemeLightDark = R.drawable.ic_theme_light_dark_24
    @DrawableRes val MovieFilter24 = R.drawable.ic_movie_filter_24
    @DrawableRes val FileDownload24 = R.drawable.ic_file_download_24
    @DrawableRes val AdultOutline = R.drawable.ic_18_up_rating_outline_24
    @DrawableRes val Cat = R.drawable.ic_cat_24
    @DrawableRes val Github = R.drawable.ic_github_24
    @DrawableRes val GooglePlay = R.drawable.ic_google_play_24

    val AccountCircle = Icons.Outlined.AccountCircle
    val ArrowBack = Icons.AutoMirrored.Outlined.ArrowBack
    val Check = Icons.Outlined.Check
    val Close = Icons.Outlined.Close
    val Info = Icons.Outlined.Info
    val FileDownload = Icons.Outlined.FileDownload
    val Fingerprint = Icons.Outlined.Fingerprint
    val GridView = Icons.Outlined.GridView
    val History = Icons.Outlined.History
    val KeyboardVoice = Icons.Outlined.KeyboardVoice
    val Language = Icons.Outlined.Language
    val LocationOn = Icons.Outlined.LocationOn
    val ManageSearch = Icons.AutoMirrored.Filled.ManageSearch
    val MovieFilter = Icons.Filled.MovieFilter
    val LocalMovies = Icons.Outlined.LocalMovies
    val Notifications = Icons.Outlined.Notifications
    val Palette = Icons.Outlined.Palette
    val Search = Icons.Outlined.Search
    val Settings = Icons.Outlined.Settings
    val Share = Icons.Outlined.Share
    val SystemUpdate = Icons.Outlined.SystemUpdate
    val Visibility = Icons.Outlined.Visibility
    val VisibilityOff = Icons.Outlined.VisibilityOff
    val Widgets = Icons.Outlined.Widgets
}