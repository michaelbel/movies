package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.ui.theme.MoviesTheme

internal class SettingsVersionBoxTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testSettingsVersionBox() {
        composeTestRule.setContent {
            MoviesTheme {
                SettingsVersionBox(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth(),
                    appVersionData = App_VERSION_DATA
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "ConstraintLayout", useUnmergedTree = true)
            .assertIsDisplayed()
            .assert(hasNoClickAction())

        composeTestRule
            .onNodeWithTag(testTag = "Icon", useUnmergedTree = true)
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
    }

    private companion object {
        private val App_VERSION_DATA: AppVersionData = AppVersionData("1.0.0", 1L)
    }
}