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
import uk.ac.aber.dcs.cs31620.quizzify.ui.questions.AddQuestionScreen

/**
 * Tests for compose UI testing of AddQuestionScreen
 */
@RunWith(AndroidJUnit4::class)
class AddQuestionScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private val sampleQuestion = cellPowerhouseQuestion

    /**
     * Set up the nav controller before running the tests
     */
    @Before
    fun setupNavController() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    /**
     * Test that the add question screen displays an empty form.
     * Do not pass in an existingQuestion argument
     * This means the user is creating a new question rather than updating an existing one
     */
    @Test
    fun addQuestionScreen_displaysInputForm() {
        composeTestRule.setContent {
            AddQuestionScreen(navController = navController)
        }

        // Title in the top app bar:
        composeTestRule.onNodeWithText("Add Question").assertExists()

        // Input text fields in the form:
        composeTestRule.onNodeWithText("Question").assertExists()
        composeTestRule.onNodeWithText("Subject (optional)").assertExists()
        composeTestRule.onNodeWithText("Option 1").assertExists()
        // Initially only a single option text field is displayed

        // Save button should be disabled since the mandatory text inputs are empty
        composeTestRule.onNodeWithContentDescription("Save").assertIsNotEnabled()
    }

    /**
     * Test that the add question screen can add and remove options
     * Call the AddQuestionScreen without an existingQuestion parameter
     * This way, the form for add new question is displayed, rather than one for updating a question
     */
    @Test
    fun addQuestionScreen_modifyOptions() {
        composeTestRule.setContent {
            AddQuestionScreen(navController = navController)
        }

        // Initially, there is a single option text field for the first option
        composeTestRule.onNodeWithText("Option 1").assertExists()

        // Click on the add option button twice
        repeat(2) {
            composeTestRule.onNodeWithText("Add Option").performClick()
        }

        // Check that clicking it twice adding text input fields for options 2 and 3
        composeTestRule.onNodeWithText("Option 2").assertExists()
        composeTestRule.onNodeWithText("Option 3").assertExists()

        // Delete an option
        composeTestRule
            .onAllNodesWithContentDescription("Remove option")
            .onFirst()
            .performClick()

        // Assert that the option was deleted
        // Displayed a total of 3 options, clicking remove option, now leaves user with 2 options
        composeTestRule.onNodeWithText("Option 3").assertDoesNotExist()
    }

    /**
     * Test that the add question actually saves a question
     */
    @Test
    fun addQuestionScreen_savesQuestion() {
        // Initialise the question as null, this will be updated using the forms
        var sampleQuestion: Question? = null

        composeTestRule.setContent {
            AddQuestionScreen(
                navController = navController,
                insertQuestion = { sampleQuestion = it }
            )
        }

        // Fill in the form to add a new question:

        // Add question text
        composeTestRule
            .onNodeWithText("Question")
            .performTextInput(cellPowerhouseQuestion.questionText)

        // Add question subject
        composeTestRule
            .onNodeWithText("Subject (optional)")
            .performTextInput(cellPowerhouseQuestion.subject)

        // Add the first option
        composeTestRule
            .onNodeWithText("Option 1")
            .performTextInput(cellPowerhouseQuestion.option1)

        // Click on the button to add another option
        composeTestRule.onNodeWithText("Add Option").performClick()

        // Add the second option
        composeTestRule
            .onNodeWithText("Option 2")
            .performTextInput(cellPowerhouseQuestion.option2!!)

        // Click the save button which should be enabled to save the question
        composeTestRule
            .onNodeWithContentDescription("Save")
            .assertIsEnabled()
            .performClick()

        // Assert navigation occurred, and the user no longer sees the add question screen
        assert(navController.currentDestination?.route != "addQuestion")

        // Assert the question was updated with the user input
        assert(sampleQuestion?.questionText == cellPowerhouseQuestion.questionText)
        assert(sampleQuestion?.subject == cellPowerhouseQuestion.subject)
        assert(sampleQuestion?.option1 == cellPowerhouseQuestion.option1)
        assert(sampleQuestion?.option2 == cellPowerhouseQuestion.option2!!)
    }


    /**
     * Test that the add question screen loads an existing question correctly
     * When the AddQuestionScreen() composable is called with a non null value pf existingQuestion,
     * the form is now populated with the existing questions' data allowing users to edit it
     */
    @Test
    fun addQuestionScreen_populateExistingQuestion() {
        composeTestRule.setContent {
            AddQuestionScreen(
                navController = navController,
                existingQuestion = sampleQuestion
            )
        }

        // The top nav bar should should make it clear that the user is editing a question
        // and not adding a new one
        composeTestRule.onNodeWithText("Edit Question").assertExists()


        // Assert the existing question's data is populated in the form
        composeTestRule
            .onNodeWithText(cellPowerhouseQuestion.questionText)
            .assertExists()
        composeTestRule
            .onNodeWithText(cellPowerhouseQuestion.option1)
            .assertExists()
        composeTestRule
            .onNodeWithText(cellPowerhouseQuestion.option2!!)
            .assertExists()
        composeTestRule
            .onNodeWithText(cellPowerhouseQuestion.option3!!)
            .assertExists()
        composeTestRule
            .onNodeWithText(cellPowerhouseQuestion.option4!!)
            .assertExists()
    }

}