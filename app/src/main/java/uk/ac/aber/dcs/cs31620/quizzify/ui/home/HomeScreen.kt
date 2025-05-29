package uk.ac.aber.dcs.cs31620.quizzify.ui.home

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Shuffle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import uk.ac.aber.dcs.cs31620.quizzify.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.quizzify.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizzify.ui.theme.QuizzifyTheme

/**
 * Top Level Home Screen Composable component that calls the HomeScreen component passing in the Questions
 */
@Composable
fun HomeScreenTopLevel(
    navController: NavHostController,
    questionsViewModel: QuestionsViewModel = viewModel()
) {
    // Retrieve using the view model a list of all questions in the question bank
    val allQuestions by questionsViewModel.allQuestions.observeAsState(listOf())

    HomeScreen(
        navController = navController,
        allQuestions = allQuestions
    )
}

/**
 * HomeScreen composable component
 * Home screen is rendered using the Scaffold
 * Contains a FAB to launch quiz mode when clicked if question bank contains questions
 * If the question bank is empty, launching a quiz opens a dialog informing the user
 * Content of the page is a card of quiz rules, each containing text and an icon
 */
@Composable
fun HomeScreen(
    navController: NavHostController,
    allQuestions: List<Question>
){
    // Initially the dialog is not shown
    var showDialog by rememberSaveable { mutableStateOf(false) }

    if (showDialog) {
        // If the dialog is shown (when question bank is empty)
        AlertDialog(
            // Clicking anywhere outside the dialog should close it
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = stringResource(R.string.add_questions_title))
            },
            text = {
                Text(text = stringResource(R.string.empty_question_bank_description))
            },
            confirmButton = {
                // 'OK' button
                TextButton(
                    onClick = {
                        // Close the dialog first
                        showDialog = false

                        // Then, take the user to the question bank
                        navController.navigate(Screen.Questions.route) {
                            // Avoid creating a new back stack
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Restore state when re-selecting this destination
                            launchSingleTop = true
                            restoreState = true
                        }

                    }
                ) {
                    Text(stringResource(R.string.ok_dialog_button))
                }
            },
            dismissButton = {
                // 'Not Now' Button
                TextButton(
                    // Close the dialog
                    onClick = { showDialog = false }
                ) {
                    Text(stringResource(R.string.not_now_dialog_button))
                }
            }
        )
    }

    TopLevelScaffold(
        navController = navController,
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    if (allQuestions.isEmpty()) {
                        // Empty question bank - Show dialog to user informing them and do not start quiz
                        showDialog = true
                    } else {
                        // Valid question bank - launch quiz mode
                        navController.navigate(Screen.Quiz.route)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = stringResource(R.string.launch_quiz),
                    modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
                )
            }
        }
    )
    {
        innerPadding -> Surface(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        )   {
            HomeScreenContent(
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

/**
 * HomeScreenContent Composable Component to display main content of the home screen page
 * This is what the users see when they launch Quizzify
 * Displays an illustration for branding and the quiz rules
 */
@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            // Illustration of a person reading a book for Branding
            painter = painterResource(
                id = R.drawable.reading_book
            ),
            contentDescription = stringResource(id = R.string.reading_book_illustration),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.size(250.dp)
        )

        ElevatedCard(
            // Card to display the three rules of the quiz
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 1.dp, bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // 'Rules' Title
                Text(
                    text = stringResource(R.string.rules_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Rule 1 - Score is shown at the end, after all questions are displayed
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        // Rule 1 Icon - Timer Icon
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = stringResource(R.string.timer_icon),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        // Rule 1 Text
                        text = stringResource(R.string.score_at_end_rule),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Horizontal Divider After Rule 1, above Rule 2
                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider()

                // Rule 2 - Questions are displayed in a random order
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        // Rule 2 Icon - Shuffle Icon
                        imageVector = Icons.Outlined.Shuffle,
                        contentDescription = stringResource(R.string.shuffle_icon),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        // Rule 2 Text
                        text = stringResource(R.string.random_question_order_rule),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Horizontal Divider After Rule 2, above Rule 3
                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider()

                // Rule 3 - Skipping a question counts as an incorrect answer
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        // Rule 3 Icon - Skip
                        imageVector = Icons.Filled.SkipNext,
                        contentDescription = stringResource(R.string.skip_icon),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        // Rule 3 Text
                        text = stringResource(R.string.skip_question_rule),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}


/**
 * Preview Function for Home Screen with non-empty question bank
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()

    val questionsList = mutableListOf(
        rectanglePerimeterQuestion,
        circleCircumferenceQuestion,
        cellPowerhouseQuestion
    )

    QuizzifyTheme(dynamicColor = false) {
        HomeScreen(
            navController = navController,
            allQuestions = questionsList
        )
    }
}

/**
 * Preview Function for Home Screen with empty question bank
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenNoQuestionsPreview() {
    val navController = rememberNavController()

    val noQuestions: List<Question> = listOf()

    QuizzifyTheme(dynamicColor = false) {
        HomeScreen(
            navController = navController,
            allQuestions = noQuestions
        )
    }
}
