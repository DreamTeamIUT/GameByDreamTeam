package unice.etu.dreamteam.Entities.Characters.Mobs.Forces;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Characters.OnCharacterEventListener;
import unice.etu.dreamteam.Entities.ForceEntity;
import unice.etu.dreamteam.Entities.Weapons.Weapon;
import unice.etu.dreamteam.Entities.Weapons.Weapons;

/**
 * Created by Dylan on 12/01/2017.
 */
public class ForceMobHolder extends ForceEntity {
    private float speed;
    private int proximityRange;
    private int detectionRange;

    private Weapon.Graphic weapon;

    private OnCharacterEventListener onCharacterEventListener;

    public ForceMobHolder(JsonValue value) {
        super(value);

        speed = value.getFloat("speed");
        proximityRange = value.getInt("proximity-range", 0);
        detectionRange = value.getInt("detection-range", 0);

        //weapon = Weapons.getInstance().get(value.getString("weapon-id")).create();
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

    public Weapon.Graphic getWeapon() {
        return weapon;
    }

    public void onKill() {
        //drop items and damage range
    }

    public OnCharacterEventListener getOnCharacterEventListener() {
        return onCharacterEventListener;
    }

    public void setOnCharacterEventListener(OnCharacterEventListener onCharacterEventListener) {
        this.onCharacterEventListener = onCharacterEventListener;
    }

    @Override
    public void render() {

    }
}
