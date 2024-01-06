package com.catan.catanui.controller.game.turn;


import com.catan.catanui.controller.ButtonsController;
import com.catan.catanui.controller.game.EndTurnController;
import com.catan.catanui.entity.Player;

public class HumanTurnController extends PlayerTurnController {

    public HumanTurnController(Player player) {
        super(player);
    }

    public void startTurn() {
        enableButtons();
        EndTurnController.getInstance().disableEndTurnButton();
        return;
    }

    public void enableButtons() {
        EndTurnController.getInstance().enableEndTurnButton();
        updateButtons();
    }

}
