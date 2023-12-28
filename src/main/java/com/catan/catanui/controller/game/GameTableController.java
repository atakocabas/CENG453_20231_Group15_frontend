package com.catan.catanui.controller.game;

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

import java.net.URL;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameTableController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(GameTableController.class);
    public static GameTableController instance;
    private static final double RADIUS = 75.0;
    private static final double START_X = 362.0;
    private static final double START_Y = 220.0;
    private static final double TILE_COEFFICIENT = 0.9;

    @FXML
    private Pane pane;

    private static int currentPlayer = 0;
    private List<SettlementButton> settlementButtons = new ArrayList<>();
    private List<RoadButton> roadButtons = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();

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
    }

    public static GameTableController getInstance() {
        if (instance == null) {
            instance = new GameTableController();
        }
        return instance;
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
                SettlementButton settlementButton = createSettlementButton(x, y, index);
                if (settlementButton != null) {
                    settlementButtons.add(settlementButton);
                    tile.getSettlementButtons().add(settlementButton);
                    index++;
                }
                startAngle += Math.toRadians(60);
            }
        }

        pane.getChildren().addAll(this.settlementButtons);
    }

    private SettlementButton createSettlementButton(double x, double y, int index) {
        SettlementButton settlementButton = findSettlementButtonByCoordinates(x, y);
        if (settlementButton != null) {
            return null;
        }
        settlementButton = new SettlementButton(RADIUS / 4, x, y, null, index);
        return settlementButton;
    }

    private SettlementButton findSettlementButtonByCoordinates(double x, double y) {
        for (SettlementButton sButton : settlementButtons) {
            if ((int) sButton.getCenterX() == (int) x && (int) sButton.getCenterY() == (int) y) {
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
                if (roadButton != null) {
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
        RoadButton roadButton = findRoadButtonByCoordinates(x, y);
        if (roadButton != null) {
            return null;
        }
        double width = 2 * RADIUS * (1 - TILE_COEFFICIENT);
        double height = RADIUS * TILE_COEFFICIENT;
        roadButton = new RoadButton(width, height, x, y, index);
        Rotate rotate = new Rotate(-rectangeleRotation);
        rotate.setPivotX(roadButton.getX());
        rotate.setPivotY(roadButton.getY());
        roadButton.getTransforms().add(rotate);
        return roadButton;
    }

    private RoadButton findRoadButtonByCoordinates(double x, double y) {
        for (RoadButton rButton : roadButtons) {
            if ((int) rButton.getX() == (int) x && (int) rButton.getY() == (int) y) {
                return rButton;
            }
        }
        return null;
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

    public void endTurn() {
        currentPlayer = (currentPlayer + 1) % 4;
        logger.info("Current player: {} ", currentPlayer);
        PlayerController.getInstance().updatePlayerCircle(currentPlayer);
    }

    private int getSettlementButtonRow(SettlementButton settlementButton) {
        int index = settlementButton.getIndex() - 5;
        if (index < 0)
            return 0;
        else {
            return (index / 7) + 1;
        }
    }
}
