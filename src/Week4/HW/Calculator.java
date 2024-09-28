/*
 * Course: CSC1020
 * HW4 - Calculator Part 2
 * Model class
 * Name: Victor Han
 * Last Updated: 9/25/2024
 */
package Week4.HW;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * model class that initializes
 * and launches the program
 */
public class Calculator extends Application {
    /**
     * main method
     * @param args paramter passed in
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Calculator.fxml"));
        stage.setTitle("Calculator");
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
