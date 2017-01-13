package unice.etu.dreamteam.Entities.Characters.Mobs.Forces;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.ForceEntity;
import unice.etu.dreamteam.Entities.Weapons.WeaponHolder;
import unice.etu.dreamteam.Entities.Weapons.Weapons;

/**
 * Created by Dylan on 12/01/2017.
 */
public class ForceMobHolder extends ForceEntity {
    private float speed;
    private int proximityRange;
    private int detectionRange;

    private String weaponId;

    public ForceMobHolder(JsonValue value) {
        super(value);

        speed = value.getFloat("speed");
        proximityRange = value.getInt("proximity-range", 0);
        detectionRange = value.getInt("detection-range", 0);

        weaponId = value.getString("weapon-id");
    }

    public float getSpeed() {
        return speed;
    }

    public int getProximityRange() {
        return proximityRange;
    }

    public int getDetectionRange() {
        return detectionRange;
    }

    public WeaponHolder getWeapon() {
        return Weapons.getInstance().get(weaponId);
    }
}
