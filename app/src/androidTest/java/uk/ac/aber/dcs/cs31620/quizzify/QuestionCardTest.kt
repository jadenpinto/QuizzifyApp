package uk.ac.aber.dcs.cs31620.quizzify

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.ac.aber.dcs.cs31620.quizzify.model.cellPowerhouseQuestion
import uk.ac.aber.dcs.cs31620.quizzify.model.questionWithoutSubject
import uk.ac.aber.dcs.cs31620.quizzify.ui.components.QuestionCard

/**
 * Tests for compose UI testing of QuestionCard
 */
@RunWith(AndroidJUnit4::class)
class QuestionCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Test that the card is initially displayed in the compact layout
     */
    @Test
    fun questionCard_compactAndExpandedView() {
        composeTestRule.setContent {
            QuestionCard(question = cellPowerhouseQuestion)
        }

        // Check the compact view displays subject with the number of options
        composeTestRule.onNodeWithText("Biology; 4 options").assertExists()
        // Check the compact view displays the question
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.questionText).assertExists()
        // Check the compact view has a button to expand the card
        composeTestRule.onNodeWithContentDescription("Expand Question Button").assertExists()

        // Options should not be visible initially
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option1).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option2!!).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option3!!).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option4!!).assertDoesNotExist()

        // Expand the question
        composeTestRule
            .onAllNodesWithContentDescription("Expand Question Button")
            .onFirst()
            .performClick()

        // The question text should still be visible
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.questionText).assertExists()
        // All options should now be visible
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option1).assertExists()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option2!!).assertExists()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option3!!).assertExists()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option4!!).assertExists()
        // Check the expanded view has a button to collapse the card
        composeTestRule.onNodeWithContentDescription("Collapse Question Button").assertExists()

        // Collapse the question
        composeTestRule
            .onNodeWithContentDescription("Collapse Question Button")
            .performClick()

        // Options now should be invisible again
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option1).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option2!!).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option3!!).assertDoesNotExist()
        composeTestRule.onNodeWithText(cellPowerhouseQuestion.option4!!).assertDoesNotExist()
    }

    /**
     * Tests that the correct option is highlighted with a checkmark
     */
    @Test
    fun questionCard_correctAnswerIsHighlighted() {
        composeTestRule.setContent {
            QuestionCard(question = cellPowerhouseQuestion)
        }

        // CLick on the button to Expand the card
        composeTestRule.onNodeWithContentDescription("Expand Question Button").performClick()

        // Assert that an option has check icon beside it, this is the correct option
        composeTestRule
            .onNodeWithContentDescription("Correct Answer Checkmark Icon")
            .assertExists()
            .assertIsDisplayed()
    }

    /**
     * Test that the question card is able to render questions correctly when they don't have a subject
     */
    @Test
    fun questionCard_handlesEmptySubject() {
        val questionWithoutSubject = questionWithoutSubject

        composeTestRule.setContent {
            QuestionCard(question = questionWithoutSubject)
        }

        // In the compact view, only the number of options is displayed since subject is null
        composeTestRule.onNodeWithText("3 options").assertExists()
    }
}