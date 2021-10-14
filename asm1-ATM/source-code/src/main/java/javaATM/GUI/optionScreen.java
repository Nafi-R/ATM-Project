package javaATM.GUI;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javafx.event.EventHandler;
import javaATM.Card;
import javafx.event.ActionEvent;
import javaATM.IO;
import javafx.stage.Stage;

public class optionScreen {
    Pane pane;
    Card card;

    public optionScreen(Card card) {
        pane = new Pane();
        createNodes();
        this.card = card;
    }

    public void createNodes() {
        Text text = new Text("Choose from one of the following options:");
        text.setStyle("-fx-font-size: 20");
        text.setLayoutX(225);
        text.setLayoutY(200);

        Button withdrawButton = new Button("Withdraw funds");
        withdrawButton.setPrefSize(150, 30);
        withdrawButton.setLayoutX(325);
        withdrawButton.setLayoutY(300);
        withdrawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                withdrawButton.getScene().setRoot(new withdrawScreen(card).getWithdrawScreen());
            }
        });

        Button depositButton = new Button("Deposit funds");
        depositButton.setPrefSize(150, 30);
        depositButton.setLayoutX(325);
        depositButton.setLayoutY(400);
        depositButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                depositButton.getScene().setRoot(new depositScreen(card).getDepositScreen());
            }
        });

        Button balanceButton = new Button("Check balance");
        balanceButton.setPrefSize(150, 30);
        balanceButton.setLayoutX(325);
        balanceButton.setLayoutY(500);
        balanceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                balanceButton.getScene().setRoot(new balanceScreen(card).getBalanceScreen());
            }
        });

        Button logoutButton = new Button("Return Home");
        logoutButton.setPrefSize(200, 30);
        logoutButton.setLayoutX(300);
        logoutButton.setLayoutY(600);
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logoutButton.getScene().setRoot(new homeScreen().getHomeScreen());
            }
        });


        pane.getChildren().addAll(text, withdrawButton, depositButton, balanceButton, logoutButton);
    }

    public Pane getOptionScreen() {
        return pane;
    }

}
