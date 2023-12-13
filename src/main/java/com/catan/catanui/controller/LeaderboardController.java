package com.catan.catanui.controller;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.entity.LeaderboardEntry;
import com.catan.catanui.service.LeaderboardService;
import com.catan.catanui.utils.Navigate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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

    public void weeklyButtonAction() {
        List<LeaderboardEntry> leaderboardEntries = leaderboardService.getWeeklyScores();

        leaderboardTable.getItems().clear();

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.getItems().addAll(leaderboardEntries);
    }

    public void monthlyButtonAction() {
        List<LeaderboardEntry> leaderboardEntries = leaderboardService.getMonthlyScores();

        leaderboardTable.getItems().clear();

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.getItems().addAll(leaderboardEntries);
    }

    public void alltimeButtonAction() {
        List<LeaderboardEntry> leaderboardEntries = leaderboardService.getAllTimeScores();

        leaderboardTable.getItems().clear();

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.getItems().addAll(leaderboardEntries);
    }

    public void backButtonAction() {
        Navigate.navigate((Stage) weeklyButton.getScene().getWindow(), getClass().getResource(Constant.MAIN));
    }
}
