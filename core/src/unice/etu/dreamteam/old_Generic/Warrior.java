package unice.etu.dreamteam.old_Generic;

/**
 * Created by Romain on 17/09/2016.
 */
public class Warrior extends Player{

    public Warrior(String path,String name) {
        super(path,name);
    }

    public void levelUp() {
        super.levelUp(2,4,1,40,20);
    }

}
