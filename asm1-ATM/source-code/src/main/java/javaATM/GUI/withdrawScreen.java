package javaATM.GUI;

import java.util.ArrayList;

import javaATM.ATM;
import javaATM.Card;
import javaATM.Transaction;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class withdrawScreen {
    Pane pane;
    private ArrayList<Integer> withdraws = new ArrayList<>();
    Card card;

    public void testAmount() {

    }
    public withdrawScreen(Card card) {
        pane = new Pane();
        createNodes();
        this.card = card;
    }

    //handle add
    public void addAmount(Text amountText) {
        int total = 0;
        for(int i =0; i < withdraws.size(); i++) {
            total+=withdraws.get(i);
        }
        amountText.setText("$"+Integer.toString(total));
    }

    //handle undo
    public void undoAmount(Text amountText) {
        if(withdraws.size() > 0) {
            withdraws.remove(withdraws.size() - 1);
            addAmount(amountText);
        }
    }

    public void resetAmount(Text amountText) {
        withdraws.clear();
        addAmount(amountText);
    }

    public void createNodes() {
        Text welcomeText = nodeCreator.createText("Withdraw");
        welcomeText.setStyle("-fx-font-size: 20");
        welcomeText.setLayoutX(350);
        welcomeText.setLayoutY(200);

        Text promptText = nodeCreator.createText("Please enter below the amount you would like to withdraw.");
        promptText.setStyle("-fx-font-size: 18");
        promptText.setLayoutX(200);
        promptText.setLayoutY(250);

        TextField amountText = nodeCreator.createTextField("Enter in the amount to withdraw..");
        amountText.setPrefWidth(300);
        amountText.setLayoutX(260);
        amountText.setLayoutY(300);

        //error text
        Text errorText = nodeCreator.createText("Error: your account has insufficient funds. Please enter a smaller amount.");
        errorText.setStyle("-fx-font-size: 12");
        errorText.setFill(Paint.valueOf("RED"));
        errorText.setLayoutX(200);
        errorText.setLayoutY(350);

        Text errorAmount = nodeCreator.createText("Error: you have inputted an invalid amount. Please try again.");
        errorAmount.setStyle("-fx-font-size: 12");
        errorAmount.setFill(Paint.valueOf("RED"));
        errorAmount.setLayoutX(200);
        errorAmount.setLayoutY(350);

        //back button
        Button backButton = nodeCreator.createButton("Go back");
        backButton.setLayoutX(320);
        backButton.setLayoutY(540);
        backButton.setPrefWidth(150);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backButton.getScene().setRoot(new optionScreen(card).getOptionScreen());
            }
        });

        //Submit button
        Button submit = nodeCreator.createButton("Submit");
        submit.setLayoutX(320);
        submit.setLayoutY(400);
        submit.setPrefWidth(150);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double amount;
                try {
                    String txt = amountText.getText();

                    amount = Double.valueOf(txt);
                    System.out.println(amount);

                if (processWithdrawal(amount)) {
                    if (amount <= ATM.getFunds()) {
                        Transaction transaction = addTransaction(amount, "Withdraw");
                        submit.getScene().setRoot(new receiptScreen(transaction).getReceiptScreen());
                    } else {
                        submit.getScene().setRoot(new atmInsufficientFundsScreen().getAtmInsufficientFundsScreen());
                    }
                } else {
                    pane.getChildren().retainAll(welcomeText, promptText, amountText, submit, backButton);
                    pane.getChildren().add(errorText);
                }
                }
                catch (Exception e) {
                    pane.getChildren().retainAll(welcomeText, promptText, amountText, submit, backButton);
                    pane.getChildren().add(errorAmount);
                }
            }
        });

        //Add to pane
        pane.getChildren().addAll(welcomeText, promptText, amountText, submit, backButton);
    }

    public Transaction addTransaction(double amount, String type) {
        Transaction transaction = new Transaction(card.getAccount(), type, card.getAccount().getInvoices().size()+1, amount);
        card.getAccount().addTransaction(transaction);
        return transaction;
    }

    public boolean processWithdrawal(double amount) {
        boolean success = false;
        if (amount <= card.getBalance()) {
            card.getAccount().deductMoney(amount);
            success = true;
        }
        return success;
    }

    public Pane getWithdrawScreen() {
        return pane;
    }
}
