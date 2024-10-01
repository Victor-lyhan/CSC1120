/*
 * Course: CSC1020
 * Lab4 - Image Manipulator Part II
 * Model class
 * Name: Victor Han
 * Last Updated: 9/30/2024
 */
package Week4.Lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class initiating javafx and launching the program
 */
public class Lab4 extends Application {
    /**
     * main method
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Lab4Controller.fxml"));
        stage.setTitle("Image Manipulator 3000");

        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
