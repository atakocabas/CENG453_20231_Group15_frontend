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

/**
 * The AiTurnController class represents a controller for managing the turn of an AI player in the game.
 * It extends the PlayerTurnController class and provides methods for making decisions on building roads, settlements, and cities.
 */
public class AiTurnController extends PlayerTurnController{
    private static Random rand = new Random();

    public AiTurnController(Player player) {
        super(player);
    }

    /**
     * Starts the turn for the AI player.
     * Enables all game table buttons, updates the buttons, rolls the dice,
     * decides to build roads, settlements, and cities.
     * If the player's total points reach the game end points constant,
     * ends the game.
     * Pauses for 2 seconds before ending the turn.
     */
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

    /**
     * Makes a decision on building roads for the AI player.
     * Selects a random road button from the available road buttons and builds it.
     */
    private void decideBuildRoads() {
        List<RoadButton> avaliableRoadButtons = player.getAvaliableRoadButtons();
        int randomIndex = rand.nextInt(avaliableRoadButtons.size());
        RoadButton roadButton = avaliableRoadButtons.get(randomIndex);
        roadButton.buildTurns(player);
    }

    /**
     * Decides and builds settlements for the AI player.
     */
    private void decideBuildSettlements() {
        List<SettlementButton> avaliableSettlementButtons = player.getAvaliableSettlementButtons();
        if(avaliableSettlementButtons.isEmpty())
            return;
        int randomIndex = rand.nextInt(avaliableSettlementButtons.size());
        SettlementButton settlementButton = avaliableSettlementButtons.get(randomIndex);
        settlementButton.buildTurns(player);
    }

    /**
     * Makes a decision to build cities for the AI player.
     * It selects a random settlement button from the list of available cities
     * and upgrades it to a city for the player.
     */
    private void decideBuildCities() {
        List<SettlementButton> availableCities = player.getUpgradableSettlementButtons();
        if(availableCities.isEmpty())
            return;
        int randomIndex = rand.nextInt(availableCities.size());
        SettlementButton settlementButton = availableCities.get(randomIndex);
        settlementButton.upgrade(player);
    }

    public void enableButtons() {
    }

}
