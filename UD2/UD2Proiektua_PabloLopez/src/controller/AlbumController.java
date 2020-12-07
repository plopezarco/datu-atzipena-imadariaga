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
    public TableColumn<Album, String> titleCol;

    @FXML
    public TableColumn<Album, Integer> artistIdCol;

    @FXML
    private HBox botoiak;

    @FXML
    private TextField addTitle;

    @FXML
    private TextField addArtistIdCol;

    @FXML
    private Button btn_albumGehitu;

    @FXML
    private Button btn_albumEzabatu;

    private static ObservableList<Album> albumOL;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        albumOL = Eragiketak.albumakKargatu();

        tableview_album.setItems(albumOL);

        albumIdCol.setCellValueFactory(new PropertyValueFactory<Album, Integer>("albumId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Album, String>("title"));
        artistIdCol.setCellValueFactory(new PropertyValueFactory<Album, Integer>("artistId"));

        albumIdCol.setCellFactory(TextFieldTableCell.<Album, Integer>forTableColumn(new IntegerStringConverter()));
        albumIdCol.setEditable(false);

        titleCol.setCellFactory(TextFieldTableCell.<Album>forTableColumn());
        titleCol.setOnEditCommit((TableColumn.CellEditEvent<Album, String> t) -> {
            int zenbakia = ((Album) t.getTableView().getItems().get(t.getTablePosition().getRow())).getAlbumId();
            if (Eragiketak.albumaAldatu(zenbakia, "title", t.getNewValue())) {
                ((Album) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle(t.getNewValue());
            } else {
                System.out.println("Errore bat gertatu da.");
            }
        });

       artistIdCol.setCellFactory(TextFieldTableCell.<Album, Integer>forTableColumn(new IntegerStringConverter()));
        artistIdCol.setOnEditCommit((TableColumn.CellEditEvent<Album, Integer> t) -> {
            try {
                int zenbakia = ((Album) t.getTableView().getItems().get(t.getTablePosition().getRow())).getAlbumId();
                if (Eragiketak.albumaAldatu(zenbakia, "artistId", t.getNewValue().toString())) {
                    ((Album) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTitle(t.getNewValue().toString());
                } else {
                    System.out.println("Errore bat gertatu da.");
                }
            } catch (NumberFormatException nfe) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Errore Dialogoa");
                alert.setHeaderText("Errore bat gertatu da");
                alert.setContentText("Saiatu berriz, zenbaki bat idatzi behar duzu");
                alert.showAndWait();
            }
        });
    }

    @FXML
    public void albumaGehitu(ActionEvent event) {
        try {
            if (!addTitle.getText().equals("") && !addArtistIdCol.getText().equals("")) {
                Album a = new Album(addTitle.getText(), Integer.parseInt(addArtistIdCol.getText()));
                if (Eragiketak.albumGehitu(a)) {
                    albumOL = Eragiketak.albumakKargatu();
                    tableview_album.setItems(albumOL);
                    addTitle.setText("");
                    addArtistIdCol.setText("");
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Errore bat gertatu da");
                    alert.setContentText("Errore ezezagun bat gertatu da.");
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
    public void albumaEzabatu(ActionEvent event) {
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
            }else {
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
}
