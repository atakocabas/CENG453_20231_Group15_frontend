package com.catan.catanui.controller;

import java.util.List;

import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.RoadButton;
import com.catan.catanui.entity.SettlementButton;

/**
 * The ButtonsController class is responsible for managing the enable/disable state of various buttons in the UI.
 */
public class ButtonsController {
    private static ButtonsController instance;

    /**
     * The ButtonsController class is responsible for managing the buttons in the user interface.
     * It follows the Singleton design pattern to ensure that only one instance of the class is created.
     */
    public static ButtonsController getInstance() {
        if (instance == null) {
            instance = new ButtonsController();
        }
        return instance;
    }

    /**
     * Enables the available road buttons for a given player.
     * 
     * @param player the player for whom to enable the road buttons
     */
    public void enableAvailableRoadButtons(Player player) {
        List<RoadButton> availableRoadButtons = player.getAvaliableRoadButtons();
        for (RoadButton roadButton : availableRoadButtons) {
            roadButton.setDisable(false);
        }
    }


    /**
     * Disables all road buttons in the game.
     */
    public void disableAllRoadButtons() {
        List<RoadButton> roadButtons = GameTableController.getInstance().getRoadButtons();
        for (RoadButton roadButton : roadButtons) {
            roadButton.setDisable(true);
        }
    }

    /**
     * Disables all settlement buttons.
     */
    public void disableAllSettlementButtons(){
        List<SettlementButton> settlementButtons = GameTableController.getInstance().getSettlementButtons();
        for (SettlementButton settlementButton : settlementButtons) {
            settlementButton.setDisable(true);
        }
    }

    /**
     * Enables the available settlement buttons for a given player.
     * 
     * @param player the player for whom to enable the settlement buttons
     */
    public void enableAvailableSettlementButtons(Player player) {
        List<SettlementButton> availableSettlementButtons = player.getAvaliableSettlementButtons();
        for (SettlementButton settlementButton : availableSettlementButtons) {
            settlementButton.setDisable(false);
        }
    }

    /**
     * Enables the upgradable settlement buttons for the specified player.
     * 
     * @param player the player for whom to enable the buttons
     */
    public void enableUpgradableSettlementButtons(Player player){
        List<SettlementButton> availableSettlementButtons = player.getUpgradableSettlementButtons();
        for (SettlementButton settlementButton : availableSettlementButtons) {
            settlementButton.setDisable(false);
        }
    }


    /**
     * Enables all game table buttons.
     * This method enables all settlement buttons and road buttons on the game table.
     */
    public void enableAllGameTableButtons() {
        enableAllSettlementButtons();
        enableAllRoadButtons();
    }

    /**
     * Enables all settlement buttons.
     */
    private void enableAllSettlementButtons() {
        List<SettlementButton> settlementButtons = GameTableController.getInstance().getSettlementButtons();
        for (SettlementButton settlementButton : settlementButtons) {
            settlementButton.setDisable(false);
        }
    }

    /**
     * Enables all road buttons in the game.
     */
    private void enableAllRoadButtons(){
        List<RoadButton> roadButtons = GameTableController.getInstance().getRoadButtons();
        for (RoadButton roadButton : roadButtons) {
            roadButton.setDisable(false);
        }
    }
}
