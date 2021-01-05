/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.uni.dam2.mongodbproiektua.controller;

import eus.uni.dam2.mongodbproiektua.model.Studio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class StudioController implements Initializable {

    @FXML
    private ImageView img_studio;
    @FXML
    private Label lbl_studio_name;
    @FXML
    private Label lbl_founded;
    
    private static Studio studio = new Studio();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String img = "/" + studio.getImage();
        try {
            Image image = new Image(this.getClass().getResource(img).toString(), 222, 280, true, true);

            img_studio.setImage(image);
        } catch (Exception e) {
        }
        lbl_studio_name.setText(studio.getName());
        lbl_founded.setText(String.valueOf(studio.getFounded()));
    }

    public static void setStudio(Studio studio) {
        StudioController.studio = studio;
    }
    
}
