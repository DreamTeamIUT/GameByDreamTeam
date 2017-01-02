package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.Character;
import unice.etu.dreamteam.Utils.Debug;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Gate extends Entity{
    private final String nextGate;
    private final GateState gateState;

    //TODO : gates haves start point and a destination point
    //TODO : gates can be closed
    //TODO : gates can disapear

    public Gate(JsonValue value) {
        super(value);
        Debug.log(this.getName());
        nextGate = value.getString("goto");
        gateState = new GateState();
        gateState.isOpen = value.getBoolean("isOpen");
    }

    public Boolean isOpen(){
        return getGateState().isOpen;
    }

    public Boolean isAlive(){
        return false;
    }

    public long getRemainingTime(){
        return 0;
    }

    public void onPass(Character p){
        getGateState().countPass++;
        p.setCell(0,0);
    }

    public GateState getGateState() {
        return gateState;
    }

    public class GateState{
        public boolean isOpen = false;
        public int countPass =0;
    }
}
