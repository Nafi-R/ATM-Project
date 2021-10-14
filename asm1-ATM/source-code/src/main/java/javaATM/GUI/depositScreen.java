package javaATM.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

import javaATM.Card;
import javaATM.Transaction;

public class depositScreen {
    Pane pane;
    private ArrayList<Integer> deposits = new ArrayList<>();
    Transaction transaction;
    Card card;

    //handle deposit
    public depositScreen(Card card) {
        pane = new Pane();
        this.card = card;
        createNodes();
    }

    //handle add
    public void addAmount(Text amountText) {
        int total = 0;
        for(int i =0; i < deposits.size(); i++) {
            total+=deposits.get(i);
        }
        amountText.setText(Integer.toString(total));
    }

    public ArrayList<Integer> getDeposit() {
        return deposits;
    }

    //handle undo
    public void undoAmount(Text amountText) {
        if(deposits.size() > 0) {
            deposits.remove(deposits.size() - 1);
            addAmount(amountText);
        }
    }

    public void resetAmount(Text amountText) {
        deposits.clear();
        addAmount(amountText);
    }

    public void createEffect(Button button) {
        DropShadow shadow = new DropShadow();
        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button.setEffect(shadow);
                    }
                });
        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        button.setEffect(null);
                    }
                });
    }
    public void createNodes() {


        //initializing deposit
        Text welcomeText = nodeCreator.createText("Deposit");
        welcomeText.setStyle("-fx-font-size: 18");
        welcomeText.setLayoutX(385);
        welcomeText.setLayoutY(200);

        //Initializing the buttons
        String[] amt = {"100", "50", "10", "5"};
        int[] sizesX = {315, 430, 315, 430};
        int[] sizesY = {375, 375, 425, 425};
        ArrayList<Button> buttons = new ArrayList<>();
        for(int i = 0; i < amt.length; i++) {
            buttons.add(nodeCreator.createButton(amt[i]));
            buttons.get(i).setLayoutX(sizesX[i]);
            buttons.get(i).setLayoutY(sizesY[i]);
        }

        //Header to deposited value
        Text amountTitle = nodeCreator.createText("Choose amount to increment deposit value");
        amountTitle.setStyle("-fx-font-size: 18");
        amountTitle.setLayoutX(260);
        amountTitle.setLayoutY(300);
        Text amountText = nodeCreator.createText(Integer.toString(0));
        amountText.setLayoutX(260);
        amountText.setLayoutY(500);

        //Added onclick to buttons
        for(int i = 0; i < amt.length; i++) {
            int amount = Integer.parseInt(amt[i]);
            Button curr_button = buttons.get(i);
            curr_button.setPrefSize(100, 40);
            curr_button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    deposits.add(amount);
                    //amountText.setText(Integer.toString(depositedAmount));
                    addAmount(amountText);
                }
            });
        }

        //Undo button
        Button undo = nodeCreator.createButton("Undo");
        undo.setLayoutX(370);
        undo.setLayoutY(480);
        undo.setStyle("-fx-background-color: yellow;");
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                undoAmount(amountText);
            }
        });

        //shadow effect for undo
        createEffect(undo);

        //Reset button
        Button reset = nodeCreator.createButton("Reset");
        reset.setLayoutX(430);
        reset.setLayoutY(480);
        reset.setStyle("-fx-text-fill: white; -fx-background-color: red");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetAmount(amountText);
            }
        });

        createEffect(reset);
        //done button
        Button done = nodeCreator.createButton("Done");
        done.setLayoutX(370);
        done.setLayoutY(510);
        done.setPrefWidth(106);
        done.setStyle("-fx-background-color: green; -fx-text-fill: white");
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                processDeposit(Integer.parseInt(amountText.getText()));
                Transaction transaction = addTransaction(Integer.parseInt(amountText.getText()), "Deposit");
                done.getScene().setRoot(new receiptScreen(transaction).getReceiptScreen());            }
        });

        createEffect(done);

        Button backButton = nodeCreator.createButton("Go back");
        backButton.setPrefSize(100, 30);
        backButton.setLayoutX(370);
        backButton.setLayoutY(650);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backButton.getScene().setRoot(new optionScreen(card).getOptionScreen());
            }
        });

        //Add to pane
        pane.getChildren().addAll(welcomeText, amountTitle, amountText, undo, reset, done, backButton);
        for(int i = 0; i < buttons.size(); i++) {
            pane.getChildren().add(buttons.get(i));
        }
    }

    public Pane getDepositScreen() {
        return pane;
    }

    public Transaction addTransaction(int amount, String type) {
        Transaction transaction = new Transaction(card.getAccount(), type, card.getAccount().getInvoices().size()+1, amount);
        card.getAccount().addTransaction(transaction);
        return transaction;
    }

    public void processDeposit(int amount) {
        card.getAccount().addMoney(amount);
    }
}