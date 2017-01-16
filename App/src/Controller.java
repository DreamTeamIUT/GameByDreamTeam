import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Controller implements Initializable {

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
    private Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("CONTROLER !");


        final ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete");
        contextMenu.getItems().addAll(delete);
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Packages/" + primaryStage.getTitle() + "/" + menuMap.getText().toLowerCase() + "/" + list_map.getSelectionModel().getSelectedItem() + ".tmx");

                deleteFile("Packages/" + primaryStage.getTitle() + "/" + menuMap.getText().toLowerCase() + "/" + list_map.getSelectionModel().getSelectedItem() + ".tmx");
                list_map.getItems().remove(list_map.getSelectionModel().getSelectedItem());

                deleteFile("Packages/" + primaryStage.getTitle() + "/" + menuCharacter.getText().toLowerCase() + "/" + list_characters.getSelectionModel().getSelectedItem() + ".json");
                list_characters.getItems().remove(list_characters.getSelectionModel().getSelectedItem());

                deleteFile("Packages/" + primaryStage.getTitle() + "/" + menuItems.getText().toLowerCase() + "/" + list_items.getSelectionModel().getSelectedItem() + ".json");
                list_items.getItems().remove(list_items.getSelectionModel().getSelectedItem());

                deleteFile("Packages/" + primaryStage.getTitle() + "/" + menuStories.getText().toLowerCase() + "/" + list_stories.getSelectionModel().getSelectedItem() + ".json");
                list_stories.getItems().remove(list_stories.getSelectionModel().getSelectedItem());


            }
        });

        list_map.setContextMenu(contextMenu);
        list_characters.setContextMenu(contextMenu);
        list_stories.setContextMenu(contextMenu);
        list_items.setContextMenu(contextMenu);
    }

    public void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    private void readDir(String path, HashMap<String, File> list) {

        File contentDir = new File(path);

        File[] fileList = contentDir.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            list.put(fileList[i].getName().replace(".json", "").replace(".tmx", ""), fileList[i]);
            System.out.println(fileList[i].getName());
        }
    }

    private boolean isEmpty(String[] tab) {
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] != null) {
                return false;
            }
        }
        return true;
    }

    public void onActionMenuCreateStory() {
        // Create the custom dialog.
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Create Story");
        dialog.setHeaderText("Create your story");

// Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

// Set the button types.
        ButtonType CreateButtonType = new ButtonType("Create story", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(CreateButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField storyName = new TextField();
        storyName.setPromptText("StoryName");
        TextField defaultMap = new TextField();
        defaultMap.setPromptText("Default Map");
        TextField runSound = new TextField();
        runSound.setPromptText("run Sound");
        TextField enterZoneSound = new TextField();
        enterZoneSound.setPromptText("enter zone sound");


        grid.add(new Label("Story name:"), 0, 0);
        grid.add(storyName, 1, 0);
        grid.add(new Label("Default map:"), 0, 1);
        grid.add(defaultMap, 1, 1);
        grid.add(new Label("Run sound:"), 0, 2);
        grid.add(runSound, 1, 2);
        grid.add(new Label("Enter zone sound:"), 0, 3);
        grid.add(enterZoneSound, 1, 3);


        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> storyName.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == CreateButtonType) {
                String[] a = new String[]{storyName.getText(), defaultMap.getText(), runSound.getText(), enterZoneSound.getText()};
                System.out.println(Arrays.toString(a));
                return a;
            }
            return null;
        });


        Optional<String[]> result = dialog.showAndWait();

        if (!isEmpty(result.get())) {
            JSONObject obj = new JSONObject();
            obj.put("name", "mkyong.com");
            obj.put("age", new Integer(100));

            JSONArray list = new JSONArray();
            list.add("msg 1");
            list.add("msg 2");
            list.add("msg 3");

            obj.put("messages", list);

            try {

                FileWriter file = new FileWriter("Packages");
                file.write(obj.toJSONString());
                file.flush();
                file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.print(obj);

        }
    }






    public void onActionMenuSelectMap() {
        HashMap<String, File> list = new HashMap<>();
        readDir("Packages/DEFAULT/maps/", list);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", list.keySet());
        dialog.setTitle("Add map");
        dialog.setHeaderText("Ajouter des maps");
        dialog.setContentText("Selectionnez:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
            if (!result.get().equals("")) {
                addFiles(list.get(result.get()), "Packages/" + primaryStage.getTitle() + "/" + menuMap.getText().toLowerCase() + "/");
                this.list_map.getItems().add(result.get());
            } else {
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur dans la selection de la map");
                else_dialog.setContentText("Vous êtes obligé de séléctionner une map si vous souhaitez ajouter");
                else_dialog.showAndWait();
            }
        }
    }

    public void onActionMenuSelectCharacters() {
        HashMap<String, File> list = new HashMap<>();
        readDir("Packages/DEFAULT/characters/", list);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", list.keySet());
        dialog.setTitle("Add Characters");
        dialog.setHeaderText("Ajouter des personnages");
        dialog.setContentText("Selectionnez:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
            if (!result.get().equals("")) {
                addFiles(list.get(result.get()), "Packages/" + primaryStage.getTitle() + "/" + menuCharacter.getText().toLowerCase() + "/");
                this.list_characters.getItems().add(result.get());
            } else {
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur dans la selection du personnage");
                else_dialog.setContentText("Vous êtes obligé de séléctionner un personnage si vous souhaitez ajouter");
                else_dialog.showAndWait();
            }
        }
    }

    public void onActionMenuSelectItems() {
        HashMap<String, File> list = new HashMap<>();
        readDir("Packages/DEFAULT/items/", list);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", list.keySet());
        dialog.setTitle("Add Items");
        dialog.setHeaderText("Ajouter des items");
        dialog.setContentText("Selectionnez:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
            if (!result.get().equals("")) {
                addFiles(list.get(result.get()), "Packages/" + primaryStage.getTitle() + "/" + menuItems.getText().toLowerCase() + "/");
                this.list_items.getItems().add(result.get());
            } else {
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur dans la selection des items");
                else_dialog.setContentText("Vous êtes obligé de séléctionner des items si vous souhaitez ajouter");
                else_dialog.showAndWait();
            }
        }
    }

    public void onActionMenuSelectStory() {
        HashMap<String, File> list = new HashMap<>();
        readDir("Packages/DEFAULT/stories/", list);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", list.keySet());
        dialog.setTitle("Add Story");
        dialog.setHeaderText("Ajouter des stories");
        dialog.setContentText("Selectionnez:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
            if (!result.get().equals("")) {
                addFiles(list.get(result.get()), "Packages/" + primaryStage.getTitle() + "/" + menuStories.getText().toLowerCase() + "/");
                this.list_stories.getItems().add(result.get());
            } else {
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur dans la selection de la story");
                else_dialog.setContentText("Vous êtes obligé de selectionner une story si vous souhaitez ajouter");
                else_dialog.showAndWait();
            }
        }
    }

    public void onActionMenuAddMap() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Add Map");
        dialog.setHeaderText("Ajouter une map en cliquant sur parcourir");
        dialog.setContentText("Rappel : Fichiers d'extension tmtx pour ce type d'élement.");
        ButtonType parcourir = new ButtonType("Parcourir");
        ButtonType close = new ButtonType("Close");
        dialog.getButtonTypes().setAll(parcourir, close);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == parcourir) {
            File file = fileChooser.showOpenDialog(primaryStage);
            String fileName = file.getName();
            if (fileName.endsWith(".tmx")) {
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuMap.getText().toLowerCase() + "/");
                this.list_map.getItems().add(fileName.substring(0, fileName.length() - 4));
                // ... user chose OK
            } else {
                System.out.println("JAVSVSVSVSV");
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur, mauvais format de fichier");
                else_dialog.setContentText("Seuls les fichiers Tiled sont acceptés : fichiers d'extension .tmtx");
                else_dialog.showAndWait();
            }
        } else {
            //user chose close
        }
    }

    public void onActionMenuAddCharacters() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
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
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur, mauvais format de fichier");
                else_dialog.setContentText("Seuls les fichiers json sont acceptés : fichiers d'extension .json");
                else_dialog.showAndWait();
            }
        } else {
            //user chose close
        }
    }

    public void onActionMenuAddItems() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
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
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur, mauvais format de fichier");
                else_dialog.setContentText("Seuls les fichiers json sont acceptés : fichier d'extension .json");
                else_dialog.showAndWait();
            }
        } else {
            //user chose close
        }
    }

    public void onActionMenuAddStory() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
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
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur, mauvais format de fichier");
                else_dialog.setContentText("Seuls les fichiers json sont acceptés : fichier d'extension .json");
                else_dialog.showAndWait();
            }
        } else {
            //user chose close
        }
    }

    public void addFiles(File file, String path) {
        try {
            File dest = new File(path + file.getName());
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onActionMenuNewFile() {
        TextInputDialog dialog = new TextInputDialog("unnamed");
        dialog.setTitle("New File");
        dialog.setHeaderText("Créer un nouveau package");
        dialog.setContentText("Nom du package :");

        //get The response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            primaryStage.setTitle(String.valueOf(result).substring(9, String.valueOf(result).length() - 1));
            this.list = new String[]{menuMap.getText().toLowerCase(), menuImages.getText().toLowerCase(), menuItems.getText().toLowerCase(), menuCharacter.getText().toLowerCase(), menuStories.getText().toLowerCase()};

            for (int i = 0; i < this.list.length; i++) {
                File f = new File("Packages/" + primaryStage.getTitle() + "/" + this.list[i]);
                f.mkdirs();
            }

            String fileName = "Packages/" + primaryStage.getTitle() + "/" + primaryStage.getTitle() + ".json";
            File file = new File(fileName);
            SaveFile(primaryStage.getTitle(), file);

            this.contain1.setVisible(false);
            this.contain2.setVisible(true);
            this.menuMap.setDisable(false);
            this.menuCharacter.setDisable(false);
            this.menuItems.setDisable(false);
            this.menuImages.setDisable(false);
            this.menuStories.setDisable(false);
        } else {
            System.out.println("not in if");
        }
    }

    private void SaveFile(String content, File file) {
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

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }
}
