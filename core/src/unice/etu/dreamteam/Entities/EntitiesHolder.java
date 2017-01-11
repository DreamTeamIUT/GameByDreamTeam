package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.GameInformation;

import java.util.ArrayList;

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
        for (JsonValue entity : jsonIterator)
            this.add(entity);
    }

    public void add(JsonValue.JsonIterator jsonIterator, String path) {
        for (JsonValue entity : jsonIterator) {
            if(entity.toString().endsWith(".json"))
                this.add(loadDependencies(path, entity.toString()));
        }
    }

    public E get(String name){
        for (E e : this){
            if (e.getName().equals(name))
                return e;
        }
        //throw new NoSuchElementException("l'élément " + name + " n'est pas trouvable !");
        return null;
    }

    protected JsonValue loadDependencies(String path, String fileName) {
        try {
            return new JsonReader().parse(Gdx.files.internal(GameInformation.getGamePackage().getPackagePath() + "/" + path + "/" + fileName).readString());
        }
        catch (Exception e) {
            return null;
        }
    }
}



