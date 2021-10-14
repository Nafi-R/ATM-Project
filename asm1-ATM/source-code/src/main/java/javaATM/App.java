package javaATM;// package javaATM;

import javaATM.GUI.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

	homeScreen homeScreen;

	public void init() {
		IO.load();
		this.homeScreen = new homeScreen();
	}

	public void start(Stage stage) {
		init();
		Pane pane = new Pane(homeScreen.getHomeScreen());
		Scene scene = new Scene(pane, 800, 800);

		stage.setTitle("ATM");
		stage.setScene(scene);
		stage.show();
		//TEST FOR Jenkins
	}

	public void stop(){
		IO.save();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}