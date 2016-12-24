package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.Debug;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Zone {

    //TODO : zone can be "un-exit-able"

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

    public String getName() {
        return name;
    }

    public void onEnter(){
        Debug.log("Entering in zone :" + this.name);
    }

    public void onExit(){
        Debug.log("Exiting zone :" + this.name);
    }

    public Boolean canEnter(){
        return false;
    }

    public Boolean canExit(){
        return false;
    }


}
