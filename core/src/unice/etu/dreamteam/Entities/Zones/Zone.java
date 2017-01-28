package unice.etu.dreamteam.Entities.Zones;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Characters.Mobs.Graphics.Mob;
import unice.etu.dreamteam.Entities.Characters.Mobs.Graphics.MobInstances;
import unice.etu.dreamteam.Entities.Characters.Mobs.Mobs;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Entities.Gates.Gate;
import unice.etu.dreamteam.Entities.Gates.Gates;
import unice.etu.dreamteam.Entities.Sounds.Sounds;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Zone extends Entity {

    private ArrayList<JsonValue> attacks;
    private ArrayList<JsonValue> gates;
    private ArrayList<String> sounds;

    public final int maxEnter;
    public final int maxExecute;

    public final Boolean general;
    public final Boolean blocked;

    private ZoneState zoneState;

    public Zone(JsonValue zone) {
        super(zone);

        loadAttacks(zone.get("attacks").iterator());
        loadGates(zone.get("gates").iterator());
        loadSounds(zone.get("sounds").iterator());

        this.maxEnter = zone.getInt("max-enter", -1);
        this.maxExecute = zone.getInt("max-execute", -1);

        this.general = zone.getBoolean("general");
        this.blocked = zone.getBoolean("blocked");

        this.zoneState = new ZoneState();
    }

    public void onEnter(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Debug.log("ZONE", "onEnter : " + getName());

        getZoneState().isIn = true;
        getZoneState().countEnter++;

        Debug.log("ZONE", "enter : " + String.valueOf(getZoneState().countEnter));

        if(getZoneState().countExecute < maxExecute || maxEnter < 0) {
            getZoneState().countExecute++;

            Debug.log("ZONE", "execute : " + String.valueOf(getZoneState().countExecute));

            triggerAttacks(spriteBatch, shapeRenderer);
            updateGates();
            playSounds();
        }
    }

    public void onLeave() {
        Debug.log("ZONE", "onLeave : " + getName());

        getZoneState().isIn = false;
    }

    public Boolean canEnter(Boolean general) {
        Debug.log("ZONE", getZoneState().countEnter + " " + maxEnter);

        return general ? this.general : getZoneState().countEnter < maxEnter || maxEnter < 0;
    }

    public Boolean canEnter() {
        return canEnter(false);
    }

    public Boolean canLeave() {
        return !blocked || !getZoneState().blocked;
    }

    public ZoneState getZoneState() {
        return zoneState;
    }

    public Boolean isIn() {
        return getZoneState().isIn;
    }

    public void setBlockedZone(Boolean is) {
        getZoneState().blocked = is;
    }

    private void loadAttacks(JsonValue.JsonIterator jsonIterator) {
        this.attacks = new ArrayList<>();

        for (JsonValue value : jsonIterator)
            this.attacks.add(value);
    }

    private void triggerAttacks(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        for (JsonValue value : this.attacks) {
            if (value.has("mob") && Mobs.getInstance().exist(value.getString("mob"))) {
                if(value.has("force"))
                    Mobs.getInstance().get(value.getString("mob")).setForce(value.getInt("force", 0));

                //TODO : set pos to mob by finding cases of the zone

                for (int i = 0; i < value.getInt("count", 1); i++)
                    MobInstances.getInstance().add((Mob)Mobs.getInstance().get(value.getString("mob")).create(spriteBatch, shapeRenderer));
            }
        }
    }

    private void loadGates(JsonValue.JsonIterator jsonIterator) {
        this.gates = new ArrayList<>();

        for (JsonValue value : jsonIterator)
            this.gates.add(value);
    }

    private void updateGates() {
        for (JsonValue value : this.gates) {
            if (Gates.getInstance().exist(value.name)) {
                Gate gate = Gates.getInstance().get(value.name);

                if(value.has("opened"))
                    gate.setOpened(value.getBoolean("opened", true));
            }
        }
    }

    private void loadSounds(JsonValue.JsonIterator sounds) {
        this.sounds = new ArrayList<>();

        for (JsonValue sound : sounds)
            this.sounds.add(sound.toString());
    }

    private void playSounds() {
        for (String sound : this.sounds)
            Sounds.getInstance().get(sound).play();
    }

    private class ZoneState {
        Boolean isIn = false;
        Boolean blocked = false;
        int countEnter = 0;
        int countExecute = 0;
    }
}
