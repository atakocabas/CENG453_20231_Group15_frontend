package com.catan.catanui.controller.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EndTurnController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndTurnController.class);

    @FXML
    public Button endTurnButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        endTurnButton.setOnAction(this::endTurn);
        LOGGER.info("EndTurnController initialized");
    }

    private void endTurn(ActionEvent event){
        LOGGER.info("End Turn Button Clicked!");
        GameTableController.getInstance().endTurn();
    }
}
