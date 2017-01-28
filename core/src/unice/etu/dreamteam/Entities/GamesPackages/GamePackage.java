package unice.etu.dreamteam.Entities.GamesPackages;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Characters.Players.Players;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Map.Story;
import unice.etu.dreamteam.Utils.Debug;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Guillaume on 10/01/2017.
 */
public class GamePackage extends Entity {

    private final String packagePath;
    private final String lastEdit;
    private final String displayName;
    private final String creator;
    private final String version;
    private final Players players;

    public GamePackage(JsonValue value) {
        super(value);
        this.packagePath = value.getString("path");
        this.displayName = value.getString("displayName", "NC");
        this.creator = value.getString("creator", "NC");
        this.version = value.getString("version", "NC");
        this.lastEdit = value.getString("last-edit", "NC");
        this.players = new Players(value.get("players").iterator(), packagePath);
    }

    public String getPackagePath() {
        return packagePath;
    }

    public String getPackagePath(String folder) {
        return packagePath + folder + "/";
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

    public String getDisplayName() {
        return displayName;
    }

    public Players getPlayers() {
        return players;
    }

    public ArrayList<Story> getStories() {
        ArrayList<Story> list = new ArrayList<Story>();

        File directory = new File(getPackagePath() + "/stories/");

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            Debug.log(file.getName());
            if (file.isFile() && file.getName().endsWith(".json")) {
                list.add(Story.load(file.getName()));
            }
        }

        return list;
    }

}
