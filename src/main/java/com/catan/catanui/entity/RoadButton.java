package com.catan.catanui.entity;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoadButton extends Rectangle implements EventHandler<MouseEvent>{
    private static Logger logger = LoggerFactory.getLogger(RoadButton.class);
    private Road road;
    private int index;

    public RoadButton(double width, double height, double x, double y, int index) {
        super(x, y, width, height);
        this.index = index;
        this.setOnMouseClicked(this);
        this.road = new Road();
        this.setFill(Color.RED);
        this.setStroke(null);
        this.setStrokeWidth(2);

        // Handle Mouse Over Events
        this.setOnMouseEntered(e -> {
            this.setStroke(Color.BLACK);
        });
        this.setOnMouseExited(e -> {
            this.setStroke(null);
        });
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public void handle(MouseEvent arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
    
}
