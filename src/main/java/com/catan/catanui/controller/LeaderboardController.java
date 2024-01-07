package com.catan.catanui.controller;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.entity.LeaderboardEntry;
import com.catan.catanui.service.LeaderboardService;
import com.catan.catanui.utils.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

/**
 * The LeaderboardController class is responsible for managing the leaderboard UI and its actions.
 */
public class LeaderboardController {


    @FXML
    public Button weeklyButton;
    @FXML
    public Button monthlyButton;
    @FXML
    public Button alltimeButton;
    @FXML
    public TableView<LeaderboardEntry> leaderboardTable;
    @FXML
    public TableColumn<LeaderboardEntry, String> usernameColumn;
    @FXML
    public TableColumn<LeaderboardEntry, Long> scoreColumn;
    LeaderboardService leaderboardService = new LeaderboardService();

    /**
     * Retrieves the weekly scores from the leaderboard service and updates the leaderboard table accordingly.
     */
    public void weeklyButtonAction() {
        List<LeaderboardEntry> leaderboardEntries = leaderboardService.getWeeklyScores();

        leaderboardTable.getItems().clear();

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.getItems().addAll(leaderboardEntries);
    }

    /**
     * Retrieves the monthly scores from the leaderboard service and updates the leaderboard table.
     */
    public void monthlyButtonAction() {
        List<LeaderboardEntry> leaderboardEntries = leaderboardService.getMonthlyScores();

        leaderboardTable.getItems().clear();

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.getItems().addAll(leaderboardEntries);
    }

    /**
     * Retrieves all-time scores from the leaderboard service and updates the leaderboard table accordingly.
     */
    public void alltimeButtonAction() {
        List<LeaderboardEntry> leaderboardEntries = leaderboardService.getAllTimeScores();

        leaderboardTable.getItems().clear();

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.getItems().addAll(leaderboardEntries);
    }

    /**
     * Performs the action when the back button is clicked.
     * Navigates to the main screen.
     */
    public void backButtonAction() {
        Utility.navigate((Stage) weeklyButton.getScene().getWindow(), getClass().getResource(Constant.MAIN));
    }
}
