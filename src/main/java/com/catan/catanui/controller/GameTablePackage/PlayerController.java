package com.catan.catanui.controller.GameTablePackage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {

    int fontSize = 20;

    String textColor =  "white";
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
    }
}

