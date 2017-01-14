package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.Debug;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    @FXML
    private Pane contain1;
    @FXML
    private Pane contain2;
    @FXML
    private Menu menuMap;
    @FXML
    private Menu menuStories;
    @FXML
    private Menu menuCharacter;
    @FXML
    private Menu menuImages;
    @FXML
    private Menu menuItems;
    @FXML
    private ListView list_map;
    @FXML
    private ListView list_characters;
    @FXML
    private ListView list_images;
    @FXML
    private ListView list_stories;
    @FXML
    private ListView list_items;


    public String[] list;
    public static Stage primaryStage;
    public static Parent root;

    @Override
    public void start(Stage primaryStage1) throws Exception {

        root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage1.setTitle("PackageCreator");
        primaryStage1.setScene(new Scene(root, 600, 500, Color.BLACK));
        primaryStage1.show();

        primaryStage = primaryStage1;

        /*final ContextMenu contextMenu = new ContextMenu();
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        contextMenu.getItems().addAll(cut, copy, paste);
        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Cut...");
            }
        });

        list_map.setContextMenu(contextMenu);*/
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void onRightClickDeleteElement() {

    }

    public void onActionMenuAddMap() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setTitle("Add Map");
        dialog.setHeaderText("Ajouter une map en cliquant sur parcourir");
        dialog.setContentText("Cliquez:");
        ButtonType parcourir = new ButtonType("Parcourir");
        ButtonType close = new ButtonType("Close");
        dialog.getButtonTypes().setAll(parcourir, close);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == parcourir) {
            File file = fileChooser.showOpenDialog(primaryStage);
            String fileName = file.getName();
            if (fileName.endsWith(".tmtx")) {
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuMap.getText().toLowerCase() + "/");
                this.list_map.getItems().add(fileName.substring(0, fileName.length() - 5));
                // ... user chose OK
            } else {
                System.out.println("JAVSVSVSVSV");
                Alert else_dialog = new Alert(AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur, mauvais format de fichier");
                else_dialog.setContentText("Seuls les fichiers Tiled sont acceptés : fichiers d'extension .tmtx");
                else_dialog.showAndWait();
            }
        }
        else {
            //user chose close
        }
    }

    public void onActionMenuAddCharacters() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setTitle("Add Characters");
        dialog.setHeaderText("Ajouter un personnage en cliquant sur parcourir");
        dialog.setContentText("Rappel : Fichiers d'extension json pour ce type d'élement");
        ButtonType parcourir = new ButtonType("Parcourir");
        ButtonType close = new ButtonType("Close");
        dialog.getButtonTypes().setAll(parcourir, close);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == parcourir) {
            File file = fileChooser.showOpenDialog(primaryStage);
            String fileName = file.getName();
            if (fileName.endsWith(".json")) {
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuMap.getText().toLowerCase() + "/");
                this.list_characters.getItems().add(fileName.substring(0, fileName.length() - 5));
                // ... user chose OK
            } else {
                System.out.println("JAVSVSVSVSV");
                Alert else_dialog = new Alert(AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur, mauvais format de fichier");
                else_dialog.setContentText("Seuls les fichiers json sont acceptés : fichiers d'extension .json");
                else_dialog.showAndWait();
            }
        }
        else {
            //user chose close
        }
    }

    public void onActionMenuAddItems() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setTitle("Add Items");
        dialog.setHeaderText("Ajouter un Item en cliquant sur parcourir");
        dialog.setContentText("Rappel : Fichiers d'extension json pour ce type d'élement");
        ButtonType parcourir = new ButtonType("Parcourir");
        ButtonType close = new ButtonType("Close");
        dialog.getButtonTypes().setAll(parcourir, close);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == parcourir) {
            File file = fileChooser.showOpenDialog(primaryStage);
            String fileName = file.getName();
            if (fileName.endsWith(".json")) {
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuMap.getText().toLowerCase() + "/");
                this.list_items.getItems().add(fileName.substring(0, fileName.length() - 5));
                // ... user chose OK
            } else {
                System.out.println("JAVSVSVSVSV");
                Alert else_dialog = new Alert(AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur, mauvais format de fichier");
                else_dialog.setContentText("Seuls les fichiers json sont acceptés : fichier d'extension .json");
                else_dialog.showAndWait();
            }
        }
        else {
            //user chose close
        }
    }

    public void onActionMenuAddStory() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setTitle("Add Story");
        dialog.setHeaderText("Ajouter une story en cliquant sur parcourir");
        dialog.setContentText("Rappel : Fichiers d'extension json pour ce type d'élement");
        ButtonType parcourir = new ButtonType("Parcourir");
        ButtonType close = new ButtonType("Close");
        dialog.getButtonTypes().setAll(parcourir, close);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == parcourir) {
            File file = fileChooser.showOpenDialog(primaryStage);
            String fileName = file.getName();
            if (fileName.endsWith(".json")) {
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuMap.getText().toLowerCase() + "/");
                this.list_stories.getItems().add(fileName.substring(0, fileName.length() - 5));
                // ... user chose OK
            } else {
                System.out.println("JAVSVSVSVSV");
                Alert else_dialog = new Alert(AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur, mauvais format de fichier");
                else_dialog.setContentText("Seuls les fichiers json sont acceptés : fichier d'extension .json");
                else_dialog.showAndWait();
            }
        }
        else {
            //user chose close
        }
    }

    public void addFiles(File file, String path){
        try {
            File dest = new File(path + file.getName());
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onActionMenuNewFile() {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

        //get The response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            primaryStage.setTitle(String.valueOf(result).substring(9, String.valueOf(result).length() - 1));
            this.list = new String[]{menuMap.getText().toLowerCase(), menuImages.getText().toLowerCase(), menuItems.getText().toLowerCase(), menuCharacter.getText().toLowerCase(), menuStories.getText().toLowerCase()};

            for (int i=0 ; i<this.list.length ; i++ ){
                File f = new File("Packages/" + primaryStage.getTitle() +"/" + this.list[i]);
                f.mkdirs();
            }

            String fileName = "Packages/" + primaryStage.getTitle() + "/"+  primaryStage.getTitle()+".json";
            File file = new File(fileName);
            SaveFile(primaryStage.getTitle(),file);

            this.contain1.setVisible(false);
            this.contain2.setVisible(true);
            this.menuMap.setDisable(false);
            this.menuCharacter.setDisable(false);
            this.menuItems.setDisable(false);
            this.menuImages.setDisable(false);
            this.menuStories.setDisable(false);
        }

        else {
            System.out.println("not in if");
        }
    }

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
            file.getParentFile().mkdirs();
            fileWriter = new FileWriter(file);
            fileWriter.write(content + "\n");
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}


