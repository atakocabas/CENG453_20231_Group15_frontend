package com.catan.catanui.controller.game;

import com.catan.catanui.controller.game.turn.AiTurnController;
import com.catan.catanui.controller.game.turn.HumanTurnController;
import com.catan.catanui.controller.game.turn.PlayerTurnController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.RoadButton;
import com.catan.catanui.entity.SettlementButton;
import com.catan.catanui.entity.Tile;
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

    private static int currentPlayer = 0;
    private List<SettlementButton> settlementButtons = new ArrayList<>();
    private List<RoadButton> roadButtons = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();
    private List<PlayerTurnController> playerTurnControllers = new ArrayList<>();

    private static final List<Color> HEXAGON_COLORS = new ArrayList<>(List.of(
            Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, // 4 dark green hexagons
            Color.SADDLEBROWN, Color.SADDLEBROWN, Color.SADDLEBROWN, // 3 dark brown hexagons
            Color.LIGHTGREEN, Color.LIGHTGREEN, Color.LIGHTGREEN, Color.LIGHTGREEN, // 4 light green hexagons
            Color.GREY, Color.GREY, Color.GREY, // 3 light blue hexagons
            Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, // 4 yellow hexagons
            Color.BURLYWOOD // 1 sand-colored hexagon
    ));

    // Make sure the number of elements in HEXAGON_COLORS and HEXAGON_NUMBERS are
    // the same
    private static final List<Integer> HEXAGON_NUMBERS = new LinkedList<>(
            List.of(2, 3, 11, 12, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        initializeTiles();
        initiateSettlementButtons();
        initializeRoadButtons();
        initalizeGame();
        startTurn();
        logger.info("Game Table Controller Initialized!");
    }

    public static GameTableController getInstance() {
        if (instance == null) {
            instance = new GameTableController();
        }
        return instance;
    }

    private void initalizeGame() {
        Random random = new Random();
        int[] randomNumbers = random.ints(0, settlementButtons.size()).distinct().limit(4).toArray();
        for (int i = 0; i < 4; i++) {
            settlementButtons.get(randomNumbers[i]).setOwner(getPlayer(i));
        }
        for (int i = 0; i < 4; i++) {

            SettlementButton settlementButton = settlementButtons.get(randomNumbers[i]);
            List<RoadButton> adjacentRoadButtons = settlementButton.getAdjacentRoadButtons();
            RoadButton roadButton = adjacentRoadButtons.get(new Random().nextInt(adjacentRoadButtons.size()));

            //RoadButton roadButton = settlementButtons.get(randomNumbers[i]).getAdjacentRoadButtons().getFirst();
            roadButton.setOwner(getPlayer(i));
        }
        createPlayerTurnControllers();
    }

    private void createPlayerTurnControllers() {
        List<Player> players = PlayerController.getInstance().getPlayers();
        PlayerTurnController humanPlayerTurnController = new HumanTurnController(players.get(0));
        playerTurnControllers.add(humanPlayerTurnController);

        for (int i = 1; i < players.size(); i++) {
            PlayerTurnController playerTurnController = new AiTurnController(players.get(i));
            playerTurnControllers.add(playerTurnController);
        }
    }

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
                    settlementButton.getAdjacentTiles().add(tile);
                    settlementButtons.add(settlementButton);
                    index++;
                }
                tile.getSettlementButtons().add(settlementButton);
                startAngle += Math.toRadians(-60);
            }
        }

        pane.getChildren().addAll(this.settlementButtons);
    }

    private SettlementButton findSettlementButtonByCoordinates(double x, double y) {
        for (SettlementButton sButton : settlementButtons) {
            if (isCloseEnough(sButton.getCenterX(), x) && isCloseEnough(sButton.getCenterY(), y)) {
                return sButton;
            }
        }
        return null;
    }

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
                    roadButtons.add(roadButton);
                    index++;
                }
                startAngle += Math.toRadians(-60);
                rectangeleRotation += 60;
            }
        }

        pane.getChildren().addAll(this.roadButtons);
    }

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

    // Start Turn and End Turn methods are for AI players.
    // Human player's end turn starts this loop, and ends it on human player.

    public void startTurn(){
        playerTurnControllers.get(currentPlayer).startTurn();
        if(currentPlayer == 0){
            return;
        }
        endTurn();
    }

    public void endTurn() {
        currentPlayer = (currentPlayer + 1) % 4;
        logger.info("Current player: {} ", currentPlayer);
        PlayerController.getInstance().updatePlayerCircle(currentPlayer);
        playerTurnControllers.get(currentPlayer).endTurn();
        startTurn();
    }

    private Player getCurrentPlayer() {
        return PlayerController.getInstance().getCurrentPlayer();
    }

    private Player getPlayer(int index) {
        return PlayerController.getInstance().getPlayer(index);
    }

    public List<SettlementButton> getSettlementButtons() {
        return this.settlementButtons;
    }
}
