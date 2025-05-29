package uk.ac.aber.dcs.cs31620.quizzify.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizzify.R
import uk.ac.aber.dcs.cs31620.quizzify.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizzify.ui.navigation.screens
import uk.ac.aber.dcs.cs31620.quizzify.ui.theme.QuizzifyTheme

/**
 * Main Page Navigation Bar, which is available to users as soon as app is opened
 * Users can access it when they're toggling between the Home page and the Question Bank
 * Inaccessible when the user is taking a quiz, editing or adding a question
 */
@Composable
fun MainPageNavigationBar(
    navController: NavHostController
) {
    // Mapping a screen to its corresponding Icon group - Icon's label and its filled & outlined variants
    val icons = mapOf(
        Screen.Home to IconGroup(
            filledIcon = Icons.Filled.Home,
            outlineIcon = Icons.Outlined.Home,
            label = stringResource(id = R.string.home)
        ),
        Screen.Questions to IconGroup(
            filledIcon = Icons.Filled.Quiz,
            outlineIcon = Icons.Outlined.Quiz,
            label = stringResource(id = R.string.questions)
        )
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        screens.forEach { screen ->
            val isSelected = currentDestination?.route == screen.route
            val labelText = icons[screen]!!.label

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = (
                                if (isSelected)
                                    // When a screen is selected, its icon should be filled
                                    icons[screen]!!.filledIcon
                                else
                                    // When a screen isn't selected, its icon should not be filled - outlined
                                    icons[screen]!!.outlineIcon
                                ),
                        contentDescription =  labelText
                    )
                },
                label = { Text(labelText) },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

/**
 * Preview function for Main Page Navigation Bar
 */
@Preview(showBackground = true)
@Composable
fun MainPageNavigationBarPreview() {
    val navController = rememberNavController()
    QuizzifyTheme(dynamicColor = false) {
        MainPageNavigationBar(navController)
    }
}
