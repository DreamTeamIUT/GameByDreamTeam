package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.Character;
import unice.etu.dreamteam.Characters.Player;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 27/12/2016.
 */
public class PlayerHolder extends CharacterHolder {

    private String realName;


    public PlayerHolder(JsonValue value){
        super(value);
        this.realName = value.getString("real-name");
    }

    @Override
    public Character create(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        Player p = new Player(this);
        p.setBatch(batch);
        p.setShapeRenderer(shapeRenderer);
        p.setDebug(GameInformation.getDebugMode());
       // p.getAnimationManager().setAnimation("STOPPED");
        return p;
    }

    public String getRealName() {
        return realName;
    }
}
