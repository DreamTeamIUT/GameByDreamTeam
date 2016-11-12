package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import unice.etu.dreamteam.Characters.Mob;
import unice.etu.dreamteam.Characters.Player;
import unice.etu.dreamteam.Entities.Item;
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

    public GameScreen() {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void buildStage() {

        othoCamera = (OrthographicCamera) getCamera();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        playerList = new ArrayList<Player>();

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

        playerList.add(Player.create(p.getPlayers().get("player01"), spriteBatch, shapeRenderer));
        playerList.add(Player.create(p.getPlayers().get("player02"), spriteBatch, shapeRenderer));
        playerList.add(Player.create(p.getPlayers().get("player01"), spriteBatch, shapeRenderer));
        playerList.add(Player.create(p.getPlayers().get("player02"), spriteBatch, shapeRenderer));

        playerList.get(1).setCellPos(4, 6);
        playerList.get(2).setCellPos(7, 6);
        playerList.get(3).setCellPos(5, 9);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Settings.isOpen)
            return;

        othoCamera.update();

        shapeRenderer.setProjectionMatrix(othoCamera.combined);
        shapeRenderer.setTransformMatrix(IsoTransform.getIsoTransform());

        map.getRenderer().setView(othoCamera);
        map.render(delta);

        map.getLayerManager().debugObjectLayer(shapeRenderer);
        map.getRenderer().render(map.getLayerManager().getBeforeLayers());

        for (Player p : playerList)
            p.render(delta);

        map.getRenderer().render(map.getLayerManager().getAfterLayers());
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
    }


    @Override
    public boolean keyDown(int keycode) {
        Debug.log("In keyup !");
        switch (keycode) {
            case Input.Keys.ESCAPE:
                if (!Settings.isOpen)
                    addActor(Settings.createWindow(getViewport()));

        }
        return false;
    }

}