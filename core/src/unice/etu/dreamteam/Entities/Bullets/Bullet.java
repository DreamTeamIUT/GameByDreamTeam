package unice.etu.dreamteam.Entities.Bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Map.Assets;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

import java.util.ArrayList;

/**
 * Created by Dylan on 12/01/2017.
 */
public class Bullet extends Entity {
    private float speed;
    private int range;
    private int damage;
    private int life;

    private Texture texture;

    public Bullet(JsonValue value) {
        super(value);

        speed = value.getFloat("speed", 0);
        range = value.getInt("range", 0);
        damage = value.getInt("damage", 0);
        life = value.getInt("life", -1);

        texture = Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath("images/bullets") + value.get("tile").getString("src"), Texture.class);

        Debug.log("BULLET", texture.getHeight() +" " + texture.getWidth());
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

    public int getLife() {
        return life;
    }

    public class Graphic {
        private Bullet bullet;

        private Vector2 position;

        private float coefficient;
        private float intercept;

        private TextureRegion textureRegion;

        private int life;
        private ArrayList<String> characterIds;

        private Boolean touched;
        private Boolean finished;

        public Graphic(Bullet bullet, Vector2 source, Vector2 destination) {
            this.bullet = bullet;
            this.position = new Vector2(source.x, source.y);

            this.life = bullet.getLife();
            this.characterIds = new ArrayList<>();

            this.touched = false;
            this.finished = false;

            textureRegion = new TextureRegion(bullet.getTexture());

            Debug.log("BULLET", textureRegion.getRegionHeight() +" " + texture.getWidth());

            calculatePath(source, destination);
        }

        private void calculatePath(Vector2 source, Vector2 destination) {
            coefficient = (destination.y - source.y) / (destination.x - source.x);
            intercept = source.y - (coefficient * source.x);
        }

        private void updatePositions() {
            this.position.x -= bullet.getSpeed();
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

            if (!touched) {
                updatePositions();

                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(1, 0, 1f, 1);
                shapeRenderer.rect(position.x - 50, position.y + 50, 20, 20);
                shapeRenderer.end();
            }
            else
                dispose();

            /*
            spriteBatch.begin();
            spriteBatch.draw(bullet.getTexture(), this.position.x, this.position.y);
            spriteBatch.end();
            */
        }

        public Rectangle getRectangle() {
            return new Rectangle().set(this.position.x, this.position.y, 32, 32);
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

        public Boolean isTouched() {
            return touched;
        }

        public void haveCollision(String characterId) {
            this.characterIds.add(characterId);
            this.life--;

            if(this.life < 0)
                this.touched = true;
        }

        public Boolean alreadyTouched(String characterId) {
            return this.characterIds.contains(characterId);
        }

        public int getDamage() {
            return bullet.getDamage();
        }
    }
}