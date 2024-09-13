/*
 * Course: CSC1020
 * Homework 2 - File IO
 * Driver
 * Name: Victor Han
 * Last Updated: 9/6/2024
 */
package Week2.HW;

import java.io.Serializable;

/**
 * MyObject stores the 3 values in 3 datatypes
 * constructor init the values
 * toString() method returns the values in string
 */
public class MyObject implements Serializable {
    private static int wholeNumber;
    private static double fraction;
    private static String words;

    MyObject(int wholeNumber, double fraction, String words) {
        this.wholeNumber = wholeNumber;
        this.fraction = fraction;
        this.words = words;
    }

    @Override
    public String toString(){
        return Integer.toString(wholeNumber) + " " + Double.toString(fraction) + " " + words;
    }

}
