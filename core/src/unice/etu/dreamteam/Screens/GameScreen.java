package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import unice.etu.dreamteam.Characters.Mob;
import unice.etu.dreamteam.Characters.Player;
import unice.etu.dreamteam.Entities.Item;
import unice.etu.dreamteam.Map.Map;
import unice.etu.dreamteam.Map.Story;
import unice.etu.dreamteam.Utils.IsoTransform;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen {

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


        Story s = Story.load("story01.json", "default");
        map = Map.load(s.getMapPath());

        map.getLayerManager().setLayersOpacity(0.3f);
        map.setSpriteBatch(spriteBatch);


        othoCamera.setToOrtho(false, map.getMapWidth(), map.getMapHeight());
        othoCamera.zoom = 1f;
        othoCamera.update();


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        othoCamera.update();

        shapeRenderer.setProjectionMatrix(othoCamera.combined);
        shapeRenderer.setTransformMatrix(IsoTransform.getIsoTransform());

        map.getRenderer().setView(othoCamera);
        map.render(delta);

        map.getLayerManager().debugObjectLayer(shapeRenderer);
        map.getRenderer().render(map.getLayerManager().getBeforeLayers());

        map.getRenderer().render(map.getLayerManager().getAfterLayers());

    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}