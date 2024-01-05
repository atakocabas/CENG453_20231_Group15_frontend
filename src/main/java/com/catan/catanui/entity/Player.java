package com.catan.catanui.entity;

import com.catan.catanui.controller.game.EndTurnController;
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
    private int totalPoints;
    private int longestPath;
    private int settlementPoints = 1;
    private int cityPoints;

    public Player(int id, String playerName, Color color) {
        this.totalPoints = settlementPoints + cityPoints; //  + isLongestPath eklenecek
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

    public void changeResource(ResourceType resourceType, int amount) {
        this.resources.put(resourceType, this.resources.get(resourceType) + amount);
    }

    public boolean isEnoughResourcesForRoad() {
        return this.resources.get(ResourceType.BRICK) >= 1 && this.resources.get(ResourceType.LUMBER) >= 1;
    }
}
