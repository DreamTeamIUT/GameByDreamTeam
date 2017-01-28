package unice.etu.dreamteam.Entities.Characters.Mobs.Graphics;

import java.util.ArrayList;

/**
 * Created by Dylan on 28/01/2017.
 */
public class MobInstances extends ArrayList<Mob> {
    private static MobInstances mobInstances;

    private MobInstances() {
        super();
    }

    public static MobInstances getInstance() {
        if(mobInstances == null)
            mobInstances = new MobInstances();

        return mobInstances;
    }
}
