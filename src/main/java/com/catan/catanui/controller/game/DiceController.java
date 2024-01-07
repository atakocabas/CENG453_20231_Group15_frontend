// DiceController.java
package com.catan.catanui.controller.game;

import com.catan.catanui.controller.game.turn.PlayerTurnController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class DiceController {
    private static final Logger logger = LoggerFactory.getLogger(DiceController.class);

    private int diceTotal;

    private static DiceController instance;

    @FXML
    private Rectangle diceFirst;

    @FXML
    private Rectangle diceSecond;

    @FXML
    private Pane dice1Pane;

    @FXML
    private Pane dice2Pane;

    @FXML
    private Text totalText;

    @FXML
    private Text dot11;
    @FXML
    private Text dot12;
    @FXML
    private Text dot13;
    @FXML
    private Text dot14;
    @FXML
    private Text dot15;
    @FXML
    private Text dot16;
    @FXML
    private Text dot21;
    @FXML
    private Text dot22;
    @FXML
    private Text dot23;
    @FXML
    private Text dot24;
    @FXML
    private Text dot25;
    @FXML
    private Text dot26;

    /**
     * Returns the singleton instance of the DiceController.
     *
     * @return the singleton instance of the DiceController
     */
    public static DiceController getInstance() {
        if (instance == null) {
            instance = new DiceController();
        }
        return instance;
    }

    /**
     * Rolls the dice and updates the display.
     * If the dice is already rolled in this turn, the method returns without performing any action.
     * The dice values are simulated using random numbers between 1 and 6 (inclusive).
     * The display is updated based on the dice values.
     * If the total of the dice values is 7, no resources are distributed.
     * Otherwise, the total is displayed and each player's resources are updated accordingly.
     * Finally, the buttons for the current player's turn are enabled.
     */
    public void rollDice() {
        // check if the dice is already rolled in this turn
        if (!GameTableController.getInstance().getCurrentPlayerTurnController().rollDice())
            return;
        // Simulate rolling a dice and update the display
        Random random = new Random();
        int diceValue1 = random.nextInt(6) + 1; // Adding 1 to get values between 1 and 6
        int diceValue2 = random.nextInt(6) + 1;

        // Update diceFirst
        switch (diceValue1) {
            case 1:
                dot11.setX(19);
                dot11.setY(33);
                dot12.setX(19);
                dot12.setY(33);
                dot13.setX(19);
                dot13.setY(33);
                dot14.setX(19);
                dot14.setY(33);
                dot15.setX(19);
                dot15.setY(33);
                dot16.setX(19);
                dot16.setY(33);
                break;
            case 2:
                dot11.setX(20);
                dot11.setY(25);
                dot12.setX(20);
                dot12.setY(42);
                dot13.setX(20);
                dot13.setY(25);
                dot14.setX(20);
                dot14.setY(25);
                dot15.setX(20);
                dot15.setY(25);
                dot16.setX(20);
                dot16.setY(25);
                break;
            case 3:
                dot11.setX(10);
                dot11.setY(20);
                dot12.setX(20);
                dot12.setY(32);
                dot13.setX(30);
                dot13.setY(44);
                dot14.setX(30);
                dot14.setY(44);
                dot15.setX(30);
                dot15.setY(44);
                dot16.setX(30);
                dot16.setY(44);
                break;
            case 4:
                dot11.setX(11);
                dot11.setY(25);
                dot12.setX(29);
                dot12.setY(25);
                dot13.setX(11);
                dot13.setY(40);
                dot14.setX(29);
                dot14.setY(40);
                dot15.setX(29);
                dot15.setY(40);
                dot16.setX(29);
                dot16.setY(40);
                break;
            case 5:
                dot11.setX(10);
                dot11.setY(20);
                dot12.setX(30);
                dot12.setY(20);
                dot13.setX(20);
                dot13.setY(32);
                dot14.setX(10);
                dot14.setY(44);
                dot15.setX(30);
                dot15.setY(44);
                dot16.setX(30);
                dot16.setY(44);
                break;
            case 6:
                dot11.setX(10);
                dot11.setY(20);
                dot12.setX(30);
                dot12.setY(20);
                dot13.setX(10);
                dot13.setY(32);
                dot14.setX(30);
                dot14.setY(32);
                dot15.setX(10);
                dot15.setY(44);
                dot16.setX(30);
                dot16.setY(44);
                break;
        }

        // Update diceSecond
        switch (diceValue2) {
            case 1:
                dot21.setX(19);
                dot21.setY(33);
                dot22.setX(19);
                dot22.setY(33);
                dot23.setX(19);
                dot23.setY(33);
                dot24.setX(19);
                dot24.setY(33);
                dot25.setX(19);
                dot25.setY(33);
                dot26.setX(19);
                dot26.setY(33);
                break;
            case 2:
                dot21.setX(20);
                dot21.setY(25);
                dot22.setX(20);
                dot22.setY(42);
                dot23.setX(20);
                dot23.setY(25);
                dot24.setX(20);
                dot24.setY(25);
                dot25.setX(20);
                dot25.setY(25);
                dot26.setX(20);
                dot26.setY(25);
                break;
            case 3:
                dot21.setX(10);
                dot21.setY(20);
                dot22.setX(20);
                dot22.setY(32);
                dot23.setX(30);
                dot23.setY(44);
                dot24.setX(30);
                dot24.setY(44);
                dot25.setX(30);
                dot25.setY(44);
                dot26.setX(30);
                dot26.setY(44);
                break;
            case 4:
                dot21.setX(11);
                dot21.setY(25);
                dot22.setX(29);
                dot22.setY(25);
                dot23.setX(11);
                dot23.setY(40);
                dot24.setX(29);
                dot24.setY(40);
                dot25.setX(29);
                dot25.setY(40);
                dot26.setX(29);
                dot26.setY(40);
                break;
            case 5:
                dot11.setX(10);
                dot11.setY(20);
                dot12.setX(30);
                dot12.setY(20);
                dot13.setX(20);
                dot13.setY(32);
                dot14.setX(10);
                dot14.setY(44);
                dot15.setX(30);
                dot15.setY(44);
                dot16.setX(30);
                dot16.setY(44);
                break;
            case 6:
                dot21.setX(10);
                dot21.setY(20);
                dot22.setX(30);
                dot22.setY(20);
                dot23.setX(10);
                dot23.setY(32);
                dot24.setX(30);
                dot24.setY(32);
                dot25.setX(10);
                dot25.setY(44);
                dot26.setX(30);
                dot26.setY(44);
                break;
        }

        // Update the totalText
        int total = diceValue1 + diceValue2;
        this.diceTotal = total;

        if (total == 7) {
            logger.info("Dice total: " + total + " no resources are distributed");
            totalText.setText(String.valueOf(total));
        } else {
            logger.info("Dice total: " + total);
            totalText.setText(String.valueOf(total));
            List<PlayerTurnController> playerTurnControllers = GameTableController.getInstance()
                    .getPlayerTurnControllers();
            for (PlayerTurnController playerTurnController : playerTurnControllers) {
                playerTurnController.updateResources(total);
            }
        }
        GameTableController.getInstance().getCurrentPlayerTurnController().enableButtons();
    }

}
