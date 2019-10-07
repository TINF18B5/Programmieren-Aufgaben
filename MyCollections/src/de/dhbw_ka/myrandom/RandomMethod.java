package de.dhbw_ka.myrandom;

public class RandomMethod {
    public static int calculate(int min, int maxExclusive) {
        if(maxExclusive <= min)
            throw new IllegalArgumentException("Maximum needs to be greater than minimum!");
        return (int) ((double) min + (0.5 * (Math.random() - Math.random()) + 0.5) * ((double) maxExclusive - (double) min));
    }
}
