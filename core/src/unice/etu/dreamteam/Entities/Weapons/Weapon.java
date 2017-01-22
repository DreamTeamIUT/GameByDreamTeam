package unice.etu.dreamteam.Entities.Weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Bullets.Bullet;
import unice.etu.dreamteam.Entities.Bullets.Bullets;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Entities.Weapons.Forces.ForceWeaponHolder;
import unice.etu.dreamteam.Entities.Weapons.Forces.ForcesWeaponHolder;

import java.util.ArrayList;

/**
 * Created by Dylan on 11/01/2017.
 */
public class Weapon extends Entity {
    private ForcesWeaponHolder forces;

    public Weapon(JsonValue value) {
        super(value);

        forces = new ForcesWeaponHolder(value.get("forces").iterator());
    }

    public Graphic create(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, int powerful) {
        if(forces.areEnough()) {
            Graphic graphic = new Graphic(getForce(powerful));
            graphic.setSpriteBatch(spriteBatch);
            graphic.setShapeRenderer(shapeRenderer);

            return graphic;
        }

        return null;
    }

    public Graphic create(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        return create(spriteBatch, shapeRenderer, -1);
    }

    public ForceWeaponHolder getForce(int powerful) {
        if(forces.areEnough())
            return forces.get(forces.existPowerful(powerful) ? powerful : forces.getDefaultPowerful());

        return null;
    }

    public class Graphic {
        private SpriteBatch spriteBatch;
        private ShapeRenderer shapeRenderer;

        private Vector2 positions;

        private ForceWeaponHolder forceWeaponHolder;

        private ArrayList<Bullet.Graphic> bullets;

        public Graphic(ForceWeaponHolder forceWeaponHolder) {
            this.positions = new Vector2(0, 0);
            this.forceWeaponHolder = forceWeaponHolder;
        }

        public void render(float delta) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 1f, 1);
            shapeRenderer.rect(positions.x - 16, positions.y + 16, 20, 20);
            shapeRenderer.setColor(0, 0, 1f, 1);
            shapeRenderer.rect(positions.x - 16 - 2 * 16, positions.y + 16 + 2 * 16, 20, 20);
            shapeRenderer.end();

            for (Bullet.Graphic bullet : bullets) {
                bullet.render(getSpriteBatch(), delta);

                if (bullet.isFinished()) {
                    bullet.dispose();
                    bullets.remove(bullet);
                }
            }
        }

        private SpriteBatch getSpriteBatch() {
            return spriteBatch;
        }

        public void setSpriteBatch(SpriteBatch spriteBatch) {
            this.spriteBatch = spriteBatch;
        }

        public void setShapeRenderer(ShapeRenderer shapeRenderer) {
            this.shapeRenderer = shapeRenderer;
        }

        public void setPositions(float x, float y) {
            this.positions.x = x + getForce().getShift().x;
            this.positions.y = y + getForce().getShift().y;
        }

        public ForceWeaponHolder getForce() {
            return forceWeaponHolder;
        }

        public void shoot(Character source, Character destination) {
            bullets.add(Bullets.getInstance().get(getForce().getBulletId()).create(source.getCellPos(), destination.getCellPos()));
        }
    }
}
