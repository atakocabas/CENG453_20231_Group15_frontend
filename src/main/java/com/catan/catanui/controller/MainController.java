package com.catan.catanui.controller;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.utils.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The MainController class is responsible for controlling the main menu UI elements of the application.
 */
public class MainController {
    @FXML
    public Button playButton;
    @FXML
    public Button leaderboardButton;
    @FXML
    public AnchorPane AnchorPane;

    /**
     * Handles the action when the play button is clicked.
     *
     * @param actionEvent The event triggered by clicking the play button.
     */
    public void handlePlayButtonAction(ActionEvent actionEvent) {
        Utility.startGame((Stage) leaderboardButton.getScene().getWindow(), getClass().getResource(Constant.GAME_TABLE));
    }

    /**
     * Handles the action when the leaderboard button is clicked.
     */
    public void handleLeaderboardButtonAction() {
        Utility.navigate((Stage) leaderboardButton.getScene().getWindow(), getClass().getResource(Constant.LEADERBOARD));
    }
}
