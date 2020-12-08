/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import model.Album;
import model.Eragiketak;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class AlbumController implements Initializable {

    @FXML
    private Label lbl_izenburua;

    @FXML
    private TableView<Album> tableview_album;

    @FXML
    private TableColumn<Album, Integer> albumIdCol;

    @FXML
    private TableColumn<Album, String> titleCol;

    @FXML
    private TableColumn<Album, String> artistNameCol;

    @FXML
    private HBox botoiak;

    @FXML
    private TextField addTitle;

    @FXML
    private TextField addArtistName;

    @FXML
    private Button btn_albumGehitu;

    @FXML
    private Button btn_albumEzabatu;

    @FXML
    private Button btn_albumArtistaBakoitzeko;

    private static ObservableList<Album> albumOL;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        albumOL = Eragiketak.albumakKargatu();

        tableview_album.setItems(albumOL);

        albumIdCol.setCellValueFactory(new PropertyValueFactory<Album, Integer>("albumId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Album, String>("title"));
        artistNameCol.setCellValueFactory(new PropertyValueFactory<Album, String>("artistName"));

        albumIdCol.setCellFactory(TextFieldTableCell.<Album, Integer>forTableColumn(new IntegerStringConverter()));
        albumIdCol.setEditable(false);

        titleCol.setCellFactory(TextFieldTableCell.<Album>forTableColumn());
        titleCol.setOnEditCommit((TableColumn.CellEditEvent<Album, String> t) -> {
            int zenbakia = ((Album) t.getTableView().getItems().get(t.getTablePosition().getRow())).getAlbumId();
            if (Eragiketak.albumaAldatu(zenbakia, t.getNewValue())) {
                ((Album) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle(t.getNewValue());
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Errore bat gertatu da");
                alert.setContentText("Errore ezezagun bat gertatu da.");
                alert.showAndWait();
            }
        });

        artistNameCol.setEditable(false);
    }

    @FXML
    private void albumaGehitu(ActionEvent event) {
        try {
            if (!addTitle.getText().equals("") && !addArtistName.getText().equals("")) {
                Album a = new Album(addTitle.getText(), addArtistName.getText());       //Album bat sortuko dugu Izenburu eta Artistaren Izenarekin
                if (Eragiketak.albumaGehitu(a)) {
                    albumOL = Eragiketak.albumakKargatu();
                    tableview_album.setItems(albumOL);
                    addTitle.setText("");
                    addArtistName.setText("");
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Errore bat gertatu da");
                    alert.setContentText("Artista hori ez da existitzen, saiatu berriz");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Kontuz!!");
                alert.setHeaderText("Eremu guztiak bete, mesedez");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore bat gertatu da");
            alert.setContentText("Errore ezezagun bat gertatu da.");
            alert.showAndWait();
        }
    }

    @FXML
    private void albumaEzabatu(ActionEvent event) {
        try {
            Album a = tableview_album.getSelectionModel().getSelectedItem();
            if (a != null) {
                if (Eragiketak.albumaEzabatu(a)) {
                    tableview_album.getItems().remove(a);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Errore bat gertatu da");
                    alert.setContentText("Ezin da erregistroa ezabatu.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Kontuz!!");
                alert.setHeaderText("Album bat aukeratu, mesedez.");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore bat gertatu da");
            alert.setContentText("Errore ezezagun bat gertatu da.");
            alert.showAndWait();
        }
    }

    @FXML
    private void trackAlbumBakoitzekoMenuIreki(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TrackAlbumBakoitzeko.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.getIcons().add(new Image("icon.png"));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Track Kopurua Album Bakoitzeko");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
