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
import uk.ac.aber.dcs.cs31620.quizzify.model.cellPowerhouseQuestion
import uk.ac.aber.dcs.cs31620.quizzify.ui.quiz.QuizScreen

/**
 * Tests for compose UI testing of QuizScreen
 */
@RunWith(AndroidJUnit4::class)
class QuizScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    private val sampleQuestions = listOf(
        cellPowerhouseQuestion
    )

    /**
     * Set up the nav controller before running the tests
     */
    @Before
    fun setupNavController() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    /**
     * Test that the quiz screen displays all components of a questions:
     * 1) Question Count and total number of questions
     * 2) Question Text
     * 3) All Options
     */
    @Test
    fun quizScreen_DisplaysQuestion() {
        composeTestRule.setContent {
            QuizScreen(
                navController = navController,
                allQuestions = sampleQuestions
            )
        }

        // Assert question tracker is displayed
        composeTestRule.onNodeWithText("Question 1 of 1").assertExists()
        // Assert Question text is displayed
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.questionText).assertExists()
        // Assert all options are displayed
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option1).assertExists()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option2!!).assertExists()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option3!!).assertExists()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option4!!).assertExists()
    }

    /**
     * Test that the options displayed using radio buttons in the quiz screen can be selected
     */
    @Test
    fun quizScreen_CanSelectOption() {
        composeTestRule.setContent {
            QuizScreen(
                navController = navController,
                allQuestions = sampleQuestions
            )
        }

        // Click on an option
//        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option1).performClick()
        composeTestRule.onNodeWithText("Mitochondria").performClick()

        // Assert that the radio button is selected
//        composeTestRule.onNode(hasParent(hasText(cellPowerhouseQuestion.option1)) and hasClickAction())
//            .assertIsSelected()
        composeTestRule.onNode(hasParent(hasText("Mitochondria")) and hasClickAction())
            .assertIsSelected()
    }


    /**
     * Tests that the quiz screen displays the score at the end of the quiz, after all questions
     */
    @Test
    fun quizScreen_DisplaysScoreAtEnd() {
        composeTestRule.setContent {
            QuizScreen(
                navController = navController,
                allQuestions = sampleQuestions
            )
        }

        // Select the correct option
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.correctAnswer).performClick()
        // Click on the button to finish the quiz (as this is the last, and only question)
        composeTestRule.onNodeWithText("View Score").performClick()

        // Check the top app bar indicates the quiz is completed
        composeTestRule.onNodeWithText("Quiz Completed!").assertExists()
        // Check for an illustration of a person and a dog is displayed
        composeTestRule.onNodeWithContentDescription("Illustration of a person and a dog").assertExists()
        // Check that score is displayed
        composeTestRule.onNodeWithText("Score: 1/1").assertExists()
        // Check that a button to exit out of the quiz mode is displayed
        composeTestRule.onNodeWithText("Finish").assertExists()
    }

    /**
     * Test that the user is able to skip a question, and in doing so, counts as an incorrect answer
     */
    @Test
    fun quizScreen_SkipCountsAsIncorrect() {
        composeTestRule.setContent {
            QuizScreen(
                navController = navController,
                allQuestions = sampleQuestions
            )
        }

        // Skip the question without selecting an answer
        // Click on the button to finish the quiz (as this is the last, and only question)
        composeTestRule.onNodeWithText("View Score").performClick()

        composeTestRule.onNodeWithText("Quiz Completed!").assertExists()
        composeTestRule.onNodeWithContentDescription("Illustration of a person and a dog").assertExists()
        // Check if score is displayed - should be 0 since the question was skipped
        composeTestRule.onNodeWithText("Score: 0/1").assertExists()
        composeTestRule.onNodeWithText("Finish").assertExists()
    }

}
