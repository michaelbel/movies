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
import org.michaelbel.movies.ui.theme.MoviesTheme

internal class SettingsPostNotificationsBoxTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun testSettingsPostNotificationsBox() {
        composeTestRule.setContent {
            MoviesTheme {
                SettingsPostNotificationsBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onShowPermissionSnackbar = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "ConstraintLayout", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasClickAction()

        composeTestRule
            .onNodeWithTag(testTag = "Text", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasNoClickAction()

        composeTestRule
            .onNodeWithTag(testTag = "Switch", useUnmergedTree = true)
            .assertIsDisplayed()
            .assertHasNoClickAction()
    }
}