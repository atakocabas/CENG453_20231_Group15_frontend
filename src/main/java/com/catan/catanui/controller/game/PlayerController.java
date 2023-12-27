package com.catan.catanui.controller.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    public static PlayerController instance;

    int fontSize = 20;

    String textColor = "white";
    @FXML
    private VBox playerVBox;

    @FXML
    private Circle player1Circle;

    @FXML
    private Text player1Text;

    @FXML
    private Circle player2Circle;

    @FXML
    private Text player2Text;

    @FXML
    private Circle player3Circle;

    @FXML
    private Text player3Text;

    @FXML
    private Circle player4Circle;

    @FXML
    private Text player4Text;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // You can initialize player-related components here if needed
        instance = this;
        updatePlayerCircle(0);
    }

    public static PlayerController getInstance() {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }

    public void updatePlayerCircle(int currentPlayer) {
        Circle[] playerCircles = { player1Circle, player2Circle, player3Circle, player4Circle };
        for (int i = 0; i < playerCircles.length; i++) {
            if (i == currentPlayer) {
                playerCircles[i].setStroke(Color.GREEN);
                playerCircles[i].setStrokeWidth(5.0);
            } else {
                playerCircles[i].setStroke(Color.BLACK);
                playerCircles[i].setStrokeWidth(1.0);
            }
        }
    }
}
