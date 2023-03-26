package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasNoClickAction
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
                    modifier = Modifier
                        .statusBarsPadding(),
                    onNavigationIconClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "TopAppBar", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "TitleText", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "BackIconButton", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "BackImage", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())
    }
}