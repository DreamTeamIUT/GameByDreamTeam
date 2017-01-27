package unice.etu.dreamteam.Entities.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Entities.GamesPackages.GamePackage;
import unice.etu.dreamteam.Entities.Weapons.Weapon;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Dylan on 12/01/2017.
 */
public class Bullet extends Entity {
    private float speed;
    private int range;
    private int damage;

    private Texture texture;

    public Bullet(JsonValue value) {
        super(value);

        speed = value.getFloat("speed", 0);
        range = value.getInt("range", 0);
        damage = value.getInt("damage", 0);

        texture = new Texture(Gdx.files.internal(GameInformation.getGamePackage().getPackagePath("images") + value.get("tile").getString("src")));
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

    public Texture getTexture() {
        return texture;
    }

    public class Graphic {
        private Bullet bullet;
        private Vector2 positions;

        private float coefficient;
        private float intercept;

        private TextureRegion textureRegion;

        private Boolean finished;

        public Graphic(Bullet bullet, Vector2 source, Vector2 destination) {
            this.bullet = bullet;
            this.positions = new Vector2(source.x, source.y);
            this.finished = false;

            textureRegion = new TextureRegion(bullet.getTexture());

            calculatePath(source, destination);
        }

        private void calculatePath(Vector2 source, Vector2 destination) {
            coefficient = (destination.y - source.y) / (destination.x - source.x);
            intercept = source.y - (coefficient * source.x);
        }

        private void updatePositions() {
            this.positions.x += bullet.getSpeed();
            this.positions.y = this.coefficient * this.positions.x + this.intercept;
        }

        public Bullet getInformations() {
            return bullet;
        }

        public Boolean isFinished() {
            return finished;
        }

        public void render(SpriteBatch spriteBatch, float delta) {
            updatePositions();

            spriteBatch.begin();
            spriteBatch.draw(this.textureRegion, this.positions.x, this.positions.y);
            spriteBatch.end();
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
