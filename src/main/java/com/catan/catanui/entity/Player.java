package com.catan.catanui.entity;

import javafx.scene.paint.Color;
import lombok.Data;

import java.util.List;

@Data
public class Player {
    private int id;
    private String playerName;
    private int longestRoad;
    private List<Road> roads;
    private List<Settlement> settlements;
    private Color color;
}
