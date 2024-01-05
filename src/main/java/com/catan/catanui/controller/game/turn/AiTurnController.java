package com.catan.catanui.controller.game.turn;

import java.util.List;
import java.util.Random;

import com.catan.catanui.controller.game.DiceController;
import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.entity.Player;
import com.catan.catanui.entity.RoadButton;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class AiTurnController extends PlayerTurnController{

    public AiTurnController(Player player) {
        super(player);
    }

    public void startTurn() {
        enableAllGameTableButtons();
        disableButtons();
        DiceController.getInstance().rollDice();
        decideBuildRoads();
        decideBuildSettlements();
        decideBuildCities();
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            GameTableController.getInstance().endTurn();
        });
        pause.play();
    }

    private void decideBuildRoads() {
        List<RoadButton> avaliableRoadButtons = player.getAvaliableRoadButtons();
        Random rand = new Random();
        int randomIndex = rand.nextInt(avaliableRoadButtons.size());
        RoadButton roadButton = avaliableRoadButtons.get(randomIndex);
        roadButton.build(player);
    }

    private void decideBuildSettlements() {
        return;
    }

    private void decideBuildCities() {
        return;
    }

    public void enableEndTurnButton() {
        return;
    }

}
