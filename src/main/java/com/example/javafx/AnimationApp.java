package com.example.javafx;

import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.AbstractList;
import java.util.ArrayList;

public class AnimationApp {
    private Background backgroundActive = new Background(new BackgroundFill(new Color(0.4,0.4,0.4,1), new CornerRadii(20), Insets.EMPTY));
    private Background backgroundHover = new Background(new BackgroundFill(new Color(0.5,0.5,0.5,1), new CornerRadii(20), Insets.EMPTY));
    private Background backgroundUnActive = new Background(new BackgroundFill(new Color(0,0,0,0), new CornerRadii(20), Insets.EMPTY));

    public void buttonClick(ArrayList<HBox> buttons, ArrayList<AnchorPane> contents) {
        for(int i = 0; i < buttons.size(); i++) {
            int finalI = i;
            int finalI1 = i;
            buttons.get(i).setOnMouseClicked((MouseEvent event) ->{
                for(int j = 0; j < buttons.size(); j++) {
                    buttons.get(j).setBackground(this.backgroundUnActive);
                    contents.get(j).setVisible(false);
                }
                buttons.get(finalI).setBackground(this.backgroundActive);
                contents.get(finalI1).setVisible(true);
            });
        }
    }

    public void hoverAnimation(ArrayList<HBox> buttons) {
        for(int i =0; i < buttons.size(); i++) {
            int finalI = i;
            buttons.get(i).setOnMouseEntered((MouseEvent event) -> {
                Background temp1 = buttons.get(finalI).getBackground();
                if(!temp1.equals(this.backgroundActive)) {
                    buttons.get(finalI).setBackground(this.backgroundHover);
                }
            });
            buttons.get(i).setOnMouseExited((MouseEvent event) -> {
                Background temp2 = buttons.get(finalI).getBackground();
                if(!temp2.equals(this.backgroundActive)) {
                    buttons.get(finalI).setBackground(this.backgroundUnActive);
                }
            });

        }
    }


}
