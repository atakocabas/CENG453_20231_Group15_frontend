package com.catan.catanui.controller.game;

import com.catan.catanui.config.TokenStore;
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
    private Player longestPathOwner;

    @FXML
    private VBox layoutVBox;


    /**
     * Returns the singleton instance of the PlayerController.
     * If the instance is null, it creates a new instance before returning it.
     *
     * @return the singleton instance of the PlayerController
     */
    public static PlayerController getInstance() {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }

    /**
     * Initializes the player controller.
     * This method is called when the controller is loaded and ready to be used.
     * It sets up the initial state of the player-related components and updates the
     * player circle.
     *
     * @param location  The URL location of the FXML file.
     * @param resources The ResourceBundle containing the localized resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // You can initialize player-related components here if needed
        instance = this;
        currentPlayerIndex = 0;
        initializePlayers();
        initializeLongestPathOwner();
        updatePlayerCircle(currentPlayerIndex);

    }

    /**
     * Initializes the longest path owner component.
     * This method creates a HBox container and adds a Text component to display the
     * longest path owner information.
     * The HBox container is then added to the layout VBox.
     */
    private void initializeLongestPathOwner() {
        HBox longestPathOwnerVBox = new HBox();
        Text longestPathOwnerText = new Text("Longest Path Owner: NONE ");
        longestPathOwnerVBox.getChildren().add(longestPathOwnerText);
        layoutVBox.getChildren().add(longestPathOwnerVBox);
    }

    /**
     * Initializes the player components in the game.
     * Creates player HBoxes with their resource information, circle and name, and
     * additional information.
     * Adds the player HBoxes to the layout VBox.
     * Initializes player resources.
     * Logs a message indicating that the PlayerController has been initialized.
     */
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

            VBox additionalInfoVBox = new VBox();
            additionalInfoVBox.setSpacing(5);

            additionalInfoVBox.getChildren().addAll(player.getSettlementText(), player.getCityText(),
                    player.getLongestPathText(), player.getTotalPointsText());

            playerCircleAndNameVBox.getChildren().addAll(playerCircle, playerNameText);
            playerHBox.getChildren().addAll(playerInfoVBox, playerCircleAndNameVBox, additionalInfoVBox);
            playerHBoxes.add(playerHBox);

        }
        layoutVBox.getChildren().addAll(playerHBoxes);
        initalizePlayerResources();
        logger.info("PlayerController initialized");
    }

    /**
     * Initializes the resources for each player.
     * Each player is given a certain amount of lumber, brick, grain, and wool resources.
     */
    private void initalizePlayerResources() {
        for (Player player : players) {
            changePlayerResource(player, ResourceType.LUMBER, 3);
            changePlayerResource(player, ResourceType.BRICK, 3);
            changePlayerResource(player, ResourceType.GRAIN, 1);
            changePlayerResource(player, ResourceType.WOOL, 1);
        }
    }

    /**
     * Creates the players for the game.
     * If the player name is not available, it defaults to "Player 1".
     * Adds the players to the list of players.
     */
    private void createPlayers() {
        String playerName = TokenStore.getInstance().getUsername();
        players.clear();
        if (playerName == null) {
            playerName = "Player 1";
        }
        players.add(new Player(1, playerName, Color.RED));
        players.add(new Player(2, "Player 2", Color.BLUE));
        players.add(new Player(3, "Player 3", Color.PURPLE));
        players.add(new Player(4, "Player 4", Color.ORANGE));
    }

    /**
     * Updates the player circle to indicate the current player.
     * The previous player's circle is set to black with a stroke width of 1,
     * while the current player's circle is set to green with a stroke width of 5.
     * 
     * @param currentPlayer The index of the current player.
     */
    public void updatePlayerCircle(int currentPlayer) {
        int previousPlayer = currentPlayer - 1;
        if (previousPlayer < 0)
            previousPlayer = 3;
        Circle currentPlayerCircle = getPlayerCircle(players.get(currentPlayer));
        Circle previousPlayerCircle = getPlayerCircle(players.get(previousPlayer));
        previousPlayerCircle.setStroke(Color.BLACK);
        previousPlayerCircle.setStrokeWidth(1);
        currentPlayerCircle.setStroke(Color.GREEN);
        currentPlayerCircle.setStrokeWidth(5);
        this.currentPlayerIndex = currentPlayer;
    }

    /**
     * Changes the resource amount of a player by the specified amount.
     * 
     * @param player       The player whose resource amount will be changed.
     * @param resourceType The type of resource to be changed.
     * @param amount       The amount by which the resource will be changed.
     */
    public void changePlayerResource(Player player, ResourceType resourceType, int amount) {
        player.changeResource(resourceType, amount);
        updatePlayerInfo(player);
        logger.info("Player {} {} increased by {}", player.getId(), resourceType, amount);
    }

    /**
     * Updates the player information in the UI.
     * 
     * @param player The player object containing the updated information.
     */
    public void updatePlayerInfo(Player player) {
        VBox playerInfoVBox = getPlayerInfoVBox(player);
        playerInfoVBox.setStyle("-fx-padding: 0 0 20 0;");
        for (int i = 0; i < playerInfoVBox.getChildren().size(); i++) {
            HBox resourceHBox = (HBox) playerInfoVBox.getChildren().get(i);
            Text keyText = (Text) resourceHBox.getChildren().get(0);
            Text valueText = (Text) resourceHBox.getChildren().get(1);
            ResourceType resourceType = ResourceType
                    .valueOf(keyText.getText().substring(0, keyText.getText().length() - 2));
            Integer value = player.getResources().get(resourceType);
            valueText.setText(value.toString());
        }

    }

    /**
     * Updates the owner of the longest path.
     * If there is a longest path owner, it updates the corresponding UI element with the owner's name.
     */
    public void updateLongestPathOwner() {
        if (longestPathOwner != null) {
            HBox longestPathOwnerHBox = getLongestPathOwnerHBox();
            Text longestPathOwnerText = (Text) longestPathOwnerHBox.getChildren().get(0);
            longestPathOwnerText.setText("Longest Path Owner: " + longestPathOwner.getPlayerName());
        }
    }

    /**
     * Retrieves the HBox representing the owner of the longest path.
     *
     * @return The HBox representing the owner of the longest path.
     */
    private HBox getLongestPathOwnerHBox() {
        return (HBox) layoutVBox.getChildren().get(layoutVBox.getChildren().size() - 1);
    }

    /**
     * Retrieves a list of HBox objects representing the players in the game.
     *
     * @return A list of HBox objects representing the players.
     */
    private List<HBox> getPlayersHBoxes() {
        List<HBox> playerHBoxes = new ArrayList<>();
        for (int i = 0; i < layoutVBox.getChildren().size(); i++) {
            playerHBoxes.add((HBox) layoutVBox.getChildren().get(i));
        }
        return playerHBoxes;
    }

    /**
     * Returns the HBox associated with the specified player.
     *
     * @param player the player object
     * @return the HBox associated with the player
     */
    private HBox getPlayerHBox(Player player) {
        return getPlayersHBoxes().get(player.getId() - 1);
    }

    /**
     * Returns a VBox containing the player information.
     *
     * @param player the player whose information is displayed in the VBox
     * @return a VBox containing the player information
     */
    private VBox getPlayerInfoVBox(Player player) {
        return (VBox) getPlayerHBox(player).getChildren().get(0);
    }

    /**
     * Returns a VBox containing the player's circle and name.
     *
     * @param player the player object
     * @return a VBox containing the player's circle and name
     */
    private VBox getPlayerCircleAndNameVBox(Player player) {
        return (VBox) getPlayerHBox(player).getChildren().get(1);
    }

    /**
     * Returns the Circle associated with the given player.
     *
     * @param player the player whose Circle is to be returned
     * @return the Circle associated with the given player
     */
    private Circle getPlayerCircle(Player player) {
        return (Circle) getPlayerCircleAndNameVBox(player).getChildren().get(0);
    }

    /**
     * Retrieves the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Retrieves the player at the specified index.
     *
     * @param index the index of the player to retrieve
     * @return the player at the specified index
     */
    public Player getPlayer(int index) {
        return players.get(index);
    }

    /**
     * Retrieves the list of players.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Builds a road for the current player.
     * 
     * @return true if the road is successfully built, false otherwise.
     */
    public boolean buildRoad() {
        Player currentPlayer = getCurrentPlayer();
        if (getCurrentPlayer().isEnoughResourcesForRoad()) {
            changePlayerResource(currentPlayer, ResourceType.BRICK, -1);
            changePlayerResource(currentPlayer, ResourceType.LUMBER, -1);
            updatePlayerInfo(getCurrentPlayer());
            return true;
        }
        return false;
    }

    /**
     * Builds a settlement for the current player.
     * 
     * @return true if the settlement is successfully built, false otherwise.
     */
    public boolean buildSettlement() {
        Player currentPlayer = getCurrentPlayer();
        if (getCurrentPlayer().isEnoughResourcesForSettlement()) {
            changePlayerResource(currentPlayer, ResourceType.BRICK, -1);
            changePlayerResource(currentPlayer, ResourceType.LUMBER, -1);
            changePlayerResource(currentPlayer, ResourceType.GRAIN, -1);
            changePlayerResource(currentPlayer, ResourceType.WOOL, -1);
            updatePlayerInfo(getCurrentPlayer());
            return true;
        }
        return false;
    }

    /**
     * Upgrades a settlement to a city for the current player.
     * 
     * @return true if the upgrade is successful, false otherwise
     */
    public boolean upgradeSettlement() {
        Player currentPlayer = getCurrentPlayer();
        if (getCurrentPlayer().isEnoughResourcesForCity()) {
            changePlayerResource(currentPlayer, ResourceType.GRAIN, -2);
            changePlayerResource(currentPlayer, ResourceType.ORE, -3);
            updatePlayerInfo(getCurrentPlayer());
            return true;
        }
        return false;
    }

    /**
     * Retrieves the player who currently holds the longest path.
     *
     * @return The player who holds the longest path.
     */
    public Player getLongestPathOwner() {
        return longestPathOwner;
    }

    /**
     * Sets the owner of the longest path.
     * 
     * @param player the player to set as the owner of the longest path
     * @return the player who is now the owner of the longest path
     */
    public Player setLongestPathOwner(Player player) {
        this.longestPathOwner = player;
        updatePlayerInfo(player);
        return longestPathOwner;
    }
}
