package uk.ac.aber.dcs.cs31620.quizzify.ui.questions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizzify.R
import uk.ac.aber.dcs.cs31620.quizzify.model.Question
import uk.ac.aber.dcs.cs31620.quizzify.model.QuestionsViewModel
import uk.ac.aber.dcs.cs31620.quizzify.model.cellPowerhouseQuestion
import uk.ac.aber.dcs.cs31620.quizzify.model.circleCircumferenceQuestion
import uk.ac.aber.dcs.cs31620.quizzify.model.rectanglePerimeterQuestion
import uk.ac.aber.dcs.cs31620.quizzify.ui.components.QuestionCard
import uk.ac.aber.dcs.cs31620.quizzify.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.quizzify.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizzify.ui.theme.QuizzifyTheme

/**
 * QuestionsScreenTopLevel Composable that is shown when user navigates to the Question Bank Tab
 * Retrieves the list of all questions in the DB, and shows content accordingly
 * If question bank is not empty, allows users to view, edit and delete questions
 */
@Composable
fun QuestionsScreenTopLevel(
    navController: NavHostController,
    questionsViewModel: QuestionsViewModel = viewModel()
) {
    val allQuestions by questionsViewModel.allQuestions.observeAsState(listOf())

    QuestionBankScreen(
        navController = navController,
        // List of all questions in the database
        allQuestions = allQuestions,
        deleteQuestion = {
            // Function to delete a question from the database
            questionsViewModel.deleteQuestion(it)
        },
        editQuestion = {
            // Navigates to the add question composable passing in the question ID a a navigation parameter
            // This loads the question input form, and populates it with the existing question data
            // This way users can make edits to existing questions using the same input form
            // used to create new ones.
            navController.navigate(Screen.AddQuestion.route + "/${it.id}")
        }
    )
}

/**
 * QuestionBankScreen composable displays question bank if it contains Questions
 * Every question in the DB is displayed using a QuestionCard
 * If the database has no questions, the screen informs the user with instructions on creating questions
 * Uses a Scaffold with a FAB to create new questions
 */
@Composable
fun QuestionBankScreen(
    navController: NavHostController,
    allQuestions: List<Question> = listOf(),
    deleteQuestion: (Question) -> Unit = {},
    editQuestion: (Question) -> Unit = {}
){
    TopLevelScaffold(
        navController = navController,
        floatingActionButton = {
            // Button to Add new Question
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddQuestion.route)
                }
            ) {
                Icon(
                    // + Icon
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_question)
                )
            }
        }
    ) {
        innerPadding -> Surface(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    )   {
            if (allQuestions.isNotEmpty()) {
                // Non-Empty Question Bank
                QuestionBankScreenContent(
                    allQuestions,
                    deleteQuestion = { deleteQuestion(it) },
                    editQuestion = { editQuestion(it) }
                )
            }
            else {
                // Empty Question Bank
                NoQuestionBankScreenContent()
            }
        }
    }
}


/**
 * QuestionBankScreenContent Composable is displayed when question bank is non-empty
 * It shows every question in the question bank using an Elevated Card - QuestionCard component
 * Allows users to view the questions, as well as edit and delete them
 */
@Composable
fun QuestionBankScreenContent(
    allQuestions: List<Question> = listOf(),
    deleteQuestion: (Question) -> Unit = {},
    editQuestion: (Question) -> Unit = {}
){
    LazyColumn {
        items(allQuestions) { question ->
            QuestionCard(
                // Current Question
                question = question,
                // Function to edit the question
                onEdit = { editedQuestion ->
                    editQuestion(editedQuestion)
                },
                // Function to delete the question
                onDelete = { deletedQuestion ->
                    deleteQuestion(deletedQuestion)
                }
            )
        }
    }
}

/**
 * NoQuestionBankScreenContent Composable component shown to users in question bank screen when
 * the question bank is empty.
 * Contains text to inform the user that the bank is empty, instructions on how to add a new question,
 * and an illustration for branding.
 */
@Composable
fun NoQuestionBankScreenContent(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            // Large text to inform user the question bank is empty
            text = stringResource(R.string.empty_question_bank),
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 40.dp)
        )
        Text(
            // Text informing user on how to add questions to the bank
            text = stringResource(R.string.add_question_to_bank),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 20.dp),
            textAlign = TextAlign.Center
        )
        Image(
            // Illustration for branding and improved UI/UX
            painter = painterResource(
                id = R.drawable.chasing_papers
            ),
            contentDescription = stringResource(id = R.string.chasing_papers),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.size(300.dp)
        )
    }
}


/**
 * Preview Function for Question Bank Screen with non-empty question bank
 */
@Preview(showBackground = true)
@Composable
fun QuestionBankScreenPreview() {
    val navController = rememberNavController()

    val questionsList = mutableListOf(
        rectanglePerimeterQuestion,
        circleCircumferenceQuestion,
        cellPowerhouseQuestion
    )

    QuizzifyTheme(dynamicColor = false) {
        QuestionBankScreen(
            navController = navController,
            allQuestions = questionsList
        )
    }
}


/**
 * Preview Function for Question Bank Screen with empty question bank
 */
@Preview(showBackground = true)
@Composable
fun EmptyQuestionBankScreenPreview() {
    val navController = rememberNavController()

    val noQuestions: List<Question> = listOf()

    QuizzifyTheme(dynamicColor = false) {
        QuestionBankScreen(
            navController = navController,
            allQuestions = noQuestions
        )
    }
}
