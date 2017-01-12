package unice.etu.dreamteam.Entities.Forces;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;

/**
 * Created by Dylan on 11/01/2017.
 */
public class Forces extends EntitiesHolder<Force> {
    public Forces() {
        super();
    }

    public Forces(JsonValue.JsonIterator jsonIterator) {
        super();
        add(jsonIterator);
    }

    public Force get(int powerful) {
        for (Force force : this) {
            if(force.getPowerful() == powerful)
                return force;
        }

        return null;
    }

    public int getDefaultPowerful() {
        for(Force force : this) {
            if(force.isDefault())
                return force.getPowerful();
        }

        return 0;
    }

    public Boolean existPowerful(int powerful) {
        for (Force force : this) {
            if(force.getPowerful() == powerful)
                return true;
        }

        return false;
    }

    public Boolean areEnough() {
        return this.size() > 0;
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new Force(value));
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue jsonValue : jsonIterator)
            add(new Force(jsonValue));
    }
}
