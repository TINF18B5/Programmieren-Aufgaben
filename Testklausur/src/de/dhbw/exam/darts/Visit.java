package de.dhbw.exam.darts;

/**
 * A visit is one round of up to three dart throws.
 */
public class Visit {
    /**
     * The fields hit.
     */
    private final Field[] fields;

    /**
     * Constructs a new Visit
     * @param fields The hit Fields, may at most be 3
     * @throws IllegalArgumentException When the throw length exceeds 3 throws.
     */
    public Visit(Field[] fields) {
        if (fields.length > 3)
            throw new IllegalArgumentException("You may not throw more than 3 Darts in one visit!");
        this.fields = fields;
    }

    public Field[] getFields() {
        return fields;
    }

    /**
     * Calculates the total value of the visit
     *     (i.e. the combined value of all fields in this visit)
     */
    public int getValue() {
        int value = 0;
        for (Field field : fields) {
            if(field == null)
                continue;
            value += field.getValue();
        }
        return value;
    }

    /**
     * Returns the last hit field in this visit
     */
    public Field getLastField() {
        return fields[fields.length - 1];
    }
}
