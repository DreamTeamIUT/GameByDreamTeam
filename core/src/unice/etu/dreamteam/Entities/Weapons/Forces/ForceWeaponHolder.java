package unice.etu.dreamteam.Entities.Weapons.Forces;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.ForceEntity;

/**
 * Created by Dylan on 12/01/2017.
 */
public class ForceWeaponHolder extends ForceEntity {
    private float speedReload;
    private int range;
    private int damage;

    public ForceWeaponHolder(JsonValue value) {
        super(value);

        speedReload = value.getFloat("speed-reload");
        range = value.getInt("range", 0);
        damage = value.getInt("damage", 0);
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

    @Override
    public void render() {

    }
}
