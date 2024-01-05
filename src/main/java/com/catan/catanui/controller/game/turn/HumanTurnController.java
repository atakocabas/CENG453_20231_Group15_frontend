package com.catan.catanui.controller.game.turn;

import java.util.ArrayList;
import java.util.List;

import com.catan.catanui.controller.game.EndTurnController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.RoadButton;
import com.catan.catanui.entity.SettlementButton;

public class HumanTurnController extends PlayerTurnController {

    public HumanTurnController(Player player) {
        super(player);
    }

    public void startTurn() {
        disableButtons();
    }

    public void disableButtons() {
        disableSettlementButtonsWithOtherOwners();
        disableAdjacentSettlements();
        disableAllRoadButtons();
        enableRoadButtonsWithSettlements();
    }

    private void enableRoadButtonsWithSettlements() {
        List<SettlementButton> ownedSettlementButtons = this.player.getOwnedSettlementButtons();
        for (SettlementButton settlementButton : ownedSettlementButtons) {
            for (RoadButton roadButton : settlementButton.getAdjacentRoadButtons()) {
                if(roadButton.getOwner() == null)
                    roadButton.setDisable(false);
            }
        }
    }

    private void disableAllRoadButtons() {
        List<RoadButton> roadButtons = gameTableController.getRoadButtons();
        for (RoadButton roadButton : roadButtons) {
            roadButton.setDisable(true);
        }
    }

    private void disableAdjacentSettlements() {
        List<SettlementButton> ownedSettlementButtons = this.player.getOwnedSettlementButtons();
        for (SettlementButton settlementButton : ownedSettlementButtons) {
            for (SettlementButton adjacSettlementButton : settlementButton.getAdjacentSettlementButtons()) {
                adjacSettlementButton.setDisable(true);
            }
        }
    }

    public void enableEndTurnButton() {
        EndTurnController.getInstance().enableEndTurnButton();
    }

    private void disableSettlementButtonsWithOtherOwners() {
        List<SettlementButton> settlementButtons = gameTableController.getSettlementButtons();
        for (SettlementButton settlementButton : settlementButtons) {
            if (settlementButton.getOwner() != null && settlementButton.getOwner() != player) {
                settlementButton.setDisable(true);
            }
        }
    }

}
