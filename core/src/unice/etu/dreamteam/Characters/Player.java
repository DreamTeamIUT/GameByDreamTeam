package unice.etu.dreamteam.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Character;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Player extends Character {
    public Player(JsonValue informations) {
        super(informations);
    }

    public static Player create(JsonValue information, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        Player p = new Player(information);
        p.setBatch(batch);
        p.setShapeRenderer(shapeRenderer);
        p.setDebug(GameInformation.getDebugMode());
        p.getAnimationManager().setAnimation("STOPPED");
        return p;
    }
}
