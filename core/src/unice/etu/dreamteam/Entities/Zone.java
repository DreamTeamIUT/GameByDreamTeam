package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.Debug;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Zone extends Entity{

    private final ArrayList<JsonValue> attacks;
    private final boolean isOneTime;
    private final String sound;
    private ZoneState currentZoneState;

    public Zone(JsonValue zone) {
        super(zone);
        this.currentZoneState = new ZoneState();
        this.sound = zone.getString("sound", null);
        this.isOneTime = zone.getBoolean("onetime", false);
        this.attacks = new ArrayList<JsonValue>();
        //TODO : improve the way of storing attacks ?
        this.attacks.add(zone.get("attack"));
    }

    public void onEnter(){
        Debug.log("Entering in zone :" + getName());
    }

    public void onExit(){
        Debug.log("Exiting zone :" + getName());
    }

    public Boolean canEnter(){
        return false;
    }

    public Boolean canExit(){
        return false;
    }

    public ZoneState getCurrentZoneState() {
        return currentZoneState;
    }

    private class ZoneState{
        public Boolean isplayerIn = false;
        public Boolean isZoneDone = false;
        public int playerTravels = 0;
    }


}
