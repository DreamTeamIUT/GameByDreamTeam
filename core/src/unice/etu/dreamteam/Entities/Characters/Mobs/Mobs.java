package unice.etu.dreamteam.Entities.Characters.Mobs;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;

/**
 * Created by Guillaume on 31/10/2016.
 */

public class Mobs extends EntitiesHolder<MobHolder> {
    private static Mobs mobs;

    private Mobs() {
        super();
    }

    {
        path = "characters";
    }

    public static Mobs getInstance() {
        if(mobs == null)
            mobs = new Mobs();

        return mobs;
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new MobHolder(loadDependencies(path, value.toString())));
    }
}