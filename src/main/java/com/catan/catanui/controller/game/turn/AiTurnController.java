package com.catan.catanui.controller.game.turn;

import com.catan.catanui.entity.Player;

public class AiTurnController extends PlayerTurnController{

    public AiTurnController(Player player) {
        super(player);
    }

    public void startTurn() {
        // sleep for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
