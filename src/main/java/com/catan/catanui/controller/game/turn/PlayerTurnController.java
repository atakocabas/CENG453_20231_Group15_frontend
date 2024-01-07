package com.catan.catanui.controller.game.turn;

import java.util.List;

import com.catan.catanui.controller.ButtonsController;
import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.controller.game.PlayerController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.SettlementButton;
import com.catan.catanui.entity.Tile;

/**
 * The abstract class PlayerTurnController represents the base class for controlling a player's turn in the game.
 * It provides methods for rolling dice, updating resources, and managing buttons.
 */
public abstract class PlayerTurnController {
    protected Player player;
    private Boolean isDiceRolled = false;
    protected static GameTableController gameTableController = GameTableController.getInstance();

    /**
     * Represents a controller for managing the turn of a player in a game.
     */
    PlayerTurnController(Player player) {
        this.player = player;
    }

    /**
     * Rolls the dice for the player's turn.
     * 
     * @return true if the dice is rolled successfully, false if the dice has already been rolled
     */
    public boolean rollDice() {
        if (isDiceRolled) {
            return false;
        }
        isDiceRolled = true;
        return true;
    }

    /**
     * Resets the flag indicating whether the dice has been rolled.
     */
    public void resetDiceRolled() {
        isDiceRolled = false;
    }

    /**
     * Returns the player associated with this turn controller.
     *
     * @return the player object
     */
    public Player getPlayer() {
        return this.player;
    }

    public abstract void startTurn();

    public abstract void enableButtons();

    //works for tiles with same number
    /**
     * Updates the player's resources based on the dice total.
     * It iterates through all the settlement buttons owned by the player,
     * checks the adjacent tiles for a matching dice number, and updates
     * the player's resources accordingly.
     *
     * @param diceTotal the total value of the dice roll
     */
    public void updateResources(int diceTotal) {
        List<SettlementButton> settlementButtons = GameTableController.getInstance().getSettlementButtons(this.getPlayer());
        for (SettlementButton settlementButton : settlementButtons) {
            List<Tile> adjacentTiles = settlementButton.getAdjacentTiles();
            for (Tile tile : adjacentTiles) {
                if (tile.getDiceNumber() == diceTotal) {
                    PlayerController.getInstance().changePlayerResource(
                            this.getPlayer(),
                            tile.getResourceType(),
                            settlementButton.getLevel()
                    );
                }
            }
        }
    }



    /**
     * Updates the buttons for the player's turn.
     * Disables all settlement buttons, enables available settlement buttons for the player,
     * disables all road buttons, enables available road buttons for the player,
     * and enables upgradable settlement buttons for the player.
     */
    protected void updateButtons() {
        ButtonsController.getInstance().disableAllSettlementButtons();
        ButtonsController.getInstance().enableAvailableSettlementButtons(this.getPlayer());
        ButtonsController.getInstance().disableAllRoadButtons();
        ButtonsController.getInstance().enableAvailableRoadButtons(this.getPlayer());
        ButtonsController.getInstance().enableUpgradableSettlementButtons(this.getPlayer());
    }

}
