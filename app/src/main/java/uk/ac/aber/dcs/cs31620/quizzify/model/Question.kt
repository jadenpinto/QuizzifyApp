package uk.ac.aber.dcs.cs31620.quizzify.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Questions Entity Table
 * Uses room Annotation used to define table mapping
 * Table Name: questions
 * Primary Key: id, of type Long, since methods like @Insert return a long - rowId for the inserted item.
 * DB Columns: question_text, subject, option_1, option_2, option_3, ..., option_9, option_10, correct_answer
 */
@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "question_text")
    var questionText: String = "",

    var subject: String = "",

    @ColumnInfo(name = "option_1")
    var option1: String = "",

    @ColumnInfo(name = "option_2")
    var option2: String? = null,

    @ColumnInfo(name = "option_3")
    var option3: String? = null,

    @ColumnInfo(name = "option_4")
    var option4: String? = null,

    @ColumnInfo(name = "option_5")
    var option5: String? = null,

    @ColumnInfo(name = "option_6")
    var option6: String? = null,

    @ColumnInfo(name = "option_7")
    var option7: String? = null,

    @ColumnInfo(name = "option_8")
    var option8: String? = null,

    @ColumnInfo(name = "option_9")
    var option9: String? = null,

    @ColumnInfo(name = "option_10")
    var option10: String? = null,

    @ColumnInfo(name = "correct_answer")
    var correctAnswer: String = ""
    // Store the text of the correct option
)
