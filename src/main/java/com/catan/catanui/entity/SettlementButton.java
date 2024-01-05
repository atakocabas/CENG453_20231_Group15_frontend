package com.catan.catanui.entity;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.controller.game.PlayerController;

public class SettlementButton extends Circle implements EventHandler<MouseEvent> {
    private static Logger logger = LoggerFactory.getLogger(SettlementButton.class);
    private Settlement settlement;
    private List<RoadButton> adjacentRoads = new ArrayList<>();
    private List<Tile> adjacentTiles = new ArrayList<>();
    private PlayerController playerController = PlayerController.getInstance();

    public SettlementButton(double radius, double centerX, double centerY, int index) {
        super(centerX, centerY, radius);
        this.setOnMouseClicked(this);
        this.settlement = new Settlement(null, index);
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.TRANSPARENT);
        this.setStrokeWidth(2);

        // Handle Mouse Over Events
        this.setOnMouseEntered(e -> {
            this.setStroke(Color.BLACK);
        });
        this.setOnMouseExited(e -> {
            this.setStroke(Color.TRANSPARENT);
        });
    }

    @Override
    public void handle(MouseEvent event) {
        logger.info("Settlement {} Button Clicked!", this.settlement.getIndex());
        if(PlayerController.getInstance().buildSettlement()) {
            Player currentPlayer = PlayerController.getInstance().getCurrentPlayer();
            this.setOwner(currentPlayer);
            this.setFill(currentPlayer.getColor());
            this.getAdjacentSettlementButtons().forEach(settlementButton -> settlementButton.setDisable(true));
            logger.info("Settlement Built by Player: {}", currentPlayer.getId());
        } else {
            logger.info("Not Enough Resources to Build Settlement.");
        }
    }

    public int getIndex() {
        return this.settlement.getIndex();
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
        return this.settlement.getOwner();
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
}