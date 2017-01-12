package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Forces.Force;

/**
 * Created by Dylan on 12/01/2017.
 */
public class ForceEntitiesHolder<E extends ForceEntity> extends EntitiesHolder<E> {
    public ForceEntitiesHolder() {
        super();
    }

    public ForceEntitiesHolder(JsonValue.JsonIterator jsonIterator) {
        super(jsonIterator);
    }

    public E get(int powerful) {
        for (E e : this) {
            if(e.getPowerful() == powerful)
                return e;
        }

        return null;
    }

    public int getDefaultPowerful() {
        for(E e : this) {
            if(e.isDefault())
                return e.getPowerful();
        }

        return 0;
    }

    public Boolean existPowerful(int powerful) {
        for (E e : this) {
            if(e.getPowerful() == powerful)
                return true;
        }

        return false;
    }

    public Boolean areEnough() {
        return this.size() > 0;
    }

    @Override
    public Boolean add(JsonValue value) {
        return null;
    }
}
