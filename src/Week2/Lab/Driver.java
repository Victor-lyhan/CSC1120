/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Main Driver class
 * Name: Victor Han
 * Last Updated: 9/9/2024
 */
package Week2.Lab;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Driver to creat, roll, count dice
 */
public class Driver {
    /**
     * Min dice num
     */
    public static final int MIN_DICE = 2;
    /**
     * Max dice num
     */
    public static final int MAX_DICE = 10;

    public static void main(String[] args) {
        int[] params = getInput();
        Die[] dice = createDice(params[0], params[1]);
        int[] results = rollDice(dice, params[1], params[2]);
        int mostFrequent = findMax(results);
        report(params[0], results, mostFrequent);
    }

    private static int[] getInput() {
        Scanner input = new Scanner(System.in);
        int[] nums = new int[3];
        String[] numsString = new String[0];
        boolean valid = false;

        while (!valid){
            int errDice = 0;
            int errSides = 0;
            try{
                System.out.print("""
                Please enter the number of dice to roll, how many sides the dice have,
                and how many rolls to complete, separating the values by a space.
                Example: "2 6 1000"
                
                Enter configuration:""");
                numsString = input.nextLine().split(" ");

                if (numsString.length != 3) {
                    throw new InputMismatchException();
                }

                for(int i = 0; i < 3; i++){
                    nums[i] = Integer.parseInt(numsString[i]);
                    if(i == 0 && nums[i] < MIN_DICE || i == 0 && nums[i] > MAX_DICE){
                        errDice = nums[i];
                        //throw new IllegalArgumentException("1");
                        System.out.println("Bad die creation: Illegal number of dice: " + errDice);
                    }
                    if(i == 1 && nums[i] < Die.MIN_SIDE || i == 1 && nums[i] > Die.MAX_SIDE){
                        errSides = nums[i];
                        //throw new IllegalArgumentException("2");
                        System.out.println("Bad die creation: " +
                                "Illegal number of sides: " + errSides);
                    }
                }
                valid = true;
            } catch(NumberFormatException e){
                System.out.println("Invalid input: All values must be whole numbers.");
            } catch(InputMismatchException e){
                System.out.println("Invalid input: " +
                        "Expected 3 values but only received " + numsString.length);
            }
            /*
                        catch(IllegalArgumentException e){
                if(e.getMessage().equals("1")){
                    System.out.println("Bad die creation: Illegal number of dice: " + errDice);
                }
                if(e.getMessage().equals("2")){
                    System.out.println("Bad die creation: Illegal number of sides: " + errSides);
                }
            }
             */
        }
        return nums;
    }

    private static Die[] createDice(int numDice, int numSides){
        Die[] dice = new Die[numDice];
        for(int i = 0; i< numDice; i++){
            Die die = new Die(numSides);
            dice[i] = die;
        }
        return dice;
    }

    private static int[] rollDice(Die[] dice, int numSides, int numRolls){
        int[] counts = new int[numSides * dice.length - dice.length + 1];

        for(int rolls = 0; rolls < numRolls; rolls++) {
            int counter = 0;
            for(int d = 0; d<dice.length; d++){
                dice[d].roll();
                counter += dice[d].getCurrentValue();
            }
            counts[counter - dice.length]++;
        }

        return counts;
    }

    private static int findMax(int[] arr){
        int max = -1;
        for(int num: arr){
            max = Math.max(num, max);
        }
        return max;
    }

    private static void report(int numDice, int[] arr, int max){
        final int percentageScale = 10;
        final int case5 = 5;
        final int case6 = 6;
        final int case7 = 7;
        final int case8 = 8;
        final int case9 = 9;
        final int case10 = 10;
        for(int i = 0; i < arr.length; i++) {
            System.out.printf("%-2d:%-5d    ", i + numDice, arr[i]);
            int percentage = (int)(Math.floor(((double) arr[i] / max) * percentageScale));
            String stars = switch (percentage) {
                case 1 -> "*";
                case 2 -> "**";
                case 3 -> "***";
                case 4 -> "****";
                case case5 -> "*****";
                case case6 -> "******";
                case case7 -> "*******";
                case case8 -> "********";
                case case9 -> "*********";
                case case10 -> "**********";
                default -> "";
            };
            System.out.printf("%-10s%n", stars);
        }
    }

}