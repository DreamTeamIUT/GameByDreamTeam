package unice.etu.dreamteam.Generic;

import unice.etu.dreamteam.Objects.ObjectGameAnimation;

/**
 * Created by Romain on 18/09/2016.
 */
public class IA extends Player {

    protected int level;

    public IA(String path, String name){
        super(path, name);
        path=path;
        this.level=1;
    }

    public void level() {
        this.level=super.getLevel();
    }


}
