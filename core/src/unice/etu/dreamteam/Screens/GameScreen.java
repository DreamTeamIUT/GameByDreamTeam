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
import unice.etu.dreamteam.Entities.Characters.Graphics.CharacterMove;
import unice.etu.dreamteam.Entities.Characters.Mobs.Graphics.Mob;
import unice.etu.dreamteam.Entities.Characters.Players.Graphics.Player;
import unice.etu.dreamteam.Entities.GamesPackages.GamePackage;
import unice.etu.dreamteam.Entities.Items.Item;
import unice.etu.dreamteam.Map.CollisionsManager;
import unice.etu.dreamteam.Map.Map;
import unice.etu.dreamteam.Map.Story;
import unice.etu.dreamteam.Utils.ActionContainer;
import unice.etu.dreamteam.Ui.Settings;
import unice.etu.dreamteam.Utils.*;

import java.util.ArrayList;
import java.util.List;

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

    private List<Integer> keyCodes;

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
        keyCodes = new ArrayList<>();

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

        map.calculateGridCell(collisionsManager);

        Debug.log("debug");
        orthoCamera.setToOrtho(false, map.getMapWidth(), map.getMapHeight());
        orthoCamera.zoom = 1f;
        orthoCamera.update();
        Debug.log("debug");


        GamePackage p = GameInformation.getGamePackage();


        playerList.add((Player) p.getPlayers().get("player01").create(spriteBatch, shapeRenderer));
        Debug.vector(map.getMapInfo().getStartPoint());
        playerList.get(0).setPos(map.getMapInfo().getStartPoint());

        mobList.add((Mob) s.getMobs().get("mob01").create(spriteBatch, shapeRenderer));
        mobList.get(0).setCellPos(1, 1);

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
        Vector3 pos = new Vector3(p.getRealPos().x * map.getTileHeight(), p.getRealPos().y * map.getTileHeight() , 0);
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

        if(keyCodes.size() > 0) {
            Player p = playerList.get(0);

            for(int i = 0; i < keyCodes.size(); i++) {
                switch (keyCodes.get(i)) {
                    case Input.Keys.ESCAPE:
                        if (!Settings.isOpen)
                            addActor(Settings.createWindow(getViewport()));
                        break;
                    case Input.Keys.D:
                        Debug.log("D");
                        if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.RIGHT), p)){
                            //p.setCellPos(p.moveToRight());
                            p.moveTo(CharacterMove.RIGHT);
                            collisionsManager.findActionFor(p);

                            getMobsPathToPlayer();
                        }
                        break;
                    case Input.Keys.S:
                        Debug.log("S");
                        if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.DOWN), p)){
                            //p.setCellPos(p.moveToDown());
                            p.moveTo(CharacterMove.DOWN);
                            collisionsManager.findActionFor(p);

                            getMobsPathToPlayer();
                        }
                        break;
                    case Input.Keys.Q:
                        Debug.log("Q");
                        if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.LEFT), p)){
                            //p.setCellPos(p.moveToLeft());
                            p.moveTo(CharacterMove.LEFT);
                            collisionsManager.findActionFor(p);

                            getMobsPathToPlayer();
                        }
                        break;
                    case Input.Keys.Z:
                        Debug.log("Z");
                        Debug.log("canGo : " + collisionsManager.canGoTo(p.moveToCheck(CharacterMove.UP), p));
                        if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.UP), p)){
                            //p.setCellPos(p.moveToUp());
                            p.moveTo(CharacterMove.UP);
                            collisionsManager.findActionFor(p);

                            getMobsPathToPlayer();
                        }
                        break;
                }
            }
        }
    }

    public void getMobsPathToPlayer() {
        for(Mob m : mobList)
            m.setPathToPlayer(map.getNavigationGrid(), playerList.get(0));
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
    public boolean keyDown(int keyCode) {
        if(keyCodes.indexOf(keyCode) < 0)
            keyCodes.add(keyCode);

        Debug.log("KEY DOWN", keyCodes.toString());

        return true;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if(keyCodes.indexOf(keyCode) >= 0)
            keyCodes.remove(keyCodes.indexOf(keyCode));

        Debug.log("KEY UP", keyCodes.toString());

        return true;
    }
}