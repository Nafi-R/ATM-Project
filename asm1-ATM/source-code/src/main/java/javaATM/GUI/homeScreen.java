package javaATM.GUI;

import javaATM.ATM;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.time.LocalDate;

import java.util.List;
import java.sql.Date;

import javaATM.Card;
import javafx.event.ActionEvent;

import javaATM.GUILogic.guiLogic;

public class homeScreen {

    Pane pane;
    int attempts = 3;
    List<Card> cardsList;
    Card validCard;

    public homeScreen() {
        pane = new Pane();
        createNodes();
        this.cardsList = ATM.getCards();
    }

    public void createNodes() {
        Text welcomeText = nodeCreator.createText("Welcome to the ATM");
        welcomeText.setStyle("-fx-font-size: 40");
        welcomeText.setLayoutX(220);
        welcomeText.setLayoutY(100);

        Text promptText = nodeCreator.createText("Enter your card number below:");
        promptText.setStyle("-fx-font-size: 20");
        promptText.setLayoutX(270);
        promptText.setLayoutY(150);

        TextField cardText = nodeCreator.createTextField("Enter card number...");
        cardText.setLayoutX(260);
        cardText.setLayoutY(200);
        cardText.setPrefSize(300, 30);

        Text pinPromptText = nodeCreator.createText("Enter your pin number below:");
        pinPromptText.setStyle("-fx-font-size: 20");
        pinPromptText.setLayoutX(270);
        pinPromptText.setLayoutY(300);

        TextField pinText = nodeCreator.createTextField("Enter pin number...");
        pinText.setLayoutX(260);
        pinText.setLayoutY(350);
        pinText.setPrefSize(300, 30);

        Button doneButton = nodeCreator.createButton("Done");
        doneButton.setLayoutX(350);
        doneButton.setLayoutY(520);
        doneButton.setPrefSize(100, 30);
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(cardText.getText().equals("")) return;
                validCard = ATM.getCard(Integer.parseInt(cardText.getText()));

                if(validCard != null)
                {
                    int status = guiLogic.checkCard(validCard, pinText.getText());
                    if (status == 1){doneButton.getScene().setRoot(new optionScreen(validCard).getOptionScreen());}
                    else if (status == -1){
                        System.out.println(attempts);
                        if (attempts == 1) {
                            doneButton.getScene().setRoot(new apologyScreen(validCard).getApologyScreen());
                        }
                        else {
                            //testing only
                            attempts--;
                            pane.getChildren().retainAll(welcomeText, promptText, cardText, pinPromptText, pinText, doneButton);
                            pane.getChildren().add(makeErrorText());
                        }
                    }
                    else if (status == -2 || status == -3){
                        doneButton.getScene().setRoot(new dateInvalidScreen().getApologyScreen());
                    }
                    else {
                        doneButton.getScene().setRoot(new apologyScreen(validCard).getApologyScreen());
                    }
                }
                else {
                    pane.getChildren().retainAll(welcomeText, promptText, cardText, pinPromptText, pinText, doneButton);
                    pane.getChildren().add(makeNoCardText());
                }

            }
        });

        Button adminScreen = nodeCreator.createButton("Admin Login Screen");
        adminScreen.setLayoutX(300);
        adminScreen.setLayoutY(700);
        adminScreen.setPrefSize(200, 30);
        adminScreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                adminScreen.getScene().setRoot(new adminLoginScreen().getAdminLoginScreen());
            }
        });
        pane.getChildren().addAll(welcomeText, promptText, cardText, pinPromptText, pinText, doneButton, adminScreen);
    }

    public Text makeErrorText() {
        Text errorText = nodeCreator.createText("Your card details are incorrect. You have "+attempts+" attempts remaining.");
        errorText.setStyle("-fx-font-size: 12");
        errorText.setFill(Paint.valueOf("RED"));
        errorText.setLayoutX(250);
        errorText.setLayoutY(700);
        return errorText;
    }

    public Text makeNoCardText() {
        Text errorText = nodeCreator.createText("Provided card number was not found in system!");
        errorText.setStyle("-fx-font-size: 12");
        errorText.setFill(Paint.valueOf("RED"));
        errorText.setLayoutX(275);
        errorText.setLayoutY(700);
        return errorText;
    }

    public Pane getHomeScreen() {
        return pane;
    }
}


