package unice.etu.dreamteam.Entities.Forces;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Entities.Characters.Mobs.OnMobKillListenner;
import unice.etu.dreamteam.Utils.Debug;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Force extends Entity {
    private final JsonValue items;
    private OnMobKillListenner onMobKillListenner;
    private String name;
    private String weapon;
    private float speed;

    private int proximityRange;
    private int detectionRange;

    public Force(JsonValue value) {
        super(value);

        Debug.log("Force", "start");
        Debug.log(value.toString());

        proximityRange = value.getInt("proximityRange");
        detectionRange = value.getInt("detectionRange");

        weapon = value.getString("weaponId");
        speed = value.getFloat("speed");
        items = value.get("onkill").get("spawnItems");
    }

    public String getWeapon() {
        return weapon;
    }

    public void kill() {
        getOnMobKillListenner().spawnItems(items);
    }

    public OnMobKillListenner getOnMobKillListenner() {
        return onMobKillListenner;
    }

    public void setOnMobKillListenner(OnMobKillListenner onMobKillListenner) {
        this.onMobKillListenner = onMobKillListenner;
    }

    public int getProximityRange() {
        return proximityRange;
    }

    public int getDetectionRange() {
        return detectionRange;
    }

    public float getSpeed() {
        return speed;
    }
}
