/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.uni.dam2.mongodbproiektua.controller;

import eus.uni.dam2.mongodbproiektua.model.Anime;
import eus.uni.dam2.mongodbproiektua.model.Studio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Pablo
 */
public class EditController implements Initializable {

    @FXML
    private ImageView img_edit_anime;
    @FXML
    private TextField txt_title, txt_rating, txt_episodes;
    @FXML
    private Label lbl_studio, lbl_imagePath;
    @FXML
    private TextArea txt_description, txt_categories;
    @FXML
    private Button btn_img, btn_edit_cancel, btn_edit_ok;

    //Leiho hau zabaltzerakoan anime eta studio bat izango ditugu
    public static Anime editAnime;
    public static Studio studio = null;
    public static int operation;
    //1 bada editatzen ari gara
    //2 bada berri bat sortzen ari gara

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_description.setWrapText(true);
        txt_categories.setWrapText(true);
        if (operation == 1) {
            setAnimeToEdit(editAnime);
            lbl_studio.setOnMouseClicked(e -> {
                this.dialogAddEditStudio();
            });
        } else if (operation == 2) {
            studio = null;
            lbl_studio.setOnMouseClicked(e -> {
                this.dialogAddEditStudio();
            });
        }
    }

    //Dagoeneko daukagun Anime bat editatu nahi badugu erakutsi egingo du
    private void setAnimeToEdit(Anime a) {
        String img = "/" + a.getImg();
        try {
            Image image = new Image(this.getClass().getResource(img).toString(), 350, 225, true, true);
            img_edit_anime.setImage(image);
        } catch (Exception e) {
        }
        txt_title.setText(a.getName());
        txt_rating.setText(String.valueOf(a.getRating()));
        txt_episodes.setText(String.valueOf(a.getEpisodes()));
        lbl_studio.setText(a.getStudio().getName());
        studio = a.getStudio();
        txt_description.setText(a.getDescription());
        lbl_imagePath.setText(a.getImg());
        String categories = "";
        for (String category : a.getCategories()) {
            categories += category + "\n";
        }
        txt_categories.setText(categories);
    }

    //Studio bat editatu edo sortu behar badugu zabalduko den dialogoa
    private void dialogAddEditStudio() {
        if (studio == null) {
            EditStudioController.operation = 2;
        } else {
            EditStudioController.operation = 1;
            EditStudioController.studio = studio;
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/EditStudio.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Studio");
            stage.showAndWait();
            lbl_studio.setText(studio.getName());
        } catch (Exception e) {
        }
    }

    //Leihotik datuak jasotzeko
    private Anime getData() {
        try {
            Anime a = new Anime();
            if (!txt_title.getText().trim().isEmpty()
                    && !txt_rating.getText().trim().isEmpty()
                    && !txt_episodes.getText().trim().isEmpty()
                    && !txt_description.getText().trim().isEmpty()
                    && !txt_categories.getText().trim().isEmpty()
                    && !lbl_imagePath.getText().trim().isEmpty()
                    && studio != null) {
                if (Double.parseDouble(txt_rating.getText()) >= 0 && Double.parseDouble(txt_rating.getText()) <= 10) {
                    a.setName(txt_title.getText());
                    a.setRating(Double.parseDouble(txt_rating.getText()));
                    a.setEpisodes(Integer.parseInt(txt_episodes.getText()));
                    a.setDescription(txt_description.getText());
                    a.setCategories(getCategoriesfromString(txt_categories.getText()));
                    a.setImg(lbl_imagePath.getText());
                    a.setStudio(studio);
                    return a;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setHeaderText(null);
                    alert.setContentText("Rating has to be between 0 and 10, and use . for decimals");
                    alert.showAndWait();
                    return null;
                }
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
            alert.setContentText("Fill Rating and Episodes with numbers");
            alert.showAndWait();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    //Kategoriak jasotzeko
    private List<String> getCategoriesfromString(String categories) {
        List<String> listCategories = new ArrayList<>();
        String[] parts = categories.split("\n");
        listCategories.addAll(Arrays.asList(parts));
        return listCategories;
    }

    @FXML
    private void confirm() {
        switch (operation) {
            case 1:
                Anime updatedAnime = getData();
                if (updatedAnime != null) {
                    MongoDB.updateAnime(updatedAnime);
                    cancel();
                }
                break;
            case 2:
                Anime newAnime = getData();
                if (newAnime != null) {
                    MongoDB.insertAnime(newAnime);
                    cancel();
                }
                break;
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
                    String outputPath = System.getProperty("user.dir") + "/src/main/resources/images/" + fileName;
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
                String relativePath = "images/" + fileName;
                lbl_imagePath.setText(relativePath);
                String img = "/" + relativePath;
                try {
                    Image image = new Image(this.getClass().getResource(img).toString(), 350, 225, true, true);
                    img_edit_anime.setImage(image);
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
