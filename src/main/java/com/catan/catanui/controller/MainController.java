package com.catan.catanui.controller;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.utils.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {
    @FXML
    public Button playButton;
    @FXML
    public Button leaderboardButton;
    @FXML
    public AnchorPane AnchorPane;


    public void handlePlayButtonAction(ActionEvent actionEvent) {
        Utility.navigate((Stage) leaderboardButton.getScene().getWindow(), getClass().getResource(Constant.GAME_TABLE));
    }

    public void handleLeaderboardButtonAction() {
        Utility.navigate((Stage) leaderboardButton.getScene().getWindow(), getClass().getResource(Constant.LEADERBOARD));
    }
}
