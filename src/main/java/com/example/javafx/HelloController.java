package com.example.javafx;

import com.example.solution.*;
import com.example.solution.Dictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable{

    public HBox bgSearch;
    public HBox bgNote;
    public HBox bgGame;
    public HBox bgSetting;

    public AnchorPane content;
    public AnchorPane control;


    public TextField inputSearch;
    public AnchorPane submitSearch;

    public AnimationApp animationApp;
    public ImageView imageNotFound;
    public ArrayList<HBox> buttons = new ArrayList<HBox>();
    public ArrayList<AnchorPane> contents = new ArrayList<AnchorPane>();
    public Search search;
    public AnchorPane contentSearch;
    public AnchorPane contentNote;
    public AnchorPane contentSetting;
    public AnchorPane contentGame;
    public AnchorPane searchOnlWrap;

    public static Dictionary dictionaryEnglish = new Dictionary(); // dich tu tieng Anh sang tieng Viet
    public static Dictionary dictionary = new Dictionary();
    public static DictionaryManagement dictionaryManagement = new DictionaryManagement();
    public static DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
    public static int IndexViewAllItem;

    public static List<String> list = new ArrayList<>();
    public TextField searchNote;
    public AnchorPane searchNoteWrap;
    public AnchorPane submitSearchNote;


    private void init() {
        this.animationApp = new AnimationApp();
        this.buttons.add(this.bgSearch);
        this.buttons.add(this.bgNote);
        this.buttons.add(this.bgGame);
        this.buttons.add(this.bgSetting);
        this.contents.add(this.contentSearch);
        this.contents.add(this.contentNote);
        this.contents.add(this.contentGame);
        this.contents.add(this.contentSetting);
        this.animationApp.buttonClick(this.buttons, this.contents);
        this.animationApp.hoverAnimation(this.buttons);
        this.search = new Search(this.inputSearch, this.submitSearch, this.contentSearch,this.imageNotFound);
        this.search.searchSol();

        //run search
        Search search= new Search(this.inputSearch, this.submitSearch, this.contentSearch, this.imageNotFound);
        search.init();
        search.searchSol();
        Suggest suggest = new Suggest(this.searchOnlWrap, this.inputSearch, dictionaryEnglish);
        suggest.solSuggest();
        Suggest suggestNote = new Suggest(this.searchNoteWrap, this.searchNote, dictionary);
        suggestNote.solSuggest();
        //run game
        Game game = new Game(this.contentGame);
        game.initGame();
        Note note = new Note(this.contentNote, this.searchNote, this.submitSearchNote ,dictionary,game);
    }

    @FXML
    private AnchorPane contentDelete;

    @FXML
    private AnchorPane contentAdd;

    @FXML
    private TextField inputEnglish;

    @FXML
    private TextField inputViet;

    @FXML
    private TextField inputWord;

    @FXML
    private ListView<String> viewAll;

    @FXML
    private TextField input; // dung cho search offline

    @FXML
    private TextArea output; // dung cho search offline

    @FXML
    private ImageView nodata; // dung khi khong tim duoc tu

    @FXML
    void add(ActionEvent event) throws IOException {
        String target = inputEnglish.getText();
        String explain = inputViet.getText();
        String announce = "T??? n??y ???? c?? trong danh s??ch y??u th??ch";

        Word new_word = new Word(target, explain);
        if (target.equals("") || explain.equals("")) {
            announce = "Ch??a nh???p t??? v??o";
            new_word = null;
        }
        if (!dictionaryCommandline.checkInDictionary(dictionary,new_word)) {
            dictionaryCommandline.Add(dictionary, new_word);
            dictionaryManagement.dictionaryExportToFile(dictionary);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("th??ng b??o");
            alert.setHeaderText(announce);
            System.out.println("T??? n??y ???? c?? trong danh s??ch y??u th??ch");
            alert.show();
        }
    }

    @FXML
    void delete(ActionEvent event) throws IOException {
        String word = inputWord.getText();
        String announce = "T??? n??y kh??ng c?? trong danh s??ch y??u th??ch";
        if (word.equals("")) {
            announce = "Ch??a nh???p t??? c???n x??a";
        }
        if (dictionaryCommandline.wordInDictionary(dictionary,word)) {
            dictionaryCommandline.Delete(dictionary, word);
            dictionaryManagement.dictionaryExportToFile(dictionary);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("th??ng b??o");
            alert.setHeaderText(announce);
            System.out.println("T??? n??y ???? c?? trong danh s??ch y??u th??ch");
            alert.show();
        }
    }

    @FXML
    void anchorAdd(ActionEvent event) {
        contentDelete.setVisible(false);
        contentAdd.setVisible(true);
    }

    @FXML
    void anchorDelete(ActionEvent event) {
        contentDelete.setVisible(true);
        contentAdd.setVisible(false);
    }

    @FXML
    public void showAll(ActionEvent event) {
        viewAll.setVisible(true);
        list = dictionaryCommandline.showAllWordsWord(dictionary);
        viewAll.getItems().clear();
        viewAll.getItems().addAll(list);
    }

    @FXML // show ra item trong listView
    void itemShow(MouseEvent event) throws IOException {
        if (!viewAll.getSelectionModel().getSelectedItems().equals("")) {
            int n = viewAll.getSelectionModel().getSelectedIndex();
            IndexViewAllItem = n;
            System.out.println(n);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("B???n c?? mu???n s???a kh??ng");
            alert.setContentText("l???a ch???n");
            ButtonType buttonTypeYes = new ButtonType("YES", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("NO", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeYes) {
                // M???t c???a s??? m???i (Stage)
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(RepairController.class.getResource("repair.fxml"));
                stage.setScene(new Scene(root));
                stage.setTitle("S???a t???");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                        ((Node)event.getSource()).getScene().getWindow() );
                stage.show();
            }
        }
    }

    @FXML
    void Search(ActionEvent event) {
        String inputText =  input.getText(); // nhap tu vao
        Word result = dictionaryCommandline.dictionarySearcher(dictionaryEnglish,inputText);
        if (result != null) {
            nodata.setVisible(false);
            output.setText(result.getWord_explain());
            if (result.getWord_target().isEmpty()) {
                result = null;
                output.setText("");
                nodata.setVisible(true);
            }
        }
        else {
            nodata.setVisible(true);
        }
    }

    @FXML
    void addInOffline(ActionEvent event) throws IOException {
        String target = input.getText();
        String explain = output.getText();
        String announce = "Kh??ng th??m v??o ???????c";

        Word new_word = new Word(target,explain);
        if (target.equals("") && explain.equals("")) {
            new_word = null;
        }
        if (!dictionaryCommandline.checkInDictionary(dictionary,new_word)) {
            dictionaryCommandline.Add(dictionary, new_word);
            dictionaryManagement.dictionaryExportToFile(dictionary);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("th??ng b??o");
            alert.setHeaderText(announce);
            System.out.println("T??? n??y ???? c?? trong danh s??ch y??u th??ch");
            alert.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String urlE = "C:\\Users\\nguye\\OneDrive\\Desktop\\workSpace\\dictionary\\input\\English.txt";
            String urlD = "C:\\Users\\nguye\\OneDrive\\Desktop\\workSpace\\dictionary\\input\\note.txt";
            dictionaryManagement.InsertFromFileNote(dictionary, urlD);
            dictionaryManagement.InsertFromFile(dictionaryEnglish, urlE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.init();
        Search test = new Search(this.inputSearch, this.submitSearch, this.contentSearch, this.imageNotFound);
        test.init();
        test.searchSol();
    }



}