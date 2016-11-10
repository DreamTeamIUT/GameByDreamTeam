package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Guillaume on 02/11/2016.
 */
public class SaveManager {

    public static JsonValue getSaves() {
        FileHandle file = Gdx.files.internal("assets/saves/saves.json");
        JsonValue saves = new JsonReader().parse(file.readString());
        return saves;
    }

    public static JsonValue getSave(String username) {
        FileHandle file = Gdx.files.internal("assets/saves/saves.json");
        JsonValue save = new JsonReader().parse(file.readString()).get(username);
        return save;
    }

    public static JsonValue getInfoSave(String username, String playerId) {
        JsonValue info = getSave(username);

        String folderName = info.getString("folder", "");

        FileHandle file = Gdx.files.internal("assets/saves/" + folderName + "/" + playerId + "_" + GameInformation.getPackageName() + ".json");
        return new JsonReader().parse(file.readString());
    }

    public static void createSaves(String username) throws IOException {
        String pathFile = "assets/saves/" + username.toUpperCase();
        File file = null;

        try{
            file = new File(pathFile);
            file.mkdirs();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        for (Packages p : Packages.getPackages()) {
            for (JsonValue player: p.getPlayers().all()){
                String jsonContent = "";

                ObjectMap<String, Object> obj = new ObjectMap<String, Object>();
                obj.put("levzl", 5);

                Json json = new Json();
                json.setOutputType(JsonWriter.OutputType.json);

                String jsonStr = json.prettyPrint(obj);

                file = new File(pathFile + "/" + player.getString("name")+ "_" + p.getFolderName() + ".json");
                try {
                    if (!file.exists() ) {
                        file.createNewFile();

                        FileWriter writer = new FileWriter(file);
                        writer.write(jsonStr);
                        writer.flush();
                        writer.close();
                    }
                } catch (IOException e) {
                    System.out.println("Error in the creation of file");
                }
            }
        }

    }

    public static void info() {

    }

    public static void load() {

    }
}
