package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import javafx.fxml.LoadException;
import javafx.scene.text.Text;
import unice.etu.dreamteam.Entities.Characters.Graphics.CharacterMove;
import unice.etu.dreamteam.Entities.Characters.Mobs.Graphics.Mob;
import unice.etu.dreamteam.Map.*;
import unice.etu.dreamteam.Entities.Characters.Players.Graphics.Player;
import unice.etu.dreamteam.Entities.GamesPackages.GamePackage;
import unice.etu.dreamteam.Entities.Items.Item;
import unice.etu.dreamteam.Entities.Maps.MapHolder;
import unice.etu.dreamteam.Ui.UiManager;
import unice.etu.dreamteam.Utils.ActionContainer;
import unice.etu.dreamteam.Ui.Settings;
import unice.etu.dreamteam.Utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends AbstractScreen implements InputProcessor {

    public static final int TYPE_MAP = 5;
    public static final int TYPE_STORY = 8;
    private ActionContainer actionContainer;
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

    private Boolean shiftPressed;
    private Boolean shiftPressedUsed;

    private Boolean leftClick;
    private Boolean leftClickUsed;
    private ShapeRenderer entitiesShapeRender;

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

        shiftPressed = false;
        shiftPressedUsed = false;

        leftClick = false;
        leftClickUsed = false;

        Gdx.input.setInputProcessor(this);

        orthoCamera = (OrthographicCamera) getCamera();

        TextButton btn = UiManager.getInstance().createCustomButton("test");
        btn.setPosition(10, 10);
        addActor(btn);

        this.spriteBatch = new SpriteBatch();
        this.entitiesSpriteBatch = new SpriteBatch();

        this.shapeRenderer = new ShapeRenderer();
        this.entitiesShapeRender = new ShapeRenderer();

        playerList = new ArrayList<>();

        if (map == null)
            map = story.getMaps().getDefaultMap().load();

        map.getLayerManager().setLayersOpacity(0.3f);
        map.setSpriteBatch(spriteBatch);


        collisionsManager = new CollisionsManager(map, this);
        collisionsManager.setStory(story);

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

        /*
        Mob mob = (Mob)Mobs.getInstance().get("mob01").create(spriteBatch, shapeRenderer);
        mob.setCellPos(157, 151);

        GraphicalInstances.getInstance().add(mob);
        */

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

        if (InventoryScreen.isOpen)
            return;

        center_camera(playerList.get(0));

        story.getItems().updateInstances(map);

        orthoCamera.update();

        collisionsManager.update(delta);

        //entitiesSpriteBatch.setProjectionMatrix(orthoCamera.combined);
        //entitiesSpriteBatch.setTransformMatrix(IsoTransform.getIsoTransform());

        shapeRenderer.setProjectionMatrix(orthoCamera.combined);
        shapeRenderer.setTransformMatrix(IsoTransform.getIsoTransform());

        map.getRenderer().setView(orthoCamera);
        map.render(delta, playerList.get(0));

        collisionsManager.debug(shapeRenderer);

        entitiesSpriteBatch.begin();
        entitiesSpriteBatch.draw(Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath("images/bullets") + "bullet01.png", Texture.class), 50, 50);
        entitiesSpriteBatch.end();

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

                else if(keyCodes.get(i) == Input.Keys.I){
                    System.out.println(keyCodes.get(i) + " = " + Input.Keys.I);
                    if(!InventoryScreen.isOpen)
                        System.out.println("dedans ! ");
                        addActor(InventoryScreen.createWindow(getViewport(), p));
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
        drawUi();

    }

    public void drawUi(){
        Texture t =Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath("images") + "board.png", Texture.class);
        Texture healthBar =Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath("images") + "empty_bar.png", Texture.class);
        Texture ManaBar =Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath("images") + "empty_bar.png", Texture.class);
        Texture start_rouge = Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath("images") + "start_rouge.png", Texture.class);
        Texture start_bleu = Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath("images") + "start_bleu.png", Texture.class);

        entitiesSpriteBatch.begin();
        entitiesSpriteBatch.draw(t, 0, 0, (float) (t.getWidth() / 1.2), (float) (t.getHeight() / 1.2));
        entitiesSpriteBatch.draw(healthBar, 325, -14, (float) (healthBar.getWidth() / 1.3), (float) (healthBar.getHeight() / 1.3));
        entitiesSpriteBatch.draw(ManaBar, 325, 14, (float) (ManaBar.getWidth() / 1.3), (float) (ManaBar.getHeight() / 1.3));
        entitiesSpriteBatch.draw(start_rouge, 335, 26, (float) (start_rouge.getWidth() / 1.3), (float) (start_rouge.getHeight() / 1.3));
        entitiesSpriteBatch.draw(start_bleu, 335, -2, (float) (start_bleu.getWidth() / 1.3), (float) (start_bleu.getHeight() / 1.3));
        entitiesSpriteBatch.end();

        entitiesShapeRender.begin(ShapeRenderer.ShapeType.Filled);
        entitiesShapeRender.setColor(1,0,0,1);
        entitiesShapeRender.rect(350,32, getWidth() / 3 + 65, 18);
        entitiesShapeRender.setColor(1,0,0,0);
        entitiesShapeRender.rect(350,4, getWidth() / 3 + 65, 18);
        entitiesShapeRender.end();


        if(shiftPressed && !shiftPressedUsed) {
            shiftPressedUsed = true;

            playerList.get(0).run();
        }

        if(!shiftPressed && shiftPressedUsed) {
            shiftPressedUsed = false;

            playerList.get(0).stopRun();
        }
    }

    public void getMobsPathToPlayer() {
        for (Mob m : GraphicalInstances.getInstance().getMobs())
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
        entitiesSpriteBatch.dispose();
        entitiesShapeRender.dispose();
        shapeRenderer.dispose();
        for (Player p : playerList)
            p.dispose();

        for (Mob m : GraphicalInstances.getInstance().getMobs())
            m.dispose();
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCodes.indexOf(keyCode) < 0)
            keyCodes.add(keyCode);

        if(keyCode == 59)
            shiftPressed = true;

        Debug.log("KEY_DOWN", keyCodes.toString());

        return true;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (keyCodes.indexOf(keyCode) >= 0)
            keyCodes.remove(keyCodes.indexOf(keyCode));

        if(keyCode == 59)
            shiftPressed = false;

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