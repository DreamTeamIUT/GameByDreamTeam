package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import unice.etu.dreamteam.Characters.Mob;
import unice.etu.dreamteam.Characters.Player;
import unice.etu.dreamteam.Entities.Item;
import unice.etu.dreamteam.Map.CollisionsManager;
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
    private OrthographicCamera orthoCamera;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private CollisionsManager collisionsManager;
    private Story s;

    public GameScreen() {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void buildStage() {

        Gdx.input.setInputProcessor(this);

        orthoCamera = (OrthographicCamera) getCamera();

        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        playerList = new ArrayList<>();
        mobList = new ArrayList<>();

        GameInformation.setPackageName("default");

        s = Story.load("story01.json");

        setMap(s.getMaps().getDefaultMap().load());


        Packages p = new Packages(GameInformation.getPackageName());


        playerList.add((Player) p.getPlayers().get("player01").create(spriteBatch, shapeRenderer));
        playerList.add((Player) p.getPlayers().get("player01").create(spriteBatch, shapeRenderer));

        mobList.add((Mob) s.getMobs().get("mob01").create(spriteBatch, shapeRenderer));
        mobList.get(0).setCellPos(1, 1);

    }

    public void center_camera(Player p) {
        Vector3 pos = new Vector3(p.getCellPos().x * map.getTileHeight(), p.getCellPos().y * map.getTileHeight() , 0);
        orthoCamera.position.set(pos.mul(IsoTransform.getIsoTransform()));
    }

    public void setMap(Map m){

        SpriteBatch oldSpriteBatch = this.spriteBatch;
        ShapeRenderer oldShapeRender = this.shapeRenderer;
        this.spriteBatch = null;
        this.shapeRenderer = null;

        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        for (Player p : playerList){
            p.setBatch(this.spriteBatch);
            p.setShapeRenderer(this.shapeRenderer);
        }

        for (Mob mob : mobList){
            mob.setBatch(this.spriteBatch);
            mob.setShapeRenderer(this.shapeRenderer);
        }

        if (oldSpriteBatch != null)
            oldSpriteBatch.dispose();


        if (oldShapeRender != null)
            oldShapeRender.dispose();



        Map oldMap = this.map;
        this.map = m;

        map.getLayerManager().setLayersOpacity(0.3f);
        map.setSpriteBatch(spriteBatch);

        if (oldMap != null)
            oldMap.dispose();


        Debug.log("debug");
        orthoCamera.setToOrtho(false, map.getMapWidth(), map.getMapHeight());
        orthoCamera.zoom = 1f;
        orthoCamera.update();
        Debug.log("debug");

        collisionsManager = new CollisionsManager(map, this);
        collisionsManager.addStory(s);

    }

    public Map getMap(){
        return map;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Settings.isOpen)
            return;

        center_camera(playerList.get(0));

        orthoCamera.update();

        shapeRenderer.setProjectionMatrix(orthoCamera.combined);
        shapeRenderer.setTransformMatrix(IsoTransform.getIsoTransform());

        collisionsManager.update(delta);


        map.getRenderer().setView(orthoCamera);
        map.render(delta);


        collisionsManager.debug(shapeRenderer);

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

        for (Mob m : mobList)
            m.dispose();
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
                if (collisionsManager.canGoTo(p.moveToRight(), p)){
                    p.setCellPos(p.moveToRight());
                    collisionsManager.findActionFor(p);
                }
                return true;
            case Input.Keys.S:
                if (collisionsManager.canGoTo(p.moveToDown(), p)){
                    p.setCellPos(p.moveToDown());
                    collisionsManager.findActionFor(p);
                }
                return true;
            case Input.Keys.Q:
                if (collisionsManager.canGoTo(p.moveToLeft(), p)){
                    p.setCellPos(p.moveToLeft());
                    collisionsManager.findActionFor(p);
                }
                return true;
            case Input.Keys.Z:
                Debug.log("canGo : " + collisionsManager.canGoTo(p.moveToUp(), p));
                if (collisionsManager.canGoTo(p.moveToUp(), p)){
                    p.setCellPos(p.moveToUp());
                    collisionsManager.findActionFor(p);
                }
                return true;
        }
        return false;
    }



}