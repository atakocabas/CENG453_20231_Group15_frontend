// FunText.java

package com.catan.catanui.controller.game;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.utils.Utility;

import ch.qos.logback.classic.pattern.Util;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameEndController {

    public static void displayFunMessage(Pane parentPane, String message) {
        // Create a container for the grey text
        Pane greyPane = new Pane();
        parentPane.getChildren().add(greyPane);

        // Set the position to the center of the parent pane
        greyPane.setLayoutX((parentPane.getWidth() - greyPane.getWidth()) / 2);
        greyPane.setLayoutY((parentPane.getHeight() - greyPane.getHeight()) / 2);

        // Create a Text node with a rotating grey gradient fill
        createGreyText(greyPane, message, 80, FontWeight.BOLD);

        // Create a "Play Again" button
        createPlayAgainButton(greyPane, parentPane.getScene());

        createBackToMainMenuButton(greyPane, parentPane.getScene());
    }

    private static void createBackToMainMenuButton(Pane parentPane, Scene scene) {
        Text backToMainMenuText = new Text("Back to Main Menu");
        backToMainMenuText.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        backToMainMenuText.setFill(Color.BLACK);
        
        Button backToMainMenuButton = new Button();
        backToMainMenuButton.setGraphic(backToMainMenuText);
        backToMainMenuButton.setOnAction(event -> {
            Utility.navigate((Stage) scene.getWindow(), GameEndController.class.getResource(Constant.MAIN));
        });

        // Set the position of the button
        backToMainMenuButton.setLayoutX((parentPane.getWidth() - backToMainMenuButton.getWidth()) / 2);
        backToMainMenuButton.setLayoutY(parentPane.getHeight() / 2 + 100);

        // Add the button to the parent pane
        parentPane.getChildren().add(backToMainMenuButton);
    }

    private static void createGreyText(Pane parentPane, String message, int fontSize, FontWeight fontWeight) {
        Text text = new Text(message);
        text.setFont(Font.font("Verdana", fontWeight, fontSize));

        // Create a timeline for rotating colors
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Rotate through grey-like colors
        for (int i = 0; i < 360; i += 10) {
            // Use grey-like colors with varying brightness
            double brightness = 0.5 + 0.5 * Math.sin(Math.toRadians(i));
            KeyValue keyValue = new KeyValue(text.fillProperty(), Color.gray(brightness));
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

        // Add a keyframe to the timeline to enable the scene root when finished
        timeline.setOnFinished(event -> enableSceneRoot(parentPane));
    }

    private static void createPlayAgainButton(Pane parentPane, Scene scene) {
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        playAgainButton.setOnAction(event -> {
            // Handle the "Play Again" button click
            // You can replace the following line with the logic to restart the game
            System.out.println("Play Again button clicked!");
            Utility.startGame((Stage) scene.getWindow(), GameEndController.class.getResource(Constant.GAME_TABLE));
        });

        // Set the position of the button
        playAgainButton.setLayoutX((parentPane.getWidth() - playAgainButton.getWidth()) / 2);
        playAgainButton.setLayoutY(parentPane.getHeight() / 2 + 50);

        // Add the button to the parent pane
        parentPane.getChildren().add(playAgainButton);
    }

    private static void disableSceneRoot(Scene scene) {
        Node root = scene.getRoot();
        if (root instanceof Parent) {
            ((Parent) root).setDisable(true);
        }
    }

    private static void enableSceneRoot(Pane parentPane) {
        Scene scene = parentPane.getScene();
        Node root = scene.getRoot();
        if (root instanceof Parent) {
            ((Parent) root).setDisable(false);
        }
        // Remove the greyPane when the animation is finished
        parentPane.getChildren().clear();
    }
    
}
