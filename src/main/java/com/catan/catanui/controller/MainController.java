package com.catan.catanui.controller;

import com.catan.catanui.constants.Constant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.catan.catanui.utils.Navigate;

public class MainController {
    @FXML
    public Button playButton;
    @FXML
    public Button leaderboardButton;

    public void handlePlayButtonAction(ActionEvent actionEvent) {

    }

    public void handleLeaderboardButtonAction() {
        Navigate.navigate((Stage) leaderboardButton.getScene().getWindow(), getClass().getResource(Constant.LEADERBOARD));
    }
}
