package uk.ac.aber.dcs.cs31620.quizzify.ui.components

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Three pieces of information make up Navigation Bar items:
 * 1. Filled icon (if selected)
 * 2. Unfilled icon
 * 3. Item label
 */
data class IconGroup(
    val filledIcon: ImageVector,
    val outlineIcon: ImageVector,
    val label: String
)
