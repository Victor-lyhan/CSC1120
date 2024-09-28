/*
 * Course: CSC1020
 * HW4 - Calculator part 2
 * controller class
 * Name: Victor Han
 * Last Updated: 9/25/2024
 */
package Week4.HW;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * controller class for FXML
 */
public class CalculatorController {
    private int curOp = 1;
    private final int ops = 5;

    @FXML
    private TextField num1;
    @FXML
    private TextField num2;
    @FXML
    private Button operationsButton;
    @FXML
    private Label resultLabel;

    @FXML
    private void opSwitch(){
        if(curOp <= ops){
            curOp++;
        } else {
            curOp = 1;
        }
        switch (curOp) {
            case 1 -> operationsButton.setText("+");
            case 2 -> operationsButton.setText("-");
            case 3 -> operationsButton.setText("*");
            case 4 -> operationsButton.setText("/");
            case ops -> operationsButton.setText("%");
        }
    }
    @FXML
    private void calculation() {
        try {
            int number1 = Integer.parseInt(num1.getText());
            int number2 = Integer.parseInt(num2.getText());
            int result = 0;
            boolean validResult = true;
            switch (curOp) {
                case 1 -> result = number1 + number2;
                case 2 -> result = number1 - number2;
                case 3 -> result = number1 * number2;
                case 4 -> {
                    if (number2 == 0) {
                        resultLabel.setText("Cannot divide by zero");
                        resultLabel.setStyle("-fx-background-color: red;");
                        validResult = false;
                    } else {
                        result = number1 / number2;
                    }
                }
                case ops -> {
                    if (number2 == 0) {
                        resultLabel.setText("Cannot modulo by zero");
                        resultLabel.setStyle("-fx-background-color: red;");
                        validResult = false;
                    } else {
                        result = number1 % number2;
                    }
                }
            }
            if (validResult) {
                resultLabel.setText(String.valueOf(result));
                String style = result < 0 ? "-fx-background-color: red;" :
                        "-fx-background-color: lightgrey;";
                resultLabel.setStyle(style);
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid Input");
            resultLabel.setStyle("-fx-background-color: red;");
        }
    }
}
