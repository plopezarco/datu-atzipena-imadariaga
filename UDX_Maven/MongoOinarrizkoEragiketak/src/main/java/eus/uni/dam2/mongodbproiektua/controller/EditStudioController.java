package eus.uni.dam2.mongodbproiektua.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import eus.uni.dam2.mongodbproiektua.model.Studio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Pablo
 */
public class EditStudioController implements Initializable {

    @FXML
    private TextField txt_edit_name, txt_edit_year;
    @FXML
    private ImageView img_studio;
    @FXML
    private Label lbl_imagePath;
    @FXML
    private Button btn_edit_ok, btn_edit_cancel, btn_img;

    public static Studio studio;
    public static int operation;
    //1 bada editatzen ari gara
    //2 bada berri bat sortzen ari gara

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (operation == 1) {
            setStudioToEdit(studio);
        } else if (operation == 2) {
            studio = null;
        }
    }

    //Dagoeneko daukagun Studio bat editatu nahi badugu erakutsi egingo du 
    private void setStudioToEdit(Studio s) {
        String img = "/" + s.getImage();
        try {
            Image image = new Image(this.getClass().getResource(img).toString(), 350, 225, true, true);
            img_studio.setImage(image);
        } catch (Exception e) {
        }
        txt_edit_name.setText(s.getName());
        txt_edit_year.setText(String.valueOf(s.getFounded()));
        lbl_imagePath.setText(s.getImage());
    }

    //Leihotik Studioaren datuak jasotzeko
    private Studio getData() {
        try {
            Studio s = new Studio();
            if (!txt_edit_name.getText().trim().isEmpty()
                    && !txt_edit_year.getText().trim().isEmpty()
                    && !lbl_imagePath.getText().trim().isEmpty()) {
                s.setName(txt_edit_name.getText());
                s.setFounded(Integer.parseInt(txt_edit_year.getText()));
                s.setImage(lbl_imagePath.getText());
                return s;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.initStyle(StageStyle.UTILITY);
                alert.setHeaderText(null);
                alert.setContentText("Fill all the empty fields");
                alert.showAndWait();
                return null;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText(null);
            alert.setContentText("Fill Founded Year with numbers");
            alert.showAndWait();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @FXML
    private void confirm() {
        Studio s = getData();
        if (s != null) {
            EditController.studio = s;
            cancel();
        }
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) btn_edit_cancel.getScene().getWindow();
        stage.fireEvent(
                new WindowEvent(
                        stage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }

    @FXML
    private void imageChooser() {
        String filePath = "";
        String fileName = "";
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        fileChooser.setTitle("Choose a file");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePath = file.getPath();
            fileName = file.getName();
            if (filePath.toLowerCase().endsWith(".png") || filePath.toLowerCase().endsWith(".jpg")) {
                InputStream is = null;
                OutputStream os = null;
                try {
                    is = new FileInputStream(new File(filePath));
                    String outputPath = System.getProperty("user.dir") + "/src/main/resources/images/studio/" + fileName;
                    os = new FileOutputStream(new File(outputPath));

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                    is.close();
                    os.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
                }

                String relativePath = "images/studio/" + fileName;
                lbl_imagePath.setText(relativePath);
                try {
                    String img = "/" + relativePath;
                    Image image = new Image(this.getClass().getResource(img).toString(), 350, 225, true, true);
                    img_studio.setImage(image);
                } catch (Exception e) {
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.initStyle(StageStyle.UTILITY);
                alert.setHeaderText(null);
                alert.setContentText("Choose a picture, JPG or PNG");
                alert.showAndWait();
            }
        }
    }
}
