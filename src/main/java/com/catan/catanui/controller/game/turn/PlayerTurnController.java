package com.catan.catanui.controller.game.turn;

import java.util.List;

import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.controller.game.PlayerController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.SettlementButton;
import com.catan.catanui.entity.Tile;

public abstract class PlayerTurnController {
    private Player player;
    private Boolean isDiceRolled = false;
    private static GameTableController gameTableController = GameTableController.getInstance();

    PlayerTurnController(Player player) {
        this.player = player;
    }

    protected void disableSettlementButtonsWithOtherOwners() {
        List<SettlementButton> settlementButtons = gameTableController.getSettlementButtons();
        for (SettlementButton settlementButton : settlementButtons) {
            if (settlementButton.getOwner() != null && settlementButton.getOwner() != player) {
                settlementButton.setDisable(true);
            }
        }
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
                    PlayerController.getInstance().increasePlayerResource(this.getPlayer(), tile.getResourceType(), settlementButton.getLevel());
                }
            }
        }
    }
}
