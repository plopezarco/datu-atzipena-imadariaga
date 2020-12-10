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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import model.Eragiketak;
import model.TrackAlbum;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class TrackAlbumBakoitzekoController implements Initializable {

     @FXML
    private TableView<TrackAlbum> tableview_trackAlbum;

    @FXML
    private TableColumn<TrackAlbum, String> albumTitleCol;

    @FXML
    private TableColumn<TrackAlbum, Integer> trackKopCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<TrackAlbum> trackAlbumOL = Eragiketak.trackAlbumKargatu();

        tableview_trackAlbum.setItems(trackAlbumOL);

        albumTitleCol.setCellValueFactory(new PropertyValueFactory<TrackAlbum, String>("albumTitle"));
        trackKopCol.setCellValueFactory(new PropertyValueFactory<TrackAlbum, Integer>("trackKop"));

        albumTitleCol.setEditable(false);
        
        trackKopCol.setCellFactory(TextFieldTableCell.<TrackAlbum, Integer>forTableColumn(new IntegerStringConverter()));
        trackKopCol.setEditable(false);
    } 
    
}
