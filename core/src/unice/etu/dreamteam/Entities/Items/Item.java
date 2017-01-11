package unice.etu.dreamteam.Entities.Items;

import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Item extends Entity {

    //TODO: items have image that should be printed
    //TODO : Add properties ...

    public Item(JsonValue value){
        super(value);
    }

    private void loadItemDeps(){

    }

    public boolean isAlive(){
        return false;
    }

    public long getRemainingTime(){
        return 0;
    }

    public void onGrab(){

    }

    public void onThrown(){

    }


}
