/*
 * Course: CSC1020
 * Fall 2024
 * Week3.Week4.Lab 4 - Image Manipulator 3000
 * Name: Victor Han
 * Created: 9/18/2024
 */

package Week3.Lab;

import edu.msoe.cs1021.ImageUtil;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * JavaFX program
 */
public class Lab8 extends Application {
    private Image originalImage;
    private final ObservableList<String> tasks = FXCollections.observableArrayList();
    private final Map<String, Image> taskImages = new HashMap<>();

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
    private final double red = 0.2126;
    private final double green = 0.7152;
    private final double blue = 0.0722;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loginView(primaryStage);
    }

    /* login scene */
    private void loginView(Stage stage) {
        Label welcomeLabel = new Label("Welcome to the Image Manipulator");
        Label createdByLabel = new Label("This program is created by Victor Han");
        Label specialThanksLabel = new Label("Special thanks to Mr. Jones.");
        CheckBox policyCheckbox = new CheckBox("I understand: ");
        Label policyLabel = new Label("Using this program means conforming to all policies.");

        welcomeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        Button proceedButton = new Button("→");
        proceedButton.setDisable(true);

        policyCheckbox.selectedProperty().addListener((observable, oldValue, newValue) ->
                proceedButton.setDisable(!newValue));

        proceedButton.setOnAction(e -> {
            FadeTransition fadeOut = new FadeTransition(
                    Duration.millis(eightHundred), stage.getScene().getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(ee -> tasksView(stage));
            fadeOut.play();
        });

        VBox loginLayout = new VBox(ten, welcomeLabel, createdByLabel, specialThanksLabel,
                policyCheckbox, policyLabel, proceedButton);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(twenty));

        Scene loginScene = new Scene(loginLayout, fourHundred, twoHundred);
        stage.setTitle("Login");
        stage.setScene(loginScene);
        stage.show();
    }

    /* task scene */
    private void tasksView(Stage stage) {
        Label titleLabel = new Label("Tasks");
        Button addTaskButton = new Button("+");
        Button deleteTaskButton = new Button("-");
        Button quitButton = new Button("Quit");

        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        ListView<String> taskListView = new ListView<>(tasks);

        /* listeners */
        taskListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedTask = taskListView.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    Image taskImage = taskImages.get(selectedTask);
                    viewOnlyScene(stage, taskImage);
                }
            }
        });

        addTaskButton.setOnAction(e -> functionView(stage, null, null));

        deleteTaskButton.setOnAction(e -> {
            String selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                tasks.remove(selectedTask);
            } else {
                showError("No task selected.");
            }
        });

        quitButton.setOnAction(e -> quitProgram(stage));

        /* layout */
        HBox buttons = new HBox(ten, addTaskButton, deleteTaskButton);
        buttons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(ten, titleLabel, buttons, taskListView, quitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(twenty));

        Scene tasksScene = new Scene(layout, fourHundred, threeHundred);
        stage.setScene(tasksScene);
        stage.show();
    }

    /* function scene */
    private void functionView(Stage stage, Image taskImage, String taskName) {
        /* ImageView */
        ImageView iv = new ImageView();
        iv.setFitWidth(threeHundred);
        iv.setFitHeight(threeFifty);
        iv.setPreserveRatio(true);

        if (taskImage != null) {
            iv.setImage(taskImage);
        }

        /* pixel color display */
        Label colorLabel = new Label("Hover over the image to see pixel color");
        colorLabel.setStyle("-fx-font-weight: bold;");

        /* Buttons */
        Button open = new Button("Open");
        Button save = new Button("Save");
        Button reload = new Button("Reload");
        Button grayscale = new Button("Grayscale");
        Button negative = new Button("Negative");
        Button complete = new Button("Complete");
        Button cancel = new Button("Cancel");

        open.setMinWidth(oneThirty);
        save.setMinWidth(oneThirty);
        reload.setMinWidth(oneThirty);
        grayscale.setMinWidth(oneThirty);
        negative.setMinWidth(oneThirty);
        complete.setMinWidth(oneThirty);
        cancel.setMinWidth(oneThirty);

        /* Event Handlers */
        open.setOnAction(e -> load(iv, stage));
        reload.setOnAction(e -> reload(iv));
        grayscale.setOnAction(e -> applyGrayscale(iv, taskName));
        negative.setOnAction(e -> applyNegative(iv, taskName));
        save.setOnAction(e -> save(iv, stage));

        iv.setOnMouseMoved(event -> {
            Image image = iv.getImage();
            if (image != null) {
                int x = (int) (event.getX() *
                        (image.getWidth() / iv.getBoundsInLocal().getWidth()));
                int y = (int) (event.getY() *
                        (image.getHeight() / iv.getBoundsInLocal().getHeight()));

                if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
                    PixelReader pixelReader = image.getPixelReader();
                    Color color = pixelReader.getColor(x, y);

                    colorLabel.setText(String.format("RGB: (%.0f, %.0f, %.0f)",
                            color.getRed() * twoFiftyFive,
                            color.getGreen() * twoFiftyFive,
                            color.getBlue() * twoFiftyFive));
                } else {
                    colorLabel.setText("Out of bounds");
                }
            }
        });

        complete.setOnAction(e -> {
            TextInputDialog taskDialog = new TextInputDialog();
            taskDialog.setTitle("Task Name");
            taskDialog.setHeaderText("Enter a name for your task:");
            taskDialog.setContentText("Task name:");

            taskDialog.showAndWait().ifPresent(taskN -> {
                if (!taskN.isEmpty()) {
                    tasks.add(taskN);
                    taskImages.put(taskN, iv.getImage());
                } else {
                    showError("Task name cannot be empty.");
                }

                originalImage = null;
                tasksView(stage);
            });
        });

        cancel.setOnAction(e -> {
            originalImage = null;
            tasksView(stage);
        });

        /* buttons layout */
        VBox buttons = new VBox(eight, open, save, reload, grayscale, negative, complete, cancel);
        buttons.setAlignment(Pos.TOP_RIGHT);

        /* iv and buttons layout */
        HBox root = new HBox(twenty, iv, buttons);
        root.setAlignment(Pos.CENTER);

        /* iv, buttons, color label layout */
        VBox main = new VBox(twenty, root, colorLabel);
        main.setAlignment(Pos.TOP_CENTER);

        /* Stage */
        stage.setTitle("Image Manipulator");
        stage.setScene(new Scene(main));
        stage.show();
    }

    /* viewing only scene */
    private void viewOnlyScene(Stage stage, Image taskImage) {
        /* components */
        ImageView iv = new ImageView();
        iv.setFitWidth(threeHundred);
        iv.setFitHeight(threeFifty);
        iv.setPreserveRatio(true);
        iv.setImage(taskImage);

        Label message = new Label("Viewing Image - Task Completed");
        message.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Button aReturn = new Button("return");

        /* Listener */
        aReturn.setOnAction(e -> tasksView(stage));

        /* Layout */
        HBox bottom = new HBox(twenty, message, aReturn);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        VBox layout = new VBox(twenty, iv, bottom);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(twenty));

        Scene scene = new Scene(layout, fourHundred, fourHundred);
        stage.setScene(scene);
        stage.setTitle("View Image");
        stage.show();
    }

    /* Load an image */
    private void load(ImageView iv, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.msoe")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                if (selectedFile.getName().endsWith(".msoe")) {
                    FileReader fileReader = new FileReader(selectedFile);
                    BufferedReader reader = new BufferedReader(fileReader);
                    ImageIO.loadMSOE(selectedFile, iv, fileReader, reader);
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

    /* Load MSOE image*/
    /*
    private void loadMSOE(File file, ImageView iv) {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String fileType = reader.readLine();
            if (!"MSOE".equals(fileType)) {
                showError("Invalid MSOE file format.");
                return;
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
                        showError("Invalid color format in the MSOE file: " + colorString);
                        return;
                    }
                }
            }

            iv.setImage(image);
            originalImage = image;

        } catch (IOException e) {
            showError("Error loading MSOE file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            showError("Invalid color format in the MSOE file.");
        }
    }
     */

    /* reload original image */
    private void reload(ImageView iv) {
        if (originalImage != null) {
            iv.setImage(originalImage);
        } else {
            showError("No image to reload.");
        }
    }

    /* Save the current image */
    private void save(ImageView iv, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.msoe")
        );
        File selectedFile = fileChooser.showSaveDialog(stage);
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

    /* Get file extension for saving */
    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return ""; // Default to no extension
        }
    }

    /* Save as PNG */
    /*
    private void saveAsPNG(ImageView iv, File file) throws IOException {
        Image image = iv.getImage();
        WritableImage writableImage = new WritableImage(
        (int) image.getWidth(), (int) image.getHeight());
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int y = 0; y < (int) image.getHeight(); y++) {
            for (int x = 0; x < (int) image.getWidth(); x++) {
                writer.setColor(x, y, reader.getColor(x, y));
            }
        }

        try (FileOutputStream output = new FileOutputStream(file)) {
            PngEncoderFX pngEncoder = new PngEncoderFX();
            pngEncoder.write(output, writableImage);
        }
    }
     */

    /* Save as JPG */
    /*
    private void saveAsJPG(ImageView iv, File file) throws IOException {
        Image image = iv.getImage();
        WritableImage writableImage = new WritableImage(
        (int) image.getWidth(), (int) image.getHeight());
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        // Remove alpha channel for JPG
        for (int y = 0; y < (int) image.getHeight(); y++) {
            for (int x = 0; x < (int) image.getWidth(); x++) {
                Color color = reader.getColor(x, y);
                Color noAlphaColor = new Color(
                color.getRed(), color.getGreen(), color.getBlue(), 1.0);
                writer.setColor(x, y, noAlphaColor);  // Set the pixel without alpha transparency
            }
        }

        try (FileOutputStream output = new FileOutputStream(file)) {
            JpgEncoderFX jpgEncoder = new JpgEncoderFX();
            jpgEncoder.write(output, writableImage);
        }
    }
     */

    /* Save as MSOE */
    /*
    private void saveAsMSOE(ImageView iv, File file) throws IOException {
        Image image = iv.getImage();
        PixelReader reader = image.getPixelReader();

        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            // Write the MSOE file format header
            writer.write("MSOE\n");
            writer.write((int) image.getWidth() + " " + (int) image.getHeight() + "\n");

            // Write pixel data as color hex values
            for (int y = 0; y < (int) image.getHeight(); y++) {
                for (int x = 0; x < (int) image.getWidth(); x++) {
                    Color color = reader.getColor(x, y);
                    String colorString = String.format("#%02X%02X%02X%02X",
                            (int) (color.getRed() * 255),
                            (int) (color.getGreen() * 255),
                            (int) (color.getBlue() * 255),
                            (int) (color.getOpacity() * 255));

                    writer.write(colorString + " ");
                }
                writer.newLine();
            }

            writer.flush();
        }
    }
    */

    /* Apply Grayscale filter */
    private void applyGrayscale(ImageView iv, String taskName) {
        Image srcImage = iv.getImage();
        int width = (int) srcImage.getWidth();
        int height = (int) srcImage.getHeight();

        WritableImage grayscaleImage = new WritableImage(width, height);
        PixelReader reader = srcImage.getPixelReader();
        PixelWriter writer = grayscaleImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);

                double gray = red * color.getRed() +
                        green * color.getGreen() +
                        blue * color.getBlue();

                Color grayscaleColor = new Color(gray, gray, gray, color.getOpacity());
                writer.setColor(x, y, grayscaleColor);
            }
        }

        iv.setImage(grayscaleImage);
        taskImages.put(taskName, grayscaleImage);
    }

    /* Negative transformation */
    private void applyNegative(ImageView iv, String taskName) {
        Image srcImage = iv.getImage();
        int width = (int) srcImage.getWidth();
        int height = (int) srcImage.getHeight();

        WritableImage negativeImage = new WritableImage(width, height);
        PixelReader reader = srcImage.getPixelReader();
        PixelWriter writer = negativeImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);

                Color negativeColor = new Color(
                        1.0 - color.getRed(),
                        1.0 - color.getGreen(),
                        1.0 - color.getBlue(),
                        color.getOpacity()
                );

                writer.setColor(x, y, negativeColor);
            }
        }

        iv.setImage(negativeImage);
        taskImages.put(taskName, negativeImage);
    }

    /* quit */
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

    /* Show error dialog */
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /* Show success dialog */
    private void showSuccess(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
}