package unice.etu.dreamteam.old_Generic;

/**
 * Created by Romain on 17/09/2016.
 */
public class Bowman extends Player{

    public Bowman(String path, String name) {
        super(path,name);
    }

    public void levelUp() {
        super.levelUp(3,2,2,30,30);
    }



}
