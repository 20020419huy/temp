package com.example.javafx;

import com.example.solution.Dictionary;
import com.example.solution.DictionaryCommandline;
import com.example.solution.DictionaryManagement;
import com.example.solution.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Note {

    private AnchorPane contentNote;
    private TableView<Word> table;
    private TableColumn<Word, String>target;
    private TableColumn<Word, String>explain;
    private AnchorPane wrapAdd;
    private TextField inputTarget;
    private TextField inputExplain;
    private Button submitAdd;
    private Dictionary dictionary;
    private TextField searchNote;
    private AnchorPane submitSearchNote;
    private HBox wrapSearch;
    private DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private Game game;

    public Note(AnchorPane contentNote, TextField searchNote, AnchorPane submitSearchNote ,Dictionary dictionary, Game game) {
        this.game = game;
        this.wrapSearch = new HBox(10);
        this.wrapSearch.setLayoutX(50);
        this.wrapSearch.setLayoutY(160);
        this.wrapSearch.setPrefHeight(30);
        this.wrapSearch.setPrefWidth(500);
        this.wrapSearch.setVisible(false);
        this.searchNote = searchNote;
        this.submitSearchNote = submitSearchNote;
        this.dictionary = dictionary;
        this.submitAdd = new Button("ADD");
        this.submitAdd.setStyle("-fx-cursor:  CLOSED_HAND;");
        this.contentNote = contentNote;
        this.wrapAdd = new AnchorPane();
        this.contentNote.getChildren().add(this.wrapAdd);
        this.contentNote.getChildren().add(wrapSearch);
        this.wrapAdd.setLayoutX(600);
        this.wrapAdd.setLayoutY(200);
        this.wrapAdd.setPrefHeight(150);
        this.wrapAdd.setPrefWidth(220);
        this.wrapAdd.setStyle("-fx-background-color: #555; -fx-background-radius:5px;");
        this.inputTarget = new TextField();
        this.inputExplain = new TextField();
        this.inputTarget.setPromptText("Target");
        this.inputExplain.setPromptText("Explain");
        this.wrapAdd.getChildren().addAll(this.inputTarget, this.inputExplain, this.submitAdd);
        this.inputTarget.setLayoutX(10);
        this.inputTarget.setLayoutY(10);
        this.inputTarget.setPrefWidth(200);
        this.inputTarget.setPrefHeight(30);
        this.inputExplain.setLayoutX(10);
        this.inputExplain.setLayoutY(60);
        this.inputExplain.setPrefWidth(200);
        this.inputExplain.setPrefHeight(30);
        this.submitAdd.setPrefWidth(200);
        this.submitAdd.setPrefHeight(30);
        this.submitAdd.setLayoutX(10);
        this.submitAdd.setLayoutY(110);
        this.table = new TableView<Word>();
        this.target = new TableColumn<Word, String>("target");
        this.explain = new TableColumn<Word, String>("explain");
        this.target.setCellValueFactory(new PropertyValueFactory<>("word_target"));
        this.explain.setCellValueFactory(new PropertyValueFactory<>("word_explain"));
        this.table.setItems(loadData());
        this.table.getColumns().addAll(target, explain);
        this.contentNote.getChildren().add(this.table);
        this.target.setPrefWidth(235);
        this.explain.setPrefWidth(235);
        this.table.setLayoutX(50);
        this.table.setLayoutY(200);
        this.table.setPrefWidth(490);
        this.table.setMaxHeight(450);

        this.add();
        this.search();
    }

    public ObservableList<Word> loadData() {
        ArrayList<Word>list = new ArrayList<Word>();
        for(int i = 0; i < dictionary.list_word.size(); i++) {
            Word newNote = new Word(dictionary.get(i).getWord_target(), dictionary.get(i).getWord_explain());
            list.add(newNote);
        }
       ObservableList<Word> result = FXCollections.observableArrayList(list);
        return result;
    }

    public void add() {
        this.submitAdd.setOnMouseClicked((MouseEvent event) ->{
            String target = inputTarget.getText();
               String explain = inputExplain.getText();
               String announce = "Từ này đã có trong danh sách yêu thích";

               Word new_word = new Word(target, explain);
               if (target.equals("") || explain.equals("")) {
                   announce = "Chưa nhập từ vào";
                   new_word = null;
               }
               if (!dictionaryCommandline.checkInDictionary(dictionary,new_word)) {
                   dictionaryCommandline.AddNote(dictionary, new_word);
                   try {
                       dictionaryManagement.dictionaryExportToFile(dictionary);
                       game.loadGame();
                       this.table.setItems(loadData());
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
               else {
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("thông báo");
                   alert.setHeaderText(announce);
                   System.out.println("Từ này đã có trong danh sách yêu thích");
                   alert.show();
               }
        });
    }

    private void Remove(Button button,int index) {
        button.setOnMouseClicked((MouseEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Bạn có muốn xóa không");
            alert.setContentText("lựa chọn");
            ButtonType buttonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("Không", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {

                this.wrapSearch.setVisible(false);
                dictionary.remove(index);
                try {
                    dictionaryManagement.dictionaryExportToFile(dictionary);
                    game.loadGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.table.setItems(loadData());
            }
        });
    }

    public void fix(Button button, int index) {
        button.setOnMouseClicked((MouseEvent e) -> {
            Stage stage = new Stage();
            stage.setTitle("Sửa từ");
            AnchorPane vBox= new AnchorPane();
            vBox.setPrefHeight(140);
            vBox.setPrefWidth(200);
            TextField fixTarget = new TextField();
            TextField fixExplain = new TextField();
            Button submitFix = new Button("Sửa");
            submitFix.setStyle("-fx-cursor: CLOSED_HAND;");
            fixExplain.setPromptText("Tiếng việt");
            fixTarget.setPromptText("Tiếng Anh");
            fixTarget.setLayoutX(10);
            fixTarget.setLayoutY(10);
            fixTarget.setPrefWidth(180);
            fixTarget.setPrefHeight(30);

            fixExplain.setLayoutX(10);
            fixExplain.setLayoutY(50);
            fixExplain.setPrefWidth(180);
            fixExplain.setPrefHeight(30);

            submitFix.setLayoutX(10);
            submitFix.setLayoutY(90);
            submitFix.setPrefHeight(30);
            submitFix.setPrefWidth(180);
            submitFix.setOnMouseClicked((MouseEvent e1) -> {
                if(!(fixTarget.getText() =="" && fixExplain.getText() == "")) {
                    if(fixTarget.getText() != "") {
                        dictionary.get(index).setWord_target(fixTarget.getText());
                    }
                    if(fixExplain.getText() != "") {
                        dictionary.get(index).setWord_explain(fixExplain.getText());
                    }
                    this.table.setItems(loadData());
                    try {
                        dictionaryManagement.dictionaryExportToFile(dictionary);
                        game.loadGame();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    stage.close();
                }

            });
            vBox.getChildren().addAll(fixTarget, fixExplain, submitFix);
            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.show();
        });
    }

    private void search() {
        this.searchNote.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                this.wrapSearch.getChildren().clear();
                String dataTemp = "";
                int index = -1;
                for(int i = 0; i < dictionary.size(); i++) {
                    if(dictionary.get(i).getWord_target().equals(this.searchNote.getText())) {
                        index = i;
                        dataTemp = dictionary.get(i).getWord_target() +": " + dictionary.get(i).getWord_explain();
                        break;
                    }
                }
                Text text = new Text();
                if(dataTemp == "") {
                    text.setText("không thấy dữ liệu");
                    text.setStyle("-fx-font-size: 20px; -fx-fill:#ccc;");
                    this.wrapSearch.getChildren().add(text);
                } else {
                    text.setText(dataTemp);
                    text.setStyle("-fx-font-size: 20px; -fx-fill:#eca1ae;");
                    Button fix = new Button("Sửa");
                    Button remove = new Button("Xóa");
                    fix.setStyle("-fx-background-color: #15a2fa; -fx-cursor: CLOSED_HAND;");
                    remove.setStyle("-fx-background-color: #15a2fa; -fx-cursor: CLOSED_HAND");
                    fix.setMinWidth(100);
                    remove.setMinWidth(100);
                    Remove(remove,index);
                    fix(fix,index);
                    this.wrapSearch.getChildren().add(text);
                    wrapSearch.getChildren().addAll(fix, remove);
                }

                this.wrapSearch.setVisible(true);
            }
        });
    }

}
