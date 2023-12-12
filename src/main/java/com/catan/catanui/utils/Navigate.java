package com.catan.catanui.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;

public class Navigate {
    public static Scene navigate(Stage stage, URL resourceUrl) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 300, 275);
            stage.setScene(scene);
            stage.show();
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
