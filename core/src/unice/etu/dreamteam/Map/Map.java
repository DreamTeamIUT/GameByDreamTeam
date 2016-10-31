package unice.etu.dreamteam.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Map {

    private LayerManager layerManager;
    private TiledMap mapData;
    private Integer mapHeight;
    private Integer mapWidth;
    private Integer tileHeight;
    private Integer tileWidth;
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

    public Integer getMapHeight() {
        return mapHeight;
    }

    public Integer getMapWidth() {
        return mapWidth;
    }

    public Integer getTileHeight() {
        return tileHeight;
    }

    public Integer getTileWidth() {
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
}
