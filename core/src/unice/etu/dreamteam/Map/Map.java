package unice.etu.dreamteam.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import unice.etu.dreamteam.Entities.Maps.MapHolder;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

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
    private MapHolder mapInfo;
    private NavigationGrid<GridCell> navigationGrid;
    private boolean gridUpdate;
    private CollisionsManager collisionsManager;

    public Map(MapHolder mapInfo) {
        this.mapInfo = mapInfo;
    }


    public static Map load(String path, MapHolder mapInfo) {
        Map map = new Map(mapInfo);
        map.setMapData(getMapData(path));
        return map;
    }

    public static TiledMap getMapData(String path) {
        return new TmxMapLoader().load(GameInformation.getGamePackage().getPackagePath() + "/maps/" + path);
    }

    public void setMapData(TiledMap mapData) {

        Debug.log("Set map data, map data : " + mapData);

        TiledMap old = this.mapData;
        if (old != null) {
            old.dispose();
            this.mapData = null;
        }

        this.mapData = mapData;


        if (layerManager == null)
            layerManager = new LayerManager(this.mapData);
        layerManager.update();

        mapHeight = this.mapData.getProperties().get("height", Integer.class);
        mapWidth = this.mapData.getProperties().get("width", Integer.class);
        tileWidth = this.mapData.getProperties().get("tilewidth", Integer.class);
        tileHeight = this.mapData.getProperties().get("tileheight", Integer.class);
    }

    public void calculateGridCell(CollisionsManager collisionsManager) {
        this.collisionsManager = collisionsManager;

        GridCell[][] gridCells = new GridCell[mapWidth][mapHeight];

        for (int x = 0; x < getMapWidth(); x++) {
            for (int y = 0; y < getMapHeight(); y++) {
                gridCells[x][y] = new GridCell(x, y, collisionsManager.canGoTo(new Vector2(x, y)));
            }
        }

        navigationGrid = new NavigationGrid<>(gridCells, false);
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
        if (gridUpdate) {
            Debug.log("UPDATE", "update grid ! ");
            calculateGridCell(this.collisionsManager);
            setGridUpdate(false);
        }
    }

    public void dispose() {
        renderer.dispose();
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

    public static Vector2 pixelToCell(float x, float y) {
        Vector2 pos = new Vector2(0, 0);

        pos.x = Math.round(x / getTileHeight());
        pos.y = Math.round(y / getTileHeight());

        Debug.vector(pos);

        return pos;
    }

    public TiledMapTile getTileByName(String name){
        for (TiledMapTileSet tiledMapTileSet : this.getData().getTileSets()){
            for (TiledMapTile t : tiledMapTileSet){
                if (t.getProperties().get("name", "", String.class).equals(name)){
                    return t;
                }
            }
        }
        return null;
    }

    public MapHolder getMapInfo() {
        return mapInfo;
    }

    public NavigationGrid<GridCell> getNavigationGrid() {
        return navigationGrid;
    }

    public void setGridUpdate(boolean gridUpdate) {
        this.gridUpdate = gridUpdate;
    }
}
