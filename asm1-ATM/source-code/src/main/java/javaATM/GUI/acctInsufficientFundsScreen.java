package javaATM.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
* a class which shows an account having insufficient funds
* to withdraw an amount X
*/

public class acctInsufficientFundsScreen {

	Pane pane;
	
	public acctInsufficientFundsScreen() {
		pane = new Pane();
		createNodes();
	}
	
	public void createNodes() {
		Text ifundstxt = nodeCreator.createText("Error: Your account has insufficient funds to perform this withdrawal.");
		Text ifundstxt2 = nodeCreator.createText("You have not been charged.");
		
		// styling ifundstxt
		ifundstxt.setStyle("-fx-font-size: 20");
		ifundstxt.setLayoutX(70);
		ifundstxt.setLayoutY(250);
		
		// styling ifundstxt2
		ifundstxt2.setStyle("-fx-font-size: 20");
		ifundstxt2.setLayoutX(250);
		ifundstxt2.setLayoutY(350);

		Button goBack = nodeCreator.createButton("Return Home");
		goBack.setLayoutX(370);
		goBack.setLayoutY(700);
		goBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				goBack.getScene().setRoot(new homeScreen().getHomeScreen());
			}
		});
		
		pane.getChildren().addAll(ifundstxt,ifundstxt2);
	}
	
	public Pane getAcctInsufficientFundsScreen() {
		return this.pane;
	}
}
