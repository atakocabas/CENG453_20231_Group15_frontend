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
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            GameTableController.getInstance().endTurn();
        });
        pause.play();
    }

    public void enableEndTurnButton() {
        return;
    }

//    public void diceRolled(int diceTotal) {
//        throw new UnsupportedOperationException("Unimplemented method 'diceRolled'");
//    }
}
