package com.catan.catanui.entity;

import javafx.scene.paint.Color;
import lombok.Data;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.catan.catanui.enums.ResourceType;

@Data
public class Player {
    private int id;
    private String playerName;
    private int longestRoad;
    private List<Road> roads;
    private List<Settlement> settlements;
    private Color color;
    private Map<ResourceType, Integer> resources;

    public Player(int id, String playerName, Color color) {
        this.id = id;
        if(playerName == null)
            this.playerName = "AI " + id;
        else
            this.playerName = playerName;
        this.color = color;
        this.resources = new EnumMap<>(ResourceType.class);
        for(ResourceType type: ResourceType.values()) {
            this.resources.put(type, 0);
        }
    }
}
