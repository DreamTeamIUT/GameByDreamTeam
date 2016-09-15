package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.Array;
import unice.etu.dreamteam.Utils.Debug;

public class GameScreen extends AbstractScreen {

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
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.3f, 0.3f, 0.3f, -1f, -0.8f, -0.2f));

        Camera cam = getCamera();
        cam.position.set(100f, 100f, 100f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        Debug.log("Loading Model");
        assetManager.load("assets/models/Knight/Knight@Attack(1).g3db", Model.class);
        assetManager.finishLoading();

        Model knight = assetManager.get("assets/models/Knight/Knight@Attack(1).g3db", Model.class);
        ModelInstance knightInstance = new ModelInstance(knight);
        instances.add(knightInstance);
        animationController = new AnimationController(knightInstance);
        animationController.setAnimation("Take 001",-1);

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
}
