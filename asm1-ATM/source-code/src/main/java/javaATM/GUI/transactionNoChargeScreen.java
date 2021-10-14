package javaATM.GUI;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class transactionNoChargeScreen {
	Pane pane;
	
	public transactionNoChargeScreen() {
		pane = new Pane();
		createNodes();
	}
	
	public void createNodes() {

		Text ifundstxt = nodeCreator.createText("Transaction Cancelled.");

		Text ifundstxt3 = nodeCreator.createText("You have not been charged.");
		
		// styling ifundstxt
		ifundstxt.setStyle("-fx-font-size: 20");
		ifundstxt.setLayoutX(275);
		ifundstxt.setLayoutY(250);
		
		// styling ifundstxt3
		ifundstxt3.setStyle("-fx-font-size: 20");
		ifundstxt3.setLayoutX(250);
		ifundstxt3.setLayoutY(350);
		
		pane.getChildren().addAll(ifundstxt,ifundstxt3);
	}
	
	public Pane getTransactionNoChargeScreen() {
		return this.pane;
	}
}
