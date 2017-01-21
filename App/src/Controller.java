import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
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
    private Menu menuWeapons;
    @FXML
    private Menu menuMobs;
    @FXML
    private Menu menuSound;
    @FXML
    private Menu menuItems;
    @FXML
    private ListView list_map;
    @FXML
    private ListView list_characters;
    @FXML
    private ListView list_stories;
    @FXML
    private ListView list_items;
    @FXML
    private ListView list_weapons;
    @FXML
    private ListView list_mobs;
    @FXML
    private ListView list_sound;
    @FXML
    private Button buttonStory;

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

                deleteFile("Packages/" + primaryStage.getTitle() + "/stories/" + list_stories.getSelectionModel().getSelectedItem() + ".json");
                list_stories.getItems().remove(list_stories.getSelectionModel().getSelectedItem());

                deleteFile("Packages/" + primaryStage.getTitle() + "/" + menuMobs.getText().toLowerCase() + "/" + list_mobs.getSelectionModel().getSelectedItem() + ".json");
                list_mobs.getItems().remove(list_mobs.getSelectionModel().getSelectedItem());

                deleteFile("Packages/" + primaryStage.getTitle() + "/" + menuSound.getText().toLowerCase() + "/" + list_sound.getSelectionModel().getSelectedItem() + ".json");
                list_sound.getItems().remove(list_sound.getSelectionModel().getSelectedItem());

                deleteFile("Packages/" + primaryStage.getTitle() + "/" + menuWeapons.getText().toLowerCase() + "/" + list_weapons.getSelectionModel().getSelectedItem() + ".json");
                list_weapons.getItems().remove(list_weapons.getSelectionModel().getSelectedItem());

                canCreateStory();


            }
        });

        list_map.setContextMenu(contextMenu);
        list_characters.setContextMenu(contextMenu);
        list_stories.setContextMenu(contextMenu);
        list_items.setContextMenu(contextMenu);
        list_mobs.setContextMenu(contextMenu);
        list_weapons.setContextMenu(contextMenu);
        list_sound.setContextMenu(contextMenu);
    }

    public void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    private void readDir(String path, HashMap<String, File> list) {

        File contentDir = new File(path);

        File[] fileList = contentDir.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            list.put(fileList[i].getName(), fileList[i]);
            System.out.println(fileList[i].getName());
        }
    }

    private String[] returnFiles (String directoryPath){
        File file = new File(directoryPath);
        File[] files = file.listFiles();
        String[] filesName = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            filesName[i] = files[i].getName().replace(".tmx", "").replace(".json", "").replace("mp3", "");
        }
        return filesName;
    }

    private boolean isEmpty(String[] tab) {
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] != null) {
                return false;
            }
        }
        return true;
    }

    public boolean compareArray(String[] array, String[] ArrayToCompare){
        ArrayList<String> arrayStrings = new ArrayList<>();

        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(ArrayToCompare));

        Boolean found;

        for(int i=0; i<array.length; i++){
            found = false;

            for(int j=0; j<ArrayToCompare.length;j++){
                if(array[i].equals(ArrayToCompare[j]))
                    found = true;
            }

            if (found)
                arrayStrings.add(array[i]);

            System.out.println("Is found ? "+ found);
            System.out.println(arrayStrings.toString());

        }

        return arrayStrings.size() >= ArrayToCompare.length;
    }

    public void copyArray( ArrayList<String> arraylist, String[] arrayToCopy) {
        int i;
        for (i = 0; i < arrayToCopy.length; i++) {
            arraylist.add(i, arrayToCopy[i]);
        }
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
        TextField minimumLevel = new TextField();
        minimumLevel.setPromptText("minimum level");


        grid.add(new Label("Story name:"), 0, 0);
        grid.add(storyName, 1, 0);
        grid.add(new Label("Default map:"), 0, 1);
        grid.add(defaultMap, 1, 1);
        grid.add(new Label("Run sound:"), 0, 2);
        grid.add(runSound, 1, 2);
        grid.add(new Label("Enter zone sound:"), 0, 3);
        grid.add(enterZoneSound, 1, 3);
        grid.add(new Label("minimum level:"), 0, 4);
        grid.add(minimumLevel, 1, 4);


        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> storyName.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == CreateButtonType) {
                String[] array = new String[]{storyName.getText(), minimumLevel.getText(), defaultMap.getText(), runSound.getText(), enterZoneSound.getText()};
                System.out.println(Arrays.toString(array));
                return array;
            }
            return null;
        });

        Optional<String[]> result = dialog.showAndWait();
        String[] array = {defaultMap.getText().replace(".tmx","").replace(".json", "").replace(".mp3", ""), runSound.getText().replace(".tmx","").replace(".json", "").replace(".mp3", ""), enterZoneSound.getText().replace(".tmx","").replace(".json", "").replace(".mp3", "")};
        ArrayList<String> array_data = new ArrayList<String>();

        copyArray(array_data, returnFiles("Packages/" + primaryStage.getTitle() + "/" + menuCharacter.getText().toLowerCase()+ "/"));
        copyArray(array_data, returnFiles("Packages/" + primaryStage.getTitle() + "/" + menuItems.getText().toLowerCase()+ "/"));
        copyArray(array_data, returnFiles("Packages/" + primaryStage.getTitle() + "/" + menuMap.getText().toLowerCase()+ "/"));
        copyArray(array_data, returnFiles("Packages/" + primaryStage.getTitle() + "/" + menuMobs.getText().toLowerCase()+ "/"));
        copyArray(array_data, returnFiles("Packages/" + primaryStage.getTitle() + "/" + menuSound.getText().toLowerCase()+ "/"));
        copyArray(array_data, returnFiles("Packages/" + primaryStage.getTitle() + "/" + menuWeapons.getText().toLowerCase()+ "/"));

        System.out.println("A: " + array_data.get(0) );
        System.out.println("B: " + array_data.get(1) );
        System.out.println("C: " + array_data.get(2) );
        System.out.println("D: " + array_data.get(3) );


        String[] arrayData = new String[array_data.size()];
        arrayData = array_data.toArray(arrayData);

        if (!isEmpty(result.get())) {
            if (!compareArray(arrayData, array)) {
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur dans la saisie des informations");
                else_dialog.setContentText("Vous avez surement rentré de mauvaises informations, verifiez que vous les avez bien ajouté dans le package");
                else_dialog.showAndWait();
            } else {
                JSONObject obj = new JSONObject();
                JSONObject sounds = new JSONObject();
                JSONObject soundsRun = new JSONObject();
                JSONObject soundsEnterZone = new JSONObject();
                JSONObject zones = new JSONObject();
                JSONObject zone1 = new JSONObject();
                JSONObject attackObject = new JSONObject();
                JSONObject spawnitemsObject = new JSONObject();
                JSONObject gates = new JSONObject();
                JSONObject gate01 = new JSONObject();
                JSONObject gate02 = new JSONObject();


                obj.put("name", result.get()[0]);
                obj.put("minimum level", result.get()[1]);
                obj.put("default-map", result.get()[2]);

                JSONArray list_map = new JSONArray();
                JSONArray list_weapons = new JSONArray();
                JSONArray list_bullets = new JSONArray();
                JSONArray list_mobs = new JSONArray();
                JSONArray list_sounds = new JSONArray();
                JSONArray attack = new JSONArray();
                JSONArray spawnitems = new JSONArray();

                String[] mapsArray = returnFiles("Packages/" + primaryStage.getTitle() + "/" + menuMap.getText().toLowerCase() + "/");
                String[] weaponsArray = returnFiles("Packages/" + primaryStage.getTitle() + "/" + menuWeapons.getText().toLowerCase() + "/");
                String[] mobsArray = returnFiles("Packages/" + primaryStage.getTitle() + "/" + menuMobs.getText().toLowerCase() + "/");

                for (int i = 0; i < mapsArray.length; i++) {
                    list_map.add(mapsArray[i]);
                }

                obj.put("maps", list_map);

                for (int i = 0; i < weaponsArray.length; i++) {
                    list_weapons.add(weaponsArray[i]);
                }

                obj.put("weapons", list_weapons);

                for (int i = 0; i < mobsArray.length; i++) {
                    list_mobs.add(mobsArray[i]);
                }

                obj.put("mobs", list_mobs);
                obj.put("bullets", list_bullets);


                soundsRun.put("src", runSound.getText() + ".mp3");
                soundsEnterZone.put("src", enterZoneSound.getText() + ".mp3");
                sounds.put("RUN", soundsRun);
                sounds.put("ENTER_ZONE", soundsEnterZone);
                obj.put("sounds", sounds);

                attackObject.put("mobName", "mob01");
                attackObject.put("force", 1);
                attackObject.put("count", 10);
                spawnitemsObject.put("type", "border-left");
                spawnitemsObject.put("rotate", 0);
                spawnitemsObject.put("item", "fence");
                attack.add(attackObject);
                spawnitems.add(spawnitemsObject);
                zone1.put("attack", attack);
                zone1.put("spawnitems", spawnitems);
                zone1.put("sound", "");
                zone1.put("maxEnter", 2);
                zone1.put("maxExecute", 2);
                zone1.put("general", true);
                zone1.put("blocked", false);
                zone1.put("maxRevive", 2);
                zone1.put("time", 600);
                zones.put("ZONE1", zone1);
                obj.put("zones", zones);


                gate01.put("goto", "maison01:GATE02");
                gate01.put("isOpen", true);
                gate02.put("goto", "map02:GATE01");
                gate02.put("isOpen", true);
                gates.put("GATE01", gate01);
                gates.put("GATE02", gate02);
                obj.put("gates", gates);

                //list.add("msg 1");
                //list.add("msg 2");
                //list.add("msg 3");

                //obj.put("messages", list);

                try {

                    FileWriter file = new FileWriter("Packages/" + primaryStage.getTitle() + "/stories/" + result.get()[0] + ".json");
                    file.write(obj.toJSONString());
                    file.flush();
                    file.close();
                    this.list_stories.getItems().add(result.get()[0]);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.print(obj);

            }
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
                this.list_map.getItems().add(result.get().replace(".tmx","").replace(".json",""));
                canCreateStory();
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
                this.list_characters.getItems().add(result.get().replace(".tmx","").replace(".json",""));
                canCreateStory();
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
                this.list_items.getItems().add(result.get().replace(".tmx","").replace(".json",""));
                canCreateStory();
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
                this.list_stories.getItems().add(result.get().replace(".tmx","").replace(".json",""));
                canCreateStory();
            } else {
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur dans la selection de la story");
                else_dialog.setContentText("Vous êtes obligé de selectionner une story si vous souhaitez ajouter");
                else_dialog.showAndWait();
            }
        }
    }

    public void onActionMenuSelectWeapon() {
        HashMap<String, File> list = new HashMap<>();
        readDir("Packages/DEFAULT/weapons/", list);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", list.keySet());
        dialog.setTitle("Add weapon");
        dialog.setHeaderText("Ajouter des armes");
        dialog.setContentText("Selectionnez:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
            if (!result.get().equals("")) {
                addFiles(list.get(result.get()), "Packages/" + primaryStage.getTitle() + "/" + menuWeapons.getText().toLowerCase() + "/");
                this.list_weapons.getItems().add(result.get().replace(".tmx","").replace(".json",""));
                canCreateStory();
            } else {
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur dans la selection de l'arme");
                else_dialog.setContentText("Vous êtes obligé de selectionner une arme si vous souhaitez ajouter");
                else_dialog.showAndWait();
            }
        }
    }

    public void onActionMenuSelectMob() {
        HashMap<String, File> list = new HashMap<>();
        readDir("Packages/DEFAULT/mobs/", list);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", list.keySet());
        dialog.setTitle("Add mob");
        dialog.setHeaderText("Ajouter des monstres");
        dialog.setContentText("Selectionnez:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
            if (!result.get().equals("")) {
                addFiles(list.get(result.get()), "Packages/" + primaryStage.getTitle() + "/" + menuMobs.getText().toLowerCase() + "/");
                this.list_mobs.getItems().add(result.get().replace(".tmx","").replace(".json",""));
                canCreateStory();
            } else {
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur dans la selection du monstre");
                else_dialog.setContentText("Vous êtes obligé de sélectionner un monstre si vous souhaitez ajouter");
                else_dialog.showAndWait();
            }
        }
    }

    public void onActionMenuSelectSound() {
        HashMap<String, File> list = new HashMap<>();
        readDir("Packages/DEFAULT/sounds/", list);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", list.keySet());
        dialog.setTitle("Add sound");
        dialog.setHeaderText("Ajouter des musiques");
        dialog.setContentText("Selectionnez:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
            if (!result.get().equals("")) {
                addFiles(list.get(result.get()), "Packages/" + primaryStage.getTitle() + "/" + menuSound.getText().toLowerCase() + "/");
                this.list_sound.getItems().add(result.get().replace(".tmx","").replace(".json",""));
                canCreateStory();
            } else {
                Alert else_dialog = new Alert(Alert.AlertType.ERROR);
                else_dialog.setTitle("Error");
                else_dialog.setHeaderText("Erreur dans la selection de la musique");
                else_dialog.setContentText("Vous êtes obligé de selectionner une musique si vous souhaitez ajouter");
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
                canCreateStory();
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
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuCharacter.getText().toLowerCase() + "/");
                this.list_characters.getItems().add(fileName.substring(0, fileName.length() - 5));
                canCreateStory();
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
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuItems.getText().toLowerCase() + "/");
                this.list_items.getItems().add(fileName.substring(0, fileName.length() - 5));
                canCreateStory();
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
        dialog.setHeaderText("Ajouter une histoire en cliquant sur parcourir");
        dialog.setContentText("Rappel : Fichiers d'extension json pour ce type d'élement");
        ButtonType parcourir = new ButtonType("Parcourir");
        ButtonType close = new ButtonType("Close");
        dialog.getButtonTypes().setAll(parcourir, close);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == parcourir) {
            File file = fileChooser.showOpenDialog(primaryStage);
            String fileName = file.getName();
            if (fileName.endsWith(".json")) {
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuStories.getText().toLowerCase() + "/");
                this.list_stories.getItems().add(fileName.substring(0, fileName.length() - 5));
                canCreateStory();
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

    public void onActionMenuAddWeapons() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Add weapon");
        dialog.setHeaderText("Ajouter une arme en cliquant sur parcourir");
        dialog.setContentText("Rappel : Fichiers d'extension json pour ce type d'élement");
        ButtonType parcourir = new ButtonType("Parcourir");
        ButtonType close = new ButtonType("Close");
        dialog.getButtonTypes().setAll(parcourir, close);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == parcourir) {
            File file = fileChooser.showOpenDialog(primaryStage);
            String fileName = file.getName();
            if (fileName.endsWith(".json")) {
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuWeapons.getText().toLowerCase() + "/");
                this.list_weapons.getItems().add(fileName.substring(0, fileName.length() - 5));
                canCreateStory();
                // ... user chose OK
            } else {
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

    public void onActionMenuAddSounds() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Add sound");
        dialog.setHeaderText("Ajouter une musique en cliquant sur parcourir");
        dialog.setContentText("Rappel : Fichiers d'extension json pour ce type d'élement");
        ButtonType parcourir = new ButtonType("Parcourir");
        ButtonType close = new ButtonType("Close");
        dialog.getButtonTypes().setAll(parcourir, close);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == parcourir) {
            File file = fileChooser.showOpenDialog(primaryStage);
            String fileName = file.getName();
            if (fileName.endsWith(".json")) {
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuSound.getText().toLowerCase() + "/");
                this.list_sound.getItems().add(fileName.substring(0, fileName.length() - 5));
                canCreateStory();
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

    public void onActionMenuAddMobs() {

        FileChooser fileChooser = new FileChooser();
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Add mob");
        dialog.setHeaderText("Ajouter un monstre en cliquant sur parcourir");
        dialog.setContentText("Rappel : Fichiers d'extension json pour ce type d'élement");
        ButtonType parcourir = new ButtonType("Parcourir");
        ButtonType close = new ButtonType("Close");
        dialog.getButtonTypes().setAll(parcourir, close);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == parcourir) {
            File file = fileChooser.showOpenDialog(primaryStage);
            String fileName = file.getName();
            if (fileName.endsWith(".json")) {
                addFiles(file, "Packages/" + primaryStage.getTitle() + "/" + menuMobs.getText().toLowerCase() + "/");
                this.list_mobs.getItems().add(fileName.substring(0, fileName.length() - 5));
                canCreateStory();
                // ... user chose OK
            } else {
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
            this.list = new String[]{menuMap.getText().toLowerCase(), menuItems.getText().toLowerCase(), menuCharacter.getText().toLowerCase(), "stories", menuWeapons.getText().toLowerCase(), menuMobs.getText().toLowerCase(), menuSound.getText().toLowerCase()};

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
            this.menuMobs.setDisable(false);
            this.menuSound.setDisable(false);
            this.menuWeapons.setDisable(false);
            this.buttonStory.setDisable(true);
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

    public void canCreateStory(){
        if(this.list_weapons.getItems().size() >= 1 && this.list_characters.getItems().size() >= 1 && this.list_map.getItems().size() >= 1 && this.list_mobs.getItems().size() >= 1 && this.list_sound.getItems().size() >= 1 && this.list_items.getItems().size() >= 1){
            buttonStory.setDisable(false);
        }
        else{
            buttonStory.setDisable(true);
        }
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }
}
