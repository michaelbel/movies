@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.movies.ui.accessibility

import movies.core.ui.generated.resources.Res
import movies.core.ui.generated.resources.content_description_account_avatar_image
import movies.core.ui.generated.resources.content_description_account_icon
import movies.core.ui.generated.resources.content_description_adult_icon
import movies.core.ui.generated.resources.content_description_app_icon
import movies.core.ui.generated.resources.content_description_appearance_icon
import movies.core.ui.generated.resources.content_description_back_icon
import movies.core.ui.generated.resources.content_description_close_icon
import movies.core.ui.generated.resources.content_description_download_icon
import movies.core.ui.generated.resources.content_description_movie_details_image
import movies.core.ui.generated.resources.content_description_password_icon
import movies.core.ui.generated.resources.content_description_password_off_icon
import movies.core.ui.generated.resources.content_description_search_icon
import movies.core.ui.generated.resources.content_description_settings_icon
import movies.core.ui.generated.resources.content_description_share_icon
import movies.core.ui.generated.resources.content_description_user_location_icon
import movies.core.ui.generated.resources.content_description_voice_icon
import org.jetbrains.compose.resources.ExperimentalResourceApi

object MoviesContentDescriptionCommon {
    val AccountIcon = Res.string.content_description_account_icon
    val AccountAvatarImage = Res.string.content_description_account_avatar_image
    val AdultIcon = Res.string.content_description_adult_icon
    val AppearanceIcon = Res.string.content_description_appearance_icon
    val AppIcon = Res.string.content_description_app_icon
    val BackIcon = Res.string.content_description_back_icon
    val CloseIcon = Res.string.content_description_close_icon
    val DownloadIcon = Res.string.content_description_download_icon
    val HistoryIcon = Res.string.content_description_download_icon
    val MovieDetailsImage = Res.string.content_description_movie_details_image
    val PasswordIcon = Res.string.content_description_password_icon
    val PasswordOffIcon = Res.string.content_description_password_off_icon
    val ShareIcon = Res.string.content_description_share_icon
    val SearchIcon = Res.string.content_description_search_icon
    val SettingsIcon = Res.string.content_description_settings_icon
    val VoiceIcon = Res.string.content_description_voice_icon
    val UserLocationIcon = Res.string.content_description_user_location_icon

    val None: String? = null
}