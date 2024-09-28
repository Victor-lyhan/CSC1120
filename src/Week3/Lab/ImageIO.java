/*
 * Course: CSC1020
 * Fall 2024
 * Week3.Week4.Lab 4 - Image Manipulator 3000 - ImageIO
 * Name: Victor Han
 * Created: 9/18/2024
 */
package Week3.Lab;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.InputMismatchException;

/**
 * imageIO class for reading and writing msoe file
 */
public class ImageIO {
    private static final int OPACITY = 255;
    /**
     * loadMSOE method
     * @param file file user selected
     * @param iv imageview for displaying image
     * @param fileReader reader for reading the msoe file extension
     * @param reader bufferedreader that goes through the pixels
     * @throws IOException exception handling
     * @throws InputMismatchException exception for invalid msoe file
     * @throws IllegalArgumentException exception for wrong color code
     */
    public static void loadMSOE(File file, ImageView iv, FileReader fileReader,
                                BufferedReader reader) throws IOException {
        String fileType = reader.readLine();
        if (!"MSOE".equals(fileType)) {
            throw new InputMismatchException("Invalid MSOE file format.");
        }

        String[] dimensions = reader.readLine().split(" ");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);

        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int y = 0; y < height; y++) {
            String[] pixelColors = reader.readLine().trim().split("\\s+");
            for (int x = 0; x < width; x++) {
                if (x >= pixelColors.length || pixelColors[x].isEmpty()) {
                    continue;
                }

                String colorString = pixelColors[x];
                try {
                    Color color = Color.web(colorString);
                    pixelWriter.setColor(x, y, color);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "Invalid color format in the MSOE file: " + colorString);
                }
            }
        }
        iv.setImage(image);
    }

    /**
     * method for saving the image as msoe file
     * @param iv imageview that displays the image
     * @param file the image
     * @throws IOException exception handling
     */
    public static void saveAsMSOE(ImageView iv, File file) throws IOException {
        Image image = iv.getImage();
        PixelReader reader = image.getPixelReader();

        try (FileWriter fileWriter = new FileWriter(file);
                BufferedWriter writer = new BufferedWriter(fileWriter)) {

            writer.write("MSOE\n");
            writer.write((int) image.getWidth() + " " + (int) image.getHeight() + "\n");

            for (int y = 0; y < (int) image.getHeight(); y++) {
                for (int x = 0; x < (int) image.getWidth(); x++) {
                    Color color = reader.getColor(x, y);
                    String colorString = String.format("#%02X%02X%02X%02X",
                            (int) (color.getRed() * OPACITY),
                            (int) (color.getGreen() * OPACITY),
                            (int) (color.getBlue() * OPACITY),
                            (int) (color.getOpacity() * OPACITY));

                    writer.write(colorString + " ");
                }
                writer.newLine();
            }

            writer.flush();
        }
    }
}
