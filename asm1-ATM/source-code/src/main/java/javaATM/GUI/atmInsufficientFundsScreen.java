package javaATM.GUI;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
* a class which shows an account having insufficient funds
* to withdraw an amount X
*/

public class atmInsufficientFundsScreen {

	Pane pane;
	
	public atmInsufficientFundsScreen() {
		pane = new Pane();
		createNodes();
	}
	
	public void createNodes() {

		Text ifundstxt = nodeCreator.createText("Critical Error: ATM has insufficient funds to dispense your withdrawal");

		Text ifundstxt2 = nodeCreator.createText("The nearest ATM admin has been notified.");
		
		Text ifundstxt4 = nodeCreator.createText("For the moment, please consider using a different ATM.");
		
		Text ifundstxt3 = nodeCreator.createText("You have not been charged.");
		
		// styling ifundstxt
		ifundstxt.setStyle("-fx-font-size: 20");
		//ifundstxt.setTextAlignment(TextAlignment.CENTER);
		ifundstxt.setLayoutX(70);
		ifundstxt.setLayoutY(250);
		
		// styling ifundstxt4
		ifundstxt4.setStyle("-fx-font-size: 20");
		ifundstxt4.setLayoutX(110);
		ifundstxt4.setLayoutY(300);
		
		// styling ifundstxt2
		ifundstxt2.setStyle("-fx-font-size: 20");
		ifundstxt2.setLayoutX(240);
		ifundstxt2.setLayoutY(350);


		// styling ifundstxt3
		ifundstxt3.setStyle("-fx-font-size: 20");
		ifundstxt3.setLayoutX(250);
		ifundstxt3.setLayoutY(400);
		
		pane.getChildren().addAll(ifundstxt,ifundstxt3,ifundstxt4);
	}
	
	public Pane getAtmInsufficientFundsScreen() {
		return this.pane;
	}
}
