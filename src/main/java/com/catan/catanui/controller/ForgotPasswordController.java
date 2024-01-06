package com.catan.catanui.controller;

import com.catan.catanui.constants.Constant;
import com.catan.catanui.service.ForgotPasswordService;
import com.catan.catanui.utils.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

@Controller
public class ForgotPasswordController {
    @FXML
    public TextField usernameField;
    @FXML
    public Button backButton;
    @FXML
    public Button submitButton;

    ForgotPasswordService forgotPasswordService = new ForgotPasswordService();

    public void handleBackButtonAction(ActionEvent actionEvent) {
        Utility.navigate((Stage) usernameField.getScene().getWindow(), getClass().getResource(Constant.LOGIN));
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        String username = usernameField.getText();
        if(forgotPasswordService.resetPassword(username)){
            Utility.navigate((Stage) usernameField.getScene().getWindow(), getClass().getResource(Constant.LOGIN));
        }
    }
}
