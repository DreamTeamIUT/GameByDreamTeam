package unice.etu.dreamteam.Entities.Characters.Mobs.Forces;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.ForceEntitiesHolder;

/**
 * Created by Dylan on 12/01/2017.
 */
public class ForcesMobHolder extends ForceEntitiesHolder<ForceMobHolder> {
    public ForcesMobHolder() {
        super();
    }

    public ForcesMobHolder(JsonValue.JsonIterator jsonIterator) {
        super();
        add(jsonIterator);
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new ForceMobHolder(value));
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue value : jsonIterator)
            add(new ForceMobHolder(value));
    }
}
