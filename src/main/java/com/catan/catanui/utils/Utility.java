package com.catan.catanui.utils;

import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Utility {
    private Utility() {
    }
    public static void navigate(Stage stage, URL resourceUrl) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 300, 275);
            stage.setScene(scene);
            stage.show();
        } catch (LoadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
