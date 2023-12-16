package com.catan.catanui.controller.GameTablePackage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

import javafx.geometry.HPos;
import javafx.geometry.VPos;

public class HexagonController implements Initializable {

    @FXML
    private GridPane hexGridPane;

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

        int row = 0;
        int column = 0;

        // Iterate through the hexagons and assign colors and numbers
        for (int i = 0; i < HEXAGON_COLORS.size(); i++) {
            Color color = HEXAGON_COLORS.get(i);
            int number = (color.equals(Color.BURLYWOOD)) ? 0 : HEXAGON_NUMBERS.remove(0);

            Polygon hexagon = createHexagon(color);
            hexGridPane.add(hexagon, column, row);

            // Add the number to the middle of the hexagon (skip if Burlywood)
            if (!color.equals(Color.BURLYWOOD)) {
                Text numberText = new Text(Integer.toString(number));
                numberText.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Adjust font and thickness
                hexGridPane.add(numberText, column, row);
                GridPane.setHalignment(numberText, HPos.CENTER);
                GridPane.setValignment(numberText, VPos.CENTER);
            }

            // Increment row and column based on the grid pattern
            column++;
            if ((row == 0 && column > 2) || (row == 1 && column > 3) || (row == 3 && column > 3) || (column > 4)) {
                column = 0;
                row++;
            }
        }
        GridPane.setHalignment(hexGridPane, HPos.CENTER);
        GridPane.setValignment(hexGridPane, VPos.CENTER);
    }

    private Polygon createHexagon(Color fill) {
        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(50.0, 0.0, 100.0, 25.0, 100.0, 75.0, 50.0, 100.0, 0.0, 75.0, 0.0, 25.0);
        hexagon.setFill(fill);
        hexagon.setStroke(Color.BLACK);
        hexagon.setStrokeWidth(2.0);
        return hexagon;
    }
}
