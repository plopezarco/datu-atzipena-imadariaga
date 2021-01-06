/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.uni.dam2.mongodbproiektua.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class LoginController implements Initializable {

    @FXML
    private Label lbl_title;
    @FXML
    private Button btn_cancel, btn_ok;
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_password, txt_password2;

    public static int operation;
    private static String user, password, password2;
    //1 login, 2 register

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        switch (operation) {
            case 1:
                lbl_title.setText("Login");
                txt_password2.setVisible(false);
                break;
            case 2:
                lbl_title.setText("Register");
                txt_password2.setVisible(true);
                break;
        }
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.fireEvent(
                new WindowEvent(
                        stage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }

    @FXML
    private void confirm() {
        switch (operation) {
            case 1:
                if (!txt_username.getText().trim().isEmpty() && !txt_password.getText().trim().isEmpty()) {
                    user = txt_username.getText();
                    password = txt_password.getText();
                    if (MongoDB.checkUser(user, password)) {
                        MainController.logged = true;
                        MainController.username = user;
                        cancel();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setHeaderText(null);
                        alert.setContentText("User or password are incorrect");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setHeaderText(null);
                    alert.setContentText("Fill all the empty fields");
                    alert.showAndWait();
                }
                break;
            case 2:
                if (!txt_username.getText().trim().isEmpty() && !txt_password.getText().trim().isEmpty() && !txt_password2.getText().trim().isEmpty()) {
                    user = txt_username.getText();
                    password = txt_password.getText();
                    password2 = txt_password2.getText();
                    if (password.equals(password2)) {
                        if (MongoDB.insertUser(user, password)) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Register");
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Registered");
                            alert.showAndWait();

                            MainController.logged = true;
                            MainController.username = user;
                            cancel();
                            break;
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Register");
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setHeaderText(null);
                            alert.setContentText("An error has occurred");
                            alert.showAndWait();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setHeaderText(null);
                    alert.setContentText("Fill all the empty fields");
                    alert.showAndWait();
                }
                break;
        }
    }
}
