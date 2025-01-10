package org.michaelbel.movies.ui.strings

import androidx.compose.runtime.Composable
import movies.core.ui.generated.resources.Res
import movies.core.ui.generated.resources.account_logout
import movies.core.ui.generated.resources.account_title
import movies.core.ui.generated.resources.appwidget_configure
import movies.core.ui.generated.resources.appwidget_configure_soon
import movies.core.ui.generated.resources.appwidget_description
import movies.core.ui.generated.resources.appwidget_failure_empty
import movies.core.ui.generated.resources.appwidget_pinned_message
import movies.core.ui.generated.resources.appwidget_title
import movies.core.ui.generated.resources.auth_error_while_create_request_token
import movies.core.ui.generated.resources.auth_error_while_create_session
import movies.core.ui.generated.resources.auth_error_while_create_session_with_login
import movies.core.ui.generated.resources.auth_error_while_loading_account_details
import movies.core.ui.generated.resources.auth_label_password
import movies.core.ui.generated.resources.auth_label_username
import movies.core.ui.generated.resources.auth_login
import movies.core.ui.generated.resources.auth_privacy_policy
import movies.core.ui.generated.resources.auth_reset_password
import movies.core.ui.generated.resources.auth_sign_in
import movies.core.ui.generated.resources.auth_sign_up
import movies.core.ui.generated.resources.auth_terms_of_use
import movies.core.ui.generated.resources.auth_title
import movies.core.ui.generated.resources.details_error_loading
import movies.core.ui.generated.resources.details_title
import movies.core.ui.generated.resources.details_url_copied
import movies.core.ui.generated.resources.error_api_key_null
import movies.core.ui.generated.resources.error_check_internet_connectivity
import movies.core.ui.generated.resources.error_loading
import movies.core.ui.generated.resources.feed_auth_failure
import movies.core.ui.generated.resources.feed_auth_success
import movies.core.ui.generated.resources.feed_error_empty
import movies.core.ui.generated.resources.feed_title_now_playing
import movies.core.ui.generated.resources.feed_title_popular
import movies.core.ui.generated.resources.feed_title_top_rated
import movies.core.ui.generated.resources.feed_title_upcoming
import movies.core.ui.generated.resources.gallery_action_open
import movies.core.ui.generated.resources.gallery_backdrop
import movies.core.ui.generated.resources.gallery_downloading_image
import movies.core.ui.generated.resources.gallery_failure
import movies.core.ui.generated.resources.gallery_image_of
import movies.core.ui.generated.resources.gallery_logo
import movies.core.ui.generated.resources.gallery_poster
import movies.core.ui.generated.resources.gallery_success
import movies.core.ui.generated.resources.language_code
import movies.core.ui.generated.resources.no_image
import movies.core.ui.generated.resources.notification_continue
import movies.core.ui.generated.resources.notification_enable_subtitle
import movies.core.ui.generated.resources.notification_enable_title
import movies.core.ui.generated.resources.notification_go_to_settings
import movies.core.ui.generated.resources.retry
import movies.core.ui.generated.resources.search_clear
import movies.core.ui.generated.resources.settings_action_cancel
import movies.core.ui.generated.resources.settings_action_go
import movies.core.ui.generated.resources.settings_app_debug
import movies.core.ui.generated.resources.settings_app_launcher_icon
import movies.core.ui.generated.resources.settings_app_launcher_icon_amoled
import movies.core.ui.generated.resources.settings_app_launcher_icon_brown
import movies.core.ui.generated.resources.settings_app_launcher_icon_changed_to
import movies.core.ui.generated.resources.settings_app_launcher_icon_purple
import movies.core.ui.generated.resources.settings_app_launcher_icon_red
import movies.core.ui.generated.resources.settings_app_version_code
import movies.core.ui.generated.resources.settings_app_version_name
import movies.core.ui.generated.resources.settings_app_widget
import movies.core.ui.generated.resources.settings_app_widget_description
import movies.core.ui.generated.resources.settings_appearance
import movies.core.ui.generated.resources.settings_appearance_grid
import movies.core.ui.generated.resources.settings_appearance_list
import movies.core.ui.generated.resources.settings_biometric_added
import movies.core.ui.generated.resources.settings_biometric_not_added
import movies.core.ui.generated.resources.settings_dynamic_colors
import movies.core.ui.generated.resources.settings_dynamic_colors_description
import movies.core.ui.generated.resources.settings_error_app_from_google_play
import movies.core.ui.generated.resources.settings_error_play_services_not_available
import movies.core.ui.generated.resources.settings_gender
import movies.core.ui.generated.resources.settings_gender_feminine
import movies.core.ui.generated.resources.settings_gender_masculine
import movies.core.ui.generated.resources.settings_gender_neutral
import movies.core.ui.generated.resources.settings_gender_not_specified
import movies.core.ui.generated.resources.settings_github
import movies.core.ui.generated.resources.settings_github_description
import movies.core.ui.generated.resources.settings_language
import movies.core.ui.generated.resources.settings_language_en
import movies.core.ui.generated.resources.settings_language_ru
import movies.core.ui.generated.resources.settings_lock_app
import movies.core.ui.generated.resources.settings_movie_list
import movies.core.ui.generated.resources.settings_movie_list_now_playing
import movies.core.ui.generated.resources.settings_movie_list_popular
import movies.core.ui.generated.resources.settings_movie_list_top_rated
import movies.core.ui.generated.resources.settings_movie_list_upcoming
import movies.core.ui.generated.resources.settings_palette_colors
import movies.core.ui.generated.resources.settings_post_notifications
import movies.core.ui.generated.resources.settings_post_notifications_denied
import movies.core.ui.generated.resources.settings_post_notifications_granted
import movies.core.ui.generated.resources.settings_post_notifications_should_request
import movies.core.ui.generated.resources.settings_review
import movies.core.ui.generated.resources.settings_review_description
import movies.core.ui.generated.resources.settings_screenshots
import movies.core.ui.generated.resources.settings_screenshots_description
import movies.core.ui.generated.resources.settings_theme
import movies.core.ui.generated.resources.settings_theme_amoled
import movies.core.ui.generated.resources.settings_theme_dark
import movies.core.ui.generated.resources.settings_theme_light
import movies.core.ui.generated.resources.settings_theme_system
import movies.core.ui.generated.resources.settings_tile
import movies.core.ui.generated.resources.settings_tile_description
import movies.core.ui.generated.resources.settings_tile_error_already_added
import movies.core.ui.generated.resources.settings_title
import movies.core.ui.generated.resources.settings_update
import movies.core.ui.generated.resources.settings_update_description
import movies.core.ui.generated.resources.share_via
import movies.core.ui.generated.resources.shortcuts_search_title
import movies.core.ui.generated.resources.shortcuts_settings_title
import movies.core.ui.generated.resources.tile_added
import movies.core.ui.generated.resources.tile_title
import org.jetbrains.compose.resources.stringResource

object MoviesStrings {
    val language_code = Res.string.language_code
    val shortcuts_search_title = Res.string.shortcuts_search_title
    val shortcuts_settings_title = Res.string.shortcuts_settings_title
    val notification_enable_title = Res.string.notification_enable_title
    val notification_enable_subtitle = Res.string.notification_enable_subtitle
    val notification_continue = Res.string.notification_continue
    val notification_go_to_settings = Res.string.notification_go_to_settings
    val retry = Res.string.retry
    val no_image = Res.string.no_image
    val error_api_key_null = Res.string.error_api_key_null
    val error_loading = Res.string.error_loading
    val error_check_internet_connectivity = Res.string.error_check_internet_connectivity
    val share_via = Res.string.share_via
    val tile_title = Res.string.tile_title
    val tile_added = Res.string.tile_added

    val account_title = Res.string.account_title
    val account_logout = Res.string.account_logout

    val auth_title = Res.string.auth_title
    val auth_label_username = Res.string.auth_label_username
    val auth_label_password = Res.string.auth_label_password
    val auth_reset_password = Res.string.auth_reset_password
    val auth_sign_in = Res.string.auth_sign_in
    val auth_sign_up = Res.string.auth_sign_up
    val auth_login = Res.string.auth_login
    val auth_terms_of_use = Res.string.auth_terms_of_use
    val auth_privacy_policy = Res.string.auth_privacy_policy
    val auth_error_while_create_request_token = Res.string.auth_error_while_create_request_token
    val auth_error_while_create_session_with_login = Res.string.auth_error_while_create_session_with_login
    val auth_error_while_create_session = Res.string.auth_error_while_create_session
    val auth_error_while_loading_account_details = Res.string.auth_error_while_loading_account_details

    val details_title = Res.string.details_title
    val details_error_loading = Res.string.details_error_loading
    val details_url_copied = Res.string.details_url_copied

    val feed_title_now_playing = Res.string.feed_title_now_playing
    val feed_title_popular = Res.string.feed_title_popular
    val feed_title_top_rated = Res.string.feed_title_top_rated
    val feed_title_upcoming = Res.string.feed_title_upcoming
    val feed_error_empty = Res.string.feed_error_empty
    val feed_auth_failure = Res.string.feed_auth_failure
    val feed_auth_success = Res.string.feed_auth_success

    val gallery_downloading_image = Res.string.gallery_downloading_image
    val gallery_poster = Res.string.gallery_poster
    val gallery_backdrop = Res.string.gallery_backdrop
    val gallery_logo = Res.string.gallery_logo
    val gallery_success = Res.string.gallery_success
    val gallery_failure = Res.string.gallery_failure
    val gallery_action_open = Res.string.gallery_action_open
    val gallery_image_of = Res.string.gallery_image_of

    val search_clear = Res.string.search_clear

    val settings_title = Res.string.settings_title
    val settings_theme = Res.string.settings_theme
    val settings_theme_system = Res.string.settings_theme_system
    val settings_theme_light = Res.string.settings_theme_light
    val settings_theme_dark = Res.string.settings_theme_dark
    val settings_theme_amoled = Res.string.settings_theme_amoled
    val settings_dynamic_colors = Res.string.settings_dynamic_colors
    val settings_dynamic_colors_description = Res.string.settings_dynamic_colors_description
    val settings_post_notifications = Res.string.settings_post_notifications
    val settings_post_notifications_denied = Res.string.settings_post_notifications_denied
    val settings_post_notifications_granted = Res.string.settings_post_notifications_granted
    val settings_review = Res.string.settings_review
    val settings_review_description = Res.string.settings_review_description
    val settings_post_notifications_should_request = Res.string.settings_post_notifications_should_request
    val settings_action_go = Res.string.settings_action_go
    val settings_error_play_services_not_available = Res.string.settings_error_play_services_not_available
    val settings_error_app_from_google_play = Res.string.settings_error_app_from_google_play
    val settings_language = Res.string.settings_language
    val settings_language_en = Res.string.settings_language_en
    val settings_language_ru = Res.string.settings_language_ru
    val settings_appearance = Res.string.settings_appearance
    val settings_appearance_list = Res.string.settings_appearance_list
    val settings_appearance_grid = Res.string.settings_appearance_grid
    val settings_movie_list = Res.string.settings_movie_list
    val settings_movie_list_now_playing = Res.string.settings_movie_list_now_playing
    val settings_movie_list_popular = Res.string.settings_movie_list_popular
    val settings_movie_list_top_rated = Res.string.settings_movie_list_top_rated
    val settings_movie_list_upcoming = Res.string.settings_movie_list_upcoming
    val settings_app_launcher_icon = Res.string.settings_app_launcher_icon
    val settings_app_launcher_icon_red = Res.string.settings_app_launcher_icon_red
    val settings_app_launcher_icon_purple = Res.string.settings_app_launcher_icon_purple
    val settings_app_launcher_icon_brown = Res.string.settings_app_launcher_icon_brown
    val settings_app_launcher_icon_amoled = Res.string.settings_app_launcher_icon_amoled
    val settings_app_launcher_icon_changed_to = Res.string.settings_app_launcher_icon_changed_to
    val settings_app_version_name = Res.string.settings_app_version_name
    val settings_app_version_code = Res.string.settings_app_version_code
    val settings_app_debug = Res.string.settings_app_debug
    val settings_action_cancel = Res.string.settings_action_cancel
    val settings_github = Res.string.settings_github
    val settings_github_description = Res.string.settings_github_description
    val settings_app_widget = Res.string.settings_app_widget
    val settings_app_widget_description = Res.string.settings_app_widget_description
    val settings_tile = Res.string.settings_tile
    val settings_tile_description = Res.string.settings_tile_description
    val settings_tile_error_already_added = Res.string.settings_tile_error_already_added
    val settings_gender = Res.string.settings_gender
    val settings_gender_not_specified = Res.string.settings_gender_not_specified
    val settings_gender_neutral = Res.string.settings_gender_neutral
    val settings_gender_feminine = Res.string.settings_gender_feminine
    val settings_gender_masculine = Res.string.settings_gender_masculine
    val settings_lock_app = Res.string.settings_lock_app
    val settings_biometric_added = Res.string.settings_biometric_added
    val settings_biometric_not_added = Res.string.settings_biometric_not_added
    val settings_update = Res.string.settings_update
    val settings_update_description = Res.string.settings_update_description
    val settings_palette_colors = Res.string.settings_palette_colors
    val settings_screenshots = Res.string.settings_screenshots
    val settings_screenshots_description = Res.string.settings_screenshots_description

    @Composable
    fun settings_app_version_name(vararg formatArgs: Any): String {
        return stringResource(Res.string.settings_app_version_name, formatArgs)
    }

    val appwidget_description = Res.string.appwidget_description
    val appwidget_title = Res.string.appwidget_title
    val appwidget_configure = Res.string.appwidget_configure
    val appwidget_configure_soon = Res.string.appwidget_configure_soon
    val appwidget_pinned_message = Res.string.appwidget_pinned_message
    val appwidget_failure_empty = Res.string.appwidget_failure_empty
}