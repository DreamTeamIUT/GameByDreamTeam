package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.Entities;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Players extends Entities {
    private String packageName;

    public Players() {
        super();
    }

    public Players(JsonValue.JsonIterator jsonIterator, String packageName) {
        super();
        this.packageName = packageName;
        add(jsonIterator);
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue entitie : jsonIterator)
            if (entitie.toString().endsWith(".json"))
                add(loadDep(entitie.toString()));
    }

    private JsonValue loadDep(String fileName) {
        FileHandle file = Gdx.files.internal("assets/" + packageName + "/characters/" + fileName);
        return new JsonReader().parse(file.readString());
    }

    @Override
    public JsonValue get(String name) {
        for (JsonValue value : entities) {
            if (value.getString("name", "").equals(name))
                return value;
        }
        return null;
    }

}