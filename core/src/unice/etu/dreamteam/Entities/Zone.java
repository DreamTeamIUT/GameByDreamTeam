package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Zone {
    private final ArrayList<JsonValue> attacks;
    private final String name;
    private final boolean isOneTime;
    private final String sound;

    public Zone(JsonValue zone) {
        this.name = zone.name;
        this.sound = zone.getString("sound", null);
        this.isOneTime = zone.getBoolean("onetime", false);
        this.attacks = new ArrayList<JsonValue>();
        this.attacks.add(zone.get("attack"));
    }
}
