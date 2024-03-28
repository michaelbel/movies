@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.movies.ui.icons

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
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.ui.graphics.vector.ImageVector
import movies.core.ui_kmp.generated.resources.Res
import movies.core.ui_kmp.generated.resources.ic_18_up_rating_outline_24
import movies.core.ui_kmp.generated.resources.ic_file_download_24
import movies.core.ui_kmp.generated.resources.ic_launcher_icon_amoled
import movies.core.ui_kmp.generated.resources.ic_launcher_icon_brown
import movies.core.ui_kmp.generated.resources.ic_launcher_icon_purple
import movies.core.ui_kmp.generated.resources.ic_launcher_icon_red
import movies.core.ui_kmp.generated.resources.ic_movie_filter_24
import movies.core.ui_kmp.generated.resources.ic_settings_account_box_24
import movies.core.ui_kmp.generated.resources.ic_settings_cinematic_blur_24
import movies.core.ui_kmp.generated.resources.ic_tmdb_logo
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * Movies icons. Material icons are [ImageVector]s, custom icons are drawable resource IDs.
 */
object MoviesIcons {
    val TmdbLogo = Res.drawable.ic_tmdb_logo
    val MovieFilter24 = Res.drawable.ic_movie_filter_24
    val FileDownload24 = Res.drawable.ic_file_download_24
    val AdultOutline = Res.drawable.ic_18_up_rating_outline_24
    val SettingsAccountBox24 = Res.drawable.ic_settings_account_box_24
    val SettingsCinematicBlur24 = Res.drawable.ic_settings_cinematic_blur_24
    val LauncherAmoled = Res.drawable.ic_launcher_icon_amoled
    val LauncherBrown = Res.drawable.ic_launcher_icon_brown
    val LauncherPurple = Res.drawable.ic_launcher_icon_purple
    val LauncherRed = Res.drawable.ic_launcher_icon_red

    val Cat = CatVector
    val Github = GithubVector
    val GooglePlay = GooglePlayVector
    val ThemeLightDark = ThemeLightDarkVector

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
    val ViewAgenda = Icons.Outlined.ViewAgenda
    val Visibility = Icons.Outlined.Visibility
    val VisibilityOff = Icons.Outlined.VisibilityOff
    val Widgets = Icons.Outlined.Widgets
}