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
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;

/**
 * Created by Dylan on 11/01/2017.
 */
public class Weapon extends Entity {
    private ForcesWeaponHolder forces;

    public Weapon(JsonValue value) {
        super(value);

        Debug.log("Entity", value.toString());
        Debug.log("Entity", "name : " + value.getString("name"));

        Debug.log("Weapon", "new");

        forces = new ForcesWeaponHolder(value.get("forces").iterator());
    }

    public Graphic create(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, int powerful) {
        if(forces.areEnough())
            return new Graphic(spriteBatch, shapeRenderer, getForce(powerful));

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

        private Vector2 elementPosition;
        private Vector2 position;

        private ForceWeaponHolder forceWeaponHolder;

        private ArrayList<Bullet.Graphic> bullets;

        public Graphic(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, ForceWeaponHolder forceWeaponHolder) {
            this.spriteBatch = spriteBatch;
            this.shapeRenderer = shapeRenderer;

            this.position = new Vector2(0, 0);
            this.forceWeaponHolder = forceWeaponHolder;

            bullets = new ArrayList<>();
        }

        public void render(float delta) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 1f, 1);
            shapeRenderer.rect(position.x - 16, position.y + 16, 20, 20);
            shapeRenderer.setColor(0, 0, 1f, 1);
            shapeRenderer.rect(position.x - 16 - 2 * 16, position.y + 16 + 2 * 16, 20, 20);
            shapeRenderer.end();

            for (Bullet.Graphic bullet : bullets) {
                bullet.render(getSpriteBatch(), shapeRenderer, delta);

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

        public void setElementPosition(Vector2 elementPosition) {
            this.elementPosition = elementPosition;
        }

        public void setPositions(float x, float y) {
            this.position.x = x + getForce().getShift().x;
            this.position.y = y + getForce().getShift().y;
        }

        public ForceWeaponHolder getForce() {
            return forceWeaponHolder;
        }

        public void shoot(Character source, Character destination) {
            shoot(source.getCellPos(), destination.getCellPos());
        }

        public void shoot(Vector2 source, Vector2 destination) {
            Debug.log("Weapon", "shoot");
            Debug.log("Weapon", String.valueOf(Bullets.getInstance().size()));

            bullets.add(Bullets.getInstance().get(getForce().getBulletId()).create(source, destination));
        }

        public void shoot(Vector2 destination) {
            shoot(this.elementPosition, destination);
        }
    }
}
