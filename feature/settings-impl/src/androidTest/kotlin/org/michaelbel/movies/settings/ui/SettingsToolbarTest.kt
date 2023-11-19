package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.michaelbel.movies.ui.theme.MoviesTheme

internal class SettingsToolbarTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testSettingsToolbar() {
        composeTestRule.setContent {
            MoviesTheme {
                SettingsToolbar(
                    modifier = Modifier.statusBarsPadding(),
                    onNavigationIconClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "TopAppBar", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasNoClickAction()

        composeTestRule
            .onNodeWithTag(testTag = "TitleText", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasNoClickAction()

        composeTestRule
            .onNodeWithTag(testTag = "BackIconButton", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule
            .onNodeWithTag(testTag = "BackImage", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasNoClickAction()
    }
}