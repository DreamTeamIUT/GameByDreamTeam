package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 31/10/2016.
 */

public class Mobs extends EntitiesHolder<MobHolder> {
    private String packageName;

    public Mobs() {
        super();
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new MobHolder(value));
    }

    public Mobs(JsonValue.JsonIterator jsonIterator, String packageName) {
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
        FileHandle file = Gdx.files.internal( GameInformation.getGamePackage().getPackagePath() + "/characters/" + fileName);
        return new JsonReader().parse(file.readString());
    }
}