package com.catan.catanui.controller.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EndTurnController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(EndTurnController.class);
    private static EndTurnController instance;

    @FXML
    public VBox endTurnVBox;

    private static Button endTurnButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createEndTurnButton();
        endTurnButton.setOnAction(this::endTurn);
        logger.info("EndTurnController initialized");
    }

    private void createEndTurnButton() {
        endTurnButton = new Button("END TURN");
        endTurnButton.setMinSize(100, 50);
        endTurnButton.setMaxSize(200, 100);
        endTurnButton.setPrefSize(100, 50);
        endTurnVBox.getChildren().add(endTurnButton);
    }

    public static EndTurnController getInstance() {
        if (instance == null) {
            instance = new EndTurnController();
        }
        return instance;
    }

    private void endTurn(ActionEvent event){
        logger.info("End Turn Button Clicked!");
        disableEndTurnButton();
        GameTableController.getInstance().endTurn();
    }

    public void disableEndTurnButton() {
        endTurnButton.setDisable(true);
    }

    public void enableEndTurnButton() {
        endTurnButton.setDisable(false);
    }
    
    public Button getEndTurnButton() {
        return endTurnButton;
    }
}
