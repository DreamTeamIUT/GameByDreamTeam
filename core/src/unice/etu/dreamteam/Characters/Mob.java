package unice.etu.dreamteam.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Character;
import unice.etu.dreamteam.Entities.Force;
import unice.etu.dreamteam.Entities.MobHolder;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Mob extends Character {
    private JsonValue informations;

    public Mob(MobHolder holder) {
        super(holder);
        //this.forces = holder.getForces();
    }

    @Override
    protected void drawDebug() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0.5f, 1);
        shapeRenderer.rect(getRectangle().x - 16, getRectangle().y + 16, getRectangle().getWidth(), getRectangle().getHeight());
        shapeRenderer.setColor(0.5f, 0, 1f, 1);
        shapeRenderer.rect(getRectangle().x - 16 - 2 * 16, getRectangle().y + 16 + 2 * 16, getRectangle().getWidth(), getRectangle().getHeight());
        shapeRenderer.end();
    }
}
