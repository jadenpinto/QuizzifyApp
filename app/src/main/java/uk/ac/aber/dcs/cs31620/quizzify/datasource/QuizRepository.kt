package uk.ac.aber.dcs.cs31620.quizzify.datasource

import android.app.Application
import uk.ac.aber.dcs.cs31620.quizzify.model.Question

/**
 * QuizRepository class containing methods that uses the DAO to perform operations
 */
class QuizRepository(application: Application) {

    // Get Questions DAO
    // Uses not-null assertion (!!), since we are guaranteed a Room database singleton object
    private val questionDao = QuizRoomDatabase.getDatabase(application)!!.questionDao()

    // Insert a single question
    suspend fun insertQuestion(question: Question){
        questionDao.insertQuestion(question)
    }

    // Insert multiple questions
    suspend fun insertQuestions(questions: List<Question>){
        questionDao.insertQuestions(questions)
    }

    // Retrieve all questions
    fun getAllQuestions() = questionDao.getAllQuestions()

    // Fetch a question by querying using its ID
    fun getQuestionById(
        id: Long
    ) = questionDao.getQuestionById(id)

    // Update a question
    suspend fun updateQuestion(question: Question) {
        questionDao.updateQuestion(question)
    }

    // Remove a question
    suspend fun deleteQuestion(question: Question) {
        questionDao.deleteQuestion(question)
    }

    // Retrieve all questions associated with a specified subject
    fun getQuestionsBySubject(
        subject: String
    ) = questionDao.getQuestionsBySubject(subject)

}