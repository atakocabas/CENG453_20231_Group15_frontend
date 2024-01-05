package com.catan.catanui.entity;

import com.catan.catanui.controller.game.EndTurnController;
import com.catan.catanui.controller.game.GameTableController;

import com.catan.catanui.controller.game.PlayerController;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Data;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.catan.catanui.enums.ResourceType;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Text settlementText;
    private Text cityText;
    private Text longestPathText;
    private Text totalPointsText;

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

        // Initialize the settlementText field
        this.settlementText = new Text("Settlement Points: " + this.settlementPoints);
        this.cityText = new Text("City Points: " + this.cityPoints);
        this.longestPathText = new Text("Longest Path: " + this.longestPath);
        this.totalPointsText = new Text("Total Points: " + this.totalPoints);
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
        avaliableRoadButtons.addAll(getAvailableRoadButtonsAdjacentToSettlements());
        avaliableRoadButtons.addAll(getAvailableRoadButtonsAdjacentToRoads());
        return avaliableRoadButtons;
    }

    private List<RoadButton> getAvailableRoadButtonsAdjacentToSettlements(){
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

    private List<RoadButton> getAvailableRoadButtonsAdjacentToRoads(){
        List<RoadButton> avaliableRoadButtons = new ArrayList<>();
        List<RoadButton> ownedRoadButtons = this.getOwnedRoadButtons();
        for(RoadButton rb: ownedRoadButtons){
            for(RoadButton roadButton : rb.getAdjacentRoadButtonsWithNoOwner()){
                if(!avaliableRoadButtons.contains(roadButton))
                    avaliableRoadButtons.add(roadButton);
            }
        }
        return avaliableRoadButtons;
    }

    public List<SettlementButton> getAvaliableSettlementButtons() {
        List<SettlementButton> avaliableSettlementButtons = new ArrayList<>();
        List<RoadButton> ownedRoadButtons = this.getOwnedRoadButtons();
        for(RoadButton roadButton : ownedRoadButtons){
            for(SettlementButton settlementButton : roadButton.getAdjacentSettlementButtonsWithNoOwner()){
                if(settlementButton.getAdjacentSettlementButtonsWithOwner().isEmpty()){
                    avaliableSettlementButtons.add(settlementButton);
                }
            }
        }
        return avaliableSettlementButtons;
    }

    public List<RoadButton> getOwnedRoadButtons() {
        List<RoadButton> ownedRoadButtons = new ArrayList<>();
        List<RoadButton> roadButtons = GameTableController.getInstance().getRoadButtons();
        for (RoadButton roadButton : roadButtons) {
            if (roadButton.getOwner() == this) {
                ownedRoadButtons.add(roadButton);
            }
        }
        return ownedRoadButtons;
    }

    public List<SettlementButton> getInitialAvailableSettlementButtons(){
        List<SettlementButton> availableSettlementButtons = new ArrayList<>();
        List<SettlementButton> settlementButtons = GameTableController.getInstance().getSettlementButtonsWithNoOwner();
        for(SettlementButton settlementButton : settlementButtons){
            if(settlementButton.getAdjacentSettlementButtonsWithOwner().isEmpty()){
                availableSettlementButtons.add(settlementButton);
            }
        }
        return availableSettlementButtons;
    }



    private static final Logger logger = LoggerFactory.getLogger(Player.class);
    public void increaseSettlementPoints(){
        settlementPoints++;
        totalPoints++;
        settlementText.setText("Settlement Points: " + settlementPoints);
        totalPointsText.setText("Total Points: " + totalPoints);
        logger.info("Settlement count increased: Player {}", id);
    }

    public void updateCityBuildPoints(){
        settlementPoints--;
        cityPoints++;
        totalPoints++;
        settlementText.setText("Settlement Points: " + settlementPoints);
        cityText.setText("City Points: " + cityPoints);
        totalPointsText.setText("Total Points: " + totalPoints);
        logger.info("City count increased: Player {}", id);
    }
}
