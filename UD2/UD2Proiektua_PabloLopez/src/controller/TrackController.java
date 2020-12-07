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
    public TableColumn<Track, String> nameCol;

    @FXML
    public TableColumn<Track, String> albumNameCol;

    @FXML
    public TableColumn<Track, String> genreNameCol;

    @FXML
    private HBox botoiak;

    @FXML
    private TextField addName;

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
    }

}
