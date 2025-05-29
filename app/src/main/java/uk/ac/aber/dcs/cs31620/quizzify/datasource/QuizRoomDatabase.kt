package uk.ac.aber.dcs.cs31620.quizzify.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.quizzify.model.Question
import uk.ac.aber.dcs.cs31620.quizzify.model.QuestionDao
import uk.ac.aber.dcs.cs31620.quizzify.model.cellPowerhouseQuestion
import uk.ac.aber.dcs.cs31620.quizzify.model.circleCircumferenceQuestion
import uk.ac.aber.dcs.cs31620.quizzify.model.rectanglePerimeterQuestion

/***
 * QuizRoomDatabase Class contains methods that:
 * - Gets the SQLLite Room database
 * - On DB creation, populates it on a separate thread
 */
@Database(entities = [Question::class], version = 1)
abstract class QuizRoomDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    companion object {
        private var instance: QuizRoomDatabase? = null
        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        @Synchronized
        fun getDatabase(context: Context): QuizRoomDatabase? {
            if (instance == null) {
                instance =
                    Room.databaseBuilder<QuizRoomDatabase>(
                        context.applicationContext,
                        QuizRoomDatabase::class.java,
                        "quiz_database"
                    )
                        .allowMainThreadQueries()
                        .addCallback(roomDatabaseCallback(context))
                        .build()
            }
            return instance
        }

        /**
         * roomDatabaseCallback populates database in a separate thread on its creation
         */
        private fun roomDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    // Override onCreate - This should only be called once on database creation
                    super.onCreate(db)

                    // By this point, SQLite database is created
                    // In a separate thread, populate the database, passing in a reference to Room DB object
                    coroutineScope.launch {
                        populateDatabase(context, getDatabase(context)!!)
                    }
                }
            }
        }

        /**
         * Function to populate the database
         * Uses the DAO's bulk insert operation to insert pre-defined questions into the database
         */
        private suspend fun populateDatabase(context: Context, instance: QuizRoomDatabase) {
            val questionsList = mutableListOf(
                rectanglePerimeterQuestion,
                circleCircumferenceQuestion,
                cellPowerhouseQuestion
            )

            // Get the DAO
            val questionDao = instance.questionDao()
            // Perform bulk insert
            val questionIds = questionDao.insertQuestions(questionsList)

        }
    }
}