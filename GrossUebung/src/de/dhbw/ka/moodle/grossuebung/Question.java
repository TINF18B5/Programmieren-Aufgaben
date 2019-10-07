package de.dhbw.ka.moodle.grossuebung;

public class Question {
    private final String questionText;
    private final String[] answers;
    private final int correctIndex;

    public Question(String questionText, String[] answers, int correctIndex) {
        this.questionText = questionText;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    public boolean isCorrect(int index) {
        return index == correctIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getAnswers() {
        return answers;
    }
}
