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

    public Graphic create(int powerful) {
        if(forces.areEnough())
            return new Graphic(getForce(powerful));

        return null;
    }

    public Graphic create() {
        return create(-1);
    }

    public ForceWeaponHolder getForce(int powerful) {
        if(forces.areEnough())
            return forces.get(forces.existPowerful(powerful) ? powerful : forces.getDefaultPowerful());

        return null;
    }

    public class Graphic {
        private Vector2 position;

        private ForceWeaponHolder forceWeaponHolder;

        private ArrayList<Bullet.Graphic> bullets;

        public Graphic(ForceWeaponHolder forceWeaponHolder) {
            this.position = new Vector2(0, 0);
            this.forceWeaponHolder = forceWeaponHolder;

            bullets = new ArrayList<>();
        }

        public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, float delta) {
            //Debug.log("WEAPON", "render");

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 1f, 1);
            shapeRenderer.rect((position.x * 32) - 64, (position.y * 32) + 64, 32, 32);
            shapeRenderer.end();

            for (Bullet.Graphic bullet : bullets) {
                bullet.render(spriteBatch, shapeRenderer, delta);

                if (bullet.isFinished()) {
                    bullet.dispose();
                    bullets.remove(bullet);
                }
            }
        }

        public void setPosition(Vector2 position) {
            this.position.x = position.x + getForce().getShift().x;
            this.position.y = position.y + getForce().getShift().y;
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
            shoot(this.position, destination);
        }
    }
}
