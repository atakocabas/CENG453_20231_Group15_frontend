package com.catan.catanui.entity;

import java.util.ArrayList;
import java.util.List;

import com.catan.catanui.enums.TileType;
import javafx.scene.paint.Color;
import lombok.Data;

@Data
public class Tile {
    private int diceNumber;
    private TileType type;
    private List<SettlementButton> settlementButtons = new ArrayList<>();
    private double x;
    private double y;

    public Tile(int diceNumber, Color color, double x, double y) {
        this.diceNumber = diceNumber;
        this.x = x;
        this.y = y;
        if(color.equals(Color.DARKGREEN)){
            this.type = TileType.FOREST;
        } else if (color.equals(Color.GREY)){
            this.type = TileType.HILLS;
        } else if (color.equals(Color.SADDLEBROWN)){
            this.type = TileType.MOUNTAINS;
        } else if (color.equals(Color.YELLOW)){
            this.type = TileType.PASTURE;
        } else if (color.equals(Color.LIGHTGREEN)){
            this.type = TileType.FIELDS;
        } else if (color.equals(Color.BURLYWOOD)){
            this.type = TileType.DESERT;
        } else {
            this.type = null;
        }
    }
}
