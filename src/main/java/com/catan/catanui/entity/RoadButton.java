package com.catan.catanui.entity;

import com.catan.catanui.enums.ResourceType;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catan.catanui.controller.ButtonsController;
import com.catan.catanui.controller.game.PlayerController;
import com.catan.catanui.controller.game.turn.PlayerTurnController;

public class RoadButton extends Rectangle implements EventHandler<MouseEvent> {
    private static Logger logger = LoggerFactory.getLogger(RoadButton.class);
    private Road road;
    private int index;
    private List<SettlementButton> adjacentSettlementButtons;
    private PlayerTurnController playerTurnController;

    public RoadButton(double width, double height, double x, double y, int index) {
        super(x, y, width, height);
        this.index = index;
        this.setOnMouseClicked(this);
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
        if(road == null){
            if(PlayerController.getInstance().buildRoad()){
                Player currentPlayer = PlayerController.getInstance().getCurrentPlayer();
                this.build(currentPlayer);
                logger.info("Road Built by Player: {}", currentPlayer.getId());
            } else {
                logger.info("Not Enough Resources to Build Road.");
            }
        } else {
            logger.info("Road Already Built.");
        }
    }

    public void setOwner(Player player) {
        this.road.setOwner(player);
        this.setFill(player.getColor());
    }

    public Player getOwner() {
        if(road != null){
            return this.road.getOwner();
        }
        return null;
    }

    public List<SettlementButton> getAdjacentSettlementButtons() {
        return this.adjacentSettlementButtons;
    }

    public void build(Player owner){
        if(this.road == null){
            this.road = new Road();
            road.setOwner(owner);
            this.setFill(owner.getColor());
            this.setDisable(true);
            ButtonsController.getInstance().enableAvailableSettlementButtons(owner);
            ButtonsController.getInstance().enableAvailableRoadButtons(owner);
            owner.updateLongestPath();
        }
    }

    //after first turn, use this for AI
    public void buildTurns(Player owner) {
        if (this.road == null) {
            if (owner.isEnoughResourcesForRoad()) {
                // Deduct resources for the road
                PlayerController.getInstance().changePlayerResource(owner, ResourceType.BRICK, -1);
                PlayerController.getInstance().changePlayerResource(owner, ResourceType.LUMBER, -1);
                PlayerController.getInstance().updatePlayerInfo(owner);

                // Build the road
                this.road = new Road();
                road.setOwner(owner);
                this.setFill(owner.getColor());
                this.setDisable(true);

                // Enable available settlement and road buttons
                ButtonsController.getInstance().enableAvailableSettlementButtons(owner);
                ButtonsController.getInstance().enableAvailableRoadButtons(owner);
            } else {
                logger.info("Not Enough Resources to Build Road.");
            }
        } else {
            logger.info("Road Already Built.");
        }
    }

    public boolean isRoadButtonHaveNoneAdjacentSettlement(){
        for(SettlementButton settlementButton : this.adjacentSettlementButtons){
            if(settlementButton.getOwner() != null){
                return false;
            }
        }
        return true;
    }

    public List<RoadButton> getAdjacentRoadButtonsWithNoOwner(){
        List<RoadButton> adjacentRoadButtonsWithNoOwner = new ArrayList<>();
        for(SettlementButton settlementButton : this.adjacentSettlementButtons){
            for(RoadButton roadButton : settlementButton.getAdjacentRoadButtons()){
                if(roadButton.getOwner() == null){
                    adjacentRoadButtonsWithNoOwner.add(roadButton);
                }
            }
        }
        return adjacentRoadButtonsWithNoOwner;
    }

    public List<SettlementButton> getAdjacentSettlementButtonsWithNoOwner(){
        List<SettlementButton> adjacentSettlementButtonsWithNoOwner = new ArrayList<>();
        for(SettlementButton settlementButton : this.adjacentSettlementButtons){
            if(settlementButton.getOwner() == null){
                adjacentSettlementButtonsWithNoOwner.add(settlementButton);
            }
        }
        return adjacentSettlementButtonsWithNoOwner;
    }

    public List<RoadButton> getAdjacentRoadButtonWithSameOwner(){
        List<RoadButton> adjacentRoadButtonsWithSameOwner = new ArrayList<>();
        for(SettlementButton settlementButton : this.adjacentSettlementButtons){
            for(RoadButton roadButton : settlementButton.getAdjacentRoadButtons()){
                if(roadButton.getOwner() != null && roadButton.getOwner().getId() == this.getOwner().getId()){
                    adjacentRoadButtonsWithSameOwner.add(roadButton);
                }
            }
        }
        return adjacentRoadButtonsWithSameOwner;
    }

}
