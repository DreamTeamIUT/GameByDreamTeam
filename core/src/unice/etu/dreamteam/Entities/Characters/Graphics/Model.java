package unice.etu.dreamteam.Entities.Characters.Graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Guillaume on 07/02/2017.
 */
public interface Model{
    void setAnimation(String name);
    void resize(float width, float height);
    void update(float delta);
    void setRotation(float angle);
    void dispose();
    TextureRegion getFrame();

}
