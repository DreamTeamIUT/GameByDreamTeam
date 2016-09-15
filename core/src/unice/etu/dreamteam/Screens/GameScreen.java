package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import unice.etu.dreamteam.Utils.Debug;

public class GameScreen extends AbstractScreen implements InputProcessor{

    private ModelBatch modelBatch;
    private Environment environment;
    private Array<ModelInstance> instances = new Array<ModelInstance>();
    private CameraInputController camController;
    private AnimationController animationController;

    public GameScreen(){
        super(new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Debug.log("Constructor");
    }

    @Override
    public void buildStage() {

        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,1f,1f,1f,1f));
        environment.add(new DirectionalLight().set(0.2f, 0.2f, 0.2f, -1f, -0.8f, -0.2f));
        Gdx.input.setInputProcessor(this);

        Camera cam = getCamera();
        cam.position.set(50f, 100f, 100f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        Debug.log("Loading Model");
        assetManager.load("assets/models/Demon/Demon@Attack(1).g3db", Model.class);
        assetManager.load("assets/models/Demon/Demon@Run.g3db", Model.class);
        assetManager.finishLoading();

        final Model Demon = assetManager.get("assets/models/Demon/Demon@Run.g3db", Model.class);
        final ModelInstance DemonInstance = new ModelInstance(Demon);
        instances.add(DemonInstance);
        animationController = new AnimationController(DemonInstance);
        animationController.setAnimation("Take 001", 5, new AnimationController.AnimationListener() {
            @Override
            public void onEnd(AnimationController.AnimationDesc animation) {
                Demon.dispose();
                instances.removeIndex(0);
                Model Demon = assetManager.get("assets/models/Demon/Demon@Attack(1).g3db", Model.class);
                ModelInstance DemonInstance = new ModelInstance(Demon);
                instances.add(DemonInstance);
                animationController = new AnimationController(DemonInstance);
                animationController.setAnimation("Take 001", 1);
            }

            @Override
            public void onLoop(AnimationController.AnimationDesc animation) {

            }
        });


    }

    @Override
    public void render(float delta) {
        super.render(delta); //Fait appel à la fonction de abstractScreen

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camController.update();
        animationController.update(delta);



        modelBatch.begin(getCamera());
        modelBatch.render(instances, environment);
        modelBatch.end();


    } //fonction appellé toutes les frames, pour actualiser l'affichage.

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
        if(keycode == Input.Keys.LEFT)
            getCamera().rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f), 1f);
        if(keycode == Input.Keys.RIGHT)
            getCamera().rotateAround(new Vector3(0f,0f,0f),new Vector3(0f,1f,0f), -1f);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
