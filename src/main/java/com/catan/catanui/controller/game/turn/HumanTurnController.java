package com.catan.catanui.controller.game.turn;

import java.util.List;

import com.catan.catanui.controller.game.EndTurnController;
import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.controller.game.PlayerController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.SettlementButton;
import com.catan.catanui.entity.Tile;

public class HumanTurnController extends PlayerTurnController {
    
    public HumanTurnController(Player player) {
        super(player);
    }

    public void startTurn() {
        disableSettlementButtonsWithOtherOwners();
//        EndTurnController.getInstance().enableEndTurnButton();
    }

    public void enableEndTurnButton() {
        EndTurnController.getInstance().enableEndTurnButton();
    }

//    public void diceRolled(int diceTotal) {
//        List<SettlementButton> settlementButtons = GameTableController.getInstance().getSettlementButtons(this.getPlayer());
//        for (SettlementButton settlementButton : settlementButtons) {
//            List<Tile> adjacentTiles = settlementButton.getAdjacentTiles();
//            for (Tile tile : adjacentTiles) {
//                if (tile.getDiceNumber() == diceTotal) {
//                    PlayerController.getInstance().increasePlayerResource(this.getPlayer(), tile.getResourceType(), settlementButton.getLevel());
//                }
//            }
//        }
//    }
}
