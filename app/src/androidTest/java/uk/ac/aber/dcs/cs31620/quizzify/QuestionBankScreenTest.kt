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
import uk.ac.aber.dcs.cs31620.quizzify.model.Question
import uk.ac.aber.dcs.cs31620.quizzify.model.cellPowerhouseQuestion
import uk.ac.aber.dcs.cs31620.quizzify.model.circleCircumferenceQuestion
import uk.ac.aber.dcs.cs31620.quizzify.ui.questions.QuestionBankScreen

/**
 * Tests for compose UI testing of QuestionBankScreen
 */
@RunWith(AndroidJUnit4::class)
class QuestionBankScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    private val sampleQuestions = listOf(
        cellPowerhouseQuestion,
        circleCircumferenceQuestion
    )

    /**
     * Set up the nav controller before running the tests
     */
    @Before
    fun setupNavController() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    /**
     * Test that the question bank informs whens when the question bank is empty
     */
    @Test
    fun questionBankScreen_NoQuestions() {
        composeTestRule.setContent {
            QuestionBankScreen(
                navController = navController,
                allQuestions = emptyList()
            )
        }

        // Check that it displays a message informing users question bank is empty
        composeTestRule.onNodeWithText("Empty Question Bank!").assertExists()
        // Check that it displays a message informing users how to add a question to the question bank
        composeTestRule.onNodeWithText("No questions added yet. Tap the plus button to create your first question.").assertExists()
        // Check that an illustration is a person chasing paper is displayed
        composeTestRule.onNodeWithContentDescription("Illustration of a person chasing pieces of paper").assertExists()
    }

    /**
     * Tests that the question cards are displayed when the question bank contains questions
     */
    @Test
    fun questionBankScreen_showsQuestions() {
        composeTestRule.setContent {
            QuestionBankScreen(
                navController = navController,
                allQuestions = sampleQuestions
            )
        }

        // Check to see if the questions cards are displayed - subject, option count
        composeTestRule.onNodeWithText("Maths; 3 options").assertExists()
        composeTestRule.onNodeWithText("Biology; 4 options").assertExists()
    }


    /**
     * Test that the question can be deleted from the question bank
     */
    @Test
    fun questionBankScreen_deleteQuestionWorks() {
        // Initialise the deleted question as null
        var deletedQuestion: Question? = null

        composeTestRule.setContent {
            QuestionBankScreen(
                navController = navController,
                allQuestions = sampleQuestions,
                deleteQuestion = { deletedQuestion = it }
            )
        }

        // Find and click first delete button to delete the first question
        composeTestRule
            .onAllNodesWithContentDescription("Delete Question Button")
            .onFirst()
            .performClick()

        // Assert that the first question question was deleted
        assert(deletedQuestion == sampleQuestions[0])
    }

    /**
     * Test that the question can be edited
     */
    @Test
    fun questionBankScreen_editQuestionWorks() {
        // Initialise the edited question as null
        var editedQuestion: Question? = null

        composeTestRule.setContent {
            QuestionBankScreen(
                navController = navController,
                allQuestions = sampleQuestions,
                editQuestion = { editedQuestion = it }
            )
        }

        // Find and click first edit button to edit the first question
        composeTestRule
            .onAllNodesWithContentDescription("Edit Question Button")
            .onFirst()
            .performClick()

        // Asset that the first question question was edited
        assert(editedQuestion == sampleQuestions[0])
    }

    /**
     * Test that the question cards in the question bank can be expanded and collapsed
     */
    @Test
    fun questionBankScreen_expandsAndCollapsesQuestions() {
        composeTestRule.setContent {
            QuestionBankScreen(
                navController = navController,
                allQuestions = sampleQuestions
            )
        }

        // Initially all options should invisible
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option1).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option2!!).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option3!!).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option4!!).assertDoesNotExist()

        // Expand the first question
        composeTestRule
            .onAllNodesWithContentDescription("Expand Question Button")
            .onFirst()
            .performClick()

        // All options should now be visible
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option1).assertExists()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option2!!).assertExists()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option3!!).assertExists()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option4!!).assertExists()



        // Collapse the first question
        composeTestRule
            .onNodeWithContentDescription("Collapse Question Button")
            .performClick()

        // Options now should be invisible again
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option1).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option2!!).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option3!!).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option4!!).assertDoesNotExist()
    }

}