package com.catan.catanui.controller.game.turn;

import java.util.List;
import java.util.Random;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.controller.ButtonsController;
import com.catan.catanui.controller.game.DiceController;
import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.RoadButton;
import com.catan.catanui.entity.SettlementButton;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class AiTurnController extends PlayerTurnController{
    private static Random rand = new Random();

    public AiTurnController(Player player) {
        super(player);
    }

    public void startTurn() {
        ButtonsController.getInstance().enableAllGameTableButtons();
        updateButtons();
        DiceController.getInstance().rollDice();
        decideBuildRoads();
        decideBuildSettlements();
        decideBuildCities();
        if(player.getTotalPoints() >= Constant.GAME_END_POINTS) {
            GameTableController.getInstance().endGame();
            return;
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            GameTableController.getInstance().endTurn();
        });
        pause.play();
    }

    private void decideBuildRoads() {
        List<RoadButton> avaliableRoadButtons = player.getAvaliableRoadButtons();
        int randomIndex = rand.nextInt(avaliableRoadButtons.size());
        RoadButton roadButton = avaliableRoadButtons.get(randomIndex);
        roadButton.buildTurns(player);
    }

    private void decideBuildSettlements() {
        List<SettlementButton> avaliableSettlementButtons = player.getAvaliableSettlementButtons();
        if(avaliableSettlementButtons.isEmpty())
            return;
        int randomIndex = rand.nextInt(avaliableSettlementButtons.size());
        SettlementButton settlementButton = avaliableSettlementButtons.get(randomIndex);
        settlementButton.buildTurns(player);
    }

    private void decideBuildCities() {
        List<SettlementButton> availableCities = player.getUpgradableSettlementButtons();
        if(availableCities.isEmpty())
            return;
        int randomIndex = rand.nextInt(availableCities.size());
        SettlementButton settlementButton = availableCities.get(randomIndex);
        settlementButton.upgrade(player);
    }

    public void enableButtons() {
        return;
    }

}
