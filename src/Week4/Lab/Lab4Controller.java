/*
 * Course: CSC1020
 * Lab4 - Image Manipulator Part II
 * Lab 4 controller
 * Name: Victor Han
 * Last Updated: 9/30/2024
 */
package Week4.Lab;

import edu.msoe.cs1021.ImageUtil;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.InputMismatchException;

/**
 * controller for main window
 */
public class Lab4Controller {
    private final int two = 2;
    private final int eight = 8;
    private final int ten = 10;
    private final int twenty = 20;
    private final int hundred = 100;
    private final int oneThirty = 130;
    private final int twoHundred = 200;
    private final int twoFiftyFive = 255;
    private final int threeHundred = 300;
    private final int threeFifty = 350;
    private final int fourHundred = 400;
    private final int eightHundred = 800;
    private final int thousand = 1000;
    private final double redd = 0.2126;
    private final double green = 0.7152;
    private final double blue = 0.0722;

    @FXML
    private ImageView iv;
    @FXML
    private Button open;
    @FXML
    private Button save;
    @FXML
    private Button reload;
    @FXML
    private Button grayScale;
    @FXML
    private Button negative;
    @FXML
    private Button red;
    @FXML
    private Button redGray;
    @FXML
    private Button showFilter;

    private Image originalImage;
    private double[] kernel;

    /**
     * communication between two controllers
     * passing the kernal data from 2 to 1
     */
    @FXML
    public void initialize() {
        iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                quitProgram((Stage) iv.getScene().getWindow());
            }
        });
    }

    @FXML
    private void load(ActionEvent a) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg", "*.msoe", "*.bmsoe")
        );
        File selectedFile = fileChooser.showOpenDialog(open.getScene().getWindow());

        if (selectedFile != null) {
            try {
                if (selectedFile.getName().endsWith(".msoe")) {
                    FileReader fileReader = new FileReader(selectedFile);
                    BufferedReader reader = new BufferedReader(fileReader);
                    ImageIO.loadMSOE(selectedFile, iv, fileReader, reader);
                    originalImage = iv.getImage();
                } else if (selectedFile.getName().endsWith(".bmsoe")) {
                    ImageIO.loadBMSOE(selectedFile, iv);
                    originalImage = iv.getImage();
                } else {
                    FileInputStream input = new FileInputStream(selectedFile);
                    Image image = new Image(input);
                    iv.setImage(image);
                    originalImage = image;
                }
            } catch (IOException e) {
                showError("Error loading image.");
            } catch (InputMismatchException e){
                showError("Invalid MSOE file format.");
            } catch (IllegalArgumentException e){
                showError(e.getMessage());
            }
        }
    }

    @FXML
    private void reload(ActionEvent a) {
        if (originalImage != null) {
            iv.setImage(originalImage);
        } else {
            showError("No image to reload.");
        }
    }

    @FXML
    private void save(ActionEvent a) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg", "*.msoe", "*.bmsoe")
        );
        File selectedFile = fileChooser.showSaveDialog(open.getScene().getWindow());
        if (selectedFile != null) {
            ProgressIndicator progressIndicator = new ProgressIndicator();
            VBox layout = new VBox(progressIndicator);
            layout.setAlignment(Pos.CENTER);

            Scene progressScene = new Scene(layout, twoHundred, twoHundred);
            Stage progressStage = new Stage();
            progressStage.setTitle("Saving...");
            progressStage.setScene(progressScene);
            progressStage.show();

            new Thread(() -> {
                try {
                    String fileExtension = getFileExtension(selectedFile);
                    if (fileExtension.equalsIgnoreCase("png")) {
                        ImageUtil.writeImage(selectedFile.toPath(), iv.getImage());
                    } else if (fileExtension.equalsIgnoreCase("jpg") ||
                            fileExtension.equalsIgnoreCase("jpeg")) {
                        ImageUtil.writeImage(selectedFile.toPath(), iv.getImage());
                    } else if (fileExtension.equalsIgnoreCase("msoe")) {
                        ImageIO.saveAsMSOE(iv, selectedFile);
                    } else if (fileExtension.equalsIgnoreCase("bmsoe")) {
                        ImageIO.saveAsBMSOE(iv, selectedFile);
                    } else {
                        showError("Unsupported file format: " + fileExtension);
                    }

                    Thread.sleep(thousand);
                    Platform.runLater(() -> {
                        progressStage.close();
                        showSuccess("Image saved successfully!");
                    });
                } catch (IOException | InterruptedException e) {
                    showError("Error saving image.");
                    progressStage.close();
                }
            }).start();
        }
    }

    private Image transformImage(Image image, Transformable transformable){
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage transformedImage = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = transformedImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                writer.setColor(x, y, transformable.apply(y, color));
            }
        }

        return transformedImage;
    }

    @FXML
    private void applyGrayscale(ActionEvent a) {
        Image srcImage = iv.getImage();
        Image grayscaleImage = transformImage(srcImage, (y, color) -> {
            double gray = redd * color.getRed()+
                            green * color.getGreen()+
                            blue * color.getBlue();
            return new Color(gray, gray, gray, color.getOpacity());
        });
        iv.setImage(grayscaleImage);
    }

    @FXML
    private void applyNegative(ActionEvent a) {
        Image srcImage = iv.getImage();
        Image negativeImage = transformImage(srcImage, (y, color) ->
                new Color(1.0 - color.getRed(),
                        1.0 - color.getGreen(),
                        1.0 - color.getBlue(),
                        color.getOpacity())
        );
        iv.setImage(negativeImage);
    }

    @FXML
    private void applyRed(ActionEvent a) {
        Image srcImage = iv.getImage();
        Image redImage = transformImage(srcImage, (y, color) ->
            new Color(color.getRed(),
                    0.0,
                    0.0,
                    color.getOpacity()
            )
        );
        iv.setImage(redImage);
    }

    @FXML
    private void applyRedGray(ActionEvent a) {
        Image srcImage = iv.getImage();
        Image redGrayImage = transformImage(srcImage, (y, color) -> {
            if(y % 2 == 0){
                return new Color(color.getRed(),
                        0.0,
                        0.0,
                        color.getOpacity()
                );
            } else {
                double gray = redd * color.getRed() +
                                green * color.getGreen() +
                                blue * color.getBlue();
                return new Color(gray, gray, gray, color.getOpacity());
            }
        });
        iv.setImage(redGrayImage);
    }

    @FXML
    private void showFilterKernel(ActionEvent a){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("KernelController.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Filter Kernel");
            stage.setScene(new Scene(loader.load()));

            KernelController kernelController = loader.getController();
            kernelController.setLab4Controller(this);

            stage.show();

        } catch (IOException e) {
            showError(e.getMessage());
        }
    }

    /**
     * communication between two controllers
     * passing the kernal data from 2 to 1
     * @param data double array of kernal param
     */
    public void filtersRead(double[] data) {
        this.kernel = data;
        iv.setImage(ImageUtil.convolve(iv.getImage(), kernel));
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return ""; // Default to no extension
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void quitProgram(Stage stage) {
        // Create a message
        Label thankYouLabel = new Label("Thank you for using this program! Have a nice day!");
        thankYouLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        VBox messageLayout = new VBox(thankYouLabel);
        messageLayout.setAlignment(Pos.CENTER);

        Scene messageScene = new Scene(messageLayout, fourHundred, twoHundred);
        stage.setScene(messageScene);

        PauseTransition pause = new PauseTransition(Duration.seconds(two));
        pause.setOnFinished(e -> Platform.exit());
        pause.play();
    }
}
