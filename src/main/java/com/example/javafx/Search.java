package com.example.javafx;

import com.google.cloud.examples.translate.snippets.*;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;

public class Search {
    private TextField input;
    private AnchorPane buttonSubmit;
    private AnchorPane contentSearch;
    private ImageView imageNotFound;
    private ScrollPane scrollSearch;
    private VBox bodySearch;
    private Text vietnamese;
    private ImageView speaker;
    private Text speakerText;
    private Translate.Oxford oxford;
    private OxfordData oxfordData;
    private Translate.GoogleTranslate googleTranslate;
    private AnchorPane wrap;
    private AnchorPane wrapImage;

    private class MainSearch {
        public Text define;
        public Text examples;
        public MainSearch() {
            this.define = new Text();
            this.examples = new Text();
            this.define.setLayoutX(0);
            this.define.setWrappingWidth(850);
            this.examples.setLayoutX(0);
            this.examples.setWrappingWidth(850);
            this.define.setStyle("-fx-fill: #fff; -fx-font-size: 20px");
            this.examples.setStyle("-fx-fill: #fff; -fx-font-size: 16px");
        }
    }

    public Search(TextField input, AnchorPane buttonSubmit, AnchorPane contentSearch,ImageView imageNotFound) {
        this.input = input;
        this.buttonSubmit = buttonSubmit;
        this.contentSearch = contentSearch;
        this.imageNotFound = imageNotFound;
    }

    public void init() {
        this.bodySearch = new VBox(2);
        this.bodySearch.setLayoutX(50);
        this.bodySearch.setLayoutY(150);
        this.bodySearch.setMinWidth(850);
        this.bodySearch.setPrefHeight(500);
        this.bodySearch.setStyle("-fx-background-color: #333");

        this.scrollSearch = new ScrollPane();
        this.scrollSearch.setPannable(true);

        this.scrollSearch.setLayoutX(50);
        this.scrollSearch.setLayoutY(150);
        this.scrollSearch.setMinWidth(850);
        this.scrollSearch.setMinHeight(500);
        this.scrollSearch.setHmax(501);
        this.scrollSearch.setStyle("-fx-background-color:transparent;");
        this.scrollSearch.setHbarPolicy (ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollSearch.setVbarPolicy (ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollSearch.setContent(this.bodySearch);
        this.contentSearch.getChildren().add(this.scrollSearch);

        this.wrapImage = new AnchorPane();
        this.wrapImage.setLayoutY(0);
        this.wrapImage.setLayoutX(0);
        this.wrapImage.setPrefWidth(850);
        this.wrapImage.setPrefHeight(500);
        this.imageNotFound = new ImageView(new Image("https://cdn.dribbble.com/users/1489103/screenshots/6326497/no-data-found.png"));
        this.imageNotFound.setFitHeight(375);
        this.imageNotFound.setFitWidth(500);
        this.imageNotFound.setLayoutX(175);
        this.imageNotFound.setLayoutY(65.5);
        this.wrapImage.getChildren().add(this.imageNotFound);
        this.bodySearch.getChildren().add(this.wrapImage);
        this.vietnamese = new Text();
        this.vietnamese.setStyle("-fx-font-size: 40px; -fx-fill:#eca1ae;");
        this.vietnamese.setLayoutX(0);
        this.vietnamese.setLayoutY(30);

        this.wrap = new AnchorPane();
        this.wrap.setLayoutX(0);
        this.wrap.setLayoutY(60);
        this.wrap.setMinHeight(60);
        this.wrap.setPrefWidth(850);

        this.speaker = new ImageView(new Image("https://cdn.pixabay.com/photo/2013/07/13/10/24/sound-157173_1280.png"));
        this.speaker.setLayoutX(0);
        this.speaker.setLayoutY(5);
        this.speaker.setFitHeight(40);
        this.speaker.setFitWidth(40);
        this.speaker.setStyle("-fx-cursor:  CLOSED_HAND");
        this.speakerText = new Text();
        this.speakerText.setLayoutY(33);
        this.speakerText.setLayoutX(60);
        this.speakerText.setStyle("-fx-font-size: 20px; -fx-fill: #ddd;");

        this.wrap.getChildren().addAll(this.speaker,this.speakerText);

        this.oxford = new Translate.Oxford();
        this.googleTranslate = new Translate.GoogleTranslate();

    }

    private void sol() throws IOException {
        this.speaker.setOnMouseClicked((MouseEvent event) -> {
            Media media = new Media(this.oxfordData.audio.get(0));
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        });
        String word = this.input.getText();
            this.bodySearch.getChildren().clear();
            oxfordData = this.oxford.getOxfordData(word);
            String translateVietnamese = this.googleTranslate.getGoogleData(word);
            if(this.oxfordData == null || translateVietnamese == null) {
                this.bodySearch.getChildren().add(this.wrapImage);
            } else {
                this.bodySearch.getChildren().addAll(this.vietnamese, this.wrap);
                this.vietnamese.setText(translateVietnamese);
                this.speakerText.setText(oxfordData.ipa.toString());
                for(int i = 0; i < oxfordData.defineExamples.size(); i++) {
                    MainSearch tempMainSearch = new MainSearch();
                    tempMainSearch.define.setText("Define: " + oxfordData.defineExamples.get(i).define);
                    String temp = "";
                    for(int j = 0; j < oxfordData.defineExamples.get(i).example.size(); j++) {
                        temp += '\t' + "Example: " + oxfordData.defineExamples.get(i).example.get(j) + '\n';
                    }
                    tempMainSearch.examples.setText(temp);
                    this.bodySearch.getChildren().addAll(tempMainSearch.define, tempMainSearch.examples);
                }
            }

    }

    public void searchSol() {
        this.input.setOnKeyPressed ((KeyEvent event) ->
        {
            if(event.getCode() == KeyCode.ENTER) {
                try {
                    this.sol();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.buttonSubmit.setOnMouseClicked((MouseEvent event) ->{
            try {
                this.sol();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
