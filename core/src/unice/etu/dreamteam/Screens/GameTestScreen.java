package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import unice.etu.dreamteam.Characters.CharacterList;
import unice.etu.dreamteam.Characters.ModelAnimationManager;
import unice.etu.dreamteam.Utils.Debug;

import java.awt.geom.AffineTransform;

/**
 * Created by Guillaume on 26/09/2016.
 */
public class GameTestScreen extends AbstractScreen implements InputProcessor {
    private ModelBatch modelBatch;
    private FrameBuffer frameBuffer;
    private PerspectiveCamera camera;
    private Environment environment;
    private ModelAnimationManager demonCharacter;
    private Texture texture;
    private TextureRegion textureRegion;
    private CameraInputController camController;
    private TiledMap map;
    private OrthographicCamera globCam;
    private IsometricTiledMapRenderer renderer;
    private SpriteBatch spriteBatch;
    private float mapCoefX = -3f;
    private float mapCoefY = -1.5f;
    private float anglePerso = 0;
    private float totalTranslateX, totalTranslateY;
    private boolean touchedDown = false;
    private ShapeRenderer shapeRenderer;


    public GameTestScreen() {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void buildStage() {

        globCam = (OrthographicCamera) getCamera();

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));

        camera.position.set(50f, 100f, 100f);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();

        camController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(camController);


        demonCharacter = new ModelAnimationManager(CharacterList.KNIGHT);
        demonCharacter.setAnimationScale(0.5f, 0.5f, 0.5f);
        demonCharacter.setAnimation("Run");
        //demonCharacter.setAnimation("Walk", 1).setAnimation("Run", 1).setAnimation("idle", 1);
        demonCharacter.getAnimation().speed = 1f;


        map = new TmxMapLoader().load("assets/map/test.tmx"); //permet de charger la map depuis le fichier fournis en paramètre et réaliser sur tiled.


       /* int mapHeight = map.getProperties().get("height", Integer.class);
        int mapWidth = map.getProperties().get("width", Integer.class);
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);*/

        //globCam.setToOrtho(false, mapWidth * tileWidth, mapHeight * tileHeight);
        globCam.zoom = 1f;
        globCam.update();

        spriteBatch = new SpriteBatch();


        renderer = new IsometricTiledMapRenderer(map, spriteBatch);


        modelBatch = new ModelBatch();
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, this.getViewport().getScreenWidth(), this.getViewport().getScreenHeight(), false);


        shapeRenderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);


        if (touchedDown) {
            double radian = (Math.PI * anglePerso) / 180;
            totalTranslateX += -((float) Math.sin(radian) * mapCoefX);
            totalTranslateY += ((float) Math.cos(radian) * mapCoefY);
            globCam.translate(-((float) Math.sin(radian) * mapCoefX), ((float) Math.cos(radian) * mapCoefY));
            //Debug.log(radian + " " + Math.cos(radian) + " " + Math.sin(radian));
            // Debug.log(totalTranslateX + " " + totalTranslateY);
        }


        globCam.update();

        camera.update();
        camController.update();

        renderer.setView(globCam);
        renderer.render();

        demonCharacter.getAnimationController().update(delta);


        frameBuffer.begin();

        modelBatch.begin(camera);
        modelBatch.render(demonCharacter.getModelInstance(), environment);
        modelBatch.end();

        frameBuffer.end();

        texture = frameBuffer.getColorBufferTexture();


        if (texture != null) {

            textureRegion = new TextureRegion(texture);
            textureRegion.flip(false, true);

            //spriteBatch.setTransformMatrix(matrix);
            //Debug.log("w : "+ textureRegion.getRegionWidth() + " h :" + textureRegion.getRegionHeight());
            //  getBatch().setProjectionMatrix(globCam.combined);
            //   getBatch().setTransformMatrix(matrix);


            //  shapeRenderer.setTransformMatrix(matrix);
            //shapeRenderer.setProjectionMatrix(globCam.combined);
/*
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(80 / 255.0f, 80 / 255.0f, 50 / 255.0f, 1);
            shapeRenderer.rect(0, 0, 160, 160);
            shapeRenderer.end();*/

            //Debug.log("X: "+renderer.getViewBounds().getX() + " Y: "+ renderer.getViewBounds().getY() +" Unit: "+ renderer.getUnitScale());

            //TODO : Matrix Change axis !

            spriteBatch.begin();
            Sprite sprite = new Sprite(new Texture(Gdx.files.internal("individual_tiles/tile__87.png")));
            sprite.setPosition(0, 0);
            sprite.setColor(Color.GOLD);
            sprite.draw(spriteBatch);
            spriteBatch.draw(textureRegion, 0, 0);
            spriteBatch.end();

            frameBuffer.dispose();
            frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, this.getViewport().getScreenWidth(), this.getViewport().getScreenHeight(), false);
        }


    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        demonCharacter.setAnimation("idle");
       /* switch (character){
            case 'z':
                instances.get(0).transform.rotate(Vector3.Y, getRelativeAngle(180));
                mapCoefX = 1f;
                mapCoefY = 0.5f;
                anglePerso = 180;
                break;
            case 'q':
                instances.get(0).transform.rotate(Vector3.Y, getRelativeAngle(270));
                mapCoefX = -1f;
                mapCoefY = 0.5f;
                anglePerso = 270;
                break;
            case 'd':
                instances.get(0).transform.rotate(Vector3.Y, getRelativeAngle(90));
                mapCoefX = 1f;
                mapCoefY = -0.5f;
                anglePerso = 90;
                break;
            case 's':
                instances.get(0).transform.rotate(Vector3.Y, getRelativeAngle(360));
                anglePerso = 0;
                mapCoefX = -1f;
                mapCoefY = -0.5f;
                break;
        }*/
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Debug.log("touch down");
        touchedDown = true;
        //animationController.setAnimation("Take 001", -1).speed = 1.5f;
        demonCharacter.setAnimation("Run");
        //camera.translate(1,0);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Debug.log("touch up");
        touchedDown = false;
        // animationController.setAnimation("Take 001", -1).speed = 0;
        demonCharacter.setAnimation("idle");
        demonCharacter.getAnimation().speed = 1f;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {


        /*
        if(touchedDown) {
            Debug.log(screenX + " " + screenY + " " + pointer);
        }
        */

        /*
        Debug.log("old x:" + oldX + " oldY : " + oldY);
        if (oldX == 0 && oldY == 0)
        {
            oldX = screenX;
            oldY = screenY;
        }

        float deltaX = oldX - screenX;
        float deltaY = oldY - screenY;
        camera.translate(deltaX,deltaY);
        camera.update();
        oldX = screenX;
        oldY = screenY;
        */
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        float playerX = getViewport().getScreenWidth() / 2;
        float playerY = getViewport().getScreenHeight() / 2;

        screenY = getViewport().getScreenHeight() - screenY;

        Vector2 stickRelativePlayer = new Vector2(screenX - playerX, screenY - playerY);
        float angleStick = stickRelativePlayer.angle() + 90;

        if (angleStick > 360) {
            angleStick = angleStick - 360;
        }

        demonCharacter.setAnimationRotation(getRelativeAngle(angleStick));
        anglePerso = angleStick;

        Debug.log(String.valueOf(angleStick));
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        /*Debug.log(String.valueOf(amount));
        camera.zoom += (amount > 0) ? 0.03 : -0.03;
        Debug.log(String.valueOf(camera.zoom));*/
        return false;
    }

    private float getRelativeAngle(float anglesDest) {
        return anglesDest - anglePerso;
    }


    @Override
    public void dispose() {
        super.dispose();
        modelBatch.dispose();
        shapeRenderer.dispose();
        frameBuffer.dispose();
        spriteBatch.dispose();
        demonCharacter.dispose();
    }
}
