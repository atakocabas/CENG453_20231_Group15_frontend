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


    /**
     * Returns the singleton instance of the EndTurnController class.
     * If the instance is null, a new instance is created.
     * 
     * @return the instance of the EndTurnController class
     */
    public static EndTurnController getInstance() {
        if (instance == null) {
            instance = new EndTurnController();
        }
        return instance;
    }

    /**
     * Initializes the EndTurnController.
     *
     * @param url            The URL of the FXML file.
     * @param resourceBundle The ResourceBundle associated with the FXML file.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createEndTurnButton();
        endTurnButton.setOnAction(this::endTurn);
        endTurnButton.setDisable(true);
        logger.info("EndTurnController initialized");
    }

    /**
     * Creates the "END TURN" button and adds it to the endTurnVBox.
     */
    private void createEndTurnButton() {
        endTurnButton = new Button("END TURN");
        endTurnButton.setMinSize(100, 50);
        endTurnButton.setMaxSize(200, 100);
        endTurnButton.setPrefSize(100, 50);
        endTurnVBox.getChildren().add(endTurnButton);
    }

    /**
     * Ends the current player's turn.
     *
     * @param event the action event triggered by clicking the "End Turn" button
     */
    private void endTurn(ActionEvent event) {
        logger.info("End Turn Button Clicked!");
        disableEndTurnButton();
        GameTableController.getInstance().endTurn();
    }

    /**
     * Disables the end turn button.
     */
    public void disableEndTurnButton() {
        endTurnButton.setDisable(true);
    }

    /**
     * Enables the "End Turn" button.
     */
    public void enableEndTurnButton() {
        endTurnButton.setDisable(false);
    }

    /**
     * Returns the end turn button.
     *
     * @return the end turn button
     */
    public Button getEndTurnButton() {
        return endTurnButton;
    }
}
