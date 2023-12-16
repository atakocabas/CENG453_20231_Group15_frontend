package com.catan.catanui.entity;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.List;

public class SettlementButton extends Circle implements EventHandler<MouseEvent> {
    private Settlement settlement;

    public SettlementButton(double radius, double centerX, double centerY, List<Tile> adjacentTiles) {
        super(centerX, centerY, radius);
        this.setOnMouseClicked(this);
        this.settlement = new Settlement(null, adjacentTiles);
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("Circle button clicked!");
    }
}