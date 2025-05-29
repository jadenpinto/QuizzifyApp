package uk.ac.aber.dcs.cs31620.quizzify

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.ac.aber.dcs.cs31620.quizzify.ui.components.MainPageTopAppBar

/**
 * Tests for compose UI testing of MainPageTopAppBar
 */
@RunWith(AndroidJUnit4::class)
class MainPageTopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Tests that the main page top app bar displays:
     * 1) The app title, QUIZZIFY, as text
     * 2) A graduation cap icon
     * Both of these elements should not clickable
     */
    @Test
    fun mainPageTopAppBar_displaysAllElements() {
        composeTestRule.setContent {
            MainPageTopAppBar()
        }

        // Check if app name is displayed
        composeTestRule
            .onNodeWithText("QUIZZIFY")
            .assertExists()
            .assertHasNoClickAction()

        // Check if Graduation Cap Icon icon is displayed
        composeTestRule
            .onNodeWithContentDescription("Graduation Cap Icon")
            .assertExists()
            .assertHasClickAction()
    }

}