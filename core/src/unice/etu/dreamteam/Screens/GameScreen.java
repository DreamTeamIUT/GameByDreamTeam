package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import unice.etu.dreamteam.Utils.Debug;

import static sun.audio.AudioPlayer.player;

public class GameScreen extends AbstractScreen implements InputProcessor {

    private ModelBatch modelBatch;
    private Environment environment;
    private Array<ModelInstance> instances = new Array<ModelInstance>();
    private CameraInputController camController;
    private AnimationController animationController;
    private TiledMap map;
    private OrthographicCamera camera;
    private IsometricTiledMapRenderer renderer;
    private float oldX = 0, oldY = 0;
    private int mouseX;
    private int mouseY;
    private float mapCoefX = -3f;
    private float mapCoefY = -1f;
    private int anglePerso = 0;

    private boolean touchedDown = false;

    public GameScreen() {
        super(new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Debug.log("Constructor");
    }

    @Override
    public void buildStage() {

        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
       // environment.add(new DirectionalLight().set(0.2f, 0.2f, 0.2f, -1f, -0.8f, -0.2f));
        Gdx.input.setInputProcessor(this);

        Camera cam = getCamera();
        cam.position.set(50f, 100f, 100f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        Debug.log("Loading Model");
        assetManager.load("assets/models/Demon/Demon@Attack(1).g3db", Model.class);
        assetManager.load("assets/models/Demon/Demon@Run.g3db", Model.class);
        assetManager.finishLoading();

        Model demon = assetManager.get("assets/models/Demon/Demon@Run.g3db", Model.class);
        ModelInstance demonInstance = new ModelInstance(demon);
        demonInstance.transform.scale(0.2f,0.2f,0.2f);

        instances.add(demonInstance);


        animationController = new AnimationController(demonInstance);
        animationController.setAnimation("Take 001", -1).speed = 0;
        //animationController.setAnimation("Take 001", -1).speed = 1.5f;

        map = new TmxMapLoader().load("assets/map/test.tmx"); //permet de charger la map depuis le fichier fournis en paramètre et réaliser sur tiled.
        int mapHeight = map.getProperties().get("height", Integer.class);
        int mapWidth = map.getProperties().get("width", Integer.class);
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);
        camera = new OrthographicCamera();
        Debug.log(String.valueOf(getViewport().getWorldWidth()));

        camera.setToOrtho(false, mapWidth*tileWidth, mapHeight*tileHeight);
        camera.zoom = 0.2f;

        Debug.log(String.valueOf(camera.zoom));
        camera.update();

        renderer = new IsometricTiledMapRenderer(map);
    }

    @Override
    public void render(float delta) {
        super.render(delta); //Fait appel à la fonction de abstractScreen

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

       // instances.get(0).transform.translate(0.5f,0f,0.5f);
       // getCamera().translate(-0.5f,0f,-0.5f);

        //camera.translate(mapCoefX,mapCoefY);

        if(touchedDown) {
            double radian = (Math.PI * anglePerso) / 180;
            camera.translate(-((float)Math.sin(radian) * mapCoefX), ((float)Math.cos(radian) * mapCoefY));
            Debug.log(radian + " " + Math.cos(radian) + " " + Math.sin(radian));
        }

        camController.update();
        camera.update();
        renderer.setView(camera);
        renderer.render();
        animationController.update(delta);

        modelBatch.begin(getCamera());
        modelBatch.render(instances, environment);
        modelBatch.end();


    } //fonction appellé toutes les frames, pour actualiser l'affichage.

    public void walk() {

    }

    @Override
    public void dispose() {
        super.dispose();
        modelBatch.dispose();
        instances.clear();
        //Ici on vide !
    }

    @Override
    public boolean keyDown(int keycode) {

        // In the real world, do not create NEW variables over and over, create
        // a temporary static member instead
        if (keycode == Input.Keys.LEFT)
            getCamera().rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f), 1f);
        if (keycode == Input.Keys.RIGHT)
            getCamera().rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f), -1f);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        switch (character){
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
        }
        return false;
    }

    private int getRelativeAngle(int anglesDest){
        return anglesDest-anglePerso;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Debug.log("touch down");
        touchedDown = true;
        animationController.setAnimation("Take 001", -1).speed = 1.5f;
        //camera.translate(1,0);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Debug.log("touch up");
        touchedDown = false;
        animationController.setAnimation("Take 001", -1).speed = 0;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        if(touchedDown) {
            float playerX = 319;
            float playerY = 230;

            screenY = 500 - screenY;

            //Debug.log(screenX + " " + screenY + " " + playerX + " " + playerY);

            double OPDistance = Math.sqrt(Math.pow(playerX, 2) + Math.pow(playerY, 2));
            double CPDistance = Math.sqrt(Math.pow((playerY - screenY), 2) + Math.pow((playerX - screenX), 2));

            double OCDistance = Math.sqrt(Math.pow(OPDistance, 2) - Math.pow(CPDistance, 2));

            double CB = 2 * CPDistance;

            //double xb = Math.sqrt((Math.pow(CPDistance, 2) - Math.pow(playerX, 2)) / (1 + 2*playerX));

            double angle = Math.asin(OCDistance/OPDistance);

            Vector2 stickRelativePlayer = new Vector2(screenX - playerX, screenY - playerY);
            float angleStick = stickRelativePlayer.angle() + 90;

            instances.get(0).transform.rotate(Vector3.Y, getRelativeAngle((int)angleStick));
            anglePerso = (int)angleStick;

            Debug.log(String.valueOf(angleStick));
        }

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
      /*  mouseX = screenX;
        mouseY = getViewport().getScreenHeight() - screenY;
        float rot = MathUtils.radiansToDegrees * MathUtils.atan2(mouseY, mouseX);
        instances.get(0).transform.rotate(Vector3.Y, rot);*/

        /*
        int middelX = getViewport().getScreenHeight() / 2;
        int middleY = getViewport().getScreenWidth() / 2;

        mouseX = screenX;
        mouseY = screenY;
        float rot = MathUtils.radiansToDegrees * MathUtils.atan2(mouseY, mouseX);

        Debug.debug(String.valueOf(screenX) + " " + screenY + " " + rot);
        */

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        Debug.log(String.valueOf(amount));
        camera.zoom += (amount > 0) ? 0.03 : -0.03;
        Debug.log(String.valueOf(camera.zoom));
        return false;
    }

}
