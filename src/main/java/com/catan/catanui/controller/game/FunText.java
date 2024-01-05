package com.catan.catanui.controller.game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FunText {

    public static void displayFunMessage(Pane parentPane, String message) {
        // Create a container for the rainbow text
        Pane rainbowPane = new Pane();
        parentPane.getChildren().add(rainbowPane);

        // Set the position to the center of the parent pane
        rainbowPane.setLayoutX((parentPane.getWidth() - rainbowPane.getWidth()) / 2);
        rainbowPane.setLayoutY((parentPane.getHeight() - rainbowPane.getHeight()) / 2);

        // Create a Text node with a rotating rainbow gradient fill
        createRainbowText(rainbowPane, message, 80, FontWeight.BOLD);
    }

    private static void createRainbowText(Pane parentPane, String message, int fontSize, FontWeight fontWeight) {
        Text text = new Text(message);
        text.setFont(Font.font("Verdana", fontWeight, fontSize));

        // Create a timeline for rotating colors
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Rotate through rainbow colors
        for (int i = 0; i < 360; i += 10) {
            KeyValue keyValue = new KeyValue(text.fillProperty(), Color.hsb(i, 1.0, 1.0));
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 10 * 1.8), keyValue); // Change the duration here
            timeline.getKeyFrames().add(keyFrame);
        }

        // Start the timeline
        timeline.play();

        // Set the position to the center of the parent pane
        text.setTranslateX((parentPane.getWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setTranslateY((parentPane.getHeight() - text.getLayoutBounds().getHeight()) / 2);

        // Add the text to the parent pane
        parentPane.getChildren().add(text);
    }
}
