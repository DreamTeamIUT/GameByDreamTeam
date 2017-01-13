package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import unice.etu.dreamteam.Entities.Characters.Graphics.ModelAnimationManager;

/**
 * Created by Guillaume on 01/11/2016.
 */
public class ModelConverter {
    private final Environment environment;
    private final PerspectiveCamera camera;
    private final ModelAnimationManager modelAnimationManager;
    private final ModelBatch modelBatch;
    private FrameBuffer frameBuffer;
    private Texture texture;

    public ModelConverter(ModelAnimationManager modelAnimationManager) {
        this.modelAnimationManager = modelAnimationManager;

        camera = new PerspectiveCamera(67, GameInformation.getViewportWidth(), GameInformation.getViewportHeight());
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));


        camera.position.set(50f, 100f, 100f);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();


        modelAnimationManager.setAnimationScale(0.5f, 0.5f, 0.5f);
        modelAnimationManager.getAnimation().speed = 1f;

        modelBatch = new ModelBatch();


    }

    public void resize() {
        camera.viewportHeight = GameInformation.getViewportHeight();
        camera.viewportWidth = GameInformation.getViewportWidth();
        camera.update();
    }

    public void update(float delta) {
        camera.update();
        modelAnimationManager.getAnimationController().update(delta);
        if (frameBuffer != null)
            frameBuffer.dispose();
        if (texture != null)
            texture.dispose();

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    public void render() {
        frameBuffer.begin();

        modelBatch.begin(camera);
        modelBatch.render(modelAnimationManager.getModelInstance(), environment);
        modelBatch.end();

        frameBuffer.end();

        texture = frameBuffer.getColorBufferTexture();

    }

    public TextureRegion getCurrentTexture() {
        if (texture != null) {

            TextureRegion textureRegion = new TextureRegion(texture, frameBuffer.getWidth() / 2 - 80, frameBuffer.getHeight() / 2 - 15, frameBuffer.getWidth() / 2 - 160, frameBuffer.getHeight() / 2 - 140);
            textureRegion.flip(false, true);
            return textureRegion;
        }
        return null;
    }

    public void dispose() {
        if (texture != null)
            texture.dispose();
        if (modelBatch != null)
            modelBatch.dispose();
        if (frameBuffer != null)
            frameBuffer.dispose();
    }
}
