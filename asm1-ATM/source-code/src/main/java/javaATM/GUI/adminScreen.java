package javaATM.GUI;

import javaATM.GUILogic.guiLogic;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javaATM.IO;
import javafx.stage.Stage;
import javaATM.ATM;
import javaATM.Card;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class adminScreen {
    Pane pane;
    Card card;
    List<Card> cardsList = new ArrayList<>();
    public adminScreen(List<Card> cardsList) {
        pane = new Pane();
        createNodes();
        this.cardsList = cardsList;
    }

    //testing only
    public adminScreen() {
        pane = new Pane();
        createNodes();
    }

    public void createNodes() {
        Text headerText = nodeCreator.createText("ATM Maintenance");
        headerText.setStyle("-fx-font-size:20");
        headerText.setLayoutX(320);
        headerText.setLayoutY(200);

        TextField addText = nodeCreator.createTextField("Enter cash amount to be added..");
        addText.setLayoutX(275);
        addText.setLayoutY(300);
        addText.setPrefWidth(250);

        Text amountErrorText = nodeCreator.createText("must enter an integer");
        amountErrorText.setStyle("-fx-font-size: 10");
        amountErrorText.setFill(Paint.valueOf("RED"));
        amountErrorText.setLayoutX(275);
        amountErrorText.setLayoutY(340);
        amountErrorText.setVisible(false);

        Text addedFundText = nodeCreator.createText("ATM funds have been successfully added. ATM funds now total to $"+ATM.getFunds());
        addedFundText.setLayoutX(260);
        addedFundText.setLayoutY(340);
        addedFundText.setStyle("-fx-font-size: 10");
        addedFundText.setFill(Paint.valueOf("BLUE"));
        addedFundText.setVisible(false);

        Text promptText = nodeCreator.createText("Enter below to add to ATM funds:");
        promptText.setLayoutX(310);
        promptText.setLayoutY(250);

        Button submit = nodeCreator.createButton("Submit");
        submit.setLayoutX(320);
        submit.setLayoutY(350);
        submit.setPrefWidth(150);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String amount = addText.getText();
                if(! guiLogic.isInt(amount)){
                    addText.setStyle("-fx-border-color: red;");
                    amountErrorText.setVisible(true);
                    addedFundText.setVisible(false);
                }else{
                    addText.setStyle("-fx-border-color: null;");
                    amountErrorText.setVisible(false);
                    ATM.deposit(Integer.parseInt(addText.getText()));
                    addedFundText.setText("ATM funds have been successfully added. ATM funds now total to $"+ATM.getFunds());
                    addedFundText.setVisible(true);
                }

                submit.getScene().setRoot(pane);
            }
        });

        Button cards = nodeCreator.createButton("View/edit cards");
        cards.setLayoutX(320);
        cards.setLayoutY(400);
        cards.setPrefWidth(150);
        cards.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cardsScreen.show();
            }
        });

        Button accounts = nodeCreator.createButton("View/edit accounts");
        accounts.setLayoutX(320);
        accounts.setLayoutY(450);
        accounts.setPrefWidth(150);
        accounts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               accountsScreen.show();
            }
        });

        Button transactions = nodeCreator.createButton("View transactions");
        transactions.setLayoutX(320);
        transactions.setLayoutY(500);
        transactions.setPrefWidth(150);
        transactions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transactionsScreen.show();
            }
        });

        //back button
        Button goBack = nodeCreator.createButton("Go Back");
        goBack.setLayoutX(370);
        goBack.setLayoutY(700);
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goBack.getScene().setRoot(new homeScreen().getHomeScreen());
            }
        });

        pane.getChildren().addAll(headerText, promptText, addText, amountErrorText, addedFundText, submit, goBack, accounts, transactions,cards);
    }

    public Pane getAdminScreen() {
        return pane;
    }
}
