package com.catan.catanui.entity;

import com.catan.catanui.enums.ResourceType;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catan.catanui.controller.ButtonsController;
import com.catan.catanui.controller.game.PlayerController;

public class SettlementButton extends Circle implements EventHandler<MouseEvent> {
    private static Logger logger = LoggerFactory.getLogger(SettlementButton.class);
    private Settlement settlement;
    private List<RoadButton> adjacentRoads = new ArrayList<>();
    private List<Tile> adjacentTiles = new ArrayList<>();
    private int index;
    private Color strokeColor = Color.TRANSPARENT;

    public SettlementButton(double radius, double centerX, double centerY, int index) {
        super(centerX, centerY, radius);
        this.index = index;
        this.setOnMouseClicked(this);
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.TRANSPARENT);
        this.setStrokeWidth(2);

        // Handle Mouse Over Events
        this.setOnMouseEntered(e -> {
            this.setStroke(Color.BLACK);
        });
        this.setOnMouseExited(e -> {
            this.setStroke(strokeColor);
        });
    }

    @Override
    public void handle(MouseEvent event) {
        logger.info("Settlement {} Button Clicked!", this.getIndex());
        if(this.settlement == null){
            if(PlayerController.getInstance().buildSettlement()){
                Player currentPlayer = PlayerController.getInstance().getCurrentPlayer();
                this.build(currentPlayer);
                this.setFill(currentPlayer.getColor());
                this.getAdjacentSettlementButtons().forEach(settlementButton -> settlementButton.setDisable(true));
                currentPlayer.increaseSettlementPoints();
                logger.info("Settlement Built by Player: {}", currentPlayer.getId());
            } else {
                logger.info("Not Enough Resources to Build Settlement.");
            }
        } else if(!this.settlement.isCity()){
            if(PlayerController.getInstance().upgradeSettlement()){
                Player currentPlayer = PlayerController.getInstance().getCurrentPlayer();
                this.upgrade(currentPlayer);
                this.setOwner(currentPlayer);
                this.setFill(currentPlayer.getColor());
                this.setDisable(true);
                this.getAdjacentSettlementButtons().forEach(settlementButton -> settlementButton.setDisable(true));
                currentPlayer.updateCityBuildPoints();
                logger.info("Settlement Upgraded by Player: {}", currentPlayer.getId());
            } else {
                logger.info("Not Enough Resources to Upgrade Settlement.");
            }
        }
    }

    public int getIndex() {
        return this.index;
    }

    public List<RoadButton> getAdjacentRoadButtons() {
        return this.adjacentRoads;
    }

    public List<Tile> getAdjacentTiles() {
        return this.adjacentTiles;
    }

    public void setOwner(Player player) {
        this.settlement.setOwner(player);
        this.setFill(player.getColor());
    }

    public Player getOwner() {
        if(this.settlement != null){
            return this.settlement.getOwner();
        }
        return null;
    }

    public int getLevel() {
        return 1;
    }

    public List<SettlementButton> getAdjacentSettlementButtons() {
        List<SettlementButton> adjacSettlementButtons = new ArrayList<>();
        for (RoadButton roadButton : this.getAdjacentRoadButtons()) {
            for (SettlementButton settlementButton2 : roadButton.getAdjacentSettlementButtons()) {
                if (settlementButton2 != this) {
                    adjacSettlementButtons.add(settlementButton2);
                }
            }
        }
        return adjacSettlementButtons;
    }

    public void build(Player owner){
        if(settlement == null){
            settlement = new Settlement(owner);
            this.setFill(owner.getColor());
            ButtonsController.getInstance().enableAvailableSettlementButtons(owner);
            ButtonsController.getInstance().enableAvailableRoadButtons(owner);
        }
    }

    public void buildTurns(Player owner) {
        if (settlement == null) {
            if (owner.isEnoughResourcesForSettlement()) {
                // Deduct resources for the settlement
                PlayerController.getInstance().changePlayerResource(owner, ResourceType.BRICK, -1);
                PlayerController.getInstance().changePlayerResource(owner, ResourceType.LUMBER, -1);
                PlayerController.getInstance().changePlayerResource(owner, ResourceType.GRAIN, -1);
                PlayerController.getInstance().changePlayerResource(owner, ResourceType.WOOL, -1);
                PlayerController.getInstance().updatePlayerInfo(owner);

                // Build the settlement
                settlement = new Settlement(owner);
                this.setFill(owner.getColor());

                owner.increaseSettlementPoints();

                // Enable available settlement and road buttons
                ButtonsController.getInstance().enableAvailableSettlementButtons(owner);
                ButtonsController.getInstance().enableAvailableRoadButtons(owner);
            } else {
                logger.info("Not Enough Resources to Build Settlement.");
            }
        } else {
            logger.info("Settlement Already Built.");
        }
    }


    public void upgrade(Player owner) {
        logger.info("Upgrading Settlement to City");
        if (this.getOwner() != null && this.settlement != null) {
            Player currentPlayer = this.getOwner();
            if (currentPlayer.isEnoughResourcesForCity()) {
                PlayerController.getInstance().changePlayerResource(currentPlayer, ResourceType.GRAIN, -2);
                PlayerController.getInstance().changePlayerResource(currentPlayer, ResourceType.ORE, -3);
                PlayerController.getInstance().updatePlayerInfo(currentPlayer);
                owner.updateCityBuildPoints();
                // Upgrade the settlement to a city
                strokeColor = Color.WHITE;
                this.setStroke(strokeColor);
                this.setStrokeWidth(4);
                this.settlement.setCity(true);
            }
        }
    }

    public List<SettlementButton> getAdjacentSettlementButtonsWithOwner() {
        List<SettlementButton> adjacentSettlementButtonsWithOwner = new ArrayList<>();
        for (SettlementButton settlementButton : this.getAdjacentSettlementButtons()) {
            if (settlementButton.getOwner() != null) {
                adjacentSettlementButtonsWithOwner.add(settlementButton);
            }
        }
        return adjacentSettlementButtonsWithOwner;
    }

    public boolean isCity() {
        if(this.settlement != null){
            return this.settlement.isCity();
        }
        return false;
    }
}