/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author root
 */
public class KalkulagailuaController implements Initializable {
    
    @FXML
    private Label batuketa, biderketa;
    
    @FXML
    private TextField batugai1,batugai2,biderkagai1,biderkagai2;
    
    @FXML
    private void handleBatuAction(ActionEvent event) {
        System.out.println("Batuketa egin dugu.");
        batuketa.setText(""+(Double.parseDouble(batugai1.getText())+Double.parseDouble(batugai2.getText())));
    }

    @FXML
    private void handleBiderkatuAction(ActionEvent event) {
        System.out.println("Biderketa egin dugu.");
        biderketa.setText(""+(Double.parseDouble(biderkagai1.getText()) * Double.parseDouble(biderkagai2.getText())));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
