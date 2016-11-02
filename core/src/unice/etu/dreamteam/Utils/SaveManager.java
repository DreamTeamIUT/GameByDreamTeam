package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by Guillaume on 02/11/2016.
 */
public class SaveManager {

    public static JsonValue getSaves() {
        FileHandle file = Gdx.files.internal("assets/saves/saves.json");
        JsonValue saves = new JsonReader().parse(file.readString());
        return saves;
    }

    public static JsonValue getSave(String username){
        FileHandle file = Gdx.files.internal("assets/saves/saves.json");
        JsonValue save = new JsonReader().parse(file.readString()).get(username);
        return save;
    }

    public static JsonValue getInfoSave(String username, String playerId){
        JsonValue info = getSave(username);

        String folderName = info.getString("folder", "");

        FileHandle file = Gdx.files.internal("assets/saves/" + folderName + "/" + playerId +"_"+GameInformation.getPackageName() +".json");
        return new JsonReader().parse(file.readString());
    }

    public static void createSaves() {

    }

    public static void info() {

    }

    public static void load() {

    }
}
