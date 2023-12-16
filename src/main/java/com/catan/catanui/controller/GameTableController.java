package com.catan.catanui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.net.URL;
import java.util.*;

public class GameTableController implements Initializable {

    @FXML
    private GridPane hexGridPane;

    private static final List<Color> HEXAGON_COLORS = Arrays.asList(
            Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, Color.DARKGREEN, // 4 dark green hexagons
            Color.SADDLEBROWN, Color.SADDLEBROWN, Color.SADDLEBROWN, // 3 dark brown hexagons
            Color.LIGHTGREEN, Color.LIGHTGREEN, Color.LIGHTGREEN, Color.LIGHTGREEN, // 4 light green hexagons
            Color.GREY, Color.GREY, Color.GREY, // 3 light blue hexagons
            Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, // 4 yellow hexagons
            Color.BURLYWOOD // 1 sand-colored hexagon
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Shuffle the hexagon colors list to randomize the order
        Collections.shuffle(HEXAGON_COLORS);

        int row = 2;
        int column = 2;

        // Iterate through the hexagons and assign colors
        for (Color color : HEXAGON_COLORS) {
            Polygon hexagon = createHexagon(color);
            hexGridPane.add(hexagon, column, row);

            // Increment row and column based on the grid pattern
            column++;
            if ( (row == 2 && column > 4) || (row == 3 && column > 5) || (row == 5 && column > 5) || (column > 6) )  {
                column = 2;
                row++;
            }
        }
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
