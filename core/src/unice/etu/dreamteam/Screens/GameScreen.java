package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import unice.etu.dreamteam.Characters.Mob;
import unice.etu.dreamteam.Characters.Player;
import unice.etu.dreamteam.Entities.Item;
import unice.etu.dreamteam.Map.ColisionsManager;
import unice.etu.dreamteam.Map.Map;
import unice.etu.dreamteam.Map.Story;
import unice.etu.dreamteam.Ui.Settings;
import unice.etu.dreamteam.Utils.*;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen implements InputProcessor {

    private ArrayList<Mob> mobList;
    private ArrayList<Player> playerList;
    private ArrayList<Item> itemList;
    private Map map;
    private OrthographicCamera othoCamera;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private ColisionsManager colisionsManager;

    public GameScreen() {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void buildStage() {

        Gdx.input.setInputProcessor(this);

        othoCamera = (OrthographicCamera) getCamera();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();


        playerList = new ArrayList<>();
        mobList = new ArrayList<>();

        GameInformation.setPackageName("default");

        Story s = Story.load("story01.json");


        map = Map.load(s.getMapPath());

        map.getLayerManager().setLayersOpacity(0.3f);
        map.setSpriteBatch(spriteBatch);

        Debug.log("debug");
        othoCamera.setToOrtho(false, map.getMapWidth(), map.getMapHeight());
        othoCamera.zoom = 1f;
        othoCamera.update();
        Debug.log("debug");

        Packages p = new Packages(GameInformation.getPackageName());

        colisionsManager = new ColisionsManager();

        playerList.add((Player) p.getPlayers().get("player01").create( spriteBatch, shapeRenderer));


        mobList.add((Mob)s.getMobs().get("mob01").create(spriteBatch, shapeRenderer));

        mobList.get(0).setCell(1,1);

        colisionsManager.addMapLayer(map.getLayerManager());
        colisionsManager.addStory(s);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Settings.isOpen)
            return;

        othoCamera.update();

        shapeRenderer.setProjectionMatrix(othoCamera.combined);
        shapeRenderer.setTransformMatrix(IsoTransform.getIsoTransform());

        colisionsManager.update(delta);


        map.getRenderer().setView(othoCamera);
        map.render(delta);

        colisionsManager.debug(shapeRenderer);

        map.getRenderer().render(map.getLayerManager().getBeforeLayers());

        for (Player p : playerList)
            p.render(delta);

        for (Mob m : mobList)
            m.render(delta);

        map.getRenderer().render(map.getLayerManager().getAfterLayers());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
       /* for (Player p : playerList)
            p.getModelConverter().resize();
        */
    }


    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
        for (Player p : playerList)
            p.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        Debug.log("In keyup !");
        Player p = playerList.get(0);
        switch (keycode) {
            case Input.Keys.ESCAPE:
                if (!Settings.isOpen)
                    addActor(Settings.createWindow(getViewport()));
                return true;
            case Input.Keys.D:
                if (colisionsManager.canGoTo(p.moveToRight(), p)){
                    p.setCellPos(p.moveToRight());
                }
                return true;
            case Input.Keys.S:
                if (colisionsManager.canGoTo(p.moveToDown(), p)){
                    p.setCellPos(p.moveToDown());
                }
                return true;
            case Input.Keys.Q:
                if (colisionsManager.canGoTo(p.moveToLeft(), p)){
                    p.setCellPos(p.moveToLeft());
                }
                return true;
            case Input.Keys.Z:
                if (colisionsManager.canGoTo(p.moveToUp(), p)){
                    p.setCellPos(p.moveToUp());
                }
                return true;
        }
        return false;
    }



}