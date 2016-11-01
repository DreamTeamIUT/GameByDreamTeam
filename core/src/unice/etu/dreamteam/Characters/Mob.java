package unice.etu.dreamteam.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Character;
import unice.etu.dreamteam.Entities.Force;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Mob extends Character {
    private Force force;
    private JsonValue informations;

    public Mob(JsonValue informations, SpriteBatch spriteBatch) {
        super(informations, spriteBatch);
        this.informations = informations;
    }

    public void setForce(String forceId) {
        this.force = new Force(informations.get(forceId));
    }

    public Force getForce() {
        return force;
    }
}
