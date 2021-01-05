/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.uni.dam2.mongodbproiektua.controller;

import eus.uni.dam2.mongodbproiektua.model.Anime;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane ap_main;
    @FXML
    private ImageView img_anime, img_edit_anime;
    @FXML
    private Label lbl_name, lbl_rating, lbl_episodes, lbl_studio, lbl_description, lbl_categories, lbl_animeNumber, lbl_imagePath, lbl_welcome;
    @FXML
    private Pane pane_login, pane_guest;
    @FXML
    private Button btn_edit, btn_delete, btn_new, btn_lehenengoa, btn_aurrekoa, btn_hurrengoa, btn_azkena, btn_img, btn_edit_ok, btn_edit_cancel, btn_login, btn_register, btn_logout;
    @FXML
    private TextField txt_title, txt_rating, txt_episodes;
    @FXML
    private TextArea txt_categories, txt_description;

    private ArrayList<Anime> animes = new ArrayList<>();
    private static int animeNumber = 0;
    public static boolean logged = false;
    public static String username;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginChange();
        if (animes.isEmpty() && ap_main != null) {
            animes = MongoDB.getAnimeArrayList();
            this.setAnime(animeNumber);
            lbl_description.setWrapText(true);
            lbl_studio.setOnMouseClicked(e -> {
                this.dialogStudio();
            });
        }
    }

    private void setAnime(int animeIndex) {
        Anime anime = animes.get(animeIndex);
        String img = "/" + anime.getImg();
        try {
            Image image = new Image(this.getClass().getResource(img).toString(), 350, 225, true, true);
            img_anime.setImage(image);
        } catch (Exception e) {
        }
        lbl_name.setText(anime.getName());
        lbl_rating.setText(String.valueOf(anime.getRating()));
        lbl_episodes.setText(String.valueOf(anime.getEpisodes()));
        try {
            lbl_studio.setText(anime.getStudio().getName());
        } catch (Exception e) {
        }
        lbl_description.setText(anime.getDescription());
        lbl_categories.setText(Anime.listToString(anime.getCategories()));
        lbl_animeNumber.setText(animeNumber + 1 + " of " + animes.size());
    }

    private void dialogStudio() {
        StudioController.setStudio(animes.get(animeNumber).getStudio());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Studio.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Studio");
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateList() {
        animes.clear();
        animes = MongoDB.getAnimeArrayList();
        try {
            this.setAnime(animeNumber);
        } catch (IndexOutOfBoundsException e) {
            animeNumber = animes.size() - 1;
            this.setAnime(animeNumber);
        }
    }

    @FXML
    private void login() {
        LoginController.operation = 1;
        loadLogin();
    }

    @FXML
    private void register() {
        LoginController.operation = 2;
        loadLogin();
    }

    @FXML
    private void logout() {
        logged = false;
        loginChange();
    }

    private void loadLogin() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            loginChange();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loginChange() {
        if (logged) {
            pane_login.setVisible(true);
            pane_guest.setVisible(false);
            lbl_welcome.setText("Welcome " + username + "!");
            btn_logout.setVisible(true);
        } else {
            pane_login.setVisible(false);
            pane_guest.setVisible(true);
            lbl_welcome.setText("");
            btn_logout.setVisible(false);
        }
    }

    @FXML
    private void editAnime() {
        EditController.operation = 1;
        EditController.editAnime = animes.get(animeNumber);
        loadEditAdd();
    }

    @FXML
    private void addAnime() {
        EditController.operation = 2;
        loadEditAdd();
    }

    private void loadEditAdd() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Edit.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            updateList();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteAnime() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Anime");
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(null);
        alert.setContentText("Do you want to delete " + animes.get(animeNumber).getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            MongoDB.deleteAnime(animes.get(animeNumber));
            updateList();
        } else {
            alert.close();
        }
    }

    @FXML
    private void firstAnime(ActionEvent event) {
        if (animeNumber > 0) {
            animeNumber = 0;
            setAnime(animeNumber);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.setContentText("This is the last one...");
            alert.showAndWait();
        }
    }

    @FXML
    private void previousAnime(ActionEvent event) {
        if (animeNumber - 1 >= 0) {
            animeNumber--;
            setAnime(animeNumber);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.setContentText("This is the last one...");
            alert.showAndWait();
        }
    }

    @FXML
    private void nextAnime(ActionEvent event) {
        if (animeNumber + 1 <= animes.size() - 1) {
            animeNumber++;
            setAnime(animeNumber);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.setContentText("This is the last one...");
            alert.showAndWait();
        }
    }

    @FXML
    private void lastAnime(ActionEvent event) {
        if (animeNumber < animes.size() - 1) {
            animeNumber = animes.size() - 1;
            setAnime(animeNumber);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("This is the last one...");
            alert.showAndWait();
        }
    }

}
