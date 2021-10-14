package javaATM.GUI;

import javaATM.Transaction;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javaATM.IO;

public class receiptScreen {
    Pane pane;
    Transaction transaction;
    int accountNo;
    String transactionType;
    int transactionNo;
    double transactionAmount;

    public receiptScreen(Transaction transaction) {
        pane = new Pane();
        this.accountNo = transaction.getAccount().getAccountNumber();
        this.transactionType = transaction.getTransactionType();
        this.transactionNo = transaction.getTransactionNumber();
        this.transactionAmount = transaction.getTransactionAmount();
        
        createNodes();
    }

    public void createNodes() {
        Text headerText = nodeCreator.createText("Success! Below is your receipt...");
        headerText.setStyle("-fx-font-size:20");
        headerText.setLayoutX(250);
        headerText.setLayoutY(200);

        //create nodes in accordance with transaction details
        Text accountText = nodeCreator.createText("Account number: "+Integer.toString(accountNo));
        accountText.setStyle("-fx-font-size:18");
        accountText.setLayoutX(250);
        accountText.setLayoutY(300);
        
        Text typeText = nodeCreator.createText("Transaction type: "+transactionType);
        typeText.setStyle("-fx-font-size:18");
        typeText.setLayoutX(250);
        typeText.setLayoutY(350);

        Text transactionText = nodeCreator.createText("Transaction number: "+Integer.toString(transactionNo));
        transactionText.setStyle("-fx-font-size:18");
        transactionText.setLayoutX(250);
        transactionText.setLayoutY(400);

        Text amountText = nodeCreator.createText("Transaction amount: $"+Double.toString(transactionAmount));
        amountText.setStyle("-fx-font-size:18");
        amountText.setLayoutX(250);
        amountText.setLayoutY(450);

        Text ejectText = nodeCreator.createText("Please take your card.");
        ejectText.setStyle("-fx-font-size:18");
        ejectText.setLayoutX(250);
        ejectText.setLayoutY(500);

        //exit button
        Button exitButton = nodeCreator.createButton("Return Home");
        exitButton.setLayoutX(300);
        exitButton.setLayoutY(600);
        exitButton.setPrefWidth(150);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exitButton.getScene().setRoot(new homeScreen().getHomeScreen());
            }
        });

        pane.getChildren().addAll(headerText, accountText, typeText, transactionText, amountText, ejectText, exitButton);
    }

    public Pane getReceiptScreen() {
        return pane;
    }


}
