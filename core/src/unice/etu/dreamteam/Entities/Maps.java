package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Romain on 05/01/2017.
 */
public class Maps extends EntitiesHolder<MapHolder> {

    private String defaultMap;

    public Maps(JsonValue.JsonIterator iterator, String defaultmap){
        super();
        this.defaultMap = defaultmap;
        add(iterator);
    }

    public Maps() {
        super();
    }


    @Override
    public Boolean add(JsonValue value) {
        return add(new MapHolder(value));
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue entitie : jsonIterator)
            if (entitie.toString().endsWith(".json"))
                add(loadDep(entitie.toString()));
    }

    private JsonValue loadDep(String fileName) {
        FileHandle file = Gdx.files.internal( GameInformation.getGamePackage().getPackagePath() + "/maps/" + fileName);
        return new JsonReader().parse(file.readString());
    }

    public MapHolder getDefaultMap() {
        return get(this.defaultMap);
    }

    public void setDefaultMap(String defaultMap) {
        this.defaultMap = defaultMap;
    }
}
