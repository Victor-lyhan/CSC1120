/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Die class
 * Name: Victor Han
 * Last Updated: 9/9/2024
 */
package Week2.Lab;

import java.util.Random;

/**
 * Die class containing two helper methods
 */
public class Die {
    /**
     * min side of dice
     */
    public static final int MIN_SIDE = 2;
    /**
     * max side of dice
     */
    public static final int MAX_SIDE = 100;

    private final int numSides;
    /**
     * current value of roll
     */
    private int currentValue;
    private final Random random;

    Die(int numSides) {
        random = new Random();
        if (numSides <= MAX_SIDE && numSides >= MIN_SIDE){
            this.numSides = numSides;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * getCurrentValue to get current roll value
     * @return returning the value of current roll
     * @throws DieNotRolledException thrown when currentvalue is not in the correct range
     */
    public int getCurrentValue(){
        if(currentValue < 1 || currentValue > numSides) {
            throw new DieNotRolledException("Not in the correct range");
        }
        int returnValue = currentValue;
        currentValue = 0;
        return returnValue;
    }

    /**
     * roll teh dice using random object
     */
    public void roll(){
        currentValue = random.nextInt(1, numSides + 1);
    }

}
