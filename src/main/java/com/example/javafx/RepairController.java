package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RepairController implements Initializable {

    @FXML
    private TextField target;

    @FXML
    private TextArea explain;

    @FXML
    void Fix(ActionEvent event) {
        String Target = target.getText();
        String Explain = explain.getText();
        int index = HelloController.IndexViewAllItem;
        HelloController.dictionary.get(index).setWord_target(Target);
        HelloController.dictionary.get(index).setWord_explain(Explain);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        target.setText(HelloController.dictionary.get(HelloController.IndexViewAllItem).getWord_target());
        explain.setText(HelloController.dictionary.get(HelloController.IndexViewAllItem).getWord_explain());
    }
}
