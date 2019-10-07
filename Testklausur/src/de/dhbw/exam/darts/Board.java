package de.dhbw.exam.darts;

/**
 * A dart Board.
 */
public class Board {

    /**
     * All fields in this board.
     * Fields that are usually found multiple times on a regular board (e.g. the single fields) are reduced to only one field here.
     */
    private final Field[] fields;

    /**
     * Create a new Board.
     * Initializes its fields to a default Dart Board.
     */
    public Board() {
        //62 Fields plus X
        this.fields = new Field[63];
        for (int i = 0; i < 20; ) {
            ++i;
            this.fields[3 * i - 3] = new Field(Integer.toString(i, 10), i, false);
            this.fields[3 * i - 2] = new Field("D" + Integer.toString(i, 10), (i) * 2, true);
            this.fields[3 * i - 1] = new Field("T" + Integer.toString(i, 10), (i) * 3, false);
        }
        this.fields[60] = new Field("25", 25, false);
        this.fields[61] = new Field("BULL", 50, true);
        this.fields[62] = new Field("X", 0, false);
    }

    /**
     * Parses the given label to a field, or returns null
     * @param label The field Label (case insensitive)
     * @return The found label or null if not found in this board.
     */
    public Field parseField(String label) {
        for (Field field : this.fields) {
            if (field.getLabel().equalsIgnoreCase(label))
                return field;
        }
        return null;
    }
}
