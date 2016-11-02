package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.OnMobKillListenner;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Force {
    private final JsonValue items;
    private OnMobKillListenner onMobKillListenner;
    private String name;
    private String weapon;
    private float speed;

    public Force(JsonValue value) {
        this.name = value.name;
        this.weapon = value.getString("weapon");
        this.speed = value.getFloat("speed");
        this.items = value.get("onkill").get("pops");
    }

    public String getName() {
        return name;
    }

    public String getWeapon() {
        return weapon;
    }

    public void kill() {
        getOnMobKillListenner().pops(items);
    }

    public OnMobKillListenner getOnMobKillListenner() {
        return onMobKillListenner;
    }

    public void setOnMobKillListenner(OnMobKillListenner onMobKillListenner) {
        this.onMobKillListenner = onMobKillListenner;
    }
}
