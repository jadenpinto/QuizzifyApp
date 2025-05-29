package uk.ac.aber.dcs.cs31620.quizzify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.quizzify.R
import uk.ac.aber.dcs.cs31620.quizzify.model.Question
import uk.ac.aber.dcs.cs31620.quizzify.model.lengthyHistoryQuestion
import uk.ac.aber.dcs.cs31620.quizzify.model.rectanglePerimeterQuestion
import uk.ac.aber.dcs.cs31620.quizzify.ui.theme.QuizzifyTheme

/**
 * Question Card Composable component used to display questions in the question bank
 * Initially displayed in a compact view, expands when tapped
 * Displayed question, subject, options, and contains buttons to edit and delete the question
 */
@Composable
fun QuestionCard(
    question: Question,
    onDelete: (Question) -> Unit = {},
    onEdit: (Question) -> Unit = {}
) {
    // Initially, card is compact
    var isExpanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = elevatedCardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            // Compact View
            if (!isExpanded) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        // Text to display Subject, followed by the number of options
                        text = buildString {
                            question.subject.takeIf { it.isNotEmpty() }?.let { append("$it; ") }
                            append("${listOfNotNull(
                                question.option1,
                                question.option2,
                                question.option3,
                                question.option4,
                                question.option5,
                                question.option6,
                                question.option7,
                                question.option8,
                                question.option9,
                                question.option10
                            ).size} " + stringResource(id = R.string.options) )
                        }
                    )
                    Row {
                        // Delete Button
                        IconButton(onClick = { onDelete(question) }) {
                            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.delete_question_button), tint = Color.Red)
                        }
                        // Edit button
                        IconButton(onClick = { onEdit(question) }) {
                            Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit_question_button))
                        }
                    }
                }
                Text(
                    // Display question text, truncate it using Ellipsis if it exceeds 2 lines
                    text = question.questionText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                IconButton(onClick = { isExpanded = true }) {
                    // Downward arrow Button to change view from Compact to Expanded
                    Icon(Icons.Filled.KeyboardArrowDown, contentDescription = stringResource(R.string.expand_question_button))
                }
            }

            // Expanded View
            if (isExpanded) {
                Text(
                    // Display entire question text
                    text = question.questionText,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Column {
                    listOfNotNull(
                        question.option1, question.option2, question.option3, question.option4,
                        question.option5, question.option6, question.option7, question.option8,
                        question.option9, question.option10
                    ).forEach { option ->
                        Row(
                            // Display every option
                            // If option is correct, it has a mint green background, all other options have
                            // background that is of the colour of the card
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (option == question.correctAnswer) Color(0xFFa0fc9c) else Color.Transparent)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                // Display each option
                                text = option,
                                modifier = Modifier.weight(1f)
                            )
                            if (option == question.correctAnswer) {
                                // Display a checkmark next to the option that is the answer
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = stringResource(R.string.correct_answer_checkmark_icon),
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }

                // Upward arrow Button to change view from Expanded to Compact
                IconButton(onClick = { isExpanded = false }) {
                    Icon(Icons.Filled.KeyboardArrowUp, contentDescription = stringResource(R.string.collapse_question_button))
                }
            }
        }
    }
}


/**
 * Preview function for Question card
 */
@Preview(showBackground = true)
@Composable
fun QuestionCardPreview(){
    QuizzifyTheme(dynamicColor = false) {
        QuestionCard(question = rectanglePerimeterQuestion)
    }
}

/**
 * Preview function for Question Card for a lengthy question to see how Ellipsis are used for text over flow
 */
@Preview(showBackground = true)
@Composable
fun QuestionCardLongQuestionPreview(){
    QuizzifyTheme(dynamicColor = false) {
        QuestionCard(question = lengthyHistoryQuestion)
    }
}