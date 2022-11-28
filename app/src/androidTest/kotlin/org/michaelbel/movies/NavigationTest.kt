package org.michaelbel.movies

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

/**
 * Checks that the navigation flows in the app are correct.
 */
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun firstScreenIsFeed() {
        startActivity()

        composeTestRule.onNodeWithTag(testTag = "FeedContent", useUnmergedTree = true)
            .assertExists()
    }

    /*@Test
    fun feedToDetails() {
        startActivity()

        composeTestRule.waitUntil(500L) {
            composeTestRule.onAllNodesWithTag("FeedMovieBox", useUnmergedTree = true)
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule.onAllNodesWithTag("FeedMovieBox", useUnmergedTree = true)[0]
            .performClick()

        composeTestRule.onNodeWithTag(testTag = "DetailsContent", useUnmergedTree = true)
            .assertExists()
    }*/

    @Test
    fun feedToSettings() {
        startActivity()

        composeTestRule.onNodeWithTag("FeedToolbarIconButton", useUnmergedTree = true)
            .performClick()

        composeTestRule.onNodeWithTag(testTag = "SettingsContent", useUnmergedTree = true)
            .assertExists()
    }

    private fun startActivity(startDestination: String? = null) {
        composeTestRule.activity.setContent {
            if (startDestination == null) {
                MainActivityContent()
            } else {
                MainActivityContent(
                    startDestination = startDestination
                )
            }
        }
    }
}
