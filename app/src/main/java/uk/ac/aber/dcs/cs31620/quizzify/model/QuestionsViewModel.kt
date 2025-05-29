package uk.ac.aber.dcs.cs31620.quizzify.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.quizzify.datasource.QuizRepository

/**
 * View Model class containing methods to add, delete, and get questions
 */
class QuestionsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: QuizRepository = QuizRepository(application)

    var allQuestions: LiveData<List<Question>> = repository.getAllQuestions()
        private set

    // Insert a new question
    fun insertQuestion(newQuestion: Question){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertQuestion(newQuestion)
        }
    }

    // Update an existing question
    fun updateQuestion(question: Question){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateQuestion(question)
        }
    }

    // Delete a specific question
    fun deleteQuestion(question: Question){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteQuestion(question)
        }
    }

    // Get a question by ID
    fun getQuestionById(questionId: Long): LiveData<Question?> {
        return repository.getQuestionById(questionId)
    }

    // Get questions based on their subject
    fun getQuestionsBySubject(subject: String): LiveData<List<Question>> {
        return repository.getQuestionsBySubject(subject)
    }

}
