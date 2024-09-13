/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * DieNotRolledException class
 * Name: Victor Han
 * Last Updated: 9/9/2024
 */
package Week2.Lab;

/**
 * customized exception extending
 * from runTimeException - unchecked
 * Used for checking if the die rolled
 * no if over or under max or min sides
 */
public class DieNotRolledException extends RuntimeException {
    /**
     * constructor -  from superclass
     * @param s exception message to display
     */
    public DieNotRolledException(String s){
        super(s);
    }
}
