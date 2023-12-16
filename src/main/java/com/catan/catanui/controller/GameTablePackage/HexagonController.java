package com.catan.catanui.controller.GameTablePackage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;

public class HexagonController implements Initializable {

    @FXML
    private Pane pane;

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
        // Shuffle the hexagon colors and numbers lists to randomize the order
        Collections.shuffle(HEXAGON_COLORS);
        Collections.shuffle(HEXAGON_NUMBERS);

        int numRows = 5; // Number of rows in the honeycomb pattern

        for (int row = 0; row < numRows; row++) {
            int numHexagons = getNumHexagonsInRow(row, numRows);

            // Adjust the starting position for each row to create the honeycomb pattern

            double startX = 80.0;


            // Adjust the starting position for the 4th row to align with the 2nd row and move it a bit to the right
            if ((row == 3) || (row == 1)) {
                startX += 60.0; // You can adjust this value as needed
            }

            if ((row == 4) || (row == 0)) {
                startX += 120.0; // You can adjust this value as needed
            }

            for (int col = 0; col < numHexagons; col++) {
                Color color = HEXAGON_COLORS.remove(0);
                int number = (color.equals(Color.BURLYWOOD)) ? 0 : HEXAGON_NUMBERS.remove(0);

                Polygon hexagon = createHexagon(color);
                hexagon.setLayoutX(startX + 100 + 125.0 * col);
                hexagon.setLayoutY(100.0 + 100.0 * row);
                pane.getChildren().add(hexagon);

                // Add the number to the middle of the hexagon (skip if Burlywood)
                if (!color.equals(Color.BURLYWOOD)) {
                    Text numberText = new Text(Integer.toString(number));
                    numberText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                    pane.getChildren().add(numberText);
                    numberText.setLayoutX(hexagon.getLayoutX() + hexagon.getTranslateX() + 50.0);
                    numberText.setLayoutY(hexagon.getLayoutY() + hexagon.getTranslateY() + 60.0);
                }
            }
        }
    }

    private int getNumHexagonsInRow(int currentRow, int totalRows) {
        if (currentRow == 1 || currentRow == 3) {
            return 4;
        } else if (currentRow < totalRows / 2) {
            return 3 + currentRow;
        } else {
            return 3 + totalRows - 1 - currentRow;
        }
    }





    private Polygon createHexagon(Color fill) {
        Polygon hexagon = new Polygon();

        // Define the scale factor for making the hexagon 20% bigger
        double scaleFactor = 1.2;

        hexagon.getPoints().addAll(
                50.0 * scaleFactor, 0.0 * scaleFactor,
                100.0 * scaleFactor, 25.0 * scaleFactor,
                100.0 * scaleFactor, 75.0 * scaleFactor,
                50.0 * scaleFactor, 100.0 * scaleFactor,
                0.0 * scaleFactor, 75.0 * scaleFactor,
                0.0 * scaleFactor, 25.0 * scaleFactor
        );

        hexagon.setFill(fill);
        hexagon.setStroke(Color.BLACK);
        hexagon.setStrokeWidth(2.0);

        return hexagon;
    }
}
