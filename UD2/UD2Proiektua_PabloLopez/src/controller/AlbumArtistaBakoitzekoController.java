/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import model.AlbumArtist;
import model.Eragiketak;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class AlbumArtistaBakoitzekoController implements Initializable {

    @FXML
    private TableView<AlbumArtist> tableview_albumArtist;

    @FXML
    private TableColumn<AlbumArtist, String> artistNameCol;

    @FXML
    private TableColumn<AlbumArtist, Integer> albumKopCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<AlbumArtist> albumArtistOL = Eragiketak.albumArtistKargatu();

        tableview_albumArtist.setItems(albumArtistOL);

        artistNameCol.setCellValueFactory(new PropertyValueFactory<AlbumArtist, String>("artistName"));
        albumKopCol.setCellValueFactory(new PropertyValueFactory<AlbumArtist, Integer>("albumKop"));

        artistNameCol.setEditable(false);
        
        albumKopCol.setCellFactory(TextFieldTableCell.<AlbumArtist, Integer>forTableColumn(new IntegerStringConverter()));
        albumKopCol.setEditable(false);
    }
}