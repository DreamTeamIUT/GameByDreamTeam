package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
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
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import unice.etu.dreamteam.Characters.CharacterList;
import unice.etu.dreamteam.Characters.ModelAnimationManager;
import unice.etu.dreamteam.Utils.Debug;

import static com.badlogic.gdx.Gdx.input;

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
    private Matrix4 isoTransform;
    private Matrix4 invIsotransform;
    private Vector3 pos = new Vector3(0, 0, 0);
    private boolean run;


    public GameTestScreen() {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void buildStage() {

        isoTransform = new Matrix4();
        isoTransform.idt();

        // isoTransform.translate(0, 32, 0);
        isoTransform.scale((float) (Math.sqrt(2.0) / 2.0), (float) (Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

        // ... and the inverse matrix
        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();

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
        input.setInputProcessor(camController);


        demonCharacter = new ModelAnimationManager(CharacterList.KNIGHT);
        demonCharacter.setAnimationScale(0.5f, 0.5f, 0.5f);
        demonCharacter.setAnimation("Run");
        //demonCharacter.setAnimation("Walk", 1).setAnimation("Run", 1).setAnimation("idle", 1);
        demonCharacter.getAnimation().speed = 1f;


        map = new TmxMapLoader().load("assets/map/test.tmx"); //permet de charger la map depuis le fichier fournis en paramètre et réaliser sur tiled.


        int mapHeight = map.getProperties().get("height", Integer.class);
        int mapWidth = map.getProperties().get("width", Integer.class);
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);


        spriteBatch = new SpriteBatch();


        renderer = new IsometricTiledMapRenderer(map, spriteBatch);

        // globCam.setToOrtho(false, mapWidth, mapHeight);
        globCam.zoom = 1f;
        globCam.update();
        renderer.setView(globCam);

        modelBatch = new ModelBatch();
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, this.getViewport().getScreenWidth(), this.getViewport().getScreenHeight(), false);


        shapeRenderer = new ShapeRenderer();

        input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);


      /*  if (touchedDown) {
            double radian = (Math.PI * anglePerso) / 180;
            totalTranslateX += -((float) Math.sin(radian) * mapCoefX);
            totalTranslateY += ((float) Math.cos(radian) * mapCoefY);
            globCam.translate(-((float) Math.sin(radian) * mapCoefX), ((float) Math.cos(radian) * mapCoefY));
            //Debug.log(radian + " " + Math.cos(radian) + " " + Math.sin(radian));
            // Debug.log(totalTranslateX + " " + totalTranslateY);
        }*/


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

            textureRegion = new TextureRegion(texture, frameBuffer.getWidth() / 2 - 80, frameBuffer.getHeight() / 2 - 15, frameBuffer.getWidth() / 2 - 160, frameBuffer.getHeight() / 2 - 140);
            textureRegion.flip(false, true);


            //spriteBatch.setTransformMatrix(matrix);
            //Debug.log("TextureRegion", "w : "+ textureRegion.getRegionWidth() + " h :" + textureRegion.getRegionHeight() + " x: "+ textureRegion.getRegionX() + " y: "+ textureRegion.getRegionY());
            //  getBatch().setProjectionMatrix(globCam.combined);
            //   getBatch().setTransformMatrix(matrix);


          /*  shapeRenderer.setTransformMatrix(isoTransform);
            shapeRenderer.setProjectionMatrix(globCam.combined);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(80 / 255.0f, 80 / 255.0f, 50 / 255.0f, 0.5f);
            shapeRenderer.rect(0, 0, 64, 64);
            shapeRenderer.end();
*/
            //Debug.log("X: "+renderer.getViewBounds().getX() + " Y: "+ renderer.getViewBounds().getY() +" Unit: "+ renderer.getUnitScale());


            if (input.isKeyPressed(Input.Keys.S)) {
                //totalTranslateX += 64 * delta * 1.5;
                totalTranslateX += 32;
                pos = new Vector3(totalTranslateX, totalTranslateY, 0);
                //pos.rotate(-40);
                pos.rot(isoTransform);

            } else if (input.isKeyPressed(Input.Keys.Z)) {
                //totalTranslateX -= 64 * delta * 1.5;
                totalTranslateX -= 32;
                pos = new Vector3(totalTranslateX, totalTranslateY, 0);
                //pos.rotate(-40);
                pos.rot(isoTransform);
            } else if (input.isKeyPressed(Input.Keys.Q)) {
                //totalTranslateY -= 64 * delta * 1.5;
                totalTranslateY -= 64;
                pos = new Vector3(totalTranslateX, totalTranslateY, 0);
                //pos.rotate(-40);
                pos.rot(isoTransform);
            } else if (input.isKeyPressed(Input.Keys.D)) {
                //totalTranslateY += 64 * delta * 1.5;
                totalTranslateY += 64;
                pos = new Vector3(totalTranslateX, totalTranslateY, 0);
                //pos.rotate(-40);
                pos.rot(isoTransform);
            }

            //Debug.log("pos x:"+pos.x+" "+ totalTranslateX +" y:"+pos.y +" "+totalTranslateY);
            getCurrentCell();

            spriteBatch.begin();

            Sprite sprite = new Sprite(new Texture(Gdx.files.internal("individual_tiles/tile__64.png")));
            sprite.setPosition(pos.x, pos.y);
            sprite.setColor(Color.WHITE);
            sprite.draw(spriteBatch);
            //spriteBatch.draw(textureRegion, pos.x - 64, pos.y);


            spriteBatch.end();

            //map.getLayers().get("tile");
          /*  Sprite sprite = new Sprite(new Texture(Gdx.files.internal("individual_tiles/tile__64.png")));
            Vector2 pos = getIsoPosition(5,5);
            sprite.setPosition(pos.x, pos.y);
            sprite.setColor(Color.WHITE);
            sprite.draw(spriteBatch);
            spriteBatch.draw(textureRegion, 0, 0);
            spriteBatch.end();*/


            frameBuffer.dispose();
            frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, this.getViewport().getScreenWidth(), this.getViewport().getScreenHeight(), false);
        }


    }

    public Vector2 getIsoPosition(float x, float y) {
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);
        return new Vector2(x * tileWidth, y * tileHeight);
    }

    public void getCurrentCell() {
        int x = (int) Math.abs(Math.floor(totalTranslateX / 32)), y = (int) Math.abs(Math.floor(totalTranslateY / 64));
        // Debug.log("x:" + x + " y:" + y);
        Debug.log(pos.x + " " + pos.y + " " + getViewport().getScreenHeight() + " " + getViewport().getScreenWidth());
        TiledMapTileLayer l = (TiledMapTileLayer) map.getLayers().get(0);
        TiledMapTileLayer.Cell c = l.getCell(x, y);
        TiledMapTile tile = map.getTileSets().getTileSet(0).getTile(2);

        if (c != null) {
            c.setTile(tile);
        } else {
            c = new TiledMapTileLayer.Cell();
            c.setTile(tile);
        }
        l.setCell(x, y, c);

    }

    @Override
    public boolean keyDown(int keycode) {
        Debug.log("down" + keycode);
        switch (keycode) {
            case Input.Keys.D:
                demonCharacter.setAnimation("walk");
                demonCharacter.setAnimationRotation(getRelativeAngle(180));
                anglePerso = 180;
                break;
            case Input.Keys.Z:
                demonCharacter.setAnimation("walk");
                demonCharacter.setAnimationRotation(getRelativeAngle(270));
                anglePerso = 270;
                break;
            case Input.Keys.S:
                demonCharacter.setAnimation("walk");
                demonCharacter.setAnimationRotation(getRelativeAngle(90));
                anglePerso = 90;
                break;
            case Input.Keys.Q:
                demonCharacter.setAnimation("walk");
                demonCharacter.setAnimationRotation(getRelativeAngle(360));
                anglePerso = 0;
                break;

        }
        demonCharacter.getAnimation().speed = 1f;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Debug.log("up");
        switch (keycode) {
            default:
                demonCharacter.setAnimation("idle");
                demonCharacter.getAnimation().speed = 1f;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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
        /*Debug.log("touch down");
        touchedDown = true;
        //animationController.setAnimation("Take 001", -1).speed = 1.5f;
        demonCharacter.setAnimation("Run");
        //camera.translate(1,0);*/
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
     /*   Debug.log("touch up");
        touchedDown = false;
        // animationController.setAnimation("Take 001", -1).speed = 0;
        demonCharacter.setAnimation("idle");
        demonCharacter.getAnimation().speed = 1f;*/

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
       /* float playerX = getViewport().getScreenWidth() / 2;
        float playerY = getViewport().getScreenHeight() / 2;

        screenY = getViewport().getScreenHeight() - screenY;

        Vector2 stickRelativePlayer = new Vector2(screenX - playerX, screenY - playerY);
        float angleStick = stickRelativePlayer.angle() + 90;

        if (angleStick > 360) {
            angleStick = angleStick - 360;
        }

        demonCharacter.setAnimationRotation(getRelativeAngle(angleStick));
        anglePerso = angleStick;

        Debug.log(String.valueOf(angleStick));*/
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
