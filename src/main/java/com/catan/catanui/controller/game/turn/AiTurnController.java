package com.catan.catanui.controller.game.turn;

import com.catan.catanui.controller.game.DiceController;
import com.catan.catanui.controller.game.GameTableController;
import com.catan.catanui.entity.Player;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class AiTurnController extends PlayerTurnController{

    public AiTurnController(Player player) {
        super(player);
    }

    public void startTurn() {
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
        return;
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

    public void disableButtons() {
        return;
    }

}
