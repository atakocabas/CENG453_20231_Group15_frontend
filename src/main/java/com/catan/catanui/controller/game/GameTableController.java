package com.catan.catanui.controller.game;

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

import java.net.URL;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameTableController implements Initializable {
    private static Logger LOGGER = LoggerFactory.getLogger(GameTableController.class);
    public static GameTableController instance;
    private static final double radius = 75.0;
    private static final double startX = 362.0;
    private static final double startY = 220.0;

    @FXML
    private Pane pane;

    private static int currentPlayer = 0;
    private List<SettlementButton> settlementButtons = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();

    private static final List<Color> HEXAGON_COLORS = new ArrayList<>(List.of(
            Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, // 4 dark green hexagons
            Color.SADDLEBROWN, Color.SADDLEBROWN, Color.SADDLEBROWN, // 3 dark brown hexagons
            Color.LIGHTGREEN, Color.LIGHTGREEN, Color.LIGHTGREEN, Color.LIGHTGREEN, // 4 light green hexagons
            Color.GREY, Color.GREY, Color.GREY, // 3 light blue hexagons
            Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, // 4 yellow hexagons
            Color.BURLYWOOD // 1 sand-colored hexagon
    ));

    // Make sure the number of elements in HEXAGON_COLORS and HEXAGON_NUMBERS are the same
    private static final List<Integer> HEXAGON_NUMBERS = new LinkedList<>(List.of(2, 3, 11, 12, 4, 5, 6, 7, 8, 9, 10, 4, 5, 6, 7, 8, 9, 10));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        initializeTiles();
        initiateSettlementButtons();
    }

    public static GameTableController getInstance() {
        if (instance == null) {
            instance = new GameTableController();
        }
        return instance;
    }

    private void initializeTiles(){
        // Shuffle the hexagon colors and numbers lists to randomize the order
        // Remove Color.BURLYWOOD from the list
        HEXAGON_COLORS.remove(Color.BURLYWOOD);

        // Shuffle the remaining colors
        Collections.shuffle(HEXAGON_COLORS);

        // Add Color.BURLYWOOD back to the middle of the list
        HEXAGON_COLORS.add(HEXAGON_COLORS.size() / 2, Color.BURLYWOOD);
        Collections.shuffle(HEXAGON_NUMBERS);

        // Define the number of hexagons in each row
        int[] hexagonsInRow = {3, 4, 5, 4, 3};

        for (int row = 0; row < hexagonsInRow.length; row++) {
            for (int col = 0; col < hexagonsInRow[row]; col++) {
                Color color = HEXAGON_COLORS.remove(0);
                int number = (color.equals(Color.BURLYWOOD)) ? 0 : HEXAGON_NUMBERS.remove(0);

                Polygon hexagon = createHexagon(color, radius*0.9);
                double row_x = row;
                if (row > 2) {
                    row_x = Math.abs(row - 4);
                }
                hexagon.setLayoutX(startX + col * radius * Math.sqrt(3) - row_x * radius * Math.sqrt(3) / 2);
                hexagon.setLayoutY(startY + row * radius * 1.5);
                pane.getChildren().add(hexagon);

                Tile tile = new Tile(number, color);
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
        double settlementStartY = startY + radius;
        double xIncrement = radius * Math.sqrt(3) / 2;

        for (int i = 0; i < 4; ++i) {
            int numSettlementsInRow = getNumSettlementsInRow(i);
            for (int j = 0; j < numSettlementsInRow; ++j) {
                double x = startX + j * xIncrement - ((i == 1 || i == 2) ? xIncrement : 0);
                double y;

                if (i > 1) {
                    y = settlementStartY + i * radius * 3 / 2 - ((j % 2 == 0) ? radius / 2 : 0);
                } else {
                    y = settlementStartY + i * radius * 3 / 2 - ((j % 2 == 0) ? 0 : radius / 2);
                }

                SettlementButton settlementButton = new SettlementButton(radius / 4, x, y, null);
                this.settlementButtons.add(settlementButton);
            }
        }

        pane.getChildren().addAll(this.settlementButtons);
    }

    private int getNumSettlementsInRow(int currentRow) {
        if (currentRow == 0 || currentRow == 3) {
            return 5;
        } else {
            return 7;
        }
    }

    private List<Tile> createAdjacentTiles(SettlementButton settlementButton) {
        return null;
    }

    private Polygon createHexagon(Color fill, double radius) {
        Polygon hexagon = new Polygon();

        for (int i = 0; i < 6; i++) {
            double x = radius * Math.cos(Math.PI / 6 + Math.PI / 3 * i);
            double y = radius * Math.sin(Math.PI / 6 + Math.PI / 3 * i);
            hexagon.getPoints().addAll(x, y);
        }

        hexagon.setFill(fill);
        hexagon.setStroke(Color.BLACK);
        hexagon.setStrokeWidth(2.0);

        return hexagon;
    }

    public void endTurn() {
        currentPlayer = (currentPlayer + 1) % 4;
        LOGGER.info("Current player: {} ", currentPlayer);
        PlayerController.getInstance().updatePlayerCircle(currentPlayer);
    }
}
