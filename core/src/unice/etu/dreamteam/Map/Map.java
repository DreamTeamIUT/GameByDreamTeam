package unice.etu.dreamteam.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import unice.etu.dreamteam.Utils.Debug;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Map {

    private LayerManager layerManager;
    private TiledMap mapData;
    private static Integer mapHeight;
    private static Integer mapWidth;
    private static Integer tileHeight;
    private static Integer tileWidth;
    private SpriteBatch spriteBatch;
    private IsometricTiledMapRenderer renderer;


    public static Map load(String path) {
        Map map = new Map();
        map.setMapData(new TmxMapLoader().load("assets/" + path));
        return map;
    }

    public void setMapData(TiledMap mapData) {
        this.mapData = mapData;
        if (layerManager == null)
            layerManager = new LayerManager(mapData);
        layerManager.update();

        mapHeight = mapData.getProperties().get("height", Integer.class);
        mapWidth = mapData.getProperties().get("width", Integer.class);
        tileWidth = mapData.getProperties().get("tilewidth", Integer.class);
        tileHeight = mapData.getProperties().get("tileheight", Integer.class);

    }

    public TiledMap getData() {
        return mapData;
    }

    public static Integer getMapHeight() {
        return mapHeight;
    }

    public static Integer getMapWidth() {
        return mapWidth;
    }

    public static Integer getTileHeight() {
        return tileHeight;
    }

    public static Integer getTileWidth() {
        return tileWidth;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }

    public void render(float delta) {

    }

    public void dispose() {
        mapData.dispose();
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        this.renderer = new IsometricTiledMapRenderer(getData(), getSpriteBatch());

    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public IsometricTiledMapRenderer getRenderer() {
        return renderer;
    }

    public static Vector2 pixelToCell(float x, float y){
        Vector2 pos = new Vector2(0,0);

        pos.x = Math.round(x/getTileHeight());
        pos.y = Math.round(y/getTileHeight());

        Debug.vector(pos);

        return pos;
    }
}
