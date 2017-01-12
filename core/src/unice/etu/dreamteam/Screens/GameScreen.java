package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import javafx.fxml.LoadException;
import unice.etu.dreamteam.Characters.Mob;
import unice.etu.dreamteam.Characters.Player;
import unice.etu.dreamteam.Entities.GamePackage;
import unice.etu.dreamteam.Entities.Item;
import unice.etu.dreamteam.Map.CollisionsManager;
import unice.etu.dreamteam.Map.Map;
import unice.etu.dreamteam.Map.Story;
import unice.etu.dreamteam.Utils.ActionContainer;
import unice.etu.dreamteam.Ui.Settings;
import unice.etu.dreamteam.Utils.*;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen implements InputProcessor {

    public static final int TYPE_MAP = 5;
    public static final int TYPE_STORY = 8;
    private ActionContainer actionContainer;
    private ArrayList<Mob> mobList;
    private ArrayList<Player> playerList;
    private ArrayList<Item> itemList;
    private Map map;
    private OrthographicCamera orthoCamera;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private CollisionsManager collisionsManager;
    private Story s;

    public GameScreen(String storyFile, int type) {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        if (type == TYPE_STORY)
        {
            s = Story.load(storyFile);
            map = s.getMaps().getDefaultMap().load();
        }
        else if (type == TYPE_MAP){
            try {
                s = Story.getStory();
            } catch (LoadException e) {
                e.printStackTrace();
                Gdx.app.exit();
            }

            Debug.log(storyFile);
            map = s.getMaps().get(storyFile).load();
        }
        else {
            Debug.log("You should pass a valid type !");
            Gdx.app.exit();
        }
    }

    public GameScreen(String storyFile, String mapName){
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        s = Story.load(storyFile);
        map = s.getMaps().get(mapName).load();
    }

    public GameScreen(String storyFile, int type, ActionContainer container ){
        this(storyFile, type);
        actionContainer = container;
    }


    @Override
    public void buildStage() {

        Gdx.input.setInputProcessor(this);

        orthoCamera = (OrthographicCamera) getCamera();

        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        playerList = new ArrayList<>();
        mobList = new ArrayList<>();

        if (map == null)
            map =  s.getMaps().getDefaultMap().load();

        map.getLayerManager().setLayersOpacity(0.3f);
        map.setSpriteBatch(spriteBatch);


        collisionsManager = new CollisionsManager(map, this);
        collisionsManager.addStory(s);

        Debug.log("debug");
        orthoCamera.setToOrtho(false, map.getMapWidth(), map.getMapHeight());
        orthoCamera.zoom = 1f;
        orthoCamera.update();
        Debug.log("debug");


        GamePackage p = GameInformation.getGamePackage();


        playerList.add((Player) p.getPlayers().get("player01").create(spriteBatch, shapeRenderer));
        playerList.get(0).setCellPos(map.getMapInfo().getStartPoint());

        mobList.add((Mob) s.getMobs().get("mob01").create(spriteBatch, shapeRenderer));
        mobList.get(0).setCellPos(1, 1);

        s.getItems().clearInstances(map);

        s.getItems().addInstances(map.getLayerManager().getTilePrositionWithProperty("type", "ITEM"));

        Item.ItemInstance i = s.getItems().get("chest").addInstance(new Vector2(0,0));
        i.onThrown(map);


        parseActionContainer();

    }

    private void parseActionContainer() {
        if (actionContainer == null)
            return;

        if (actionContainer.moveTo != null)
            playerList.get(0).setCellPos(actionContainer.moveTo);

        if (actionContainer.moveToGate != null){
            RectangleMapObject obj = (RectangleMapObject) map.getLayerManager().getCurrentGateLayer().getObjects().get(actionContainer.moveToGate);
            Vector2 v = Map.pixelToCell(obj.getRectangle().getX(), obj.getRectangle().getY());
            playerList.get(0).setCellPos(v);
        }

    }

    public void center_camera(Player p) {
        Vector3 pos = new Vector3(p.getCellPos().x * map.getTileHeight(), p.getCellPos().y * map.getTileHeight() , 0);
        orthoCamera.position.set(pos.mul(IsoTransform.getIsoTransform()));
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

        s.getItems().updateInstances(map);

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