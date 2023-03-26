package org.michaelbel.movies.settings.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.theme.MoviesTheme

internal class SettingsThemeDialogTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testSettingsThemeDialog() {
        composeTestRule.setContent {
            MoviesTheme {
                SettingThemeDialog(
                    themes = THEMES,
                    currentTheme = CURRENT_THEME,
                    onThemeSelect = {},
                    onDismissRequest = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "ConfirmTextButton", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "ConfirmText", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "Icon", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "Title", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "Content", useUnmergedTree = true)
            .assertIsDisplayed()
    }

    private companion object {
        private val THEMES: List<AppTheme> = listOf(
            AppTheme.NightNo,
            AppTheme.NightYes,
            AppTheme.FollowSystem
        )
        private val CURRENT_THEME = AppTheme.FollowSystem
    }
}