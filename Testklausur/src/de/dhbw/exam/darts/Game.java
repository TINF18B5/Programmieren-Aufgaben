package de.dhbw.exam.darts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * A standard Darts Game ("Leg")
 */
public class Game {
    /**
     * The board to be used
     */
    private final Board board;
    /**
     * The players participating
     */
    private final Player[] players;

    /**
     * Possible early checkouts are stored here.
     */
    private final Checkouts checkouts;

    public Game(Board board, Player[] players) {
        this.board = board;
        this.players = players;
        this.checkouts = new Checkouts();
    }

    public void start() {
        final Scanner scanner = new Scanner(System.in);
        for (int roundNo = 0; roundNo < 10; roundNo++) {
            for (Player player : players) {
                System.out.println(player.toString());
                if(player.getRemainingPoints() <= 170){
                    final String checkout = checkouts.getCheckout(player.getRemainingPoints());
                    if (!checkout.equals("-"))
                        System.out.println("Possible Checkout: " + checkout);
                }
                final Visit visit;
                {
                    System.out.print("Enter visit: ");
                    final String[] darts = scanner.nextLine().split(" ");
                    final Field[] fields = new Field[darts.length];
                    for (int i = 0; i < darts.length; i++) {
                        fields[i] = board.parseField(darts[i]);
                    }
                    visit = new Visit(fields);
                }
                System.out.println("Scored: " + visit.getValue());
                if(!player.addVisit(visit))
                    System.out.println("Score not added!");
                System.out.println("==========================");

                if (player.getRemainingPoints() == 0) {
                    System.out.println();
                    System.out.println("Game shot and the leg, " + player.getName());
                    writeHighScore(player);
                    return;
                }
            }
        }
        System.out.println();
        System.out.println("You're too bad for this game!");
    }

    /**
     * Writes the successfully finished games to a file named "Highscore.txt" in a files/ subdir
     * @param player The player who won the game.
     */
    private void writeHighScore(Player player) {
        final File file = new File("files/highscore.txt");
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            System.err.println("Could not create highscore file path, aborting!");
            return;
        }

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.format("%s won with %d darts.", player.getName(), player.getCountDartsThrown()));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Could not write to highscore file: " + e.getMessage());
        }
    }
}
