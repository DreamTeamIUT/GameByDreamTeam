package unice.etu.dreamteam.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Character;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Player extends Character {
    public Player(JsonValue informations, SpriteBatch spriteBatch) {
        super(informations, spriteBatch);
        getAnimationManager().setAnimation("STOPPED");
    }
}
