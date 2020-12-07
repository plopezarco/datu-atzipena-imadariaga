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
import model.Genre;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class GenreController implements Initializable {

    @FXML
    private Label lbl_izenburua;

    @FXML
    private TableView<Genre> tableview_genre;

    @FXML
    private TableColumn<Genre, Integer> genreIdCol;

    @FXML
    public TableColumn<Genre, String> nameCol;

    @FXML
    private HBox botoiak;

    @FXML
    private TextField addName;

    @FXML
    private Button btn_genreGehitu;

    @FXML
    private Button btn_genreEzabatu;

    private static ObservableList<Genre> genreOL;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genreOL = Eragiketak.generoakKargatu();
        
        tableview_genre.setItems(genreOL);
        
        genreIdCol.setCellValueFactory(new PropertyValueFactory<Genre, Integer>("genreId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Genre, String>("name"));
        
        genreIdCol.setCellFactory(TextFieldTableCell.<Genre, Integer>forTableColumn(new IntegerStringConverter()));
        genreIdCol.setEditable(false);

        nameCol.setCellFactory(TextFieldTableCell.<Genre>forTableColumn());
        nameCol.setOnEditCommit((TableColumn.CellEditEvent<Genre, String> t) -> {
            int zenbakia = ((Genre) t.getTableView().getItems().get(t.getTablePosition().getRow())).getGenreId();
            if (Eragiketak.generoaAldatu(zenbakia, t.getNewValue())) {
                ((Genre) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
            } else {
                System.out.println("Errore bat gertatu da.");
            }
        });
    }   
    
     @FXML
    public void genreGehitu(ActionEvent event) {
        try {
            if (!addName.getText().equals("")) {
                Genre g = new Genre(addName.getText());
                if (Eragiketak.generoaGehitu(g)) {
                    genreOL = Eragiketak.artistakKargatu();
                    tableview_genre.setItems(genreOL);
                    addName.setText("");
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore bat gertatu da");
            alert.setContentText("Errore ezezagun bat gertatu da.");
            alert.showAndWait();
        }

    }

    @FXML
    public void genreEzabatu(ActionEvent event) {
        try {
            Genre g = tableview_genre.getSelectionModel().getSelectedItem();
            if (g != null) {
                if (Eragiketak.generoaEzabatu(g)) {
                    tableview_genre.getItems().remove(g);
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Errore bat gertatu da");
                    alert.setContentText("Ezin da erregistroa ezabatu.");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Kontuz!!");
                alert.setHeaderText("Genero bat aukeratu, mesedez.");
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
