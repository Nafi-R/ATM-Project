package javaATM.GUI;

import javaATM.Card;
import javaATM.IO;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class balanceScreen {
    Pane pane;
    double balance;
    Card card;

    public balanceScreen(Card card) {
        pane = new Pane();
        balance = card.getBalance();
        createNodes();
        this.card = card;
    }

    public void createNodes() {
        Text headerText = nodeCreator.createText("Available funds");
        headerText.setStyle("-fx-font-size:20");
        headerText.setLayoutX(350);
        headerText.setLayoutY(200);

        Text promptText = nodeCreator.createText("Please see your available funds below. Go back to deposit or withdraw funds.");
        promptText.setStyle("-fx-font-size:16");
        promptText.setLayoutX(150);
        promptText.setLayoutY(250);

        Text balanceText = nodeCreator.createText("Balance: $"+Double.toString(balance));
        balanceText.setStyle("-fx-font-size:20");
        balanceText.setLayoutX(320);
        balanceText.setLayoutY(300);

        Button backButton = nodeCreator.createButton("Go back");
        backButton.setPrefSize(100, 30);
        backButton.setLayoutX(360);
        backButton.setLayoutY(400);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backButton.getScene().setRoot(new optionScreen(card).getOptionScreen());
            }
        });

        //exit button
        Button exitButton = nodeCreator.createButton("Exit ATM");
        exitButton.setLayoutX(340);
        exitButton.setLayoutY(600);
        exitButton.setPrefWidth(150);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            }
        });

        pane.getChildren().addAll(headerText, promptText, balanceText, backButton, exitButton);
    }

    public Pane getBalanceScreen() {
        return pane;
    }
}
