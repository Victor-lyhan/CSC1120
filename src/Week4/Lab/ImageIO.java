/*
 * Course: CSC1020
 * Lab4 - Image Manipulator Part II
 * ImageIO
 * Name: Victor Han
 * Last Updated: 9/30/2024
 */
package Week4.Lab;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.InputMismatchException;

/**
 * ImageIO for reading and writing msoe and bmsoe
 */
public class ImageIO {
    private static final int RED_OFFSET = 16;
    private static final int GREEN_OFFSET = 8;
    private static final int ALPHA_OFFSET = 24;
    private static final int MASK = 0x000000FF;
    private static final double SCALAR = 255.0;
    private static final int OPACITY = 255;
    private static final int FILEHEADER = 5;
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
     * @throws IllegalArgumentException no images found
     */
    public static void saveAsMSOE(ImageView iv, File file) throws IOException {
        Image image = iv.getImage();
        if (image == null) {
            throw new IllegalArgumentException("No image to save");
        }
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

    /**
     *  class for loading bmsoe
     * @param file selected file - image
     * @param iv imagefield update
     * @throws IOException exception handling
     */
    public static void loadBMSOE(File file, ImageView iv) throws IOException {
        try (DataInputStream input = new DataInputStream(new FileInputStream(file))) {
            byte[] header = new byte[FILEHEADER];
            input.readFully(header);
            String headerStr = new String(header);
            if (!"BMSOE".equals(headerStr)) {
                throw new IOException("Invalid BMSOE file format");
            }

            int width = input.readInt();
            int height = input.readInt();

            WritableImage image = new WritableImage(width, height);
            PixelWriter writer = image.getPixelWriter();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = input.readInt();
                    Color color = intToColor(pixel);
                    writer.setColor(x, y, color);
                }
            }

            iv.setImage(image);
        }
    }

    /**
     * class for saving bmsoe
     * @param iv imagefield update
     * @param file user selected file
     * @throws IOException exception handling
     * @throws IllegalArgumentException exception handling
     */
    public static void saveAsBMSOE(ImageView iv, File file) throws IOException {
        Image image = iv.getImage();
        if (image == null) {
            throw new IllegalArgumentException("No image to save");
        }

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        PixelReader reader = image.getPixelReader();

        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(file))) {
            output.writeBytes("BMSOE");
            output.writeInt(width);
            output.writeInt(height);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = reader.getColor(x, y);
                    output.writeInt(colorToInt(color));
                }
            }
        }
    }

    private static Color intToColor(int color) {
        double red = ((color >> RED_OFFSET) & MASK)/SCALAR;
        double green = ((color >> GREEN_OFFSET) & MASK)/SCALAR;
        double blue = (color & MASK)/SCALAR;
        double alpha = ((color >> ALPHA_OFFSET) & MASK)/SCALAR;
        return new Color((int) red, (int) green, (int) blue, (int) alpha);
    }

    private static int colorToInt(Color color) {
        int red = ((int)(color.getRed()*SCALAR)) & MASK;
        int green = ((int)(color.getGreen()*SCALAR)) & MASK;
        int blue = ((int)(color.getBlue()*SCALAR)) & MASK;
        int alpha = ((int)(color.getOpacity()*SCALAR)) & MASK;
        return (alpha << ALPHA_OFFSET) + (red << RED_OFFSET) + (green << GREEN_OFFSET) + blue;
    }
}
