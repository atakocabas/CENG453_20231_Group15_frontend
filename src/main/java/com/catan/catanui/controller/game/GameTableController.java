package com.catan.catanui.controller.game;

import com.catan.catanui.controller.ButtonsController;
import com.catan.catanui.controller.game.turn.AiTurnController;
import com.catan.catanui.controller.game.turn.HumanTurnController;
import com.catan.catanui.controller.game.turn.PlayerTurnController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.RoadButton;
import com.catan.catanui.entity.SettlementButton;
import com.catan.catanui.entity.Tile;
import com.catan.catanui.service.LeaderboardService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.awt.geom.Point2D;
import java.net.URL;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameTableController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(GameTableController.class);
    private static GameTableController instance;
    private static final double RADIUS = 75.0;
    private static final double START_X = 362.0;
    private static final double START_Y = 220.0;
    private static final double TILE_COEFFICIENT = 0.9;
    private static final double TOLERANCE = RADIUS * TILE_COEFFICIENT / 20;

    @FXML
    private Pane pane;

    // Game Table Variables
    private static int currentPlayer;
    private List<SettlementButton> settlementButtons = new ArrayList<>();
    private List<RoadButton> roadButtons = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();
    private List<PlayerTurnController> playerTurnControllers = new ArrayList<>();

    @FXML
    private Pane mainPane;

    private static final List<Color> HEXAGON_COLORS = new ArrayList<>();

    // Make sure the number of elements in HEXAGON_COLORS and HEXAGON_NUMBERS are
    // the same
    private static final List<Integer> HEXAGON_NUMBERS = new LinkedList<>();

    private static final LeaderboardService leaderboardService = new LeaderboardService();

    private boolean isGameEnded = false;


    /**
     * Initializes the game table controller.
     * 
     * @param location  the URL location of the FXML file
     * @param resources the ResourceBundle containing the resources for the FXML file
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        mainPane = pane;
        initializeGameTableVariables();
        initializeHexagonColorsAndNumbers();
        initializeTiles();
        initiateSettlementButtons();
        initializeRoadButtons();
        initalizeGame();
        startTurn();
        logger.info("Game Table Controller Initialized!");
    }

    /**
     * Initializes the variables used in the game table.
     * Resets the current player, clears the settlement buttons, road buttons, tiles, and player turn controllers.
     */
    private void initializeGameTableVariables() {
        currentPlayer = 0;
        settlementButtons.clear();
        roadButtons.clear();
        tiles.clear();
        playerTurnControllers.clear();
    }

    /**
     * Initializes the hexagon colors and numbers for the game table.
     * Clears the existing hexagon colors and numbers lists and adds new values.
     */
    private void initializeHexagonColorsAndNumbers() {
        HEXAGON_COLORS.clear();
        HEXAGON_COLORS.addAll(List.of(
                Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, // 4 dark green hexagons
                Color.SADDLEBROWN, Color.SADDLEBROWN, Color.SADDLEBROWN, // 3 dark brown hexagons
                Color.LIGHTGREEN, Color.LIGHTGREEN, Color.LIGHTGREEN, Color.LIGHTGREEN, // 4 light green hexagons
                Color.GREY, Color.GREY, Color.GREY, // 3 light blue hexagons
                Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, // 4 yellow hexagons
                Color.BURLYWOOD // 1 sand-colored hexagon
        ));
        HEXAGON_NUMBERS.clear();
        HEXAGON_NUMBERS.addAll(List.of(
                2, 3, 11, 12, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10));
    }

    public Pane getMainPane() {
        return mainPane;
    }
    public static GameTableController getInstance() {
        if (instance == null) {
            instance = new GameTableController();
        }
        return instance;
    }

    /**
     * Initializes the game by placing initial settlements and roads for each player.
     * Also creates player turn controllers and disables all buttons at the beginning of the game.
     */
    private void initalizeGame() {

        for(Player player: PlayerController.getInstance().getPlayers()){
            Random random = new Random();
            List<SettlementButton> availableSettlementButtons = player.getInitialAvailableSettlementButtons();
            if(!availableSettlementButtons.isEmpty()){
                SettlementButton settlementButton = availableSettlementButtons.get(random.nextInt(availableSettlementButtons.size()));
                List<Tile> adjacentTiles = settlementButton.getAdjacentTiles();
                for(Tile tile: adjacentTiles){
                    if (tile.getResourceType() != null)
                        PlayerController.getInstance().changePlayerResource(player, tile.getResourceType(), settlementButton.getLevel());
                }
                settlementButton.build(player);
            }
        }
        for(Player player: PlayerController.getInstance().getPlayers()){
            Random random = new Random();
            List<RoadButton> availableRoadButtons = player.getAvaliableRoadButtons();
            if(!availableRoadButtons.isEmpty()){
                RoadButton roadButton = availableRoadButtons.get(random.nextInt(availableRoadButtons.size()));
                roadButton.build(player);
            }
        }

        createPlayerTurnControllers();
    }


    /**
     * Creates player turn controllers for each player in the game.
     * The first player is assigned a HumanTurnController, while the rest of the players are assigned AiTurnControllers.
     * The player turn controllers are added to the playerTurnControllers list.
     */
    private void createPlayerTurnControllers() {
        List<Player> players = PlayerController.getInstance().getPlayers();
        PlayerTurnController humanPlayerTurnController = new HumanTurnController(players.get(0));
        playerTurnControllers.add(humanPlayerTurnController);

        for (int i = 1; i < players.size(); i++) {
            PlayerTurnController playerTurnController = new AiTurnController(players.get(i));
            playerTurnControllers.add(playerTurnController);
        }
    }

    /**
     * Initializes the tiles on the game table.
     */
    private void initializeTiles() {
        // Shuffle the hexagon colors and numbers lists to randomize the order
        // Remove Color.BURLYWOOD from the list
        HEXAGON_COLORS.remove(Color.BURLYWOOD);

        // Shuffle the remaining colors
        Collections.shuffle(HEXAGON_COLORS);

        // Add Color.BURLYWOOD back to the middle of the list
        HEXAGON_COLORS.add(HEXAGON_COLORS.size() / 2, Color.BURLYWOOD);
        Collections.shuffle(HEXAGON_NUMBERS);

        // Define the number of hexagons in each row
        int[] hexagonsInRow = { 3, 4, 5, 4, 3 };

        for (int row = 0; row < hexagonsInRow.length; row++) {
            for (int col = 0; col < hexagonsInRow[row]; col++) {
                Color color = HEXAGON_COLORS.remove(0);
                int number = (color.equals(Color.BURLYWOOD)) ? 0 : HEXAGON_NUMBERS.remove(0);

                Polygon hexagon = createHexagon(color, RADIUS * TILE_COEFFICIENT);
                double rowX = row;
                if (row > 2) {
                    rowX = Math.abs(row - 4);
                }
                double hexagonX = START_X + col * RADIUS * Math.sqrt(3) - rowX * RADIUS * Math.sqrt(3) / 2;
                double hexagonY = START_Y + row * RADIUS * 1.5;
                hexagon.setLayoutX(hexagonX);
                hexagon.setLayoutY(hexagonY);
                pane.getChildren().add(hexagon);

                Tile tile = new Tile(number, color, hexagonX, hexagonY);
                tiles.add(tile);

                // add selected number to the middle of the hexagon (skip if Burlywood)
                if (!color.equals(Color.BURLYWOOD)) {
                    Text text = new Text(String.valueOf(number));
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

                    double textWidth = text.getLayoutBounds().getWidth();
                    double textHeight = text.getLayoutBounds().getHeight();

                    text.setX(hexagon.getLayoutX() - textWidth / 2);
                    text.setY(hexagon.getLayoutY() + textHeight / 4);

                    pane.getChildren().add(text);
                }
            }
        }
    }

    /**
     * Initializes the settlement buttons on the game table.
     * Calculates the coordinates for each settlement button based on the tiles.
     * Creates new settlement buttons if they don't already exist.
     * Adds the settlement buttons to the list of buttons and assigns adjacent tiles to each button.
     * Finally, adds the settlement buttons to the pane.
     */
    private void initiateSettlementButtons() {
        double length = RADIUS;
        int index = 0;

        for (Tile tile : tiles) {
            double tileX = tile.getX();
            double tileY = tile.getY();
            double startAngle = Math.toRadians(-30);
            for (int i = 0; i < 6; ++i) {
                double x = tileX + length * Math.cos(startAngle);
                double y = tileY + length * Math.sin(startAngle);
                SettlementButton settlementButton = findSettlementButtonByCoordinates(x, y);
                if (settlementButton == null) {
                    settlementButton = new SettlementButton(RADIUS / 4, x, y, index);
                    settlementButtons.add(settlementButton);
                    index++;
                }
                if(!settlementButton.getAdjacentTiles().contains(tile)){
                    settlementButton.getAdjacentTiles().add(tile);
                }
                tile.getSettlementButtons().add(settlementButton);
                startAngle += Math.toRadians(-60);
            }
        }

        pane.getChildren().addAll(this.settlementButtons);
    }

    /**
     * Finds a SettlementButton by its coordinates.
     * 
     * @param x The x-coordinate to search for.
     * @param y The y-coordinate to search for.
     * @return The SettlementButton found, or null if not found.
     */
    private SettlementButton findSettlementButtonByCoordinates(double x, double y) {
        for (SettlementButton sButton : settlementButtons) {
            if (isCloseEnough(sButton.getCenterX(), x) && isCloseEnough(sButton.getCenterY(), y)) {
                return sButton;
            }
        }
        return null;
    }

    /**
     * Initializes the road buttons on the game table.
     * Calculates the coordinates for each road button based on the tiles' positions.
     * Connects the road buttons to the adjacent settlement buttons.
     * Adds the road buttons to the list of road buttons.
     * Finally, adds the road buttons to the pane.
     */
    private void initializeRoadButtons() {
        double length = RADIUS * TILE_COEFFICIENT;
        int index = 0;
        for (Tile tile : tiles) {
            double tileX = tile.getX();
            double tileY = tile.getY();
            double startAngle = Math.toRadians(-30);
            double rectangeleRotation = 0;
            for (int i = 0; i < 6; ++i) {
                double x = tileX + length * Math.cos(startAngle);
                double y = tileY + length * Math.sin(startAngle);
                RoadButton roadButton = createRoadButton(x, y, index, rectangeleRotation);
                RoadButton roadButtonTmp = findRoadButtonByCoordinates(roadButton);
                if(roadButtonTmp == null) {
                    Integer otherAdjacentSettlementButtonIndex = (i - 1) != -1 ? i - 1 : 5;
                    tile.getSettlementButtons().get(i).getAdjacentRoadButtons().add(roadButton);
                    tile.getSettlementButtons().get(otherAdjacentSettlementButtonIndex).getAdjacentRoadButtons().add(roadButton);
                    roadButton.getAdjacentSettlementButtons().add(tile.getSettlementButtons().get(i));
                    roadButton.getAdjacentSettlementButtons().add(tile.getSettlementButtons().get(otherAdjacentSettlementButtonIndex));
                    roadButtons.add(roadButton);
                    index++;
                }
                startAngle += Math.toRadians(-60);
                rectangeleRotation += 60;
            }
        }

        pane.getChildren().addAll(this.roadButtons);
    }

    /**
     * Creates a RoadButton with the specified dimensions and position.
     *
     * @param x The x-coordinate of the RoadButton's position.
     * @param y The y-coordinate of the RoadButton's position.
     * @param index The index of the RoadButton.
     * @param rectangeleRotation The rotation angle of the RoadButton in degrees.
     * @return The created RoadButton.
     */
    private RoadButton createRoadButton(double x, double y, int index, double rectangeleRotation) {
        double width = 2 * RADIUS * (1 - TILE_COEFFICIENT);
        double height = RADIUS * TILE_COEFFICIENT;
        RoadButton roadButton = new RoadButton(width, height, x, y, index);
        Rotate rotate = new Rotate(-rectangeleRotation);
        rotate.setPivotX(roadButton.getX());
        rotate.setPivotY(roadButton.getY());
        roadButton.getTransforms().add(rotate);
        return roadButton;
    }

    /**
     * Finds a RoadButton in the list of roadButtons based on its coordinates.
     *
     * @param roadButton The RoadButton to find.
     * @return The found RoadButton, or null if not found.
     */
    private RoadButton findRoadButtonByCoordinates(RoadButton roadButton) {
        Point2D bottomRightCorner = calculateBottomRightCorner(roadButton);
        for (RoadButton rButton : roadButtons) {
            if (isCloseEnough(rButton.getX(), bottomRightCorner.getX())
                    && isCloseEnough(rButton.getY(), bottomRightCorner.getY())) {
                return rButton;
            }
        }
        return null;
    }

    private boolean isCloseEnough(double a, double b) {
        return Math.abs(a - b) < TOLERANCE;
    }

    
    /**
     * Calculates the coordinates of the bottom right corner of a road button after rotation.
     *
     * @param roadButton The road button to calculate the bottom right corner for.
     * @return The coordinates of the bottom right corner as a Point2D object.
     */
    private Point2D calculateBottomRightCorner(RoadButton roadButton) {
        // Get the Rotate object from the transforms list
        Rotate rotate = (Rotate) roadButton.getTransforms().get(0);

        // Get the rotation angle
        double rotationAngle = rotate.getAngle();

        // Convert the rotation angle to radians
        double angleInRadians = Math.toRadians(rotationAngle);
        // Calculate the coordinates of the bottom right corner after rotation
        double width = roadButton.getWidth();
        double height = roadButton.getHeight();

        double newX = roadButton.getX() + width * Math.cos(angleInRadians) - height * Math.sin(angleInRadians);
        double newY = roadButton.getY() + width * Math.sin(angleInRadians) + height * Math.cos(angleInRadians);

        return new Point2D.Double(newX, newY);
    }

    /**
     * Creates a hexagon shape with the specified fill color and radius.
     *
     * @param fill   the fill color of the hexagon
     * @param RADIUS the radius of the hexagon
     * @return the created hexagon shape
     */
    private Polygon createHexagon(Color fill, double RADIUS) {
        Polygon hexagon = new Polygon();

        for (int i = 0; i < 6; i++) {
            double x = RADIUS * Math.cos(Math.PI / 6 + Math.PI / 3 * i);
            double y = RADIUS * Math.sin(Math.PI / 6 + Math.PI / 3 * i);
            hexagon.getPoints().addAll(x, y);
        }

        hexagon.setFill(fill);
        hexagon.setStroke(Color.BLACK);
        hexagon.setStrokeWidth(2.0);

        return hexagon;
    }

    /**
     * Starts the turn for the current player.
     */
    public void startTurn(){
        playerTurnControllers.get(currentPlayer).startTurn();
    }

    /**
     * Ends the current player's turn and starts the next player's turn.
     * Resets the dice rolled by the current player.
     * Updates the player circle to indicate the current player.
     */
    public void endTurn() {
        PlayerTurnController playerTurnController = playerTurnControllers.get(currentPlayer);
        playerTurnController.resetDiceRolled();

        currentPlayer = (currentPlayer + 1) % 4;
        logger.info("Current player: {} ", currentPlayer);
        PlayerController.getInstance().updatePlayerCircle(currentPlayer);
        startTurn();
    }


    public List<SettlementButton> getSettlementButtons() {
        return this.settlementButtons;
    }

    public PlayerTurnController getCurrentPlayerTurnController() {
        return playerTurnControllers.get(currentPlayer);
    }

    public List<PlayerTurnController> getPlayerTurnControllers() {
        return playerTurnControllers;
    }

    public List<SettlementButton> getSettlementButtons(Player player) {
        List<SettlementButton> playerSettlementButtons = new ArrayList<>();
        for (SettlementButton settlementButton : settlementButtons) {
            if (settlementButton.getOwner() == player) {
                playerSettlementButtons.add(settlementButton);
            }
        }
        return playerSettlementButtons;
    }

    public List<RoadButton> getRoadButtons() {
        return this.roadButtons;
    }

    public List<SettlementButton> getSettlementButtonsWithNoOwner() {
        List<SettlementButton> settlementButtonsWithNoOwner = new ArrayList<>();
        for (SettlementButton settlementButton : settlementButtons) {
            if (settlementButton.getOwner() == null) {
                settlementButtonsWithNoOwner.add(settlementButton);
            }
        }
        return settlementButtonsWithNoOwner;
    }

    /**
     * Ends the game and performs necessary actions such as adding the player's score to the leaderboard
     * and displaying a message indicating the winner of the game.
     */
    public void endGame() {
        if(isGameEnded)
            return;
        isGameEnded = true;
        Player player = playerTurnControllers.get(0).getPlayer();
        String playerName = player.getPlayerName();
        int score = player.getTotalPoints();
        if(leaderboardService.addLeaderboardEntry(playerName, score)){
            logger.info("Leaderboard entry added successfully with username: {} and score: {}", playerName, score);
        }
        else{
            logger.info("Leaderboard entry could not be added");
        }
        if(currentPlayer == 0){
            GameEndController.displayEndGameMessage(mainPane, playerName + " won the game!");
        }
        else{
            GameEndController.displayEndGameMessage(mainPane, "Player " + (currentPlayer + 1) + " won the game!");
        }
    }
}