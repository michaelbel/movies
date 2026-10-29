package org.michaelbel.movies.ui.strings

import androidx.compose.runtime.Composable
import movies.shared.ui.generated.resources.Res
import movies.shared.ui.generated.resources.account_logout
import movies.shared.ui.generated.resources.account_title
import movies.shared.ui.generated.resources.add_to_favorites
import movies.shared.ui.generated.resources.appwidget_configure
import movies.shared.ui.generated.resources.appwidget_configure_soon
import movies.shared.ui.generated.resources.appwidget_description
import movies.shared.ui.generated.resources.appwidget_failure_empty
import movies.shared.ui.generated.resources.appwidget_pinned_message
import movies.shared.ui.generated.resources.appwidget_title
import movies.shared.ui.generated.resources.auth_error_while_create_request_token
import movies.shared.ui.generated.resources.auth_error_while_create_session
import movies.shared.ui.generated.resources.auth_error_while_create_session_with_login
import movies.shared.ui.generated.resources.auth_error_while_loading_account_details
import movies.shared.ui.generated.resources.auth_label_password
import movies.shared.ui.generated.resources.auth_label_username
import movies.shared.ui.generated.resources.auth_login
import movies.shared.ui.generated.resources.auth_privacy_policy
import movies.shared.ui.generated.resources.auth_reset_password
import movies.shared.ui.generated.resources.auth_sign_in
import movies.shared.ui.generated.resources.auth_sign_up
import movies.shared.ui.generated.resources.auth_terms_of_use
import movies.shared.ui.generated.resources.auth_title
import movies.shared.ui.generated.resources.details_error_loading
import movies.shared.ui.generated.resources.details_url_copied
import movies.shared.ui.generated.resources.download
import movies.shared.ui.generated.resources.error_api_key_null
import movies.shared.ui.generated.resources.error_check_internet_connectivity
import movies.shared.ui.generated.resources.error_loading
import movies.shared.ui.generated.resources.feed_auth_failure
import movies.shared.ui.generated.resources.feed_auth_success
import movies.shared.ui.generated.resources.feed_error_empty
import movies.shared.ui.generated.resources.feed_title_now_playing
import movies.shared.ui.generated.resources.feed_title_popular
import movies.shared.ui.generated.resources.feed_title_top_rated
import movies.shared.ui.generated.resources.feed_title_upcoming
import movies.shared.ui.generated.resources.gallery_action_open
import movies.shared.ui.generated.resources.gallery_backdrop
import movies.shared.ui.generated.resources.gallery_downloading_image
import movies.shared.ui.generated.resources.gallery_failure
import movies.shared.ui.generated.resources.gallery_image_of
import movies.shared.ui.generated.resources.gallery_logo
import movies.shared.ui.generated.resources.gallery_poster
import movies.shared.ui.generated.resources.gallery_success
import movies.shared.ui.generated.resources.language_code
import movies.shared.ui.generated.resources.login
import movies.shared.ui.generated.resources.main_nav_fave
import movies.shared.ui.generated.resources.main_nav_feed
import movies.shared.ui.generated.resources.main_nav_settings
import movies.shared.ui.generated.resources.notification_continue
import movies.shared.ui.generated.resources.notification_enable_subtitle
import movies.shared.ui.generated.resources.notification_enable_title
import movies.shared.ui.generated.resources.notification_go_to_settings
import movies.shared.ui.generated.resources.remove_from_favorites
import movies.shared.ui.generated.resources.retry
import movies.shared.ui.generated.resources.search_clear
import movies.shared.ui.generated.resources.search_history_empty
import movies.shared.ui.generated.resources.search_recent
import movies.shared.ui.generated.resources.search_suggestions
import movies.shared.ui.generated.resources.search_remove
import movies.shared.ui.generated.resources.search_results_empty
import movies.shared.ui.generated.resources.search_title
import movies.shared.ui.generated.resources.settings_action_cancel
import movies.shared.ui.generated.resources.settings_action_go
import movies.shared.ui.generated.resources.settings_action_reset
import movies.shared.ui.generated.resources.settings_app_debug
import movies.shared.ui.generated.resources.settings_app_launcher_icon
import movies.shared.ui.generated.resources.settings_app_launcher_icon_black
import movies.shared.ui.generated.resources.settings_app_launcher_icon_brown
import movies.shared.ui.generated.resources.settings_app_launcher_icon_changed_to
import movies.shared.ui.generated.resources.settings_app_launcher_icon_description
import movies.shared.ui.generated.resources.settings_app_launcher_icon_purple
import movies.shared.ui.generated.resources.settings_app_launcher_icon_red
import movies.shared.ui.generated.resources.settings_app_version_code
import movies.shared.ui.generated.resources.settings_app_version_name
import movies.shared.ui.generated.resources.settings_app_widget
import movies.shared.ui.generated.resources.settings_app_widget_description
import movies.shared.ui.generated.resources.settings_appearance
import movies.shared.ui.generated.resources.settings_appearance_grid
import movies.shared.ui.generated.resources.settings_appearance_list
import movies.shared.ui.generated.resources.settings_battery_optimization
import movies.shared.ui.generated.resources.settings_battery_optimization_ignored
import movies.shared.ui.generated.resources.settings_battery_optimization_optimized
import movies.shared.ui.generated.resources.settings_biometric_added
import movies.shared.ui.generated.resources.settings_biometric_not_added
import movies.shared.ui.generated.resources.settings_do_not_disturb
import movies.shared.ui.generated.resources.settings_do_not_disturb_disabled
import movies.shared.ui.generated.resources.settings_do_not_disturb_enabled
import movies.shared.ui.generated.resources.settings_do_not_disturb_not_granted
import movies.shared.ui.generated.resources.settings_dynamic_colors
import movies.shared.ui.generated.resources.settings_dynamic_colors_description
import movies.shared.ui.generated.resources.settings_error_app_from_google_play
import movies.shared.ui.generated.resources.settings_error_play_services_not_available
import movies.shared.ui.generated.resources.settings_gender
import movies.shared.ui.generated.resources.settings_gender_feminine
import movies.shared.ui.generated.resources.settings_gender_masculine
import movies.shared.ui.generated.resources.settings_gender_neutral
import movies.shared.ui.generated.resources.settings_gender_not_specified
import movies.shared.ui.generated.resources.settings_github
import movies.shared.ui.generated.resources.settings_github_description
import movies.shared.ui.generated.resources.settings_language
import movies.shared.ui.generated.resources.settings_language_en
import movies.shared.ui.generated.resources.settings_language_ru
import movies.shared.ui.generated.resources.settings_lock_app
import movies.shared.ui.generated.resources.settings_movie_list
import movies.shared.ui.generated.resources.settings_movie_list_now_playing
import movies.shared.ui.generated.resources.settings_movie_list_popular
import movies.shared.ui.generated.resources.settings_movie_list_top_rated
import movies.shared.ui.generated.resources.settings_movie_list_upcoming
import movies.shared.ui.generated.resources.settings_open_by_default
import movies.shared.ui.generated.resources.settings_open_by_default_description
import movies.shared.ui.generated.resources.settings_palette_colors
import movies.shared.ui.generated.resources.settings_palette_colors_description
import movies.shared.ui.generated.resources.settings_post_notifications
import movies.shared.ui.generated.resources.settings_post_notifications_denied
import movies.shared.ui.generated.resources.settings_post_notifications_granted
import movies.shared.ui.generated.resources.settings_post_notifications_should_request
import movies.shared.ui.generated.resources.settings_provider
import movies.shared.ui.generated.resources.settings_provider_tmdb
import movies.shared.ui.generated.resources.settings_provider_trakt
import movies.shared.ui.generated.resources.settings_reset
import movies.shared.ui.generated.resources.settings_reset_confirmation
import movies.shared.ui.generated.resources.settings_review
import movies.shared.ui.generated.resources.settings_review_description
import movies.shared.ui.generated.resources.settings_screenshots
import movies.shared.ui.generated.resources.settings_screenshots_description
import movies.shared.ui.generated.resources.settings_telegram
import movies.shared.ui.generated.resources.settings_telegram_description
import movies.shared.ui.generated.resources.settings_google_play
import movies.shared.ui.generated.resources.settings_google_play_description
import movies.shared.ui.generated.resources.settings_theme
import movies.shared.ui.generated.resources.settings_theme_dark
import movies.shared.ui.generated.resources.settings_theme_light
import movies.shared.ui.generated.resources.settings_theme_system
import movies.shared.ui.generated.resources.settings_tile
import movies.shared.ui.generated.resources.settings_tile_description
import movies.shared.ui.generated.resources.settings_tile_error_already_added
import movies.shared.ui.generated.resources.settings_title
import movies.shared.ui.generated.resources.settings_update
import movies.shared.ui.generated.resources.settings_update_description
import movies.shared.ui.generated.resources.settings_update_not_available
import movies.shared.ui.generated.resources.share
import movies.shared.ui.generated.resources.share_via
import movies.shared.ui.generated.resources.shortcuts_search_title
import movies.shared.ui.generated.resources.shortcuts_settings_title
import movies.shared.ui.generated.resources.tile_added
import movies.shared.ui.generated.resources.tile_title
import movies.shared.ui.generated.resources.voice_search
import org.jetbrains.compose.resources.stringResource

object MoviesStrings {
    val language_code = Res.string.language_code
    val main_nav_feed = Res.string.main_nav_feed
    val main_nav_fave = Res.string.main_nav_fave
    val main_nav_settings = Res.string.main_nav_settings
    val shortcuts_search_title = Res.string.shortcuts_search_title
    val shortcuts_settings_title = Res.string.shortcuts_settings_title
    val notification_enable_title = Res.string.notification_enable_title
    val notification_enable_subtitle = Res.string.notification_enable_subtitle
    val notification_continue = Res.string.notification_continue
    val notification_go_to_settings = Res.string.notification_go_to_settings
    val retry = Res.string.retry
    val error_api_key_null = Res.string.error_api_key_null
    val error_loading = Res.string.error_loading
    val error_check_internet_connectivity = Res.string.error_check_internet_connectivity
    val add_to_favorites = Res.string.add_to_favorites
    val remove_from_favorites = Res.string.remove_from_favorites
    val download = Res.string.download
    val login = Res.string.login
    val share = Res.string.share
    val share_via = Res.string.share_via
    val voice_search = Res.string.voice_search
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

    val search_title = Res.string.search_title
    val search_history_empty = Res.string.search_history_empty
    val search_results_empty = Res.string.search_results_empty
    val search_recent = Res.string.search_recent
    val search_clear = Res.string.search_clear
    val search_remove = Res.string.search_remove
    val search_suggestions = Res.string.search_suggestions


    val settings_title = Res.string.settings_title
    val settings_reset = Res.string.settings_reset
    val settings_theme = Res.string.settings_theme
    val settings_theme_system = Res.string.settings_theme_system
    val settings_theme_light = Res.string.settings_theme_light
    val settings_theme_dark = Res.string.settings_theme_dark
    val settings_dynamic_colors = Res.string.settings_dynamic_colors
    val settings_dynamic_colors_description = Res.string.settings_dynamic_colors_description
    val settings_post_notifications = Res.string.settings_post_notifications
    val settings_post_notifications_denied = Res.string.settings_post_notifications_denied
    val settings_post_notifications_granted = Res.string.settings_post_notifications_granted
    val settings_do_not_disturb = Res.string.settings_do_not_disturb
    val settings_do_not_disturb_not_granted = Res.string.settings_do_not_disturb_not_granted
    val settings_do_not_disturb_enabled = Res.string.settings_do_not_disturb_enabled
    val settings_do_not_disturb_disabled = Res.string.settings_do_not_disturb_disabled
    val settings_battery_optimization = Res.string.settings_battery_optimization
    val settings_battery_optimization_optimized = Res.string.settings_battery_optimization_optimized
    val settings_battery_optimization_ignored = Res.string.settings_battery_optimization_ignored
    val settings_review = Res.string.settings_review
    val settings_review_description = Res.string.settings_review_description
    val settings_post_notifications_should_request = Res.string.settings_post_notifications_should_request
    val settings_reset_confirmation = Res.string.settings_reset_confirmation
    val settings_action_reset = Res.string.settings_action_reset
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
    val settings_app_launcher_icon_description = Res.string.settings_app_launcher_icon_description
    val settings_open_by_default = Res.string.settings_open_by_default
    val settings_open_by_default_description = Res.string.settings_open_by_default_description
    val settings_app_launcher_icon_red = Res.string.settings_app_launcher_icon_red
    val settings_app_launcher_icon_purple = Res.string.settings_app_launcher_icon_purple
    val settings_app_launcher_icon_brown = Res.string.settings_app_launcher_icon_brown
    val settings_app_launcher_icon_black = Res.string.settings_app_launcher_icon_black
    val settings_app_launcher_icon_changed_to = Res.string.settings_app_launcher_icon_changed_to
    val settings_app_version_name = Res.string.settings_app_version_name
    val settings_app_version_code = Res.string.settings_app_version_code
    val settings_app_debug = Res.string.settings_app_debug
    val settings_action_cancel = Res.string.settings_action_cancel
    val settings_github = Res.string.settings_github
    val settings_github_description = Res.string.settings_github_description
    val settings_telegram = Res.string.settings_telegram
    val settings_telegram_description = Res.string.settings_telegram_description
    val settings_google_play = Res.string.settings_google_play
    val settings_google_play_description = Res.string.settings_google_play_description
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
    val settings_update_not_available = Res.string.settings_update_not_available
    val settings_palette_colors = Res.string.settings_palette_colors
    val settings_palette_colors_description = Res.string.settings_palette_colors_description
    val settings_screenshots = Res.string.settings_screenshots
    val settings_screenshots_description = Res.string.settings_screenshots_description
    val settings_provider = Res.string.settings_provider
    val settings_provider_tmdb = Res.string.settings_provider_tmdb
    val settings_provider_trakt = Res.string.settings_provider_trakt

    @Suppress("FunctionNaming")
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
