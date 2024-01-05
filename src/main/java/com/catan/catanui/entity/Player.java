package com.catan.catanui.entity;

import com.catan.catanui.controller.game.EndTurnController;
import com.catan.catanui.controller.game.GameTableController;

import javafx.scene.paint.Color;
import lombok.Data;

import java.util.ArrayList;
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
    

    public boolean isEnoughResourcesForSettlement() {
        return this.resources.get(ResourceType.BRICK) >= 1 
            && this.resources.get(ResourceType.LUMBER) >= 1 
            && this.resources.get(ResourceType.WOOL) >= 1 
            && this.resources.get(ResourceType.GRAIN) >= 1;
    }

    public List<SettlementButton> getOwnedSettlementButtons() {
        List<SettlementButton> ownedSettlementButtons = new ArrayList<>();
        List<SettlementButton> settlementButtons = GameTableController.getInstance().getSettlementButtons();
        for (SettlementButton settlementButton : settlementButtons) {
            if (settlementButton.getOwner() == this) {
                ownedSettlementButtons.add(settlementButton);
            }
        }
        return ownedSettlementButtons;
    }

    public boolean isEnoughResourcesForCity() {
        return this.resources.get(ResourceType.ORE) >= 3 
            && this.resources.get(ResourceType.GRAIN) >= 2;
    }

    public List<RoadButton> getAvaliableRoadButtons() {
        List<RoadButton> avaliableRoadButtons = new ArrayList<>();
        List<SettlementButton> ownedSettlementButtons = this.getOwnedSettlementButtons();
        for (SettlementButton settlementButton : ownedSettlementButtons) {
            for (RoadButton roadButton : settlementButton.getAdjacentRoadButtons()) {
                if(roadButton.getOwner() == null)
                    avaliableRoadButtons.add(roadButton);
            }
        }
        return avaliableRoadButtons;
    }
}
