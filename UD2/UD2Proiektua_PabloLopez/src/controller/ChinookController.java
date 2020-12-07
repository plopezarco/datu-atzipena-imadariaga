package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class ChinookController implements Initializable {

    @FXML
    private Label lbl_izenburua;
    @FXML
    private Button btn_artist;
    @FXML
    private Button btn_album;
    @FXML
    private Button btn_genre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void artistMenuIreki(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Artist.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Artistak");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void albumMenuIreki(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Album.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Albumak");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     @FXML
    private void genreMenuIreki(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Genre.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Generoak");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     @FXML
    private void trackMenuIreki(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Track.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Trackak");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}