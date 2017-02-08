package unice.etu.dreamteam.Entities.Characters.Graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Guillaume on 07/02/2017.
 */
public class TwoDimensionalData implements Disposable {
    private TextureAtlas zAtlas;
    private TextureAtlas sAtlas;
    private TextureAtlas qAtlas;
    private TextureAtlas dAtlas;

    private float frameRate = 1 / 2;

    private Animation<TextureAtlas.AtlasRegion> animationZ;
    private Animation<TextureAtlas.AtlasRegion> animationS;
    private Animation<TextureAtlas.AtlasRegion> animationQ;
    private Animation<TextureAtlas.AtlasRegion> animationD;

    @Override
    public void dispose() {
    }

    public TextureAtlas getzAtlas() {
        return zAtlas;
    }

    public TextureAtlas getqAtlas() {
        return qAtlas;
    }

    public TextureAtlas getdAtlas() {
        return dAtlas;
    }

    public TextureAtlas getsAtlas() {
        return sAtlas;
    }

    public void setzAtlas(TextureAtlas zAtlas) {
        this.zAtlas = zAtlas;
        animationZ = new Animation<>(this.frameRate, this.zAtlas.getRegions());

    }

    public void setdAtlas(TextureAtlas dAtlas) {
        this.dAtlas = dAtlas;
        animationD = new Animation<>(this.frameRate, this.dAtlas.getRegions());

    }

    public void setqAtlas(TextureAtlas qAtlas) {
        this.qAtlas = qAtlas;
        animationQ = new Animation<>(this.frameRate, this.qAtlas.getRegions());

    }

    public void setsAtlas(TextureAtlas sAtlas) {
        this.sAtlas = sAtlas;
        animationS = new Animation<>(this.frameRate, this.sAtlas.getRegions());

    }

    public float getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(float frameRate) {
        this.frameRate = frameRate;
    }

    public Animation getAnimationZ() {
        return animationZ;
    }

    public Animation getAnimationS() {
        return animationS;
    }

    public Animation getAnimationQ() {
        return animationQ;
    }

    public Animation getAnimationD() {
        return animationD;
    }
}
