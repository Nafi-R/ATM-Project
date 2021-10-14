package javaATM.GUI;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import com.sun.prism.paint.Color;
import javaATM.GUILogic.guiLogic;
import javaATM.ATM;
import javaATM.Admin;
import javaATM.Card;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

import static javaATM.GUILogic.guiLogic.isInt;

public class adminLoginScreen {
    Pane pane;
    public adminLoginScreen() {
        pane = new Pane();
        createNodes();
    }

    public void createNodes() {
        Text adminModeText = nodeCreator.createText("Admin mode");
        adminModeText.setStyle("-fx-font-size: 40");
        adminModeText.setLayoutX(300);
        adminModeText.setLayoutY(100);

        Text IDpromptText = nodeCreator.createText("Enter admin ID:");
        IDpromptText.setStyle("-fx-font-size: 20");
        IDpromptText.setLayoutX(340);
        IDpromptText.setLayoutY(200);

        TextField IDText = nodeCreator.createTextField("Enter admin ID...");
        IDText.setLayoutX(260);
        IDText.setLayoutY(250);
        IDText.setPrefSize(300, 30);

        Text passcodePromptText = nodeCreator.createText("Enter passcode:");
        passcodePromptText.setStyle("-fx-font-size: 20");
        passcodePromptText.setLayoutX(340);
        passcodePromptText.setLayoutY(400);

        TextField passcodeText = nodeCreator.createTextField("Enter passcode...");
        passcodeText.setLayoutX(260);
        passcodeText.setLayoutY(450);
        passcodeText.setPrefSize(300, 30);

        Text errorText = nodeCreator.createText("Your admin credentials are incorrect. Please try again.");
        errorText.setStyle("-fx-font-size: 15");
        errorText.setFill(Paint.valueOf("RED"));
        errorText.setLayoutX(250);
        errorText.setLayoutY(660);

        Text IDerrorText = nodeCreator.createText("id must be integer");
        IDerrorText.setStyle("-fx-font-size: 10");
        IDerrorText.setFill(Paint.valueOf("RED"));
        IDerrorText.setLayoutX(260);
        IDerrorText.setLayoutY(300);
        IDerrorText.setVisible(false);


        Text passcodeErrorText = nodeCreator.createText("passcode must be integer");
        passcodeErrorText.setStyle("-fx-font-size: 10");
        passcodeErrorText.setFill(Paint.valueOf("RED"));
        passcodeErrorText.setLayoutX(260);
        passcodeErrorText.setLayoutY(500);
        passcodeErrorText.setVisible(false);
        //Submit button
        Button submit = nodeCreator.createButton("Submit");
        submit.setLayoutX(350);
        submit.setLayoutY(600);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String id = IDText.getText();
                String passcode = passcodeText.getText();
                if (! guiLogic.isInt(id) && ! guiLogic.isInt(passcode)){
                    IDText.setStyle("-fx-border-color: red;");
                    passcodeText.setStyle("-fx-border-color: red;");
                    IDerrorText.setVisible(true);
                    passcodeErrorText.setVisible(true);
                }else if(! guiLogic.isInt(passcode) && guiLogic.isInt(id)){
                    IDText.setStyle("-fx-border-color: null;");
                    passcodeText.setStyle("-fx-border-color: red;");
                    IDerrorText.setVisible(false);
                    passcodeErrorText.setVisible(true);
                }else if(! guiLogic.isInt(id) && guiLogic.isInt(passcode)){
                    IDText.setStyle("-fx-border-color: red;");
                    passcodeText.setStyle("-fx-border-color: null;");
                    IDerrorText.setVisible(true);
                    passcodeErrorText.setVisible(false);
                } else{
                    IDText.setStyle("-fx-border-color: null;");
                    passcodeText.setStyle("-fx-border-color: null;");
                    IDerrorText.setVisible(false);
                    passcodeErrorText.setVisible(false);
                    boolean verified = guiLogic.checkValidAdmin(Integer.parseInt(id), Integer.parseInt(passcode));
                    //testing only
                    //verified = true;
                    if(verified) {
                        submit.getScene().setRoot(new adminScreen().getAdminScreen());
                    }
                    else {
                        pane.getChildren().add(errorText);
                        submit.getScene().setRoot(pane);
                    }
                }
            }
        });
        Button goBack = nodeCreator.createButton("Go Back");
        goBack.setLayoutX(430);
        goBack.setLayoutY(600);
        goBack.setOnMouseClicked(event -> {
            goBack.getScene().setRoot(new homeScreen().getHomeScreen());
        });

        pane.getChildren().addAll(adminModeText, IDpromptText, IDText, passcodePromptText, passcodeText, submit, IDerrorText, passcodeErrorText, goBack);
    }

    public Pane getAdminLoginScreen() {
        return pane;
    }

}
