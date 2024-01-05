package com.catan.catanui.entity;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoadButton extends Rectangle implements EventHandler<MouseEvent>{
    private static Logger logger = LoggerFactory.getLogger(RoadButton.class);
    private Road road;
    private int index;
    private List<SettlementButton> adjacentSettlementButtons;

    public RoadButton(double width, double height, double x, double y, int index) {
        super(x, y, width, height);
        this.index = index;
        this.setOnMouseClicked(this);
        this.road = new Road();
        this.setFill(Color.TRANSPARENT);
        this.setStroke(null);
        this.setStrokeWidth(2);

        // Handle Mouse Over Events
        this.setOnMouseEntered(e -> {
            this.setStroke(Color.BLACK);
        });
        this.setOnMouseExited(e -> {
            this.setStroke(null);
        });
        this.adjacentSettlementButtons = new ArrayList<>();
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public void handle(MouseEvent arg0) {
        logger.info("RoadButton clicked: {}", this.index);
    }

    public void setOwner(Player player) {
        this.road.setOwner(player);
        this.setFill(player.getColor());
    }

    public Player getOwner() {
        return road.getOwner();
    }

    public List<SettlementButton> getAdjacentSettlementButtons() {
        return this.adjacentSettlementButtons;
    }
}
