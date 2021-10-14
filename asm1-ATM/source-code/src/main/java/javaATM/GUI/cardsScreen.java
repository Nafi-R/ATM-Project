package javaATM.GUI;

import javaATM.ATM;
import javaATM.Account;
import javaATM.Card;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class cardsScreen {
    static Pane pane = new Pane();
    static TableView<Card> table = new TableView<>();
    static Scene scene= new Scene(pane, 700, 475);
    static Card selectedCard = null;
    static Stage stage = new Stage();

    public static void show()
    {
        stage.setTitle("Cards");
        createNodes();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static void createNodes(){
        table = new TableView<>();
        table.setMaxWidth(700);
        List<Node> nodes = new ArrayList<>();
        List<TableColumn<Card,?>> columns = new ArrayList<>();

        TableColumn<Card, Integer> carNumColumn = new TableColumn<>("Card Number");
        carNumColumn.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        carNumColumn.setMinWidth(200);
        columns.add(carNumColumn);

        TableColumn<Card, String> accNumColumn = new TableColumn<>("Account Number");
        accNumColumn.setCellValueFactory(val -> new SimpleStringProperty(Integer.toString(val.getValue().getAccount().getAccountNumber())));
        accNumColumn.setMinWidth(200);
        columns.add(accNumColumn);

        TableColumn<Card, String> cardStatusColumn = new TableColumn<>("Card Status");
        cardStatusColumn.setCellValueFactory(val -> new SimpleStringProperty(val.getValue().getStatus()));
        cardStatusColumn.setMinWidth(100);
        columns.add(cardStatusColumn);

        TableColumn<Card, String> cardStartDateColumn = new TableColumn<>("Start Date");
        cardStartDateColumn.setCellValueFactory(val -> new SimpleStringProperty(val.getValue().getStartDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))));
        cardStartDateColumn.setMinWidth(100);
        columns.add(cardStartDateColumn);

        TableColumn<Card, String> cardEndDateColumn = new TableColumn<>("Expiry Date");
        cardEndDateColumn.setCellValueFactory(val -> new SimpleStringProperty(val.getValue().getExpiryDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))));
        cardEndDateColumn.setMinWidth(100);
        columns.add(cardEndDateColumn);


        table.setItems(getCardsList());
        table.getColumns().addAll(columns);


        Button updateBtn = nodeCreator.createButton("Refresh Cards");
        updateBtn.setLayoutX(50);
        updateBtn.setLayoutY(425);
        updateBtn.setOnMouseClicked(event -> {
                ObservableList<Card> cards = getCardsList();
                for(Card c : cards)
                    c.getStatus();
                table.setItems(cards);
                table.refresh();
        });
        nodes.add(updateBtn);

        Button createBtn = nodeCreator.createButton("Create New Card");
        createBtn.setLayoutX(150);
        createBtn.setLayoutY(425);
        createBtn.setOnMouseClicked(event -> addCardScreen.show());
        nodes.add(createBtn);

        Button lostBtn = nodeCreator.createButton("Set to Lost");
        lostBtn.setLayoutX(475);
        lostBtn.setLayoutY(425);
        lostBtn.setOnMouseClicked(event -> {
            selectedCard.setStatus("Lost");
            table.setItems(getCardsList());
            table.refresh();
        });
        lostBtn.setDisable(true);
        nodes.add(lostBtn);

        Button stolenBtn = nodeCreator.createButton("Set to Stolen");
        stolenBtn.setLayoutX(550);
        stolenBtn.setLayoutY(425);
        stolenBtn.setOnMouseClicked(event -> {
            selectedCard.setStatus("Stolen");
            table.setItems(getCardsList());
            table.refresh();
        });
        stolenBtn.setDisable(true);
        nodes.add(stolenBtn);

        table.setOnMouseClicked(event -> {
            selectedCard = table.getSelectionModel().getSelectedItem();
            if (selectedCard != null) {
                stolenBtn.setDisable(false);
                lostBtn.setDisable(false);
            }
        });

        pane.getChildren().addAll(nodes);
        pane.getChildren().add(table);
    }


    private static ObservableList<Card> getCardsList () {
        ObservableList<Card> outputList = FXCollections.observableArrayList();
        outputList.addAll(ATM.getCards());
        return outputList;
    }

    private static class addCardScreen{
        static Pane addCardPane;
        static TableView<Account> cardTable = new TableView<>();
        static Stage cardStage = new Stage();
        private static Account selectedAccount = null;
        static Scene scene;

        static void show()
        {
            updateTable();
            cardStage.setTitle("Create New Card");
            cardStage.setScene(scene);
            cardStage.setResizable(false);
            cardStage.setAlwaysOnTop(true);
;           cardStage.show();
        }


        private static void updateTable(){
            addCardPane = new Pane();
            scene = new Scene(addCardPane, 600, 250);
            cardTable = new TableView<>();
            cardTable.setMaxWidth(300);
            cardTable.setMaxHeight(200);
            cardTable.setLayoutY(50);
            List<Node> nodes = new ArrayList<>();
            TableColumn<Account, Integer> accNumColumn = new TableColumn<>("Account Number");
            accNumColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
            accNumColumn.setMinWidth(300);

            cardTable.setItems(getAccountsList());
            cardTable.getColumns().add(accNumColumn);
            nodes.add(cardTable);

            Button createBtn = nodeCreator.createButton("Create Card");
            createBtn.setLayoutX(375);
            createBtn.setLayoutY(150);

            createBtn.setDisable(true);
            nodes.add(createBtn);

            Button cancelBtn = nodeCreator.createButton("Cancel");
            cancelBtn.setLayoutX(475);
            cancelBtn.setLayoutY(150);
            cancelBtn.setOnMouseClicked(event -> cardStage.close());
            nodes.add(cancelBtn);

            cardTable.setOnMouseClicked(event -> {
                selectedAccount = addCardScreen.cardTable.getSelectionModel().getSelectedItem();
                if (selectedAccount != null)
                    createBtn.setDisable(false);
            });

            Text selectAsk = nodeCreator.createText("Select an account to link to this card:");
            selectAsk.setLayoutX(30);
            selectAsk.setLayoutY(25);
            nodes.add(selectAsk);


            Text pinAsk = nodeCreator.createText("Enter a 5 digit PIN number:");
            pinAsk.setLayoutX(375);
            pinAsk.setLayoutY(25);
            nodes.add(pinAsk);

            Text invalidTxt = nodeCreator.createText("Invalid PIN Number");
            invalidTxt.setLayoutX(390);
            invalidTxt.setLayoutY(60);
            invalidTxt.setFill(Paint.valueOf("RED"));
            invalidTxt.setVisible(false);
            nodes.add(invalidTxt);

            TextField pinInput = nodeCreator.createTextField("PIN number");
            pinInput.setLayoutX(375);
            pinInput.setLayoutY(75);
            nodes.add(pinInput);

            createBtn.setOnMouseClicked(event -> {
                String input = pinInput.getCharacters().toString();
                int pinNum = 0;
                try{
                     pinNum = Integer.parseInt(input);
                    if(pinNum > 99999 || pinNum < 10000){
                        invalidTxt.setVisible(true);
                    }
                    else{
                        Card c = new Card(pinNum, selectedAccount);
                        ATM.addCard(c);
                        cardsScreen.table.setItems(getCardsList());
                        cardsScreen.table.refresh();
                        popUpScreen.show("New Card  '" + c.getCardNumber()  + "' added to account '"+selectedAccount.getAccountNumber()+"'" , "Card Added", Paint.valueOf("Green"));
                        cardStage.close();
                    }
                }
                catch(NumberFormatException nfe)
                {
                    invalidTxt.setVisible(true);
                }
            });

            addCardPane.getChildren().addAll(nodes);
        }

        private static ObservableList<Account> getAccountsList() {
            ObservableList<Account> output = FXCollections.observableArrayList();
            output.addAll(ATM.getAccounts());
            return output;
        }

    }

}
