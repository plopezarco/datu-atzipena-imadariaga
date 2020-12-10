package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Pablo
 */
/*
OHARRA:
1.- Exekutatu fitxategi hau, ez proiektua

2.-Track menua irekitzerakoan, track berriren sortuta badaukazu, hasieran ez da tableview-an agertuko. 
    Hala ere, tableview-aren zutabe baten ordena aldatzen baduzu, agertu egingo da.

3.-Track-ak sinplifikatzeko asmoz, track berri bat sortzerakoan hurrengo eremuetan balio hauek jarri ditut:
        MediaTypeId = 1;
        Milliseconds = 0;
        UnitPrice = 0;

Eremu hauek datu basean sartzea derrigorrezkoa da, baina nik ez ditudanez erabiliko erraztu egin dut.


 */
public class FXMain extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));

            Scene scene = new Scene(root);

            stage.getIcons().add(new Image("icon.png"));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Chinook Datubasea");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
