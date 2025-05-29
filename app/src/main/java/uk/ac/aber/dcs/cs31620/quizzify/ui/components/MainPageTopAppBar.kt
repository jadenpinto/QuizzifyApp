package uk.ac.aber.dcs.cs31620.quizzify.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.aber.dcs.cs31620.quizzify.R
import uk.ac.aber.dcs.cs31620.quizzify.ui.theme.QuizzifyTheme

/**
 * A center Aligned Top Bar, used primarily for branding
 * Display when user is viewing the Home screen and the Question Bank
 * Displays app name along with an icon
 */
@Composable
fun MainPageTopAppBar() {
    CenterAlignedTopAppBar(
        // Bolded App Name
        title = {
            Text(stringResource(id = R.string.app_name_upper_case), fontWeight = FontWeight.Bold)
        },
        // Graduation Cap Icon
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.School,
                    contentDescription = stringResource(R.string.graduation)
                )
            }
        }
    )
}

/**
 * Preview function for Main Page Top App Bar
 */
@Preview(showBackground = true)
@Composable
fun MainPageTopAppBarPreview() {
    QuizzifyTheme(dynamicColor = false) {
        MainPageTopAppBar()
    }
}
