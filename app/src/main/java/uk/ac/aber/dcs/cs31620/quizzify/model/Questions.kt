package uk.ac.aber.dcs.cs31620.quizzify.model

/**
 * This file stores a collection of pre-defined hard-coded questions used for:
 * 1. Database Population
 * 2. Compose UI Testing
 * 3. Preview Functions
 */

val rectanglePerimeterQuestion = Question(
    id = 1,
    questionText = "What is the formula for the perimeter of a rectangle",
    subject = "Maths",

    option1 = "Length * Width",
    option2 = "Length * Length",
    option3 = "2 * (Length + Width)",
    option4 = "Pi * Length * Width",
    option5 = "Length * 4",
    option6 = "2 * Pi * Length",

    correctAnswer = "2 * (Length + Width)"
)

val circleCircumferenceQuestion = Question(
    id = 2,
    questionText = "What is the formula for the circumference of a circle",
    subject = "Maths",

    option1 = "Pi * Radius * Radius",
    option2 = "2 * Pi * Radius",
    option3 = "Radius * Pi",

    correctAnswer = "2 * Pi * Radius"
)

val cellPowerhouseQuestion = Question(
    id = 3,
    questionText = "Which organelle is known as the powerhouse of the cell",
    subject = "Biology",

    option1 = "Mitochondria",
    option2 = "Cell Wall",
    option3 = "Ribosome",
    option4 = "Chloroplast",

    correctAnswer = "Mitochondria"
)

val lengthyHistoryQuestion : Question = Question(
    id = 4,
    questionText = "In the context of ancient civilizations, which of the following statements accurately describes the significance of the Code of Hammurabi, one of the earliest written legal codes in history? This code, created around 1754 BC in ancient Mesopotamia, is often noted for its role in establishing justice and order within society. It is inscribed on a stele and includes a variety of laws covering topics such as trade, family relations, and civil rights. Which of the following options best captures its importance?",
    subject = "History",

    option1 = "The Code of Hammurabi was primarily a religious text that outlined the duties of priests and rituals to be performed in temples",
    option2 = "The Code of Hammurabi served as a foundational legal document that influenced subsequent legal systems by introducing the principle of an eye for an eye, thereby establishing a form of retributive justice",
    option3 = "The Code of Hammurabi was a collection of philosophical writings that discussed the nature of justice and morality without any specific legal implications.",

    correctAnswer = "The Code of Hammurabi served as a foundational legal document that influenced subsequent legal systems by introducing the principle of an eye for an eye, thereby establishing a form of retributive justice"
)

val questionWithoutSubject : Question = Question(
    id = 5,
    questionText = "Sample question text for a question without a subject",

    option1 = "Sample Option 1",
    option2 = "Sample Option 2",
    option3 = "Sample Option 3",

    correctAnswer = "Sample Option 3"
)