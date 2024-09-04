/*
 * Course: CSC1110 - 311
 * Fall 2024
 * Lecture - My First Program
 * Name: Liyang(Victor) Han
 * Created: 9/3/2024
 */
package hanl;

public enum Color {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String ansi;

    Color(String color) {
        ansi = color;
    }
    @Override
    public String toString() { return ansi; }
}