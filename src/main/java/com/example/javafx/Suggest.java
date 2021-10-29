package com.example.javafx;

import com.example.solution.Dictionary;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.util.ArrayList;


public class Suggest {
    private AnchorPane wrap;
    private TextField input;
    private Dictionary dictionary;
    private ArrayList<String> data;
    private ListView<Text> listView;
    public Suggest(AnchorPane wrap, TextField input, Dictionary dictionary) {
        this.wrap = wrap;
        this.input = input;
        this.dictionary = dictionary;
        this.data = new ArrayList<String>();
        this.listView = new ListView<Text>();
        this.listView.setVisible(false);
        this.wrap.getChildren().add(this.listView);
        this.listView.setLayoutX(0);
        this.listView.setLayoutY(52);
        this.listView.setMaxHeight(50);
    }

    private void dataSuggest(String input) {
        this.data.clear();
        boolean isStart = false;
        int size = this.dictionary.list_word.size();
        for(int i = 0; i < size; i++) {
            if(this.dictionary.get(i).getWord_target().length() >= input.length()) {
                if (this.dictionary.get(i).getWord_target().substring(0, input.length()).equals(input)) {
                    isStart = true;
                    this.data.add(this.dictionary.get(i).getWord_target());
                } else {
                    if (isStart) {
                        break;
                    }
                }
            }
        }
    }

    public void solSuggest() {
        this.input.setOnKeyReleased((KeyEvent event) -> {
            if(event.getCode() != KeyCode.ENTER) {
                dataSuggest(input.getText());
                this.listView.setVisible(true);
                if(this.data.size() == 0 || input.getText().length() == 0) {
                    this.listView.setVisible(false);
                }
                this.listView.getItems().clear();
                for (int i = 0; i < this.data.size(); i++) {
                    Text text = new Text(this.data.get(i));
                    this.listView.getItems().add(text);
                }
            } else {
                this.listView.setVisible(false);
            }
        });

    }

}
