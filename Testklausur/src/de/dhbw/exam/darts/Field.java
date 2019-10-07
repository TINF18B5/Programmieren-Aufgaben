package de.dhbw.exam.darts;

/**
 * Represents a Field on a dart Board
 */
public class Field {
    /**
     * The label, e.g. "T20", "D20", "20", "BULL"
     */
    private final String label;

    /**
     * The associated value, e.g. 60, 40, 20, 25
     */
    private final int value;

    /**
     * Represents whether this field is a double field
     */
    private final boolean doubleField;


    public Field(String label, int value, boolean doubleField) {
        this.label = label;
        this.value = value;
        this.doubleField = doubleField;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    public boolean isDoubleField() {
        return doubleField;
    }
}
