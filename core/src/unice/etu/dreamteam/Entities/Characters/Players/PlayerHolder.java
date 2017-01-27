package unice.etu.dreamteam.Entities.Characters.Players;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Characters.CharacterHolder;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Characters.OnCharacterEventListener;
import unice.etu.dreamteam.Entities.Characters.Players.Graphics.Player;
import unice.etu.dreamteam.Entities.Weapons.Weapon;
import unice.etu.dreamteam.Entities.Weapons.Weapons;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

import java.util.ArrayList;

/**
 * Created by Guillaume on 27/12/2016.
 */
public class PlayerHolder extends CharacterHolder {
    private String realName;
    private ArrayList<String> weapons;

    private OnCharacterEventListener onCharacterEventListener;

    public PlayerHolder(JsonValue value){
        super(value);
        this.realName = value.getString("real-name");

        loadWeapons(value.get("weapons").iterator());
    }

    @Override
    public Character create(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        Player p = new Player(this);
        p.setBatch(batch);
        p.setShapeRenderer(shapeRenderer);
        p.setDebug(GameInformation.getDebugMode());
        p.getAnimationManager().setAnimation("STOPPED");
        p.setWeapon("weapon01");

        return p;
    }

    public String getRealName() {
        return realName;
    }

    private void loadWeapons(JsonValue.JsonIterator valueWeapons) {
        Debug.log("PlayerHolder", "loadWeapons");

        weapons = new ArrayList<>();

        for (JsonValue value : valueWeapons) {
            Debug.log("PlayerHolder", value.toString());
            //weapons.add(Weapons.getInstance().get(value.toString()));
            weapons.add(value.toString());
        }
    }

    public ArrayList<Weapon> getWeapons() {
        ArrayList<Weapon> realWeapons = new ArrayList<>();

        for (String weapon : weapons)
            realWeapons.add(Weapons.getInstance().get(weapon));

        return realWeapons;
    }

    @Override
    public OnCharacterEventListener triggerEvent() {
        return this.onCharacterEventListener;
    }

    public void setOnCharacterEventListener(OnCharacterEventListener onCharacterEventListener) {
        this.onCharacterEventListener = onCharacterEventListener;
    }
}
