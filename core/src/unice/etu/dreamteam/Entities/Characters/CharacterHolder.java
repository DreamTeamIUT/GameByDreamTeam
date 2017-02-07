package unice.etu.dreamteam.Entities.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;

/**
 * Created by Guillaume on 27/12/2016.
 */
public abstract class CharacterHolder extends Entity {
    private String modelName;
    private int healthPoint;

    private float speed;
    private float speedRun;

    private Sounds sounds;

    public CharacterHolder(JsonValue value){
        super(value);

        this.modelName = value.getString("model-name");
        this.healthPoint = value.getInt("health-point");

        this.speed = value.getFloat("speed", 0.07f);
        this.speedRun = value.getFloat("speed-run", 0.09f);

        if (value.has("sounds")) {
            sounds = new Sounds();
            sounds.setRun(value.get("sounds").getString("RUN", "NONE"));
        }
    }

    public abstract Character create(SpriteBatch batch, ShapeRenderer shapeRenderer);

    public String getModelName() {
        return modelName;
    }

    public int getHealthPoint(){ return healthPoint; }

    public float getSpeed() {
        return speed;
    }

    public float getSpeedRun() {
        return speedRun;
    }

    public Sounds getSounds() {
        return sounds;
    }

    public abstract OnCharacterEventListener triggerEvent();

    public class Sounds {
        private String run;

        public Sounds() {
            run = "NONE";
        }

        public String getRun() {
            return run;
        }

        public void setRun(String run) {
            this.run = run;
        }
    }
}
