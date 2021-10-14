package javaATM.GUI;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class popUpScreen {
    static Pane pane = new Pane();
    static Scene scene;
    static Stage stage = new Stage();

    public static void show(String inputText)
    {
        show(inputText, "");
    }

    public static void show(String inputText, String title)
    {
        show(inputText,title, Paint.valueOf(null));
    }

    public static void show(String inputText, String title, Paint colour)
    {
        Text input = nodeCreator.createText(inputText);
        input.setFill(colour);
        pane = new Pane();
        scene = new Scene(pane, input.getLayoutBounds().getWidth() + 100, input.getLayoutBounds().getHeight() + 100);
        createNodes(input);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static void createNodes(Text input)
    {
        List<Node> nodes = new ArrayList<Node>();
        input.setLayoutX(50);
        input.setLayoutY(50);
        input.setTextAlignment(TextAlignment.CENTER);
        nodes.add(input);

        Button closeBtn = nodeCreator.createButton("Close");
        closeBtn.setLayoutX(scene.getWidth()/2 - 20);
        closeBtn.setLayoutY(input.getLayoutBounds().getHeight() + 50);
        closeBtn.setOnMouseClicked(event -> stage.close());
        nodes.add(closeBtn);
        pane.getChildren().addAll(nodes);

    }
}
