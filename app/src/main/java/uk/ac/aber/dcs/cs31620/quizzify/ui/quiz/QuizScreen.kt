package uk.ac.aber.dcs.cs31620.quizzify.ui.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizzify.R
import uk.ac.aber.dcs.cs31620.quizzify.model.Question
import uk.ac.aber.dcs.cs31620.quizzify.model.QuestionsViewModel
import uk.ac.aber.dcs.cs31620.quizzify.model.cellPowerhouseQuestion
import uk.ac.aber.dcs.cs31620.quizzify.model.circleCircumferenceQuestion
import uk.ac.aber.dcs.cs31620.quizzify.model.rectanglePerimeterQuestion
import uk.ac.aber.dcs.cs31620.quizzify.ui.theme.QuizzifyTheme

/**
 * QuizScreenTopLevel Composable is displayed when the user launches Quiz Mode
 * It shows questions in a random order allowing users to pick an option or skip the question
 * Displays score at the end
 */
@Composable
fun QuizScreenTopLevel(
    navController: NavHostController,
    questionsViewModel: QuestionsViewModel = viewModel()
) {
    val allQuestions by questionsViewModel.allQuestions.observeAsState(listOf())

    QuizScreen(
        navController = navController,
        allQuestions = allQuestions
    )
}

/**
 * QuizScreen displaying a question at a time in random order
 * Informs the user how many the count of current question and the total number of questions in the bank
 * For every question, it displays the question's text with the list of all options using radio buttons
 * Users can pick an option by selecting the radio buttons and then click the next question button
 * Alternatively, users can skip the question by clicking the next question button
 * After all questions have been attempted, the user is shown the final score of their attempt
 */
@Composable
fun QuizScreen(
    navController: NavHostController,
    allQuestions: List<Question>
) {
    // Shuffle the questions so that they are displayed in a random order during the quiz
    val randomisedQuestions by rememberSaveable {
        mutableStateOf(allQuestions.shuffled())
    }

    // Index of the current question, used to track progress through the quiz
    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }
    // Index of the option that is selected by the user
    // Default value is null which is unchanged if user does not select an option for a question
    var selectedOptionIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    // Counter to keep track of the total number of correct answers
    var correctAnswerCount by rememberSaveable { mutableStateOf(0) }

    // Quiz is completed when the current question index equals (or exceeds) the size of the total questions
    // For example, if the database has 10 questions, quiz is completed if user gets to question index 10
    val isQuizCompleted = currentQuestionIndex >= randomisedQuestions.size

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    // Top App Bar - Uses to track progress throughout the quiz
                    Text(
                        if (!isQuizCompleted) {
                            // While displaying questions, it informs user of current question count and
                            // the total number of question in the bank. For example: Question 4 of 10
                            "${stringResource(R.string.question)} ${currentQuestionIndex + 1} ${stringResource(R.string.of)} ${randomisedQuestions.size}"
                        } else {
                            // When the quiz is complete, the top app bar displays "Quiz Complete!"
                            stringResource(R.string.quiz_completed)
                        }
                    )
                },
                navigationIcon = {
                    // Icon of graduation cap used for branding
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.School,
                            contentDescription = stringResource(R.string.graduation)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isQuizCompleted) {
                // While the quiz is still running and incomplete:

                // Get the current question using the current question index
                val currentQuestion = randomisedQuestions[currentQuestionIndex]

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        // Display question text
                        text = currentQuestion.questionText,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                // Get the list of options (not null - valid options)
                val options = listOfNotNull(
                    currentQuestion.option1,
                    currentQuestion.option2,
                    currentQuestion.option3,
                    currentQuestion.option4,
                    currentQuestion.option5,
                    currentQuestion.option6,
                    currentQuestion.option7,
                    currentQuestion.option8,
                    currentQuestion.option9,
                    currentQuestion.option10
                )

                // Iterate through every option and its index
                options.forEachIndexed { index, option ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { selectedOptionIndex = index }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Display the option's text
                            Text(text = option)

                            // To always position RadioButton on the rightmost side of the Row
                            Spacer(modifier = Modifier.weight(1f))

                            // Display a radio button next to each option so users can select the question
                            RadioButton(
                                selected = selectedOptionIndex == index,
                                onClick = { selectedOptionIndex = index }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Next Question Button - Used to move to the next question
                // It is always enabled allowing users to skip a question as they do not have to attempt
                // every question in order to move on to the next
                Button(
                    onClick = {
                        // Check if the user selected the correct answer
                        if (selectedOptionIndex != null) {
                            // If the user selected an option
                            val selectedAnswer = options[selectedOptionIndex!!]
                            if (selectedAnswer == currentQuestion.correctAnswer) {
                                // And if the option is the answer to the question, increment the score
                                correctAnswerCount++
                            }
                        }

                        // Move to next question
                        currentQuestionIndex++
                        selectedOptionIndex = null
                    },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Icon(
                        // Submit Icon on the Next question button
                        imageVector = Icons.AutoMirrored.Outlined.Send,
                        contentDescription = stringResource(id = R.string.submit_icon),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Text(
                        text = if (currentQuestionIndex == randomisedQuestions.size - 1)
                            // For the last question, pressing the button ends quiz, showing the final score
                            stringResource(R.string.view_score)
                        else
                            // For all other questions, pressing the button shows the next question
                            stringResource(R.string.next_question)
                    )
                }
            } else {
                // Quiz Completed Screen
                Image(
                    // Illustration to help with branding and fill whitespace for better UI/UX
                    painter = painterResource(
                        id = R.drawable.jumping_dog
                    ),
                    contentDescription = stringResource(id = R.string.jumping_dog_illustration),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.size(300.dp)
                )

                ElevatedCard(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        // Shows the score along with the total number of questions in the quiz
                        text = "${stringResource(R.string.score)}: $correctAnswerCount/${randomisedQuestions.size}",
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(120.dp))

                Button(
                    // Button in the score screen to finish the quiz
                    // Ends the quiz mode, and navigates user back to the home screen
                    onClick = {
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(45.dp)
                ) {
                    Text(
                        // Simple button with no icon, only text that reads "Finish"
                        text = stringResource(R.string.finish),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}


/**
 * Preview function for the quiz screen
 */
@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    val navController = rememberNavController()

    val questionsList = mutableListOf(
        rectanglePerimeterQuestion,
        circleCircumferenceQuestion,
        cellPowerhouseQuestion
    )

    QuizzifyTheme(dynamicColor = false) {
        QuizScreen(
            navController = navController,
            allQuestions = questionsList

        )
    }
}
