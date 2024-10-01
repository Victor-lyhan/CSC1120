/*
 * Course: CSC1020
 * Lab4 - Image Manipulator Part II
 * Transformable interface
 * Name: Victor Han
 * Last Updated: 9/30/2024
 */
package Week4.Lab;
import javafx.scene.paint.Color;

/**
 * interface
 */
@FunctionalInterface
public interface Transformable {
    /**
     * interface method - apply color
     * @param color color to print
     * @param y redGray alternating
     * @return color to print
     */
    Color apply(int y, Color color);
}
