package unice.etu.dreamteam.Map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.regexp.internal.RE;
import unice.etu.dreamteam.Characters.Player;
import unice.etu.dreamteam.Characters.Character;
import unice.etu.dreamteam.Entities.Gate;
import unice.etu.dreamteam.Entities.Zone;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;

/**
 * Created by Guillaume on 02/11/2016.
 */
public class CollisionsManager {

    private static final int TYPE_ZONE = 1;
    private static final int TYPE_GATE = 2;
    private final Map map;

    private ArrayList<Character> characters;
    private Story story;

    //TODO : make a generic class that can be used for all other classes/entities/players/mobs
    public CollisionsManager(Map map) {
        this.map = map;
    }


    public void update(float delta) {

    }

    public Object handleCollision(Object a, Object b) {
        Debug.log("Type a : " + a.getClass().getName());
        Debug.log("Type b : " + b.getClass().getName());
        return null;
    }

    public Object hasCollisionWith(Player p) {

        //TODO : check if hole or Wall ( if zone is tagged as not existable, it act like wall )
        //      Return

        //TODO : Contact object

        //TODO : check if going in zone
        //      return zone

        //TODO :

        // return null if no colid, if colid : return the object that colid with ( item, zone, ... )
        //todo : read memory and grab position of any object ( bullet, player, holes, objects,)
        return null;
    }

    public Object hasCollisionWithAt(Vector2 cell, Character p) {
        return null;
    }

    public Object hasRelativeCollisionWithAt(float x, float y, Character p) {
        Vector2 v = new Vector2(p.getCellPos().x + x, p.getCellPos().y + y);
        return hasCollisionWithAt(v, p);
    }

    public Boolean canGoTo(Vector2 cells, Character p) {

        Debug.vector(p.getCellPos());

        if (cells.x >= map.getMapHeight() || cells.y >= map.getMapWidth() || cells.x < 0 || cells.y < 0)
            return false;

        if (map.getLayerManager().getCurrentTileLayer().get(0).getCell((int) cells.x, (int) cells.y) == null)
            return false;

        TiledMapTileLayer.Cell c = map.getLayerManager().getCurrentTileLayer().get(1).getCell((int) cells.x, (int) cells.y);
        if (c != null) {
            String value = c.getTile().getProperties().get("type", String.class);
            if (value != null && !value.equals("GATE"))
                return false;

        }


        Debug.log("Invisible Wall -> ok ");

        for (RectangleMapObject rectangleObject : map.getLayerManager().getCurrentObjectLayer().getObjects().getByType(RectangleMapObject.class)) {
            if (Intersector.overlaps(rectangleObject.getRectangle(), p.getRectangleAt(cells))) {
                Debug.log("Intersect with" + rectangleObject.getName());
                return false;
            }
        }

        for (RectangleMapObject rectangleMapObject : map.getLayerManager().getCurrentZoneLayer().getObjects().getByType(RectangleMapObject.class)) {
            if (Intersector.overlaps(rectangleMapObject.getRectangle(), p.getRectangleAt(cells))) {

                if (story.getZones().exist(rectangleMapObject.getName())) {
                    Zone z = story.getZones().get(rectangleMapObject.getName());

                    Debug.log(!z.canEnter() + " zone");

                    if (z.isIn()) {
                        if (!z.canLeave())
                            return false;
                    } else if (!z.canEnter())
                        return false;
                }
            }
        }

        for (RectangleMapObject gateObject : map.getLayerManager().getCurrentGateLayer().getObjects().getByType(RectangleMapObject.class)) {
            Gate g = story.getGates().get(gateObject.getName());
            if (Intersector.overlaps(p.getRectangleAt(cells), gateObject.getRectangle())) {
                if (!g.isOpen())
                    return false;
            }
        }


        return true;
    }

    public Boolean canGoTo(int cellx, int cellY, Character p) {
        return canGoTo(new Vector2(cellx, cellY), p);
    }


    public void addCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public void addStory(Story s) {
        this.story = s;
    }

    public void debug(ShapeRenderer shapeRenderer) {
        map.getLayerManager().debugObjectsLayer(shapeRenderer);

        //TODO : draw zones, items, players, ... ( every thing collision related )
    }

    public void findActionFor(Character p) {
        for (RectangleMapObject rectangleMapObject : map.getLayerManager().getCurrentZoneLayer().getObjects().getByType(RectangleMapObject.class)) {
            if (story.getZones().exist(rectangleMapObject.getName())) {
                Zone zone = story.getZones().get(rectangleMapObject.getName());

                if (zone.isIn()) {
                    if (!Intersector.overlaps(rectangleMapObject.getRectangle(), p.getRectangle()) && zone.canLeave()) {
                        zone.onLeave();
                    }
                } else {
                    Debug.log("not in");
                    if (Intersector.overlaps(rectangleMapObject.getRectangle(), p.getRectangle()) && zone.canEnter()) {
                        zone.onEnter();
                    }
                }
            }
        }

        for (RectangleMapObject gateObject : map.getLayerManager().getCurrentGateLayer().getObjects().getByType(RectangleMapObject.class)) {
            Gate g = story.getGates().get(gateObject.getName());
            Debug.log(gateObject.getName());
            Debug.log(g.getName() + " : " + (Intersector.overlaps(p.getRectangle(), gateObject.getRectangle()) && g.isOpen()));
            Debug.log(gateObject.getRectangle().toString());
            if (Intersector.overlaps(p.getRectangle(), gateObject.getRectangle()) && g.isOpen()) {
                g.onPass(new MapEvent(p, map));
                break;
            }
        }

    }
}