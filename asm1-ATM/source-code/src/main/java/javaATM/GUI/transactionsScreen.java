package javaATM.GUI;

import javaATM.ATM;
import javaATM.Account;
import javaATM.Transaction;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javaATM.IO;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class transactionsScreen {
    static Pane pane = new Pane();
    static TableView<Transaction> table = new TableView<>();
    static Stage stage = new Stage();
    static Scene scene = new Scene(pane, 800, 475);

    public static void show()
    {
        createNodes();
        stage.setTitle("ATM Transactions");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static  void createNodes(){
        table = new TableView<>();
        table.setMinWidth(800);
        List<TableColumn<Transaction, ?>> columns = new ArrayList<>();
        TableColumn<Transaction, String> accNumColumn = new TableColumn<>("Account Number");
        accNumColumn.setCellValueFactory(val -> new SimpleStringProperty("" +val.getValue().getAccount().getAccountNumber()));
        accNumColumn.setMinWidth(200);
        columns.add(accNumColumn);


        TableColumn<Transaction, String> transactNumColumn = new TableColumn<>("Transaction Number");
        transactNumColumn.setCellValueFactory(val -> new SimpleStringProperty(""+ val.getValue().getTransactionNumber()));
        transactNumColumn.setMinWidth(200);
        columns.add(transactNumColumn);

        DecimalFormatSymbols currencySymbol = new DecimalFormatSymbols(Locale.ENGLISH);
        currencySymbol.setDecimalSeparator('.');
        currencySymbol.setGroupingSeparator(',');
        DecimalFormat currency = new DecimalFormat("$###,##0.00",currencySymbol);
        currency.setGroupingSize(3);
        currency.setDecimalSeparatorAlwaysShown(true);

        TableColumn<Transaction, String> transactAmountColumn = new TableColumn<>("Transaction Amount");
        transactAmountColumn.setCellValueFactory(transObj -> {
            String formatted_amount  = currency.format(transObj.getValue().getTransactionAmount());
            return new SimpleStringProperty(formatted_amount);
        });
        transactAmountColumn.setMinWidth(200);
        columns.add(transactAmountColumn);

        TableColumn<Transaction, String> transactTypeColumn = new TableColumn<>("Transaction Type");
        transactTypeColumn.setCellValueFactory(transObj -> new SimpleStringProperty(transObj.getValue().getTransactionType()));
        transactTypeColumn.setMinWidth(200);
        columns.add(transactTypeColumn);

        table.setItems(getTransactions());
        table.getColumns().addAll(columns);

        Button closeButton = nodeCreator.createButton("Close");
        closeButton.setLayoutX(380);
        closeButton.setLayoutY(425);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();
            }
        });

        pane.getChildren().addAll(table, closeButton);
    }

    private static ObservableList<Transaction> getTransactions(){
        ObservableList<Transaction> output = FXCollections.observableArrayList();
        for(Account a : ATM.getAccounts())
            output.addAll(a.getInvoices());
        return output;
    }

    public Pane getTransactionsScreen() {
        return pane;
    }
}
