package de.dhbw.ka.moodle.grossuebung;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class GameTerm implements GameClient {
    private final String playerName;
    private final Game game;
    private GameWindow window;

    public GameTerm(String playerName, Game game) {
        this.playerName = playerName;
        this.game = game;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int getPoints() {
        return window.questionLabels.getPoints();
    }

    @Override
    public void setQuestion(int questionIndex, Question q) {
        if (window == null)
            window = new GameWindow();

        this.window.setQuestion(q);
        this.setAnswerState(questionIndex, Status.ACTIVE);
    }

    @Override
    public void setRemainingSeconds(int seconds) {
        this.window.questionText.setRemainingSeconds(seconds);
    }

    @Override
    public void gameIsOver() {
        this.window.answers.gameIsOver();
    }

    @Override
    public void setAnswerState(int questionIndex, Status status) {
        window.questionLabels.questionNumberLabels[questionIndex].setStatus(status);
    }

    private static final class QuestionLabels extends JPanel {
        private final QuestionNumberLabel[] questionNumberLabels;

        private QuestionLabels(int questionCount) {
            this.setLayout(new GridLayout(1, questionCount, 5, 5));

            questionNumberLabels = IntStream.range(1, questionCount + 1)
                    .mapToObj(QuestionNumberLabel::new)
                    .peek(this::add)
                    .toArray(QuestionNumberLabel[]::new);
        }

        public int getPoints() {
            return Math.max(Arrays.stream(questionNumberLabels).mapToInt(QuestionNumberLabel::getPoints).sum(), 0);
        }

    }

    private static final class QuestionNumberLabel extends JLabel {
        private Status status;

        private QuestionNumberLabel(int number) {
            super(Integer.toString(number, 10), CENTER);
            this.setOpaque(true);
            this.setStatus(Status.PENDING);
        }

        private void setStatus(Status status) {
            this.status = status;
            this.setBackground(status.getColor());
        }

        private int getPoints() {
            return status.getCredits();
        }
    }

    private static final class QuestionText extends JPanel {
        private final JLabel answerText;
        private final JLabel remainingTime;

        public QuestionText() {
            super(new BorderLayout(5, 5));

            this.answerText = new JLabel();
            this.answerText.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(answerText, BorderLayout.CENTER);
            this.remainingTime = new JLabel("Remaining Time: " + 10);
            this.remainingTime.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(remainingTime, BorderLayout.SOUTH);
        }

        private void setQuestion(Question q) {
            this.answerText.setText(q.getQuestionText());
        }

        private void setRemainingSeconds(int time) {
            this.remainingTime.setText("Remaining Time: " + time);
        }
    }

    private final class Answers extends JPanel {
        private final JButton[] buttons;


        private Answers() {
            this.setLayout(new GridLayout(2, 2, 5, 5));

            this.buttons = new JButton[4];
            for (int i = 0; i < 4; i++) {
                final int i1 = i;
                final JButton jButton = new JButton();
                buttons[i] = jButton;
                jButton.addActionListener(e -> GameTerm.this.game.answerSelected(GameTerm.this, i1));
                this.add(jButton);
            }
        }

        private void setQuestion(Question question) {
            for (int i = 0; i < this.buttons.length; i++) {
                this.buttons[i].setText(question.getAnswers()[i]);
            }
        }

        public void gameIsOver() {
            for (JButton button : buttons) {
                button.setEnabled(false);
            }
        }
    }

    private final class GameWindow extends JFrame {
        private final QuestionLabels questionLabels;
        private final QuestionText questionText;
        private final Answers answers;

        public GameWindow() {
            super(GameTerm.this.playerName);
            
            this.questionLabels = new QuestionLabels(GameTerm.this.game.getQuestionsCount());

            this.setLayout(new BorderLayout(5, 5));
            this.add(questionLabels, BorderLayout.NORTH);


            this.questionText = new QuestionText();
            this.add(questionText, BorderLayout.CENTER);


            this.answers = new Answers();
            this.add(answers, BorderLayout.SOUTH);


            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setSize(800, 600);
            EventQueue.invokeLater(() -> this.setVisible(true));
        }

        private void setQuestion(Question question) {
            this.questionText.setQuestion(question);
            this.answers.setQuestion(question);
        }
    }
}
