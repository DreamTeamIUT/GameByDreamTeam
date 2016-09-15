package unice.etu.dreamteam.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Romain on 15/09/2016.
 */
public class ObjectGame {
    private Sprite sprite;

    private int width;
    private int height;

    private int posX;
    private int posY;

    public ObjectGame(String path) {
        width = 50;
        height = 50;

        posX = 0;
        posY = 0;

        sprite = new Sprite(new Texture(Gdx.files.internal(path)), width, height);
    }

    public void setPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        sprite.setPosition(posX, posY);
    }

    public void drawSprite(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
