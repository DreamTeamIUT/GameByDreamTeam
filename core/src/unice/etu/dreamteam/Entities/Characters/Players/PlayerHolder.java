package unice.etu.dreamteam.Entities.Characters.Players;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Characters.CharacterHolder;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Characters.OnCharacterEventListener;
import unice.etu.dreamteam.Entities.Characters.Players.Graphics.Player;
import unice.etu.dreamteam.Entities.Weapons.Weapons;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 27/12/2016.
 */
public class PlayerHolder extends CharacterHolder {
    private String realName;
    private Weapons weapons;

    private OnCharacterEventListener onCharacterEventListener;

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

    @Override
    public OnCharacterEventListener triggerEvent() {
        return this.onCharacterEventListener;
    }

    public void setOnCharacterEventListener(OnCharacterEventListener onCharacterEventListener) {
        this.onCharacterEventListener = onCharacterEventListener;
    }
}
