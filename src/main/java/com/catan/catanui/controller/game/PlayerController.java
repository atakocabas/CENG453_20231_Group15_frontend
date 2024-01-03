package com.catan.catanui.controller.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catan.catanui.entity.Player;
import com.catan.catanui.enums.ResourceType;

public class PlayerController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    public static PlayerController instance;
    private static final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex;

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

    @FXML
    private VBox layoutVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // You can initialize player-related components here if needed
        instance = this;
        currentPlayerIndex = 0;
        initializePlayers();
        updatePlayerCircle(currentPlayerIndex);

    }

    public void initializePlayers() {
        createPlayers();
        List<HBox> playerHBoxes = new ArrayList<>();
        for (Player player : players) {
            HBox playerHBox = new HBox();
            playerHBox.setSpacing(10);
            playerHBox.setAlignment(Pos.CENTER);
            VBox playerInfoVBox = new VBox();
            playerInfoVBox.setSpacing(5);
            for (Map.Entry<ResourceType, Integer> entry : player.getResources().entrySet()) {
                HBox resourceHBox = new HBox();
                Text keyText = new Text(entry.getKey().toString() + ": ");
                Text valueText = new Text(entry.getValue().toString());
                resourceHBox.getChildren().addAll(keyText, valueText);
                playerInfoVBox.getChildren().add(resourceHBox);
            }
            VBox playerCircleAndNameVBox = new VBox();
            playerCircleAndNameVBox.setSpacing(5);
            Circle playerCircle = new Circle();
            playerCircle.setRadius(25);
            playerCircle.setFill(player.getColor());
            playerCircle.setStroke(Color.BLACK);
            Text playerNameText = new Text(player.getPlayerName());
            playerCircleAndNameVBox.getChildren().addAll(playerCircle, playerNameText);
            playerHBox.getChildren().addAll(playerInfoVBox, playerCircleAndNameVBox);
            playerHBoxes.add(playerHBox);
        }
        layoutVBox.getChildren().addAll(playerHBoxes);
        logger.info("PlayerController initialized");
    }

    private void createPlayers(){
        players.add(new Player(1, "Player 1", Color.RED));
        players.add(new Player(2, "Player 2", Color.BLUE));
        players.add(new Player(3, "Player 3", Color.GREEN));
        players.add(new Player(4, "Player 4", Color.YELLOW));
    }

    public static PlayerController getInstance() {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }

    public void updatePlayerCircle(int currentPlayer) {
        int previousPlayer = currentPlayer - 1;
        if (previousPlayer < 0) previousPlayer = 3;
        Circle currentPlayerCircle = getPlayerCircle(currentPlayer);
        Circle previousPlayerCircle = getPlayerCircle(previousPlayer);
        previousPlayerCircle.setStroke(Color.BLACK);
        previousPlayerCircle.setStrokeWidth(1);
        currentPlayerCircle.setStroke(Color.GREEN);
        currentPlayerCircle.setStrokeWidth(3);
        this.currentPlayerIndex = currentPlayer;
    }

    private List<HBox> getPlayersHBoxes() {
        List<HBox> playerHBoxes = new ArrayList<>();
        for (int i = 0; i < layoutVBox.getChildren().size(); i++) {
            playerHBoxes.add((HBox) layoutVBox.getChildren().get(i));
        }
        return playerHBoxes;
    }

    private HBox getPlayerHBox(int playerNumber) {
        return getPlayersHBoxes().get(playerNumber);
    }

    private VBox getPlayerInfoVBox(int playerNumber) {
        return (VBox) getPlayerHBox(playerNumber).getChildren().get(0);
    }

    private VBox getPlayerCircleAndNameVBox(int playerNumber) {
        return (VBox) getPlayerHBox(playerNumber).getChildren().get(1);
    }

    private Circle getPlayerCircle(int playerNumber) {
        return (Circle) getPlayerCircleAndNameVBox(playerNumber).getChildren().get(0);
    }
    
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getPlayer(int index){
        return players.get(index);
    }
}
