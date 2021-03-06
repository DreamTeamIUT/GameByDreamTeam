package unice.etu.dreamteam.Entities.Characters.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;
import unice.etu.dreamteam.Entities.GamesPackages.GamePackages;
import unice.etu.dreamteam.Utils.Debug;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Players extends EntitiesHolder<PlayerHolder> {
    public Players() {
        super();
    }

    public Players(JsonValue.JsonIterator jsonIterator, String packagePath) {
        super();
        add(jsonIterator, packagePath);
    }

    {
        path = "characters";
    }

    /*
    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue entitie : jsonIterator)
            if (entitie.toString().endsWith(".json"))
                add(loadDep(entitie.toString()));
    }
    */

    @Override
    public Boolean add(JsonValue value) {
        return add(new PlayerHolder(value));
    }

    public void add(JsonValue.JsonIterator jsonIterator, String packagePath) {
        for (JsonValue value : jsonIterator)
            if (value.toString().endsWith(".json"))
                add(new PlayerHolder(loadDependencies(packagePath, value.toString())));
    }

    @Override
    protected JsonValue loadDependencies(String packagePath, String fileName) {
        Debug.log(packagePath);
        FileHandle file = Gdx.files.internal(packagePath + "/" + path + "/" + fileName);
        return new JsonReader().parse(file.readString());
    }

    /*
    private JsonValue loadDep(String fileName) {
        return loadDep(fileName, GamePackages.getPackages().get(packageName).getPackagePath());
    }

    private JsonValue loadDep(String fileName, String packagePath) {
        FileHandle file = Gdx.files.internal(packagePath + "/characters/" + fileName);
        return new JsonReader().parse(file.readString());
    }
    */
}