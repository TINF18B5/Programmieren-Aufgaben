package de.dhbw.ka.moodle.grossuebung;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game {
    private final Question[] usedQuestions;
    private final Set<GameClient> clients;
    private Thread counterThread;

    private long millisStart;
    private int currentQuestion = -1;


    public Game(List<Question> questionPool, int questionCount) throws GameException {
        if (questionCount > questionPool.size())
            throw new GameException("Too few questions available");

        this.usedQuestions = new Random().ints(0, questionPool.size())
                .distinct()
                .limit(questionCount)
                .mapToObj(questionPool::get)
                .toArray(Question[]::new);

        this.clients = new HashSet<>();
    }

    public void registerClient(GameClient client) {
        if (!isStarted())
            clients.add(client);
    }

    private boolean isStarted() {
        return this.currentQuestion >= 0;
    }

    public int getQuestionsCount() {
        return usedQuestions.length;
    }

    public void startGame() {
        this.millisStart = System.currentTimeMillis();
        nextQuestion();
    }

    private void noAnswer() {
        for (GameClient client : clients) {
            client.setAnswerState(currentQuestion, Status.NO_ANSWER);
        }
        nextQuestion();
    }

    private void nextQuestion() {
        if (++currentQuestion >= usedQuestions.length) {
            end();
            return;
        }

        final Question usedQuestion = usedQuestions[currentQuestion];
        for (GameClient client : clients) {
            client.setQuestion(currentQuestion, usedQuestion);
        }

        counterThread = new CounterThread();
        counterThread.start();
    }

    private void end() {
        for (GameClient client : this.clients) {
            client.gameIsOver();
        }

        final String message;
        {
            final StringBuilder sb = new StringBuilder("Game finished after ");
            sb.append((System.currentTimeMillis() - millisStart) / 1000).append(" seconds. Score: ");

            boolean first = true;
            for (GameClient client : clients) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(client.getPlayerName()).append(": (").append(client.getPoints()).append(")");
            }
            message = sb.toString();
        }


        JOptionPane.showMessageDialog(null, message, "Results", JOptionPane.INFORMATION_MESSAGE);

        try (final BufferedWriter writer = Files.newBufferedWriter(Paths.get("files/highscore.txt"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Could not write highscore", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void answerSelected(GameClient client, int index) {
        counterThread.interrupt();
        for (GameClient gameClient : clients) {
            if (gameClient == client) {
                final boolean correct = usedQuestions[currentQuestion].isCorrect(index);
                gameClient.setAnswerState(currentQuestion, correct ? Status.CORRECT : Status.WRONG);
            } else {
                gameClient.setAnswerState(currentQuestion, Status.NO_ANSWER);
            }
        }

        nextQuestion();
    }

    private final class CounterThread extends Thread {
        @Override
        public void run() {
            {
                for (int i = 10; i >= 1; i--) {
                    try {
                        for (GameClient client : clients) {
                            client.setRemainingSeconds(i);
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                noAnswer();
            }
        }
    }

}
