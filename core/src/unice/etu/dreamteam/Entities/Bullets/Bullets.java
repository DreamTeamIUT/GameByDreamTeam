package unice.etu.dreamteam.Entities.Bullets;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;

/**
 * Created by Dylan on 12/01/2017.
 */
public class Bullets extends EntitiesHolder<BulletHolder> {
    private static Bullets bullets;

    private Bullets() {
        super();
        path = "bullets";
    }

    public static Bullets getInstance() {
        if(bullets == null)
            bullets = new Bullets();

        return bullets;
    }

    @Override
    public Boolean add(JsonValue value) {
        return null;
    }
}
