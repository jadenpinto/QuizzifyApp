package uk.ac.aber.dcs.cs31620.quizzify.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDao {

    // Insert a new question
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question): Long

    // Insert multiple questions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>): List<Long>

    // Update an existing question
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateQuestion(question: Question)

    // Delete a specific question
    @Delete
    suspend fun deleteQuestion(question: Question)

    // Get all questions in the quiz
    @Query("SELECT * FROM questions")
    fun getAllQuestions(): LiveData<List<Question>>

    // Get a specific question by its ID
    @Query("SELECT * FROM questions WHERE id = :id")
    fun getQuestionById(id: Long): LiveData<Question?>

    // Retrieve all questions associated with a specified subject
    @Query("SELECT * FROM questions WHERE subject = :subject")
    fun getQuestionsBySubject(
        subject: String
    ): LiveData<List<Question>>

}
