/*
 * Course: CSC1020
 * HW3 - calculator
 * Main class
 * Name: Victor Han
 * Last Updated: 9/16/2024
 */
package Week3.HW;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The main class including UI
 */
public class Calculator extends Application {
    private final int width = 800;
    private final int p24 = 24;
    private final int p18 = 18;
    private final int p10 = 10;
    private final int p20 = 20;
    private final int p150 = 150;
    private final int p300 = 300;
    @Override
    public void start(Stage primaryStage) {
        /* Components */
        /* row 1*/
        TextField num1 = new TextField();
        TextField num2 = new TextField();
        Button plus = new Button("+");
        Button equals = new Button("=");
        /* row 2 */
        Label sum = new Label("Result");
        sum.setFont(new Font("Arial", p24));
        sum.setPrefWidth(width);
        sum.setStyle("-fx-background-color: lightgrey;");

        /* Listener */
        EventHandler<ActionEvent> calculateSum = e -> {
            try {
                double number1 = Double.parseDouble(num1.getText());
                double number2 = Double.parseDouble(num2.getText());
                double result = number1 + number2;

                String style = result < 0 ? "-fx-background-color: red;"
                        : "-fx-background-color: lightgrey;";

                sum.setText(String.format("%.2f", result));
                sum.setStyle(style);
            } catch (NumberFormatException ex) {
                sum.setText("Invalid input");
            }
        };

        equals.setOnAction(calculateSum);
        num1.setOnAction(calculateSum);
        num2.setOnAction(calculateSum);

        /* Assigning */
        HBox inputBox = new HBox(p10, num1, plus, num2, equals);
        inputBox.setPrefWidth(width);

        VBox root = new VBox(p10, inputBox, sum);
        root.setPadding(new Insets(p20));

        Scene scene = new Scene(root, p300, p150);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Lauching the UI
     * @param args input
     */
    public static void main(String[] args) {
        launch(args);
    }
}
