package com.catan.catanui.controller.game.turn;

import java.util.List;

import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.SettlementButton;

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
            if (settlementButton.getOwner() != null) {
                settlementButton.setDisable(true);
            }
        }
    }

    public abstract void startTurn();

    public void endTurn() {
        List<SettlementButton> settlementButtons = gameTableController.getSettlementButtons();
        for(SettlementButton settlementButton : settlementButtons) {
            settlementButton.setDisable(false);
        }
    }
}
