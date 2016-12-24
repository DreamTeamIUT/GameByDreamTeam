package unice.etu.dreamteam.Entities;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Items extends EntitiesHolder<Item>{

    //TODO: items have image that should be printed
    //TODO: items have action when grabed
    //TODO: items may have time before desapearing

    public Items(){
        //TODO : Write better constructor
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
