package unice.etu.dreamteam.Entities.Characters.Mobs;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;

/**
 * Created by Guillaume on 31/10/2016.
 */

public class Mobs extends EntitiesHolder<MobHolder> {
    public Mobs() {
        super();
    }

    public Mobs(JsonValue.JsonIterator jsonIterator) {
        super();
        add(jsonIterator);
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new MobHolder(value));
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue jsonValue : jsonIterator) {
            if (jsonValue.toString().endsWith(".json"))
                add(new MobHolder(loadDependencies("characters", jsonValue.toString())));
        }
    }
}