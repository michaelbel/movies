package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
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
                    appVersionData = APP_VERSION_DATA,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .fillMaxWidth()
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "ConstraintLayout", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasNoClickAction()

        composeTestRule
            .onNodeWithTag(testTag = "Icon", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasNoClickAction()

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
        private val APP_VERSION_DATA: AppVersionData = AppVersionData(
            version = "1.0.0",
            code = 1L,
            flavor = "GMS",
            isDebug = true
        )
    }
}