package unice.etu.dreamteam.Maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

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

    public void dispose() {
        mapData.dispose();
    }
}
