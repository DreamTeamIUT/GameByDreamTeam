package unice.etu.dreamteam.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Character;
import unice.etu.dreamteam.Entities.Force;
import unice.etu.dreamteam.Entities.MobHolder;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Mob extends Character {
    private JsonValue informations;

    public Mob(MobHolder holder) {
        super(holder);
        //this.forces = holder.getForces();
    }
}
