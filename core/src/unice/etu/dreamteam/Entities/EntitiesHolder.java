package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by Guillaume on 24/12/2016.
 */

public abstract class EntitiesHolder<E extends Entity> extends ArrayList<E>{
    public EntitiesHolder(){
        super();
    }

    public EntitiesHolder(JsonValue.JsonIterator jsonValues){
        add(jsonValues);
    }

    public EntitiesHolder<E> all(){
        return this;
    }

    public abstract Boolean add(JsonValue value);

    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue entitie : jsonIterator)
            this.add(entitie);
    }

    public E get(String name){
        for (E e : this){
            if (e.getName().equals(name))
                return e;
        }
        //throw new NoSuchElementException("l'élément " + name + " n'est pas trouvable !");
        return null;
    }
}



