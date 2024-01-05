package com.catan.catanui.controller.game.turn;

import java.util.ArrayList;
import java.util.List;

import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.controller.game.PlayerController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.RoadButton;
import com.catan.catanui.entity.Settlement;
import com.catan.catanui.entity.SettlementButton;
import com.catan.catanui.entity.Tile;

public abstract class PlayerTurnController {
    protected Player player;
    private Boolean isDiceRolled = false;
    protected static GameTableController gameTableController = GameTableController.getInstance();

    PlayerTurnController(Player player) {
        this.player = player;
    }

    public boolean rollDice() {
        if (isDiceRolled) {
            return false;
        }
        isDiceRolled = true;
        return true;
    }

    public void resetDiceRolled() {
        isDiceRolled = false;
    }

    public Player getPlayer() {
        return this.player;
    }

    public abstract void startTurn();

    public abstract void enableEndTurnButton();

    public void updateResources(int diceTotal) {
        List<SettlementButton> settlementButtons = GameTableController.getInstance().getSettlementButtons(this.getPlayer());
        for (SettlementButton settlementButton : settlementButtons) {
            List<Tile> adjacentTiles = settlementButton.getAdjacentTiles();
            for (Tile tile : adjacentTiles) {
                if (tile.getDiceNumber() == diceTotal) {
                    PlayerController.getInstance().changePlayerResource(this.getPlayer(), tile.getResourceType(), settlementButton.getLevel());
                }
            }
        }
    }

    protected void disableButtons() {
        disableAllSettlementButtons();
        enableAvailableSettlementButtons();
        disableAllRoadButtons();
        enableAvailableRoadButtons();
    }

    private void enableAvailableRoadButtons() {
        List<RoadButton> availableRoadButtons = player.getAvaliableRoadButtons();
        for (RoadButton roadButton : availableRoadButtons) {
            roadButton.setDisable(false);
        }
    }


    private void disableAllRoadButtons() {
        List<RoadButton> roadButtons = gameTableController.getRoadButtons();
        for (RoadButton roadButton : roadButtons) {
            roadButton.setDisable(true);
        }
    }

    private void disableAllSettlementButtons(){
        List<SettlementButton> settlementButtons = gameTableController.getSettlementButtons();
        for (SettlementButton settlementButton : settlementButtons) {
            settlementButton.setDisable(true);
        }
    }

    private void enableAvailableSettlementButtons() {
        List<SettlementButton> availableSettlementButtons = player.getAvaliableSettlementButtons();
        for (SettlementButton settlementButton : availableSettlementButtons) {
            settlementButton.setDisable(false);
        }
    }


    protected void enableAllGameTableButtons() {
        enableAllSettlementButtons();
        enableAllRoadButtons();
    }

    private void enableAllSettlementButtons() {
        List<SettlementButton> settlementButtons = gameTableController.getSettlementButtons();
        for (SettlementButton settlementButton : settlementButtons) {
            settlementButton.setDisable(false);
        }
    }

    private void enableAllRoadButtons(){
        List<RoadButton> roadButtons = gameTableController.getRoadButtons();
        for (RoadButton roadButton : roadButtons) {
            roadButton.setDisable(false);
        }
    }
}
