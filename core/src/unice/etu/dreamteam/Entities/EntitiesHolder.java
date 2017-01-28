package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

import java.util.ArrayList;

/**
 * Created by Guillaume on 24/12/2016.
 */

public abstract class EntitiesHolder<E extends Entity> extends ArrayList<E> {
    protected String path;

    public EntitiesHolder(){
        super();
    }

    public EntitiesHolder(JsonValue.JsonIterator jsonValues){
        super();
        add(jsonValues);
    }

    public EntitiesHolder<E> all(){
        return this;
    }

    public abstract Boolean add(JsonValue value);

    public void add(JsonValue.JsonIterator jsonIterator, Boolean jsonFile) {
        for (JsonValue entity : jsonIterator) {
            if(jsonFile && !entity.toString().endsWith(".json"))
                continue;

            this.add(entity);
        }
    }

    public void add(JsonValue.JsonIterator jsonIterator) {
        add(jsonIterator, false);
    }

    public E get(String name){
        for (E e : this) {
            if (e.getName().equals(name))
                return e;
        }
        //throw new NoSuchElementException("l'élément " + name + " n'est pas trouvable !");
        return null;
    }

    public Boolean exist(String name) {
        return this.get(name) != null || name == null;
    }

    protected JsonValue loadDependencies(String path, String fileName) {
        Debug.log("assets/packages/default/");
        Debug.log(GameInformation.getGamePackage().toString());

        try {
            return new JsonReader().parse(Gdx.files.internal(GameInformation.getGamePackage().getPackagePath() + "/" + path + "/" + fileName).readString());
        }
        catch (Exception e) {
            Debug.log("error loading player");

            return null;
        }
    }
}



