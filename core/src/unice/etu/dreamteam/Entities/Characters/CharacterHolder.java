package unice.etu.dreamteam.Entities.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;

/**
 * Created by Guillaume on 27/12/2016.
 */
public abstract class CharacterHolder extends Entity {

    private String modelName;

    public CharacterHolder(JsonValue value){
        super(value);
        this.modelName = value.getString("model-name");
    }

    public abstract Character create(SpriteBatch batch, ShapeRenderer shapeRenderer);

    public String getModelName() {
        return modelName;
    }
}
