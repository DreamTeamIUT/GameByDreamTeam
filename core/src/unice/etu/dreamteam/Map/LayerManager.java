package unice.etu.dreamteam.Map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import unice.etu.dreamteam.Utils.Debug;

import java.util.*;

/**
 * Created by Guillaume on 22/10/2016.
 */
public class LayerManager {
    private final TiledMap map;
    private int tiledLayerIndex;
    private int realLayer = -1;
    private int beforeLayers[];
    private int afterLayers[];
    private int objectLayerIndex;
    private int maxTiledLayer;

    public LayerManager(TiledMap map) {
        this.map = map;
        updateData();
    }

    private void updateData() {
        maxTiledLayer = getMaxLayer();

        if (realLayer == -1)
            realLayer = 1;

        tiledLayerIndex = map.getLayers().getIndex(map.getLayers().get(realLayer + "_T"));
        objectLayerIndex = map.getLayers().getIndex(map.getLayers().get(realLayer + "_O"));

        ArrayList<String> orderedLayers = getOrderedLayersList(map.getLayers());

        int index = realLayer == maxTiledLayer ? realLayer : realLayer + 1;

        List<String> before = orderedLayers.subList(0, orderedLayers.indexOf(index + "_T"));
        List<String> after = orderedLayers.subList(orderedLayers.indexOf(index + "_T"), orderedLayers.size());

        Debug.log(before.toString());
        Debug.log(after.toString());

        beforeLayers = convertLayerNameToId(before);
        afterLayers = convertLayerNameToId(after);

        Debug.log(Arrays.toString(beforeLayers));
        Debug.log(Arrays.toString(afterLayers));


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

    public int getTiledLayerIndex() {
        return tiledLayerIndex;
    }

    public TiledMapTileLayer getCurrentTileLayer() {
        return (TiledMapTileLayer) this.map.getLayers().get(tiledLayerIndex);
    }

    public MapLayer getCurrentObjectLayer() {
        return this.map.getLayers().get(objectLayerIndex);
    }

    public void jumptToNext() {
        realLayer = (realLayer == maxTiledLayer) ? maxTiledLayer : realLayer + 1;
        updateData();
    }

    public void jumpToPrevious() {
        realLayer = (realLayer == 1) ? realLayer : realLayer - 1;
        updateData();
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
        opacity = (opacity > 1) ? 1 : opacity;
        opacity = (opacity < 0) ? 0 : opacity;

        for (TiledMapTileLayer l : map.getLayers().getByType(TiledMapTileLayer.class)) {
            l.setOpacity(opacity);
        }

        getCurrentTileLayer().setOpacity(1);

        if (realLayer != maxTiledLayer)
            map.getLayers().get((realLayer + 1) + "_T").setOpacity(1);
    }

    public void debugObjectLayer(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);

        MapObjects obj = this.getCurrentObjectLayer().getObjects();

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
}
