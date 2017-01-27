package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
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
import unice.etu.dreamteam.Entities.Maps.MapHolder;
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
    private SpriteBatch entitiesSpriteBatch;
    private ShapeRenderer shapeRenderer;
    private CollisionsManager collisionsManager;
    private Story story;

    private List<Integer> keyCodes;
    private Preferences prefs;

    private Boolean leftClick;
    private Boolean leftClickUsed;

    public GameScreen(String storyFile, int type) {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        if (type == TYPE_STORY) {
            story = Story.load(storyFile);
            map = story.getMaps().getDefaultMap().load();
        } else if (type == TYPE_MAP) {
            try {
                story = Story.getStory();
            } catch (LoadException e) {
                e.printStackTrace();
                Gdx.app.exit();
            }

            Debug.log(storyFile);
            Debug.log(String.valueOf(story.getMaps().size()));
            Debug.log(story.getName());
            for (MapHolder mdkdd : story.getMaps()){
                Debug.log("MAPS_NAMES", mdkdd.getName());
            }

            map = story.getMaps().get(storyFile).load();
        } else {
            Debug.log("You should pass a valid type !");
            Gdx.app.exit();
        }
    }

    public GameScreen(String storyFile, String mapName) {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        story = Story.load(storyFile);
        map = story.getMaps().get(mapName).load();
    }

    public GameScreen(String storyFile, int type, ActionContainer container) {
        this(storyFile, type);
        actionContainer = container;
    }

    public GameScreen(Story story) {
        this.story = story;
        map = this.story.getMaps().getDefaultMap().load();
    }


    @Override
    public void buildStage() {
        prefs = Gdx.app.getPreferences("GameSettings");

        keyCodes = new ArrayList<>();

        leftClick = false;
        leftClickUsed = false;

        Gdx.input.setInputProcessor(this);

        orthoCamera = (OrthographicCamera) getCamera();

        this.spriteBatch = new SpriteBatch();
        this.entitiesSpriteBatch = new SpriteBatch();

        this.shapeRenderer = new ShapeRenderer();

        playerList = new ArrayList<>();
        mobList = new ArrayList<>();

        if (map == null)
            map = story.getMaps().getDefaultMap().load();

        map.getLayerManager().setLayersOpacity(0.3f);
        map.setSpriteBatch(spriteBatch);


        collisionsManager = new CollisionsManager(map, this);
        collisionsManager.addStory(story);

        map.calculateGridCell(collisionsManager);

        Debug.log("debug");
        orthoCamera.setToOrtho(false, map.getMapWidth(), map.getMapHeight());
        orthoCamera.zoom = 1f;
        orthoCamera.update();
        Debug.log("debug");

        GamePackage gamePackage = GameInformation.getGamePackage();


        playerList.add((Player) gamePackage.getPlayers().get("player01").create(spriteBatch, shapeRenderer));
        Debug.vector(map.getMapInfo().getStartPoint());
        playerList.get(0).setPos(map.getMapInfo().getStartPoint());

        //  mobList.add((Mob) story.getMobs().get("mob01").create(spriteBatch, shapeRenderer));
        //mobList.get(0).setCellPos(1, 1);

        story.getItems().clearInstances(map);

        story.getItems().addInstances(map.getLayerManager().getTilePrositionWithProperty("type", "ITEM"));

        Item.ItemInstance i = story.getItems().get("chest").addInstance(new Vector2(157,148));
        i.onThrown(map);

        playerList.get(0).getAnimationManager().setAnimation("STOPPED");

        parseActionContainer();
    }

    private void parseActionContainer() {
        if (actionContainer == null)
            return;

        if (actionContainer.moveTo != null)
            playerList.get(0).setCellPos(actionContainer.moveTo);

        if (actionContainer.moveToGate != null) {
            Debug.log("MOVETOGATE", actionContainer.moveToGate);
            Debug.log("GATESMOVETO", Debug.iteratorToString(map.getLayerManager().getCurrentGateLayer().getObjects().iterator()));
            RectangleMapObject obj = (RectangleMapObject) map.getLayerManager().getCurrentGateLayer().getObjects().get(actionContainer.moveToGate);
            Vector2 v = Map.pixelToCell(obj.getRectangle().getX(), obj.getRectangle().getY());
            playerList.get(0).setCellPos(v);
        }

    }

    public void center_camera(Player p) {
        Vector3 pos = new Vector3(p.getRealPos().x * map.getTileHeight(), p.getRealPos().y * map.getTileHeight(), 0);
        orthoCamera.position.set(pos.mul(IsoTransform.getIsoTransform()));
    }

    public Map getMap() {
        return map;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Settings.isOpen)
            return;

        center_camera(playerList.get(0));

        story.getItems().updateInstances(map);

        orthoCamera.update();

        collisionsManager.update(delta);

        entitiesSpriteBatch.setProjectionMatrix(orthoCamera.combined);
        entitiesSpriteBatch.setTransformMatrix(IsoTransform.getIsoTransform());

        shapeRenderer.setProjectionMatrix(orthoCamera.combined);
        shapeRenderer.setTransformMatrix(IsoTransform.getIsoTransform());

        map.getRenderer().setView(orthoCamera);
        map.render(delta, playerList.get(0));

        collisionsManager.debug(shapeRenderer);

      //  map.getRenderer().render(map.getLayerManager().getBeforeLayers());


      /*  for (Player p : playerList)
            p.render(delta, map);

        for (Mob m : mobList)
            m.render(delta, map);*/

      //  map.getRenderer().render(map.getLayerManager().getAfterLayers());

        if(leftClick && !leftClickUsed) {
            leftClickUsed = true;
            playerList.get(0).getWeapon().shoot(new Vector2(0, 0));
        }

        final int keyUp = prefs.getInteger(Settings.KEY_UP, Settings.DEFAULTSETTINGSUP);
        final int keyDown = prefs.getInteger(Settings.KEY_DOWN, Settings.DEFAULTSETTINGSDOWN);
        final int keyleft = prefs.getInteger(Settings.KEY_LEFT, Settings.DEFAULTSETTINGSLEFT);
        final int keyRight = prefs.getInteger(Settings.KEY_RIGHT, Settings.DEFAULTSETTINGSRIGHT);
        final int keyGrab = prefs.getInteger(Settings.KEY_GRAB, Settings.DEFAULTSETTINGSGRAB);

        if (keyCodes.size() > 0) {
            Player p = playerList.get(0);
            for (int i = 0; i < keyCodes.size(); i++) {
                if (keyCodes.get(i) == Input.Keys.ESCAPE){
                    if (!Settings.isOpen)
                        addActor(Settings.createWindow(getViewport()));
                }
                else if (keyCodes.get(i) == keyRight){
                    Debug.log("D");
                    p.setView(CharacterMove.RIGHT);
                    if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.RIGHT), p)) {
                        //p.setCellPos(p.moveToRight());
                        p.moveTo(CharacterMove.RIGHT);
                        collisionsManager.findActionFor(p);

                        getMobsPathToPlayer();
                    }
                }
                else if (keyCodes.get(i) == keyleft){
                    Debug.log("Q");
                    p.setView(CharacterMove.LEFT);
                    if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.LEFT), p)) {
                        //p.setCellPos(p.moveToLeft());
                        p.moveTo(CharacterMove.LEFT);
                        collisionsManager.findActionFor(p);

                        getMobsPathToPlayer();
                    }
                }
                else if (keyCodes.get(i) == keyUp){
                    Debug.log("Z");
                    Debug.log("canGo : " + collisionsManager.canGoTo(p.moveToCheck(CharacterMove.UP), p));
                    p.setView(CharacterMove.UP);
                    if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.UP), p)) {
                        //p.setCellPos(p.moveToUp());
                        p.moveTo(CharacterMove.UP);
                        collisionsManager.findActionFor(p);

                        getMobsPathToPlayer();
                    }
                }
                else if (keyCodes.get(i) == keyDown){
                    p.setView(CharacterMove.DOWN);
                    if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.DOWN), p)) {
                        //p.setCellPos(p.moveToDown());
                        p.moveTo(CharacterMove.DOWN);
                        collisionsManager.findActionFor(p);

                        getMobsPathToPlayer();
                    }
                }
                else if (keyCodes.get(i) == keyGrab){
                    collisionsManager.doGrab(p);
                }

                /*switch (keyCodes.get(i)) {
                    case Input.Keys.ESCAPE:
                        if (!Settings.isOpen)
                            addActor(Settings.createWindow(getViewport()));
                        break;
                    case Input.Keys.D:
                        Debug.log("D");
                        p.setView(CharacterMove.RIGHT);
                        if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.RIGHT), p)) {
                            //p.setCellPos(p.moveToRight());
                            p.moveTo(CharacterMove.RIGHT);
                            collisionsManager.findActionFor(p);

                            getMobsPathToPlayer();
                        }
                        break;
                    case Input.Keys.S:
                        Debug.log("S");
                        p.setView(CharacterMove.DOWN);
                        if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.DOWN), p)) {
                            //p.setCellPos(p.moveToDown());
                            p.moveTo(CharacterMove.DOWN);
                            collisionsManager.findActionFor(p);

                            getMobsPathToPlayer();
                        }
                        break;
                    case Input.Keys.Q:
                        Debug.log("Q");
                        p.setView(CharacterMove.LEFT);
                        if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.LEFT), p)) {
                            //p.setCellPos(p.moveToLeft());
                            p.moveTo(CharacterMove.LEFT);
                            collisionsManager.findActionFor(p);

                            getMobsPathToPlayer();
                        }
                        break;
                    case Input.Keys.Z:
                        p.setView(CharacterMove.UP);
                        Debug.log("Z");
                        Debug.log("canGo : " + collisionsManager.canGoTo(p.moveToCheck(CharacterMove.UP), p));
                        if (collisionsManager.canGoTo(p.moveToCheck(CharacterMove.UP), p)) {
                            //p.setCellPos(p.moveToUp());
                            p.moveTo(CharacterMove.UP);
                            collisionsManager.findActionFor(p);

                            getMobsPathToPlayer();
                        }
                        break;
                }*/
            }
        }
    }

    public void getMobsPathToPlayer() {
        for (Mob m : mobList)
            m.setPathToPlayer(map.getNavigationGrid(), playerList.get(0));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        for (Player p : playerList)
            p.getModelConverter().resize();
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
        if (keyCodes.indexOf(keyCode) < 0)
            keyCodes.add(keyCode);

        Debug.log("KEY DOWN", keyCodes.toString());

        return true;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (keyCodes.indexOf(keyCode) >= 0)
            keyCodes.remove(keyCodes.indexOf(keyCode));

        Debug.log("KEY UP", keyCodes.toString());

        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        leftClick = true;
        leftClickUsed = false;

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        leftClick = false;

        return true;
    }
}