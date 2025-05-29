package uk.ac.aber.dcs.cs31620.quizzify.ui.questions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizzify.R
import uk.ac.aber.dcs.cs31620.quizzify.model.Question
import uk.ac.aber.dcs.cs31620.quizzify.model.QuestionsViewModel
import uk.ac.aber.dcs.cs31620.quizzify.model.lengthyHistoryQuestion
import uk.ac.aber.dcs.cs31620.quizzify.ui.theme.QuizzifyTheme

/**
 * AddQuestionScreenTopLevel used for adding new questions or editing existing questions
 * Add questions top level screen loads the screen with blank input fields to add new question by default
 * If it is called with a questionId, it loads the data of the question and populates the input fields
 */
@Composable
fun AddQuestionScreenTopLevel(
    navController: NavHostController,
    questionsViewModel: QuestionsViewModel = viewModel(),
    questionId: Long? = null
) {
    // Set to null by default:
    // - If it's called with default null value, it means user is creating a new question
    // - Otherwise, they're editing existing an question
    var existingQuestion: Question? = null

    // If questionId is provided, user is editing an existing question, load it from the DB
    if (questionId != null) {
        val questionTemp by questionsViewModel.getQuestionById(questionId)
            .observeAsState()
        existingQuestion = questionTemp
    }

    AddQuestionScreen(
        navController = navController,
        existingQuestion = existingQuestion,
        insertQuestion = { newQuestion ->
            questionsViewModel.insertQuestion(newQuestion)
        }
    )
}

/**
 * AddQuestionScreen composable components displays to users the input form to add or edit a question
 * If it is called with an existing question, the fields are populated with the question data for editing
 * Otherwise, the fields are left blank, allowing users to input the data for the new questions
 */
@Composable
fun AddQuestionScreen(
    navController: NavHostController,
    insertQuestion: (Question) -> Unit = {},
    existingQuestion: Question? = null
) {

    // User input variables for question. Initialise them as null (empty for strings, and 0 for numbers)
    var questionText by rememberSaveable { mutableStateOf("") }
    var options by rememberSaveable { mutableStateOf(listOf("")) }
    var selectedOptionIndex by rememberSaveable { mutableStateOf(0) }
    var subject by rememberSaveable { mutableStateOf("") }

    // If editing an existing question, update the variables using the questions data for populating fields
    if (existingQuestion != null) {

        // Get question's text
        questionText = existingQuestion.questionText

        // Get question's subject
        subject = existingQuestion.subject

        // Get question's options
        val questionOptions = listOfNotNull(
            existingQuestion.option1,
            existingQuestion.option2,
            existingQuestion.option3,
            existingQuestion.option4,
            existingQuestion.option5,
            existingQuestion.option6,
            existingQuestion.option7,
            existingQuestion.option8,
            existingQuestion.option9,
            existingQuestion.option10
        )
        options = questionOptions


        // Get the index of correct answer in options list
        // Iterate though all options, if it's text matches that of the correct answer, store it's index

        selectedOptionIndex = questionOptions.indexOfFirst {
            it == existingQuestion.correctAnswer
            // Set selectedOptionIndex to index of the first questionOptions element matching correctAnswer
        }.takeIf { it >= 0 } ?: 0
        // Error handling:  If no match is found (indexOfFirst returns -1), so default it to 0 instead

    }


    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(
                    // Title of the form depends on whether the user is adding a new question or editing one
                    if (existingQuestion != null) stringResource(R.string.edit_question_title) else stringResource(R.string.add_question_title)
                ) },
                navigationIcon = {
                    // Back button
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                },
                actions = {
                    IconButton(
                        // Save button
                        onClick = {
                            // Fetch all question data
                            val questionToSave = (existingQuestion ?: Question()).apply {
                                id = existingQuestion?.id ?: 0
                                this.questionText = questionText
                                this.subject = subject
                                this.correctAnswer = options[selectedOptionIndex]
                                option1 = options.getOrNull(0) ?: ""
                                option2 = options.getOrNull(1)
                                option3 = options.getOrNull(2)
                                option4 = options.getOrNull(3)
                                option5 = options.getOrNull(4)
                                option6 = options.getOrNull(5)
                                option7 = options.getOrNull(6)
                                option8 = options.getOrNull(7)
                                option9 = options.getOrNull(8)
                                option10 = options.getOrNull(9)
                            }
                            // Insert the question into into the DB
                            insertQuestion(questionToSave)
                            // Use Navigation controller to navigate back to the Question Bank Screen
                            navController.navigateUp()
                        },
                        // Users can only save a question if they've entered:
                        // 1) Question text
                        // 2) All options (Not necessarily all 10, but rather all option forms must be filled
                        // Note that subject is not a required hence is not included in the enabled check
                        enabled = questionText.isNotBlank() && options.all { it.isNotBlank() }
                    ) {
                        Icon(
                            // Icon button to save the question
                            Icons.Filled.Check,
                            contentDescription = stringResource(R.string.save_button)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            // Form for the Question
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                // Text field to enter the question's text
                value = questionText,
                onValueChange = { questionText = it },
                label = { Text(stringResource(R.string.question_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                // Text field to enter the question's subject
                value = subject,
                onValueChange = { subject = it },
                label = { Text(stringResource(R.string.optional_subject_label)) },
                modifier = Modifier.fillMaxWidth()
            )

            // Iterate through every option with its index
            options.forEachIndexed { index, option ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RadioButton(
                        // RadioButton so the user can only mark one option as the right one
                        // When clicked, updates the selected options index to the current option's index
                        selected = selectedOptionIndex == index,
                        onClick = { selectedOptionIndex = index }
                    )
                    OutlinedTextField(
                        // Text field to enter the option text
                        value = option,
                        onValueChange = { newValue ->
                            options = options.toMutableList().apply {
                                set(index, newValue)
                            }
                        },
                        label = { Text("${stringResource(id = R.string.option_label)} ${index + 1}") },
                        modifier = Modifier.weight(1f)
                    )
                    if (index > 0) {
                        // For all options besides the first, include the delete button
                        // The first option cannot be delete because every question must have at least one option
                        // This allows users to reduced the number of options while having at least one
                        IconButton(
                            // Delete Icon Button
                            onClick = {
                                options = options.toMutableList().apply {
                                    removeAt(index)
                                }
                                // If the user deletes an option that was marked as the correct answer
                                // Select its next option as the correct answer (last option in list)
                                // If all options but the first remain, the first is marked as correct
                                if (selectedOptionIndex >= index) {
                                    if (selectedOptionIndex > options.size - 1) {
                                        selectedOptionIndex = options.size - 1
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.remove_option_button))
                        }
                    }
                }
            }

            Button(
                // Add new option - disable when user has entered 10 options (maximum allowed limit)
                onClick = { options = options + "" },
                enabled = options.size < 10,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                // Plus Icon to add option
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.add_option_button))
            }
        }
    }
}


/**
 * Preview Function for the Add Screen - To Add a new question
 */
@Preview(showBackground = true)
@Composable
fun AddQuestionScreenPreview() {
    val navController = rememberNavController()

    QuizzifyTheme(dynamicColor = false) {
        AddQuestionScreen(
            navController = navController,
            existingQuestion = null
        )
    }
}

/**
 * Preview Function for the Add Screen - To Edit an existing question
 */
@Preview(showBackground = true)
@Composable
fun EditQuestionScreenPreview() {
    val navController = rememberNavController()

    QuizzifyTheme(dynamicColor = false) {
        AddQuestionScreen(
            navController = navController,
            existingQuestion = lengthyHistoryQuestion
        )
    }
}
