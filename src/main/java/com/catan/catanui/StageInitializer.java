package com.catan.catanui;

import com.catan.catanui.constants.Constant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class StageInitializer implements ApplicationListener<ClientApplication.StageReadyEvent> {
    @Override
    public void onApplicationEvent(ClientApplication.StageReadyEvent event) {
        Stage stage = event.getStage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.GAME_TABLE));
            // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.LOGIN));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 1100, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}