package com.catan.catanui.controller.game.turn;


import com.catan.catanui.controller.game.EndTurnController;
import com.catan.catanui.entity.Player;

/**
 * The HumanTurnController class represents a controller for managing the turn of a human player in a game.
 * It extends the PlayerTurnController class.
 */
public class HumanTurnController extends PlayerTurnController {

    /**
     * Constructs a new HumanTurnController object with the specified player.
     *
     * @param player the player whose turn is being controlled
     */
    public HumanTurnController(Player player) {
        super(player);
    }

    /**
     * Starts the turn of the human player by enabling buttons and disabling the end turn button.
     */
    public void startTurn() {
        enableButtons();
        EndTurnController.getInstance().disableEndTurnButton();
        return;
    }

    /**
     * Enables the buttons for the human player's turn and updates their state.
     */
    public void enableButtons() {
        EndTurnController.getInstance().enableEndTurnButton();
        updateButtons();
    }

}
