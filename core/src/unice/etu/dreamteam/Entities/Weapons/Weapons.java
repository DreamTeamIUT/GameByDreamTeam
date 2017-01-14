package unice.etu.dreamteam.Entities.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;
import unice.etu.dreamteam.Entities.Items.Item;
import unice.etu.dreamteam.Utils.GameInformation;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Dylan on 11/01/2017.
 */
public class Weapons extends EntitiesHolder<Weapon> {
    private static Weapons weapons;

    private Weapons() {
        super();
    }

    {
        path = "weapons";
    }

    public static Weapons getInstance() {
        if(weapons == null)
            weapons = new Weapons();

        return weapons;
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new Weapon(value));
    }

    @Override
    public void add(JsonValue.JsonIterator jsonIterator) {
        for (JsonValue entity : jsonIterator) {
            if(entity.toString().endsWith(".json"))
                this.add(new Weapon(loadDependencies(path, entity.toString())));
        }
    }
}
