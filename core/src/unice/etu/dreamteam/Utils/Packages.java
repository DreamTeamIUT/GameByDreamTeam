package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Map.Story;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Guillaume on 02/11/2016.
 */
public class Packages {

    private final String folderName;
    private final String lastEdit;
    private final String name;
    private final String creator;
    private final String version;

    public Packages(String name) {
        this.folderName = name;

        FileHandle file = Gdx.files.internal("assets/packages/" + this.folderName + "/info.json");

        JsonValue jsonPackage = new JsonReader().parse(file.readString());

        this.name = jsonPackage.getString("name", "NC");
        this.creator = jsonPackage.getString("creator", "NC");
        this.version = jsonPackage.getString("version", "NC");
        this.lastEdit = jsonPackage.getString("last-edit", "NC");


    }

    public ArrayList<Story> getStories() {
        ArrayList<Story> list = new ArrayList<Story>();

        File directory = new File("assets/packages/" + getFolderName() + "/stories/");

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                list.add(Story.load(file.getName()));
            }
        }

        return list;
    }

    public static ArrayList<Packages> getPackages() {
        ArrayList<Packages> list = new ArrayList<Packages>();

        File directory = new File("assets/packages/");

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isDirectory()) {
                list.add(new Packages(file.getName()));
            }
        }

        return list;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getVersion() {
        return version;
    }

    public String getCreator() {
        return creator;
    }

    public String getLastEdit() {
        return lastEdit;
    }

    public String getName() {
        return name;
    }
}
