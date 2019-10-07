package de.dhbw.ka.moodle.grossuebung;

public interface GameClient {

    String getPlayerName();

    int getPoints();

    void setQuestion(int questionIndex, Question q);

    void setRemainingSeconds(int seconds);

    void gameIsOver();

    void setAnswerState(int questionIndex, Status status);
}
