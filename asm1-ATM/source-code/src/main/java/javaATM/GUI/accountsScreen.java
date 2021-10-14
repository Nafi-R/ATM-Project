package javaATM.GUI;

import javaATM.Account;
import javaATM.ATM;
import javaATM.Transaction;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class accountsScreen {
    static Pane pane = new Pane();
    static TableView<Account> table = new TableView<>();
    static Account selectedAccount = null;
    static Scene scene = new Scene(pane, 700, 475);
    static Stage stage = new Stage();

    public static void show(){
        createNodes();
        stage.setTitle("Accounts");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public Pane getAccountsScreenPane() {
        return pane;
    }

    private static  void createNodes(){
        table = new TableView<>();
        table.setMaxWidth(700);
        List<TableColumn<Account, ?>> columns = new ArrayList<>();
        TableColumn<Account, Integer> accNumColumn = new TableColumn<>("Account Number");
        accNumColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        accNumColumn.setMinWidth(300);
        columns.add(accNumColumn);

        TableColumn<Account, String> accBalanceColumn = new TableColumn<>("Balance");
        accBalanceColumn.setMinWidth(400);
        DecimalFormatSymbols currencySymbol = new DecimalFormatSymbols(Locale.ENGLISH);
        currencySymbol.setDecimalSeparator('.');
        currencySymbol.setGroupingSeparator(',');

        DecimalFormat currency = new DecimalFormat("$###,##0.00",currencySymbol);
        currency.setGroupingSize(3);
        currency.setDecimalSeparatorAlwaysShown(true);

        accBalanceColumn.setCellValueFactory(account -> {
            String formattedCost = currency.format(account.getValue().getBalance());
            return new SimpleStringProperty(formattedCost);
        });
        columns.add(accBalanceColumn);

        table.setItems(getAccountsList());
        table.getColumns().addAll(columns);

        Button rmBtn = nodeCreator.createButton("Remove Account");
        rmBtn.setLayoutX(100);
        rmBtn.setLayoutY(425);
        rmBtn.setOnMouseClicked(event -> {
                if(selectedAccount == null){
                    System.out.println("No account selected!");
                }
                else {
                    ATM.removeAccount(selectedAccount);
                    table.setItems(getAccountsList());
                    table.refresh();
                }

        });
        rmBtn.setDisable(true);

        Button transBtn = nodeCreator.createButton("View Transactions");
        transBtn.setLayoutX(275);
        transBtn.setLayoutY(425);
        transBtn.setOnMouseClicked(event -> {
            if(selectedAccount == null){
                System.out.println("No account selected!");
            }
            else {
                accountTransactionScreen.show(selectedAccount);
            }

        });
        transBtn.setDisable(true);

        Button addBtn = nodeCreator.createButton("Create New Account");
        addBtn.setLayoutX(450);
        addBtn.setLayoutY(425);
        addBtn.setOnMouseClicked(event -> {
                Account acc = new Account();
                ATM.addAccount(acc);
                table.setItems(getAccountsList());
                popUpScreen.show("New account '" + acc.getAccountNumber()  +"' added!", "Account Created", Paint.valueOf("Green"));
                table.refresh();
        });

        table.setOnMouseClicked(event -> {
            selectedAccount = table.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                rmBtn.setDisable(false);
                transBtn.setDisable(false);
            }
        });
        Button goBack = nodeCreator.createButton("Go Back");
        goBack.setLayoutX(450);
        goBack.setLayoutY(525);
        goBack.setOnMouseClicked(event -> {
            goBack.getScene().setRoot(new adminScreen().getAdminScreen());
        });
        pane.getChildren().addAll(rmBtn, addBtn, transBtn, goBack);
        pane.getChildren().add(table);


    }


    private static ObservableList<Account> getAccountsList () {
        ObservableList<Account> outputList = FXCollections.observableArrayList();
        outputList.addAll(ATM.getAccounts());
        return outputList;
    }

    private static class accountTransactionScreen{
        static Pane pane = new Pane();
        static TableView<Transaction> table = new TableView<>();
        static Stage stage = new Stage();
        static Scene scene = new Scene(pane, 700, 475);

        static void show(Account account)
        {
            createNodes(account);
            stage.setTitle("Transactions for Account: " + account.getAccountNumber());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        private static  void createNodes(Account account){
            table = new TableView<>();
            table.setMinWidth(700);
            List<TableColumn<Transaction, ?>> columns = new ArrayList<>();
            TableColumn<Transaction, Integer> transactNumColumn = new TableColumn<>("Transaction Number");
            transactNumColumn.setCellValueFactory(new PropertyValueFactory<>("transactNumber"));
            transactNumColumn.setMinWidth(200);
            columns.add(transactNumColumn);

            TableColumn<Transaction, Double> transactAmountColumn = new TableColumn<>("Transaction Amount");
            transactAmountColumn.setCellValueFactory(new PropertyValueFactory<>("transactAmt"));
            transactAmountColumn.setMinWidth(300);
            columns.add(transactAmountColumn);

            TableColumn<Transaction, Integer> transactTypeColumn = new TableColumn<>("Transaction Type");
            transactTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactAmt"));
            transactTypeColumn.setMinWidth(200);
            columns.add(transactTypeColumn);

            table.setItems(getTransactions(account));
            table.getColumns().addAll(columns);

            Button closeButton = nodeCreator.createButton("Close");
            closeButton.setLayoutX(325);
            closeButton.setLayoutY(425);
            closeButton.setOnMouseClicked(event -> stage.close());

            pane.getChildren().add(closeButton);
            pane.getChildren().add(table);
        }

        private static ObservableList<Transaction> getTransactions(Account account){
            ObservableList<Transaction> output = FXCollections.observableArrayList();
            output.addAll(account.getInvoices());
            return output;
        }
    }

}
