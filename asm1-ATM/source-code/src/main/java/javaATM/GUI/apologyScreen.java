package javaATM.GUI;

import javaATM.Card;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javaATM.ATM;
import javaATM.IO;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class apologyScreen {
    Pane pane;
    Card card;

    public apologyScreen(Card c) {
        pane = new Pane();
        createNodes();
        this.card = c;
    }

    public void createNodes() {
        Text warningText = nodeCreator.createText("There have been too many login attempts with this card and/or it has been detected as a lost or stolen card.");
        warningText.setStyle("-fx-font-size: 14");
        warningText.setLayoutX(70);
        warningText.setLayoutY(200);

        Text apologyText = nodeCreator.createText("Apologies, your card has either been blocked or confiscated.");
        apologyText.setStyle("-fx-font-size: 16");
        apologyText.setLayoutX(200);
        apologyText.setLayoutY(300);


        //exit button
        Button homeButton = nodeCreator.createButton("Return Home");
        homeButton.setLayoutX(325);
        homeButton.setLayoutY(400);
        homeButton.setPrefWidth(150);
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                homeButton.getScene().setRoot(new homeScreen().getHomeScreen());
                card.setStatus("Confiscated");
            }
        });

        pane.getChildren().addAll(warningText, apologyText, homeButton);
    }

    public Pane getApologyScreen() {
        return pane;
    }
}
