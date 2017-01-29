package unice.etu.dreamteam.Entities.Characters.Mobs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Characters.CharacterHolder;
import unice.etu.dreamteam.Entities.Characters.Mobs.Forces.ForceMobHolder;
import unice.etu.dreamteam.Entities.Characters.Mobs.Forces.ForcesMobHolder;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Characters.Mobs.Graphics.Mob;
import unice.etu.dreamteam.Entities.Characters.OnCharacterEventListener;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 27/12/2016.
 */
public class MobHolder extends CharacterHolder {
    private ForcesMobHolder forces;
    private int currentPowerful;

    public MobHolder(JsonValue value){
        super(value);
        //TODO : Load Forces as Forces object !

        Debug.log("MobHolder", "start");

        forces = new ForcesMobHolder(value.get("forces").iterator());
        currentPowerful = forces.getDefaultPowerful();

        Debug.log("MobHolder", String.valueOf(forces.getDefaultPowerful()));
        Debug.log("MobHolder", String.valueOf(forces.areEnough()));
        Debug.log(String.valueOf(forces.size()));
    }

    public ForceMobHolder getForce() {
        return forces.areEnough() ? forces.get(currentPowerful) : null;
    }

    public void setForce(int powerful) {
        if(forces.areEnough())
            currentPowerful = forces.existPowerful(powerful) ? powerful : forces.getDefaultPowerful();
    }

    @Override
    public OnCharacterEventListener triggerEvent() {
        if(forces.areEnough())
            return getForce().getOnCharacterEventListener();
        else
            return null;
    }

    @Override
    public Character create(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if(forces.areEnough()) {
            Mob mob = new Mob(this);
            mob.setBatch(batch);
            mob.setShapeRenderer(shapeRenderer);
            mob.setDebug(GameInformation.getDebugMode());
            mob.getAnimationManager().setAnimation("STOPPED");

            return mob;
        }

        return null;
    }
}
