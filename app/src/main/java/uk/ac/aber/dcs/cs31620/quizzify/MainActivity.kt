package uk.ac.aber.dcs.cs31620.quizzify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.aber.dcs.cs31620.quizzify.model.QuestionsViewModel
import uk.ac.aber.dcs.cs31620.quizzify.ui.home.HomeScreenTopLevel
import uk.ac.aber.dcs.cs31620.quizzify.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizzify.ui.questions.AddQuestionScreenTopLevel
import uk.ac.aber.dcs.cs31620.quizzify.ui.questions.QuestionsScreenTopLevel
import uk.ac.aber.dcs.cs31620.quizzify.ui.quiz.QuizScreenTopLevel
import uk.ac.aber.dcs.cs31620.quizzify.ui.theme.QuizzifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuizzifyTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Use Compose Navigation
                    BuildNavigationGraph()
                }
            }
        }

    }
}

@Composable
private fun BuildNavigationGraph(
    questionsViewModel: QuestionsViewModel = viewModel()
) {
    val navController = rememberNavController()
    // Stores Navigation stack.
    // rememberController creates a NavController or uses a previous one and remembers it between
    // recompositions.

    // Graph of destinations
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // The composable function calls are used to build the graph

        // Question Bank Screen - View and Edit Questions
        composable(Screen.Questions.route){
             QuestionsScreenTopLevel(navController, questionsViewModel)
        }

        // Home Screen - View rules and start a quiz
        composable(Screen.Home.route){
             HomeScreenTopLevel(navController, questionsViewModel)
        }

        // Quiz Screen - Quiz mode that displays all questions in a random order, and shows score at finish
        composable(Screen.Quiz.route){
            QuizScreenTopLevel(navController, questionsViewModel)
        }

        // Add question - Add a new question
        composable(Screen.AddQuestion.route){
            AddQuestionScreenTopLevel(navController)
        }

        // Edit Question: 'Add' an existing Question with an ID
        composable(
            Screen.AddQuestion.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) { backStackEntry ->
             AddQuestionScreenTopLevel(
                 navController = navController,
                 questionId = backStackEntry.arguments?.getLong("id"),
                 questionsViewModel = questionsViewModel
            )
        }

    }
}

