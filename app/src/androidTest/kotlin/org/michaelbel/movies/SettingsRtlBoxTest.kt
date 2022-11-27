package org.michaelbel.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import org.michaelbel.movies.settings.ui.SettingsRtlBox
import org.michaelbel.movies.ui.theme.MoviesTheme

class SettingsRtlBoxTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testSettingsRtlBox() {
        composeTestRule.setContent {
            MoviesTheme {
                SettingsRtlBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {},
                    isRtlEnabled = IS_RTL_ENABLED
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "ConstraintLayout", useUnmergedTree = true)
            .assert(hasClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "Text", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "Switch", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())
    }

    private companion object {
        private const val IS_RTL_ENABLED = true
    }
}