package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.michaelbel.movies.ui.theme.MoviesTheme

internal class SettingsNetworkRequestDelayBoxTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testSettingsNetworkRequestDelayBox() {
        composeTestRule.setContent {
            MoviesTheme {
                SettingsNetworkRequestDelayBox(
                    modifier = Modifier
                        .fillMaxWidth(),
                    delay = NETWORK_REQUEST_DELAY,
                    onDelayChangeFinished = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "ConstraintLayout", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "TitleText", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "ValueText", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "Slider", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())
    }

    private companion object {
        private const val NETWORK_REQUEST_DELAY: Int = 0
    }
}