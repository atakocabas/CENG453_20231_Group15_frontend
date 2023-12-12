package com.catan.catanui;

import com.catan.catanui.constants.Constant;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.core.io.Resource;

import java.io.IOException;


@Component
public class StageInitializer implements ApplicationListener<ClientApplication.StageReadyEvent> {
    @Override
    public void onApplicationEvent(ClientApplication.StageReadyEvent event) {
        Stage stage = event.getStage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Constant.LOGIN));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 300, 275);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
