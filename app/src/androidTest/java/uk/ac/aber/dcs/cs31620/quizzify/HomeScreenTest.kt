package uk.ac.aber.dcs.cs31620.quizzify

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.ac.aber.dcs.cs31620.quizzify.ui.home.HomeScreen
import uk.ac.aber.dcs.cs31620.quizzify.ui.home.HomeScreenContent

/**
 * Tests for compose UI testing of HomeScreen
 */
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
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
     * Tests the home screen shows all content initially:
     * 1) Rules Card
     * 2) Floating action button to launch a quiz
     * 3) Illustration of a person reading a book
     */
    @Test
    fun homeScreen_showsAllContentInitially() {
        composeTestRule.setContent {
            HomeScreen(
                navController = navController,
                allQuestions = emptyList()
            )
        }

        // Check that the Rules card is displayed
        composeTestRule.onNodeWithText("Rules:").assertExists()

        // Check that the illustration is displayed
        composeTestRule.onNodeWithContentDescription("Illustration of a person reading a book").assertExists()

        // Check FAB exists
        composeTestRule.onNodeWithContentDescription("Launch Quiz FAB")
            .assertExists()
    }

    /**
     * Tests that the home screen displays dialog when user attempts to launch quiz with empty question bank
     */
    @Test
    fun homeScreen_showsDialogWhenStartingQuizWithNoQuestions() {
        composeTestRule.setContent {
            HomeScreen(
                navController = navController,
                // Empty Question Bank
                allQuestions = emptyList()
            )
        }

        // Try to launch a quiz mode by click the launch quiz FAB
        composeTestRule
            .onNodeWithContentDescription("Launch Quiz FAB")
            .performClick()

        // Check that the dialog appears informing users that a quiz cannot be started
        composeTestRule.onNodeWithText("Add Questions to Bank?").assertExists()
        composeTestRule.onNodeWithText("Your question bank is currently empty. You need to add questions before you can start a quiz.")
            .assertExists()
        composeTestRule.onNodeWithText("OK").assertExists()
        composeTestRule.onNodeWithText("Not Now").assertExists()
    }


    /**
     * Test actions on the dialog displayed in the home screen when a quiz is launched with no questions
     */
    @Test
    fun homeScreen_dialogActions() {
        composeTestRule.setContent {
            HomeScreen(
                navController = navController,
                // Empty Question Bank
                allQuestions = emptyList()
            )
        }

        // Attempt to start a new quiz by clicking FAB
        composeTestRule
            .onNodeWithContentDescription("Launch Quiz FAB")
            .performClick()

        // Check dialog is shown
        composeTestRule.onNodeWithText("Add Questions to Bank?").assertExists()

        // Click Not Now button
        composeTestRule
            .onNodeWithText("Not Now")
            .performClick()

        // Check dialog is dismissed
        composeTestRule.onNodeWithText("Add Questions to Bank?").assertDoesNotExist()
    }

    /**
     * Tests that hte home screen content displays all 3 quiz rules
     * Each rule is displayed using text and an icon
     */
    @Test
    fun homeScreenContent_displaysAllRulesWithIcons() {
        composeTestRule.setContent {
            HomeScreenContent()
        }

        // Assert all rules are displayed - each rule containing text and an icon

        // Rule 1 - Score is shown at the end, after all questions are displayed
        composeTestRule.onNodeWithContentDescription("Timer Icon").assertExists()
        composeTestRule.onNodeWithText("Score shown at the end; quiz ends when all questions are answered").assertExists()

        // Rule 2 - Questions are displayed in a random order
        composeTestRule.onNodeWithContentDescription("Shuffle Icon").assertExists()
        composeTestRule.onNodeWithText("Questions are displayed in a random order").assertExists()

        // Rule 3 - Skipping a question counts as an incorrect answer
        composeTestRule.onNodeWithContentDescription("Skip Icon").assertExists()
        composeTestRule.onNodeWithText("Skipping a question counts as an incorrect answer").assertExists()
    }

}