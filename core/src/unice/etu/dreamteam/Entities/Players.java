package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Players extends EntitiesHolder<PlayerHolder> {
    private String packageName;

    public Players() {
        super();
    }

    public Players(JsonValue.JsonIterator jsonIterator, String packageName) {
        super();
        this.packageName = packageName;
        add(jsonIterator);
    }

    public Players(JsonValue.JsonIterator jsonIterator, String packageName, String path) {
        super();
        this.packageName = packageName;
        add(jsonIterator, path);
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue entitie : jsonIterator)
            if (entitie.toString().endsWith(".json"))
                add(loadDep(entitie.toString()));
    }

    public void add(JsonValue.JsonIterator jsonIterator, String path) {
        for (JsonValue entitie : jsonIterator)
            if (entitie.toString().endsWith(".json"))
                add(loadDep(entitie.toString(), path));
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new PlayerHolder(value));
    }


    private JsonValue loadDep(String fileName) {
        return loadDep(fileName, GamePackages.getPackages().get(packageName).getPackagePath());
    }

    private JsonValue loadDep(String fileName, String packagePath) {
        FileHandle file = Gdx.files.internal(packagePath + "/characters/" + fileName);
        return new JsonReader().parse(file.readString());
    }

}