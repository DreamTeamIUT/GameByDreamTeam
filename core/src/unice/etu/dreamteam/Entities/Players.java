package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Players extends EntitiesHolder<PlayerHolder>{
    private String packageName;

    public Players() {
        super();
    }

    public Players(JsonValue.JsonIterator jsonIterator, String packageName) {
        super();
        //this.packageName = packageName;
        GameInformation.setPackageName(packageName);
        add(jsonIterator);
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue entitie : jsonIterator)
            if (entitie.toString().endsWith(".json"))
                add(loadDep(entitie.toString()));
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new PlayerHolder(value));
    }


    private JsonValue loadDep(String fileName) {
        FileHandle file = Gdx.files.internal("assets/" + GameInformation.getPackagePath() + "/characters/" + fileName);
        return new JsonReader().parse(file.readString());
    }

}