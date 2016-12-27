package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Items extends EntitiesHolder<Item>{

    public Items() {
        super();
    }

    public Items(JsonValue.JsonIterator jsonValues) {
        super(jsonValues);
    }

    @Override
    public Boolean add(JsonValue value) {
       return add(new Item(value));
    }
}
