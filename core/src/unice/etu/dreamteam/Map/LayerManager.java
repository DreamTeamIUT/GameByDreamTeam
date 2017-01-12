package unice.etu.dreamteam.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import unice.etu.dreamteam.Utils.Debug;

import java.util.*;

/**
 * Created by Guillaume on 22/10/2016.
 */
public class LayerManager {

    private static final Color DEBUG_ZONE_COLOR = Color.valueOf("cf7900ff");
    private static final Color DEBUG_OBJECT_COLOR = Color.valueOf("00ff00ff");
    private static final Color DEBUG_GATE_COLOR = Color.valueOf("4742d8ff");

    private int currentFloor = 1;
    private ArrayList<TiledMapTileLayer> tiledLayers;


    private final TiledMap map;
    private int beforeLayers[];
    private int afterLayers[];
    private int maxTiledLayer;
    private float opacity = 1;

    private int zoneLayerIndex;
    private int objectLayerIndex;
    private int gateLayerIndex;


    public LayerManager(TiledMap map) {
        tiledLayers = new ArrayList<>();
        this.map = map;
        updateData();
    }

    private void updateData() {
        maxTiledLayer = getMaxLayer();

        if (currentFloor == -1)
            currentFloor = 1;

        for (MapLayer l : map.getLayers()) {
            if (l.getName().contains(currentFloor + "_T"))
                tiledLayers.add((TiledMapTileLayer) l);
        }


        if (map.getLayers().get(currentFloor + "_O") == null)
            addEmptyObjectLayer(currentFloor + "_O");
        if (map.getLayers().get(currentFloor + "_Z") == null)
            addEmptyObjectLayer(currentFloor + "_Z");


        objectLayerIndex = map.getLayers().getIndex(map.getLayers().get(currentFloor + "_O"));
        zoneLayerIndex = map.getLayers().getIndex(map.getLayers().get(currentFloor + "_Z"));
        gateLayerIndex = map.getLayers().getIndex(map.getLayers().get(currentFloor + "_G"));

        ArrayList<String> orderedLayers = getOrderedLayersList(map.getLayers());

        int index = currentFloor == maxTiledLayer ? currentFloor : currentFloor + 1;

        List<String> before = orderedLayers.subList(0, orderedLayers.indexOf(index + "_T1"));
        List<String> after = orderedLayers.subList(orderedLayers.indexOf(index + "_T1"), orderedLayers.size());

        Debug.log(before.toString());
        Debug.log(after.toString());

        beforeLayers = convertLayerNameToId(before);
        afterLayers = convertLayerNameToId(after);

        Debug.log(Arrays.toString(beforeLayers));
        Debug.log(Arrays.toString(afterLayers));


    }

    public void update() {
        updateData();

    }

    public HashMap<Vector2, String> getTilePrositionWithProperty(String property, String value) {
        HashMap<Vector2, String> selectedTiles = new HashMap<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < getCurrentTileLayers().size(); i++) {
            for (int x = 0; x < Map.getMapWidth(); x++) {
                for (int y = 0; y < Map.getMapHeight(); y++) {
                    TiledMapTileLayer.Cell c = getCurrentTileLayers().get(i).getCell(x, y);
                    if (c != null) {
                        Object valueProperty = c.getTile().getProperties().get(property);
                        if (valueProperty != null) {
                            if (String.valueOf(valueProperty).equals(value))
                                selectedTiles.put(new Vector2(x,y), c.getTile().getProperties().get("name", null, String.class));
                        }
                    }
                }
            }
        }
        Debug.log("TIMING", String.valueOf(System.currentTimeMillis() - start));
        return selectedTiles;
    }

    public TiledMapTileLayer getLayerForTypeAt(String type, Vector2 at){
        for (int i=0; i< getCurrentTileLayers().size(); i++){
            TiledMapTileLayer.Cell c = getCurrentTileLayers().get(i).getCell((int)at.x, (int)at.y);
            if (c != null)
            {
                if (c.getTile().getProperties().get("type", "", String.class).equals(type))
                    return getCurrentTileLayers().get(i);
            }
        }
        return null;
    }

    private int getMaxLayer() {
        int max = 0;
        for (MapLayer l : map.getLayers().getByType(TiledMapTileLayer.class)) {
            int n = Integer.parseInt(l.getName().split("_")[0]);
            if (n > max)
                max = n;
        }

        Debug.log("Max is :" + max);
        return max;
    }


    public ArrayList<TiledMapTileLayer> getCurrentTileLayers() {
        return tiledLayers;
    }

    public MapLayer getCurrentObjectLayer() {
        return this.map.getLayers().get(objectLayerIndex);
    }

    public MapLayer getCurrentZoneLayer() {
        return this.map.getLayers().get(zoneLayerIndex);
    }

    public MapLayer getCurrentGateLayer() {
        return this.map.getLayers().get(gateLayerIndex);
    }


    public void jumptToNext() {
        currentFloor = (currentFloor == maxTiledLayer) ? maxTiledLayer : currentFloor + 1;
        updateData();
        setLayersOpacity(opacity);

    }

    public void jumpToPrevious() {
        currentFloor = (currentFloor == 1) ? currentFloor : currentFloor - 1;
        updateData();
        setLayersOpacity(opacity);
    }

    private int[] convertLayerNameToId(List<String> names) {
        int[] layerIds = new int[names.size()];

        for (int i = 0; i < names.size(); i++) {
            layerIds[i] = map.getLayers().getIndex(map.getLayers().get(names.get(i)));
        }
        return layerIds;
    }

    private ArrayList<String> getOrderedLayersList(MapLayers layers) {
        ArrayList<String> layerList = new ArrayList<String>();

        for (MapLayer layer : layers) {
            if (layer.getName().split("_").length == 2) {
                layerList.add(layer.getName());
            }
        }

        Collections.sort(layerList, new Comparator<String>() {
            @Override
            public int compare(final String object1, final String object2) {
                return object1.compareTo(object2);
            }
        });

        Debug.log(layerList.toString());
        return layerList;
    }

    public void setLayersOpacity(float opacity) {

        this.opacity = (opacity > 1) ? 1 : opacity;
        this.opacity = (opacity < 0) ? 0 : opacity;

        for (TiledMapTileLayer l : map.getLayers().getByType(TiledMapTileLayer.class))
            l.setOpacity(opacity);


        for (MapLayer l : getCurrentTileLayers())
            l.setOpacity(1);

    }

    public void debugObjectsLayer(ShapeRenderer shapeRenderer) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


        shapeRenderer.setColor(DEBUG_OBJECT_COLOR);

        MapObjects obj = this.getCurrentObjectLayer().getObjects();

        for (RectangleMapObject rectangleObject : obj.getByType(RectangleMapObject.class)) {
            Rectangle r = rectangleObject.getRectangle();
            shapeRenderer.rect(r.getX() - 16, r.getY() + 16, r.getWidth(), r.getHeight());
        }


        shapeRenderer.setColor(DEBUG_ZONE_COLOR);

        obj = this.getCurrentZoneLayer().getObjects();

        for (RectangleMapObject rectangleObject : obj.getByType(RectangleMapObject.class)) {
            Rectangle r = rectangleObject.getRectangle();
            shapeRenderer.rect(r.getX() - 16, r.getY() + 16, r.getWidth(), r.getHeight());
        }


        shapeRenderer.setColor(DEBUG_GATE_COLOR);

        obj = this.getCurrentGateLayer().getObjects();

        for (RectangleMapObject rectangleObject : obj.getByType(RectangleMapObject.class)) {
            Rectangle r = rectangleObject.getRectangle();
            shapeRenderer.rect(r.getX() - 16, r.getY() + 16, r.getWidth(), r.getHeight());
        }


        shapeRenderer.end();
    }

    public int[] getBeforeLayers() {
        return beforeLayers;
    }

    public int[] getAfterLayers() {
        return afterLayers;
    }

    private void addEmptyObjectLayer(String name) {
        MapLayer layer = new MapLayer();
        layer.setOpacity(1);
        layer.setName(name);
        layer.setVisible(true);
        map.getLayers().add(layer);
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16), 1);
    }
}
