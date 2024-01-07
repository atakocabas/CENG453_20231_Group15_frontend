// FunText.java

package com.catan.catanui.controller.game;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.utils.Utility;

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

    /**
     * Displays the end game message on the specified parent pane.
     * 
     * @param parentPane The parent pane where the end game message will be displayed.
     * @param message The message to be displayed.
     */
    public static void displayEndGameMessage(Pane parentPane, String message) {
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

    /**
     * Creates a back to main menu button and adds it to the parent pane.
     * 
     * @param parentPane the parent pane to which the button will be added
     * @param scene the scene containing the parent pane
     */
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

    /**
     * Creates a grey text with rotating colors and adds it to the specified parent pane.
     * The text is centered within the parent pane.
     *
     * @param parentPane  the parent pane to which the grey text will be added
     * @param message     the text message to be displayed
     * @param fontSize    the font size of the text
     * @param fontWeight  the font weight of the text
     */
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

    /**
     * Creates a "Play Again" button and adds it to the specified parent pane.
     * When the button is clicked, it triggers the logic to restart the game.
     *
     * @param parentPane the parent pane to which the button will be added
     * @param scene the scene containing the parent pane
     */
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

    /**
     * Disables the root node of the given scene.
     *
     * @param scene the scene whose root node needs to be disabled
     */
    private static void disableSceneRoot(Scene scene) {
        Node root = scene.getRoot();
        if (root instanceof Parent) {
            ((Parent) root).setDisable(true);
        }
    }

    /**
     * Enables the root of the scene by setting its disable property to false.
     * Additionally, it clears the children of the parent pane.
     *
     * @param parentPane the parent pane containing the scene
     */
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
