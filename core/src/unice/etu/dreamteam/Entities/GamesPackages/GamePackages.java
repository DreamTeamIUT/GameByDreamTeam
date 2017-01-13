package unice.etu.dreamteam.Entities.GamesPackages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;
import unice.etu.dreamteam.Utils.Debug;

import java.io.File;

/**
 * Created by Guillaume on 02/11/2016.
 */
public class GamePackages extends EntitiesHolder<GamePackage> {

    public static final String PACKAGES_ROOT_PATH = "assets/packages/";

    private static GamePackages currentPackages = null;

    public GamePackages() {
        super();
        Debug.log("NEW GAMEPAKCAGES !");
    }

    public static GamePackages getPackages() {

        if (currentPackages != null)
            return currentPackages;


        currentPackages = new GamePackages();

        File directory = new File(PACKAGES_ROOT_PATH);
        File[] fList = directory.listFiles();
        Debug.log("File list",String.valueOf(fList.length));

        for (File file : fList) {
            if (file.isDirectory()) {
                Debug.log("That's a dir !");
                JsonValue v = new JsonValue(JsonValue.ValueType.stringValue);
                v.setName("path");
                v.set(PACKAGES_ROOT_PATH + file.getName() + "/");

                FileHandle packageData = Gdx.files.internal(PACKAGES_ROOT_PATH + file.getName() + "/info.json");
                JsonValue jsonPackage = new JsonReader().parse(packageData.readString());
                jsonPackage.addChild(v);

                currentPackages.add(jsonPackage);

            }
        }
        Debug.log("Size pakcage",String.valueOf(currentPackages.size()));
        return currentPackages;
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new GamePackage(value));
    }
}
