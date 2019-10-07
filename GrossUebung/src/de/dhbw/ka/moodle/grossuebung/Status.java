package de.dhbw.ka.moodle.grossuebung;

import java.awt.*;

public enum Status {
    ACTIVE(Color.ORANGE, 0),
    CORRECT(Color.GREEN, 1),
    WRONG(Color.RED, -1),
    PENDING(Color.WHITE, 0),
    NO_ANSWER(Color.GRAY, 0);

    private final Color color;
    private final int credits;

    Status(Color color, int credits) {
        this.color = color;
        this.credits = credits;
    }

    public Color getColor() {
        return color;
    }

    public int getCredits() {
        return credits;
    }
}
