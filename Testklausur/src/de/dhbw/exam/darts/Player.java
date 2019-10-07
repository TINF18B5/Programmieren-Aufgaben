package de.dhbw.exam.darts;

/**
 * A player is someone who participates in a Game of Dart
 */
public class Player {
    /**
     * The name of the player.
     * Players will be addressed by this name
     */
    private final String name;

    /**
     * The points the player needs to get to 0
     */
    private int remainingPoints;

    /**
     * The amount of darts this player has thrown so far.
     */
    private int countDartsThrown;

    /**
     * The Visits the player has had and will have (null if not yet visited)
     */
    private final Visit[] visits;

    public Player(String name) {
        this.name = name;
        this.remainingPoints = 501;
        this.countDartsThrown = 0;
        this.visits = new Visit[10];
    }

    public int getRemainingPoints() {
        return remainingPoints;
    }

    /**
     * Registers a visit to the player
     * @param visit The visit the player just threw.
     * @return True, only if the visit is credited, that is not overthrown or not ended with a non-double field.
     */
    public boolean addVisit(Visit visit) {
        int visitIndex;
        {
            for (visitIndex = 0; visitIndex < visits.length; visitIndex++) {
                if (visits[visitIndex] == null) {
                    break;
                }
            }

            if(visitIndex == visits.length)
                return false;
        }

        final int value = visit.getValue();
        countDartsThrown += visit.getFields().length;
        final int newRemainingPoints = remainingPoints - value;
        if (newRemainingPoints == 0 && visit.getLastField().isDoubleField() || newRemainingPoints > 1) {
            remainingPoints = newRemainingPoints;
            visits[visitIndex] = visit;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("Player: %s, %d Points remaining", this.name, this.remainingPoints);
    }

    public String getName() {
        return name;
    }

    public int getCountDartsThrown() {
        return countDartsThrown;
    }
}
