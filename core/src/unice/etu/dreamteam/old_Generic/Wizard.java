package unice.etu.dreamteam.old_Generic;

/**
 * Created by Romain on 17/09/2016.
 */
public class Wizard extends Player{

    public Wizard(String path, String name) {
        super(path,name);
    }

    public void levelUp() {
        super.levelUp(2,1,4,20,40);
    }


}
