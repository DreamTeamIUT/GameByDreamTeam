package unice.etu.dreamteam.Entities.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Utils.Debug;
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

        texture = new Texture(Gdx.files.internal(GameInformation.getGamePackage().getPackagePath("images/bullets") + value.get("tile").getString("src")));
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
        private Vector2 position;

        private float coefficient;
        private float intercept;

        private TextureRegion textureRegion;

        private Boolean finished;

        public Graphic(Bullet bullet, Vector2 source, Vector2 destination) {
            this.bullet = bullet;
            this.position = new Vector2(source.x, source.y);
            this.finished = false;

            textureRegion = new TextureRegion(bullet.getTexture());

            calculatePath(source, destination);
        }

        private void calculatePath(Vector2 source, Vector2 destination) {
            coefficient = (destination.y - source.y) / (destination.x - source.x);
            intercept = source.y - (coefficient * source.x);
        }

        private void updatePositions() {
            this.position.x += bullet.getSpeed();
            this.position.y = this.coefficient * this.position.x + this.intercept;
        }

        public Bullet getInformations() {
            return bullet;
        }

        public Boolean isFinished() {
            return finished;
        }

        public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, float delta) {
            Debug.log("Weapon.Graphic", "render");
            Debug.log("Weapon.Graphic", this.position.toString());
            //updatePositions();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 1f, 1);
            shapeRenderer.rect(position.x - 50, position.y + 50, 20, 20);
            shapeRenderer.end();

            /*
            spriteBatch.begin();
            spriteBatch.draw(this.textureRegion, this.position.x, this.position.y);
            spriteBatch.end();
            */
        }

        public void dispose() {

        }

        public Vector2 getPosition() {
            return position;
        }

        public void setPosition(float x, float y) {
            this.position.x = x;
            this.position.y = y;
        }
    }
}
