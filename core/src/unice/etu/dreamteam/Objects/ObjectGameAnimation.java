package unice.etu.dreamteam.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Romain on 15/09/2016.
 */
public class ObjectGameAnimation {
    private Animation animation;
    private float stateTime = 0f;

    private int width;
    private int height;

    private int posX;
    private int posY;

    public ObjectGameAnimation(String path, int columns, int rows, int width, int height) {
        this.width = width;
        this.height = height;

        Texture texture = new Texture(Gdx.files.internal(path));
        TextureRegion[][] regions = TextureRegion.split(texture, texture.getWidth()/ columns, texture.getHeight() / rows);

        TextureRegion[] frames = new TextureRegion[columns * rows];

        int index = 0;

        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++)
                frames[index++] = regions[i][j];
        }

        animation = new Animation(0.025f, frames);
    }

    public void setPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void drawAnimation(SpriteBatch batch, float delta) {
        stateTime += delta;
        batch.draw(animation.getKeyFrame(stateTime, true), posX, posY);
    }
}
