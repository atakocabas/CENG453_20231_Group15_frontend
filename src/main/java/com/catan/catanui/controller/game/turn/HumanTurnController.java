package com.catan.catanui.controller.game.turn;

import com.catan.catanui.entity.Player;

public class HumanTurnController extends PlayerTurnController {
    
    public HumanTurnController(Player player) {
        super(player);
    }

    public void startTurn() {
        disableSettlementButtonsWithOtherOwners();
    }
}
