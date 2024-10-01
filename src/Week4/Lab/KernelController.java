/*
 * Course: CSC1020
 * Lab4 - Image Manipulator Part II
 * Kernel Controller
 * Name: Victor Han
 * Last Updated: 9/30/2024
 */
package Week4.Lab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * kernel controller
 */
public class KernelController {
    @FXML
    private Button blur;
    @FXML
    private Button sharpen;
    @FXML
    private Button apply;
    @FXML
    private Button cancel;
    @FXML
    private TextField textField11;
    @FXML
    private TextField textField12;
    @FXML
    private TextField textField13;
    @FXML
    private TextField textField21;
    @FXML
    private TextField textField22;
    @FXML
    private TextField textField23;
    @FXML
    private TextField textField31;
    @FXML
    private TextField textField32;
    @FXML
    private TextField textField33;

    private final int kernalParam = 9;
    private final int five = 5;
    private final int six = 6;
    private final int seven = 7;
    private final int eight = 8;

    private Lab4Controller lab4Controller;

    /**
     * double array for kernal filter
     */
    private double[] kernel = new double[kernalParam];

    public void setLab4Controller(Lab4Controller lab4Controller){
        this.lab4Controller = lab4Controller;
    }

    @FXML
    private void closeKernel(ActionEvent a){
        Stage curStage = (Stage)((Node) a.getSource()).getScene().getWindow();
        curStage.close();
    }

    @FXML
    private void setBlur(ActionEvent a){
        textField12.setText("1");
        textField21.setText("1");
        textField22.setText("5");
        textField23.setText("1");
        textField32.setText("1");
    }

    @FXML
    private void setSharpen(ActionEvent a){
        textField12.setText("-1");
        textField21.setText("-1");
        textField22.setText("5");
        textField23.setText("-1");
        textField32.setText("-1");
    }

    @FXML
    private void setApply(ActionEvent a){
        try {
            double sum = parseTextField(textField11) +
                    parseTextField(textField12) +
                    parseTextField(textField13) +
                    parseTextField(textField21) +
                    parseTextField(textField22) +
                    parseTextField(textField23) +
                    parseTextField(textField31) +
                    parseTextField(textField32) +
                    parseTextField(textField33);
            if (sum > 0) {
                kernel[0] = parseTextField(textField11) / sum;
                kernel[1] = parseTextField(textField12) / sum;
                kernel[2] = parseTextField(textField13) / sum;
                kernel[3] = parseTextField(textField21) / sum;
                kernel[4] = parseTextField(textField22) / sum;
                kernel[five] = parseTextField(textField23) / sum;
                kernel[six] = parseTextField(textField31) / sum;
                kernel[seven] = parseTextField(textField32) / sum;
                kernel[eight] = parseTextField(textField33) / sum;
                if (lab4Controller != null) {
                    lab4Controller.filtersRead(kernel);
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException e){
            showError("Weights must be integers");
        } catch (IllegalArgumentException e){
            showError("Weights must be positive");
        }
    }

    private double parseTextField(TextField textField) {
        if (textField.getText().isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(textField.getText());
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
