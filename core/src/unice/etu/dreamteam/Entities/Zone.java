package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.Debug;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Zone extends Entity {

    private final ArrayList<JsonValue> attacks;
    private final String sound;

    public final int maxEnter;
    public final int maxExecute;

    public final Boolean general;
    public final Boolean blocked;

    private ZoneState zoneState;

    public Zone(JsonValue zone) {
        super(zone);
        this.zoneState = new ZoneState();
        this.sound = zone.getString("sound", null);
        this.attacks = new ArrayList<JsonValue>();
        //TODO : improve the way of storing attacks ?
        this.attacks.add(zone.get("attack"));

        this.maxEnter = zone.getInt("maxEnter");
        this.maxExecute = zone.getInt("maxExecute");

        this.general = zone.getBoolean("general");
        this.blocked = zone.getBoolean("blocked");
    }

    public void onEnter() {
        Debug.log("Entering in zone :" + getName());

        getZoneState().isIn = true;
        getZoneState().countEnter++;

        if(getZoneState().countExecute < maxExecute)
            Debug.log("Execute script ...");
    }

    public void onLeave() {
        Debug.log("Exiting zone :" + getName());

        getZoneState().isIn = false;
    }

    public Boolean canEnter(Boolean general) {
        Debug.log(getZoneState().countEnter + " " + maxEnter);

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

    private class ZoneState {
        Boolean isIn = false;
        Boolean blocked = false;
        int countEnter = 0;
        int countExecute = 0;
    }


}
