/*
 * Course: CSC1110 - 311
 * Fall 2024
 * Lecture - My First Program
 * Name: Liyang(Victor) Han
 * Created: 9/3/2024
 */
package hanl;

import java.util.Scanner;

public class ColorDriver {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("""
        Welcome to Number Converter 3000!
        This program will take a decimal number
        and convert it to binary and hexadecimal representations""");
        System.out.println( Color.BLUE + "Decimal will be displayed in blue" + Color.RESET);
        System.out.println( Color.RED + "Hexadecimal will be displayed in red" + Color.RESET);
        System.out.println( Color.GREEN + "Binary will be displayed in green" + Color.RESET);
        System.out.println("Please enter a number to convert (or q to quit)");

        String command = input.nextLine();
        while(!command.equals("q")){
            boolean isInt = validate(command);
            if(isInt){
                System.out.println(display(Integer.parseInt(command)));
            }
            System.out.println("Please enter a number to convert (or q to quit)");
            command = input.nextLine();
        }

        System.out.println("Thank you for using Number Converter 3000!");
    }

    public static boolean validate(String input){
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String display(int input) {
        return  Color.BLUE + "Decimal: " + Integer.toString(input) + Color.RESET + "\n" +
                Color.RED + "Hexadecimal: 0x" + Integer.toHexString(input).toUpperCase() + Color.RESET + "\n" +
                Color.GREEN + "Binary: " + Integer.toBinaryString(input) + Color.RESET + "\n";
    }

}
