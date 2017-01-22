package unice.etu.dreamteam.Entities.Bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Entities.Weapons.Weapon;

/**
 * Created by Dylan on 12/01/2017.
 */
public class Bullet extends Entity {
    private float speed;
    private int range;
    private int damage;

    public Bullet(JsonValue value) {
        super(value);

        speed = value.getFloat("speed", 0);
        range = value.getInt("range", 0);
        damage = value.getInt("damage", 0);
    }

    public Bullet.Graphic create(Vector2 source, Vector2 destination) {
        return new Bullet.Graphic(this, source, destination);
    }

    public float getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public class Graphic {
        private Bullet bullet;
        private Vector2 positions;

        private Boolean finished;

        public Graphic(Bullet bullet, Vector2 source, Vector2 destination) {
            this.bullet = bullet;
            this.finished = false;

            calculatePath(source, destination);
        }

        public void calculatePath(Vector2 source, Vector2 destination) {

        }

        public Bullet getInformations() {
            return bullet;
        }

        public Boolean isFinished() {
            return finished;
        }

        public void render(SpriteBatch spriteBatch, float delta) {

        }

        public void dispose() {

        }

        public Vector2 getPositions() {
            return positions;
        }

        public void setPositions(float x, float y) {
            this.positions.x = x;
            this.positions.y = y;
        }
    }
}
