package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Character;
import unice.etu.dreamteam.Entities.Entity;

/**
 * Created by Guillaume on 27/12/2016.
 */
public abstract class CharacterHolder extends Entity {

    private String modelName;

    public CharacterHolder(JsonValue value){
        super(value);
        this.modelName = value.getString("modelName");
    }

    public abstract Character create(SpriteBatch batch, ShapeRenderer shapeRenderer );

    public String getModelName() {
        return modelName;
    }
}
