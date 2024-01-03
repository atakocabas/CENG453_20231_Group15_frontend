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

    private static PlayerController instance;
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

    private void initializePlayers() {
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
            Text totalPointsText = new Text("Total Points: " + player.getTotalPoints());
            playerCircleAndNameVBox.getChildren().addAll(playerCircle, playerNameText, totalPointsText);
            playerHBox.getChildren().addAll(playerInfoVBox, playerCircleAndNameVBox);
            playerHBoxes.add(playerHBox);
        }
        layoutVBox.getChildren().addAll(playerHBoxes);
        initalizePlayerResources();
        logger.info("PlayerController initialized");
    }

    private void initalizePlayerResources(){
        for (Player player : players) {
            increasePlayerResource(player, ResourceType.LUMBER, 3);
            increasePlayerResource(player, ResourceType.BRICK, 3);
            increasePlayerResource(player, ResourceType.GRAIN, 1);
            increasePlayerResource(player, ResourceType.WOOL, 1);
            // increasePlayerLumber(player, 3);
            // increasePlayerBrick(player, 3);
            // increasePlayerGrain(player, 1);
            // increasePlayerWool(player, 1);
        }
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
        Circle currentPlayerCircle = getPlayerCircle(players.get(currentPlayer));
        Circle previousPlayerCircle = getPlayerCircle(players.get(previousPlayer));
        previousPlayerCircle.setStroke(Color.BLACK);
        previousPlayerCircle.setStrokeWidth(1);
        currentPlayerCircle.setStroke(Color.GREEN);
        currentPlayerCircle.setStrokeWidth(3);
        this.currentPlayerIndex = currentPlayer;
    }
    
    public void increasePlayerResource(Player player, ResourceType resourceType, int amount) {
        player.increaseResource(resourceType, amount);
        updatePlayerInfo(player);
    }

    private void updatePlayerInfo(Player player) {
        VBox playerInfoVBox = getPlayerInfoVBox(player);
        for (int i = 0; i < playerInfoVBox.getChildren().size(); i++) {
            HBox resourceHBox = (HBox) playerInfoVBox.getChildren().get(i);
            Text keyText = (Text) resourceHBox.getChildren().get(0);
            Text valueText = (Text) resourceHBox.getChildren().get(1);
            ResourceType resourceType = ResourceType.valueOf(keyText.getText().substring(0, keyText.getText().length() - 2));
            Integer value = player.getResources().get(resourceType);
            valueText.setText(value.toString());
        }
    }

    private List<HBox> getPlayersHBoxes() {
        List<HBox> playerHBoxes = new ArrayList<>();
        for (int i = 0; i < layoutVBox.getChildren().size(); i++) {
            playerHBoxes.add((HBox) layoutVBox.getChildren().get(i));
        }
        return playerHBoxes;
    }

    private HBox getPlayerHBox(Player player) {
        return getPlayersHBoxes().get(player.getId() - 1);
    }

    private VBox getPlayerInfoVBox(Player player) {
        return (VBox) getPlayerHBox(player).getChildren().get(0);
    }

    private VBox getPlayerCircleAndNameVBox(Player player) {
        return (VBox) getPlayerHBox(player).getChildren().get(1);
    }

    private Circle getPlayerCircle(Player player) {
        return (Circle) getPlayerCircleAndNameVBox(player).getChildren().get(0);
    }
    
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getPlayer(int index){
        return players.get(index);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
