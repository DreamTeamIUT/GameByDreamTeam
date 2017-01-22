package unice.etu.dreamteam.Entities.Weapons.Forces;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.ForceEntity;

/**
 * Created by Dylan on 12/01/2017.
 */
public class ForceWeaponHolder extends ForceEntity {
    private float speedReload;
    private int range;
    private int damage;
    private Vector2 shift;

    private String bulletId;

    public ForceWeaponHolder(JsonValue value) {
        super(value);

        speedReload = value.getFloat("speed-reload");
        range = value.getInt("range", 0);
        damage = value.getInt("damage", 0);

        JsonValue shiftValue = value.get("shift");
        shift = new Vector2(shiftValue.getInt("x", 0), shiftValue.getInt("y", 0));

        bulletId = value.getString("bullet-id");
    }

    @Override
    public void render() {

    }

    public float getSpeedReload() {
        return speedReload;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public Vector2 getShift() {
        return shift;
    }

    public String getBulletId() {
        return bulletId;
    }
}
