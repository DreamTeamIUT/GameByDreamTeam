package unice.etu.dreamteam.Entities.Characters.Mobs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Characters.CharacterHolder;
import unice.etu.dreamteam.Entities.Forces.Forces;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Characters.Mobs.Graphics.Mob;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 27/12/2016.
 */
public class MobHolder extends CharacterHolder {
    private Forces forces;

    public MobHolder(JsonValue value){
        super(value);
        //TODO : Load Forces as Forces object !

        Debug.log("MobHolder", "start");

        forces = new Forces(value.get("forces").iterator());
    }

    @Override
    public Character create(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        Mob mob = new Mob(this);
        mob.setBatch(batch);

        mob.setShapeRenderer(shapeRenderer);
        mob.setDebug(GameInformation.getDebugMode());
       // mob.getAnimationManager().setAnimation("STOPPED");
        return mob;
    }

}
