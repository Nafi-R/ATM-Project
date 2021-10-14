package javaATM.GUI;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class nodeCreator {
    
    public static Text createText(String text) {
        return new Text(text);
    }

    public static Button createButton(String text) {
        return new Button(text);
    }

    public static TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setFocusTraversable(false);
        return textField;
    }

    public static DatePicker createDatePicker(String promptText) {
        DatePicker date = new DatePicker();
        date.setPromptText(promptText);
        return date;
    }
}
