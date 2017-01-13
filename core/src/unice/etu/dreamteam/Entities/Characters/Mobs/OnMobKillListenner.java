package unice.etu.dreamteam.Entities.Characters.Mobs;

import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by Guillaume on 31/10/2016.
 */
public interface OnMobKillListenner {
    void spawnItems(JsonValue items);
}
