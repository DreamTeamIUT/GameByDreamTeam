package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import unice.etu.dreamteam.Characters.Mob;
import unice.etu.dreamteam.Characters.Player;
import unice.etu.dreamteam.Entities.Item;
import unice.etu.dreamteam.Maps.Map;
import unice.etu.dreamteam.Maps.Story;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen {

    private ArrayList<Mob> mobList;
    private ArrayList<Player> playerList;
    private ArrayList<Item> itemList;
    private Map map;

    public GameScreen() {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void buildStage() {
        Story s = Story.load("story01.json", "default");
        Debug.log(s.getMapPath());
        Debug.log(s.getName());
        Debug.log(s.getGates().all().toString());
        Debug.log(s.getZones().all().toString());
        Debug.log(s.getMobs().all().toString());
        Debug.log(s.getSounds().all().toString());

        map = Map.load(s.getMapPath());
        Debug.log(String.valueOf(map.getMapHeight()));
        Debug.log(String.valueOf(map.getMapWidth()));
        Debug.log(String.valueOf(map.getTileHeight()));
        Debug.log(String.valueOf(map.getTileWidth()));

    }

    @Override
    public void render(float delta) {
        super.render(delta);

    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
    }
}