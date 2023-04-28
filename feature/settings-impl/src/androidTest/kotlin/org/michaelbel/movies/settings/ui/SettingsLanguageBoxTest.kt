package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.ui.theme.MoviesTheme

internal class SettingsLanguageBoxTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testSettingsLanguageBox() {
        composeTestRule.setContent {
            MoviesTheme {
                SettingsLanguageBox(
                    languages = LANGUAGES,
                    currentLanguage = CURRENT_LANGUAGE,
                    onLanguageSelect = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "ConstraintLayout", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule
            .onNodeWithTag(testTag = "TitleText", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasNoClickAction()

        composeTestRule
            .onNodeWithTag(testTag = "ValueText", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasNoClickAction()
    }

    private companion object {
        private val LANGUAGES: List<AppLanguage> = listOf(
            AppLanguage.English,
            AppLanguage.Russian
        )
        private val CURRENT_LANGUAGE: AppLanguage = AppLanguage.English
    }
}