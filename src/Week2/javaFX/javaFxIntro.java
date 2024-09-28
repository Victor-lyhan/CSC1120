package Week2.javaFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class javaFxIntro extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();

        Scene scene = new Scene(root);

        HBox name = new HBox();
        Label fn = new Label("first name:");
        TextField tfn = new TextField();
        Label ln = new Label("last name:");
        TextField tln = new TextField();
        Button b = new Button("Enter");
        name.getChildren().addAll(fn, tfn, ln, tln, b);

        root.getChildren().add(name);

        stage.setScene(scene);
        stage.setTitle("Hello World");
        stage.show();
    }

//    private class ClickMeHandler implements EventHandler<ActionEvent> {
//        @Override
//        public void handle(){
//
//        }
//    }
}
