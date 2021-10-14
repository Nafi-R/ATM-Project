package javaATM.GUI;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javaATM.ATM;
import javaATM.IO;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class dateInvalidScreen {
    Pane pane;

    public dateInvalidScreen() {
        pane = new Pane();
        createNodes();
    }

    public void createNodes() {
        Text warningText = nodeCreator.createText("Your card has passed its expiry date or has been used before its issue date");
        warningText.setStyle("-fx-font-size: 14");
        warningText.setLayoutX(70);
        warningText.setLayoutY(200);

        Text apologyText = nodeCreator.createText("Apologies, your card has either been blocked or confiscated.");
        apologyText.setStyle("-fx-font-size: 16");
        apologyText.setLayoutX(200);
        apologyText.setLayoutY(300);


        //exit button
        Button exitButton = nodeCreator.createButton("Return Home");
        exitButton.setLayoutX(325);
        exitButton.setLayoutY(400);
        exitButton.setPrefWidth(150);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exitButton.getScene().setRoot(new homeScreen().getHomeScreen());
            }
        });

        pane.getChildren().addAll(warningText, apologyText, exitButton);
    }

    public Pane getApologyScreen() {
        return pane;
    }
}
