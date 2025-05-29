package uk.ac.aber.dcs.cs31620.quizzify

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import uk.ac.aber.dcs.cs31620.quizzify.ui.components.MainPageNavigationBar
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests for compose UI testing of MainPageNavigationBar
 */
@RunWith(AndroidJUnit4::class)
class MainPageNavigationBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    /**
     * Set up the nav controller before running the tests
     */
    @Before
    fun setupNavController() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    /**
     * Tests that the navigation bar displays all items, i.e. to allow users to navigate to:
     * 1) Home Screen
     * 2) Questions Screen
     * Each item must have a logo that is clickable and text
     */
    @Test
    fun navigationBar_displaysAllItems() {
        composeTestRule.setContent {
            MainPageNavigationBar(navController = navController)
        }

        // Check the Home navigation Icon exists, is clickable, and has text that reads "Home"
        composeTestRule
            .onNodeWithContentDescription("Home", useUnmergedTree = true)
            // use the unmerged tree to get full access to the navigation bar
            .assertExists()
        composeTestRule
            .onNodeWithText("Home")
            .assertExists()
            .assertHasClickAction()

        // Check the Home Questions Icon exists, is clickable, and has text that reads "Questions"
        composeTestRule
            .onNodeWithContentDescription("Questions", useUnmergedTree = true)
            // use the unmerged tree to get full access to the navigation bar
            .assertExists()
        composeTestRule
            .onNodeWithText("Questions")
            .assertExists()
            .assertHasClickAction()
    }
}
