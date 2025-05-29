package uk.ac.aber.dcs.cs31620.quizzify.ui.navigation

/**
 * Sealed class to represent Screens in Quizzify
 */
sealed class Screen (
    val route: String // Route String to uniquely identify a screen
) {
    // Screen for the Home page
    data object Home : Screen("home")

    // Screen for displaying the Question Bank (list of questions)
    data object Questions : Screen("questions")

    // Screen for adding a new question (and to edit an existing question)
    data object AddQuestion : Screen("addQuestion")

    // Screen for taking the quiz
    data object Quiz : Screen("quiz")
}

// A list of screens used for the main navigation
val screens = listOf(
    Screen.Home,
    Screen.Questions
)