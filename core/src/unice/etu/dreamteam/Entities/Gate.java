package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Gate {

    //TODO : gates haves start point and a destination point
    //TODO : gates can be closed
    //TODO : gates can disapear

    public Gate(JsonValue gate) {

    }

    public Boolean isOpen(){
        return false;
    }

    public Boolean isAlive(){
        return false;
    }

    public long getRemainingTime(){
        return 0;
    }

    public void onPass(){

    }
}
