/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;
import model.Eragiketak;
import model.Track;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class TrackController implements Initializable {

    @FXML
    private Label izenburua;

    @FXML
    private TableView<Track> tableview_track;

    @FXML
    private TableColumn<Track, Integer> trackIdCol;

    @FXML
    private TableColumn<Track, String> nameCol;

    @FXML
    private TableColumn<Track, String> albumNameCol;

    @FXML
    private TableColumn<Track, String> genreNameCol;

    @FXML
    private HBox botoiak;

    @FXML
    private TextField addName;

    @FXML
    private TextField addAlbumName;

    @FXML
    private TextField addGenreName;

    @FXML
    private Button btn_trackGehitu;

    @FXML
    private Button btn_trackEzabatu;

    private static ObservableList<Track> trackOL;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trackOL = Eragiketak.trackakKargatu();

        tableview_track.setItems(trackOL);

        trackIdCol.setCellValueFactory(new PropertyValueFactory<Track, Integer>("trackId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Track, String>("name"));
        albumNameCol.setCellValueFactory(new PropertyValueFactory<Track, String>("albumName"));
        genreNameCol.setCellValueFactory(new PropertyValueFactory<Track, String>("genreName"));

        trackIdCol.setCellFactory(TextFieldTableCell.<Track, Integer>forTableColumn(new IntegerStringConverter()));
        trackIdCol.setEditable(false);

        nameCol.setCellFactory(TextFieldTableCell.<Track>forTableColumn());
        nameCol.setOnEditCommit((TableColumn.CellEditEvent<Track, String> t) -> {
            int zenbakia = ((Track) t.getTableView().getItems().get(t.getTablePosition().getRow())).getTrackId();
            if (Eragiketak.trackaAldatu(zenbakia, t.getNewValue())) {
                ((Track) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
            } else {
                System.out.println("Errore bat gertatu da.");
            }
        });

        albumNameCol.setEditable(false);
        genreNameCol.setEditable(false);

    }

    @FXML
    private void trackaGehitu(ActionEvent event) {
        try {
            if (!addName.getText().equals("") && !addAlbumName.getText().equals("") && !addGenreName.getText().equals("")) {
                Track t = new Track(addName.getText(), addAlbumName.getText(), addGenreName.getText());       //Track bat sortuko dugu izen, album eta generoarekin
                if (Eragiketak.trackaGehitu(t)) {
                    trackOL = Eragiketak.trackakKargatu();
                    tableview_track.setItems(trackOL);
                    addName.setText("");
                    addAlbumName.setText("");
                    addGenreName.setText("");
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Errore bat gertatu da");
                    alert.setContentText("Album edo Genero hori ez da existitzen, saiatu berriz");
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
            Track t = tableview_track.getSelectionModel().getSelectedItem();
            if (t != null) {
                if (Eragiketak.trackaEzabatu(t)) {
                    tableview_track.getItems().remove(t);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Errore bat gertatu da");
                    alert.setContentText("Ezin da erregistroa ezabatu.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Kontuz!!");
                alert.setHeaderText("Track bat aukeratu, mesedez.");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore bat gertatu da");
            alert.setContentText("Errore ezezagun bat gertatu da.");
            alert.showAndWait();
        }
    }

}
