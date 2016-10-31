package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.ModelAnimationManager;
import unice.etu.dreamteam.Utils.Debug;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Character {

    private ModelAnimationManager animationManager;
    private Vector2 cellPos;
    private Vector2 realPos;

    public Character(JsonValue informations) {
        Debug.log("Load character");
        cellPos = new Vector2(0, 0);
        animationManager = new ModelAnimationManager(informations.getString("modelName", null));

    }

    public void dispose() {
        animationManager.dispose();
    }

    public Vector2 getRealPos() {
        return realPos;
    }

    public void setRealPos(Vector2 realPos) {
        this.realPos = realPos;
    }

    public void setRealPos(float x, float y) {
        this.realPos.x = x;
        this.realPos.y = y;
    }

    public Vector2 getCellPos() {
        return cellPos;
    }

    public void setCellPos(Vector2 cellPos) {
        this.cellPos = cellPos;
    }

    public void setCellPos(int x, int y) {
        this.cellPos.x = x;
        this.cellPos.y = y;
    }

    public Texture get2DModel() throws Exception {
        throw new Exception("Please define the function befor using it !");
    }
}
