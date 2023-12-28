package com.catan.catanui.entity;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettlementButton extends Circle implements EventHandler<MouseEvent> {
    private static Logger logger = LoggerFactory.getLogger(SettlementButton.class);
    private Settlement settlement;

    public SettlementButton(double radius, double centerX, double centerY, List<Tile> adjacentTiles, int index) {
        super(centerX, centerY, radius);
        this.setOnMouseClicked(this);
        this.settlement = new Settlement(null, adjacentTiles, index);
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.TRANSPARENT);
        this.setStrokeWidth(2);

        // Handle Mouse Over Events
        this.setOnMouseEntered(e -> {
            this.setStroke(Color.BLACK);
        });
        this.setOnMouseExited(e -> {
            this.setStroke(Color.TRANSPARENT);
        });
    }

    @Override
    public void handle(MouseEvent event) {
        logger.info("Settlement {} Button Clicked!", this.settlement.getIndex());
    }

    public int getIndex() {
        return this.settlement.getIndex();
    }
}