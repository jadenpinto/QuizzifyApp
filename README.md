# Quizzify - Quiz Android App

Quizzify is an Android quiz application built using **Jetpack Compose** and the **Room Database**. It allows users to create, manage, and play quizzes with a modern UI that follows **Material 3 design standards**.

## Screenshots

### Landing & Navigation
<p float="left">
  <img src="readme-assets/home_screen.jpg" width="30%" style="margin-right:10px;" />
  <img src="readme-assets/start_quiz_no_question.jpg" width="30%" style="margin-right:10px;" />
  <img src="readme-assets/empty_question_bank.jpg" width="30%" />
</p>

### Add & Edit Questions
<p float="left">
  <img src="readme-assets/add_question_1.jpg" width="23%" style="margin-right:10px;" />
  <img src="readme-assets/add_question_2.jpg" width="23%" style="margin-right:10px;" />
  <img src="readme-assets/question_bank.jpg" width="23%" style="margin-right:10px;" />
  <img src="readme-assets/edit_question.jpg" width="23%" />
</p>

### Quiz Session & Results
<p float="left">
  <img src="readme-assets/quiz_mode.jpg" width="30%" style="margin-right:10px;" />
  <img src="readme-assets/score_screen.jpg" width="30%" />
</p>


## Architecture - MVVM design pattern

### Package Structure

- **`model/`**  
  Contains everything related to the `Question` entity:
    - Entity definition
    - Data Access Object (DAO)
    - ViewModel

- **`datasource/`**  
  Acts as the single source of truth for data:
    - Repository
    - Room database
    - Uses the DAO and entity from the `model` package

- **`ui/`**  
  Contains all Jetpack Compose UI screens and navigation:
    - Screens organised in subfolders
    - Navigation handled via `MainActivity.kt`
    - Reusable Compose components stored in a `components/` folder


## Technologies Used

- Kotlin
- Database: Room Database (Android's recommended abstraction layer over SQLite for local data persistence)
- UI: Jetpack Compose, Material Design 3

## Features
- Create questions with: Multiple options, a correct answer and a subject tag
- View, edit, and delete existing questions
- Play a quiz with: Randomised question order, with final score displayed at the end

## Getting Started
1. Clone the repository: `git clone http://github.com/jadenpinto/QuizzifyApp.git`
2. Open the project in Android Studio
3. Build and run the app on an emulator or physical device
