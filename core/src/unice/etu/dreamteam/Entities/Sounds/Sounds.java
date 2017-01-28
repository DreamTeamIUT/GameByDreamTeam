package unice.etu.dreamteam.Entities.Sounds;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Sounds extends EntitiesHolder<SoundEntity> {
    private static Sounds sounds;

    public Sounds() {
        super();
    }

    {
        path = "sounds";
    }

    public static Sounds getInstance() {
        if(sounds == null)
            sounds = new Sounds();

        return sounds;
    }

    @Override
    public Boolean add(JsonValue value) {
        return add(new SoundEntity(value));
    }

}