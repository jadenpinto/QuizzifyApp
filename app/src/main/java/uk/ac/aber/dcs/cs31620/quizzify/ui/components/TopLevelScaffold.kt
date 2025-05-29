package uk.ac.aber.dcs.cs31620.quizzify.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * Define a reusable scaffold function
 * This is used to display the top level pages - Home Screen abd Question Bank
 * Takes in parameters for a top app bar, a floating action button, and the content of the papge
 */
@Composable
fun TopLevelScaffold(
    navController: NavHostController,
    floatingActionButton: @Composable () -> Unit = { },
    pageContent: @Composable (innerPadding: PaddingValues) -> Unit = {}
) {
    Scaffold(
        // Main Page Top App Bar
        topBar = { MainPageTopAppBar() },
        // Floating Action Button
        floatingActionButton = floatingActionButton,
        // Bottom Main Page Navigation Bar
        bottomBar = {
            MainPageNavigationBar(navController)
        },
        // Content of the page
        content = { innerPadding -> pageContent(innerPadding) }
    )
}

/**
 * Preview function for the Top Level Scaffold
 */
@Preview(showBackground = true)
@Composable
fun TopLevelScaffoldPreview(){
    val navController = rememberNavController()
    TopLevelScaffold(navController)
}
