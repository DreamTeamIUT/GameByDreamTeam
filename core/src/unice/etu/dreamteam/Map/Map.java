package unice.etu.dreamteam.Map;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import unice.etu.dreamteam.Entities.Bullets.Bullet;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Characters.Mobs.Graphics.Mob;
import unice.etu.dreamteam.Entities.Characters.Players.Graphics.Player;
import unice.etu.dreamteam.Entities.Gates.Gate;
import unice.etu.dreamteam.Entities.Gates.Gates;
import unice.etu.dreamteam.Entities.Items.Item;
import unice.etu.dreamteam.Entities.Maps.MapHolder;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

import java.util.ArrayList;

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

        map.updateGates();

        return map;
    }

    private void updateGates() {

        Gates.getInstance().clear();

        for (MapObject o :layerManager.getCurrentGateLayer().getObjects()){
            RectangleMapObject rectangleMapObject = (RectangleMapObject) o;
            if (rectangleMapObject != null && !rectangleMapObject.getName().equals("")){
               Gates.getInstance().add(new Gate(rectangleMapObject));
               Debug.log("MAP", "New Gate ");
            }
        }

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

    public void render(float delta, Player p) {
        if (gridUpdate) {
            Debug.log("UPDATE", "update grid ! ");
            calculateGridCell(this.collisionsManager);
            setGridUpdate(false);
        }

        this.getRenderer().render(this.getLayerManager().getBeforeLayers());

        ArrayList<ArrayList<TiledMapTileLayer>> layersToDraw = new ArrayList<>();
        this.layerManager.getLayersForPlayer(layersToDraw, p);

        ArrayList<TiledMapTileLayer> backgrounds = layersToDraw.get(0);
        ArrayList<TiledMapTileLayer> foregrounds = layersToDraw.get(1);

        this.getSpriteBatch().begin();
        for (TiledMapTileLayer l : backgrounds) {
            this.getRenderer().renderTileLayer(l);
        }
        this.getSpriteBatch().end();

        p.render(delta);

        for (Bullet.Graphic bullet : p.getWeapon().getBullets()) {
            if (Intersector.overlaps(bullet.getRectangle(), p.getRectangle()) && !bullet.isTouched() && !bullet.alreadyTouched(p.getId())) {
                Debug.log("MAP", "Collision player");

                bullet.haveCollision(p.getId());
                p.getStats().injuries(bullet.getDamage());
            }
        }

        for (Mob mob : GraphicalInstances.getInstance().getMobs()) {
            mob.render(delta);
            for (Bullet.Graphic bullet : p.getWeapon().getBullets()){
                if (Intersector.overlaps(bullet.getRectangle(), mob.getRectangle())){
                    //TODO : Retirer des pv au mob / Si pv = 0 alors mob = disparu.
                    Debug.log("MAP", "Collision mob");
                }
            }

            for(Bullet.Graphic bullet : mob.getWeapon().getBullets()) {
                if (Intersector.overlaps(bullet.getRectangle(), p.getRectangle())) {
                    Debug.log("MAP", "Collision player");
                }
            }
        }

        for (Item.ItemInstance itemInstance : GraphicalInstances.getInstance().getItemInstances()) {
            if(!itemInstance.isDrawn())
                itemInstance.onThrown(this);
        }

        this.getSpriteBatch().begin();
        for (TiledMapTileLayer l : foregrounds)
            this.getRenderer().renderTileLayer(l);
        this.getSpriteBatch().end();
    }
    public void render_new(float delta, Player p) {
        //Debug.log("MAP", "---------- Render ----------");
        if (gridUpdate) {
            Debug.log("UPDATE", "update grid ! ");
            calculateGridCell(this.collisionsManager);
            setGridUpdate(false);
        }

        this.getRenderer().render(this.getLayerManager().getBeforeLayers());

        ArrayList<Character> characters = new ArrayList<>();
        characters.addAll(GraphicalInstances.getInstance().getMobs());
        characters.add(p);

        int stop = 0;

        for (Character c : characters){
            for(int l : layerManager.getAfterLayers()){
                TiledMapTileLayer orig = (TiledMapTileLayer) this.mapData.getLayers().get(l);

                if (!orig.isVisible())
                    continue;

                TiledMapTileLayer layer = new TiledMapTileLayer(orig.getWidth(), orig.getHeight(), (int)orig.getTileWidth(), (int)orig.getTileHeight());
                layer.setOffsetX(orig.getOffsetX());
                layer.setOffsetY(orig.getOffsetY());

                for (int x=stop; x<c.getCellPos().x+1; x++){
                    for (int y = 0; y < orig.getHeight(); y++) {
                        layer.setCell(x,y, orig.getCell(x,y));
                    }
                }

                this.spriteBatch.begin();
                this.renderer.renderTileLayer(layer);
                this.spriteBatch.end();

            }
            stop = (int) c.getCellPos().x;

            c.render(delta);
        }

        for(int l : layerManager.getAfterLayers()){
            TiledMapTileLayer orig = (TiledMapTileLayer) this.mapData.getLayers().get(l);

            if (!orig.isVisible())
                continue;

            TiledMapTileLayer layer = new TiledMapTileLayer(orig.getWidth(), orig.getHeight(), (int) orig.getTileWidth(), (int) orig.getTileHeight());
            layer.setOffsetX(orig.getOffsetX());
            layer.setOffsetY(orig.getOffsetY());

            //Debug.log("MAP", "Stop : " + stop);

            for (int x=stop+1; x<orig.getWidth(); x++){
                for (int y = 0; y < orig.getHeight(); y++) {
                    layer.setCell(x,y, orig.getCell(x,y));
                }
            }

            this.spriteBatch.begin();
            this.renderer.renderTileLayer(layer);
            this.spriteBatch.end();

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

    public TiledMapTile getTileByName(String name) {
        for (TiledMapTileSet tiledMapTileSet : this.getData().getTileSets()) {
            for (TiledMapTile t : tiledMapTileSet) {
                if (t.getProperties().get("name", "", String.class).equals(name)) {
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
