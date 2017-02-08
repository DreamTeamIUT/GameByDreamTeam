package unice.etu.dreamteam.Entities.Characters.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 07/02/2017.
 */
public class TriDimensionalModel implements Model, Disposable {

    private final Environment environment;
    private final PerspectiveCamera camera;
    private final ModelAnimationManager modelAnimationManager;
    private final ModelBatch modelBatch;
    private FrameBuffer frameBuffer;
    private Texture texture;
    private Vector3 vector3;


    public TriDimensionalModel(String modelName){
        this.modelAnimationManager = new ModelAnimationManager(modelName);

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

        vector3 = new Vector3();
    }

    @Override
    public void setAnimation(String name) {
        this.modelAnimationManager.setAnimation(name);
    }

    @Override
    public void resize(float width, float height) {
        camera.viewportHeight = GameInformation.getViewportHeight();
        camera.viewportWidth = GameInformation.getViewportWidth();
        camera.update();
    }

    @Override
    public void update(float delta) {
        camera.update();
        modelAnimationManager.getAnimationController().update(delta);

        if (frameBuffer != null)
            frameBuffer.dispose();
        if (texture != null)
            texture.dispose();

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

        frameBuffer.begin();

        modelBatch.begin(camera);
        modelBatch.render(modelAnimationManager.getModelInstance(), environment);
        modelBatch.end();

        frameBuffer.end();

        texture = frameBuffer.getColorBufferTexture();

    }

    @Override
    public void setRotation(float angle) {
        this.modelAnimationManager.setRotation(angle);
    }

    @Override
    public TextureRegion getFrame() {
        if (texture != null) {

            TextureRegion textureRegion = new TextureRegion(texture, frameBuffer.getWidth() / 2 - 80, frameBuffer.getHeight() / 2 - 15, frameBuffer.getWidth() / 2 - 160, frameBuffer.getHeight() / 2 - 140);
            textureRegion.flip(false, true);
            return textureRegion;
        }
        return null;
    }

    @Override
    public void dispose() {
        if (texture != null)
            texture.dispose();
        if (modelBatch != null)
            modelBatch.dispose();
        if (frameBuffer != null)
            frameBuffer.dispose();
    }
}
