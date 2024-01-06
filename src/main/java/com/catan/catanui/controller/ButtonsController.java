package com.catan.catanui.controller;

import java.util.List;

import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.RoadButton;
import com.catan.catanui.entity.SettlementButton;

public class ButtonsController {
    private static ButtonsController instance;

    public static ButtonsController getInstance() {
        if (instance == null) {
            instance = new ButtonsController();
        }
        return instance;
    }

    public void enableAvailableRoadButtons(Player player) {
        List<RoadButton> availableRoadButtons = player.getAvaliableRoadButtons();
        for (RoadButton roadButton : availableRoadButtons) {
            roadButton.setDisable(false);
        }
    }


    public void disableAllRoadButtons() {
        List<RoadButton> roadButtons = GameTableController.getInstance().getRoadButtons();
        for (RoadButton roadButton : roadButtons) {
            roadButton.setDisable(true);
        }
    }

    public void disableAllSettlementButtons(){
        List<SettlementButton> settlementButtons = GameTableController.getInstance().getSettlementButtons();
        for (SettlementButton settlementButton : settlementButtons) {
            settlementButton.setDisable(true);
        }
    }

    public void enableAvailableSettlementButtons(Player player) {
        List<SettlementButton> availableSettlementButtons = player.getAvaliableSettlementButtons();
        for (SettlementButton settlementButton : availableSettlementButtons) {
            settlementButton.setDisable(false);
        }
    }

    public void enableUpgradableSettlementButtons(Player player){
        List<SettlementButton> availableSettlementButtons = player.getUpgradableSettlementButtons();
        for (SettlementButton settlementButton : availableSettlementButtons) {
            settlementButton.setDisable(false);
        }
    }


    public void enableAllGameTableButtons() {
        enableAllSettlementButtons();
        enableAllRoadButtons();
    }

    private void enableAllSettlementButtons() {
        List<SettlementButton> settlementButtons = GameTableController.getInstance().getSettlementButtons();
        for (SettlementButton settlementButton : settlementButtons) {
            settlementButton.setDisable(false);
        }
    }

    private void enableAllRoadButtons(){
        List<RoadButton> roadButtons = GameTableController.getInstance().getRoadButtons();
        for (RoadButton roadButton : roadButtons) {
            roadButton.setDisable(false);
        }
    }
}
