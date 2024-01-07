package com.catan.catanui.controller.game.turn;


import com.catan.catanui.controller.ButtonsController;
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
     * Starts the turn for the human player, disables all road and settlement buttons.
     */
    public void startTurn() {
        // Disable all buttons at the beginning of the game, they will be enabled when dice is rolled
        ButtonsController.getInstance().disableAllRoadButtons();
        ButtonsController.getInstance().disableAllSettlementButtons();
    }

    /**
     * Enables the buttons for the human player's turn and updates their state.
     */
    public void enableButtons() {
        EndTurnController.getInstance().enableEndTurnButton();
        updateButtons();
    }

}
