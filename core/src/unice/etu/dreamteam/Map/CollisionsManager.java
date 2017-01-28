package unice.etu.dreamteam.Map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import unice.etu.dreamteam.Entities.Characters.Graphics.CharacterMove;
import unice.etu.dreamteam.Entities.Characters.Players.Graphics.Player;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Gates.Gate;
import unice.etu.dreamteam.Entities.Gates.Gates;
import unice.etu.dreamteam.Entities.Items.Item;
import unice.etu.dreamteam.Entities.Items.Items;
import unice.etu.dreamteam.Entities.Zones.Zone;
import unice.etu.dreamteam.Entities.Zones.Zones;
import unice.etu.dreamteam.Screens.GameScreen;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;

/**
 * Created by Guillaume on 02/11/2016.
 */
public class CollisionsManager {

    private static final int TYPE_ZONE = 1;
    private static final int TYPE_GATE = 2;
    private final Map map;
    protected final GameScreen game;

    private ArrayList<Character> characters;
    private Story story;

    //TODO : make a generic class that can be used for all other classes/entities/players/mobs
    public CollisionsManager(Map map, GameScreen game) {
        this.map = map;
        this.game = game;
    }


    public void update(float delta) {

    }

    public Object handleCollision(Object a, Object b) {
        //Debug.log("Type a : " + a.getClass().getName());
        //Debug.log("Type b : " + b.getClass().getName());
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

        //Debug.vector(p.getCellPos());

        if (cells.x >= map.getMapHeight() || cells.y >= map.getMapWidth() || cells.x < 0 || cells.y < 0)
            return false;

        if (map.getLayerManager().getCurrentTileLayers().get(0).getCell((int) cells.x, (int) cells.y) == null)
            return false;

        TiledMapTileLayer.Cell c = map.getLayerManager().getCurrentTileLayers().get(1).getCell((int) cells.x, (int) cells.y);
        if (c != null) {
            //Can be more precise...
            if (!TileTypes.contain(c.getTile().getProperties().get("type", "", String.class)))
                return false;
        }

        for (RectangleMapObject gateObject : map.getLayerManager().getCurrentGateLayer().getObjects().getByType(RectangleMapObject.class)) {
            Gate g = Gates.getInstance().get(gateObject.getName());
            if (g == null) {
                Debug.log("GATE", gateObject.getName() + " not exist !");
                continue;
            }

            if (Intersector.overlaps(p.getRectangleAt(cells), gateObject.getRectangle())) {
                if (!g.isOpened())
                    return false;
            }
        }

        for (RectangleMapObject rectangleObject : map.getLayerManager().getCurrentObjectLayer().getObjects().getByType(RectangleMapObject.class)) {
            if (Intersector.overlaps(rectangleObject.getRectangle(), p.getRectangleAt(cells))) {
                return false;
            }
        }

        for (RectangleMapObject rectangleMapObject : map.getLayerManager().getCurrentZoneLayer().getObjects().getByType(RectangleMapObject.class)) {
            if (Intersector.overlaps(rectangleMapObject.getRectangle(), p.getRectangleAt(cells))) {

                if (Zones.getInstance().exist(rectangleMapObject.getName())) {
                    Zone z = Zones.getInstance().get(rectangleMapObject.getName());

                    //Debug.log(!z.canEnter() + " zone");

                    if (z.isIn()) {
                        if (!z.canLeave())
                            return false;
                    } else if (!z.canEnter())
                        return false;
                }
            }
        }


        return true;
    }

    public Boolean canGoTo(int cellx, int cellY, Character p) {
        return canGoTo(new Vector2(cellx, cellY), p);
    }

    public Boolean canGoTo(Vector2 cells) {

        if (!inMap(cells))
            return false;

        if (cellEmpty(cells))
            return false;


        TiledMapTileLayer.Cell c = map.getLayerManager().getCurrentTileLayers().get(1).getCell((int) cells.x, (int) cells.y);
        if (c != null) {
            //Can be more precise...
            if (!TileTypes.contain(c.getTile().getProperties().get("type", "", String.class)))
                return false;
        }

        Rectangle rectangle = defineRect(cells);

        //Debug.log("Invisible Wall -> ok ");

        for (RectangleMapObject rectangleObject : map.getLayerManager().getCurrentObjectLayer().getObjects().getByType(RectangleMapObject.class)) {
            if (Intersector.overlaps(rectangleObject.getRectangle(), rectangle)) {
                //Debug.log("Intersect with" + rectangleObject.getName());
                return false;
            }
        }

        for (RectangleMapObject rectangleMapObject : map.getLayerManager().getCurrentZoneLayer().getObjects().getByType(RectangleMapObject.class)) {
            if (Intersector.overlaps(rectangleMapObject.getRectangle(), rectangle)) {

                if (Zones.getInstance().exist(rectangleMapObject.getName())) {
                    Zone z = Zones.getInstance().get(rectangleMapObject.getName());

                    //Debug.log(!z.canEnter(true) + " zone");

                    if (!z.canEnter(true))
                        return false;
                }
            }
        }

        for (RectangleMapObject gateObject : map.getLayerManager().getCurrentGateLayer().getObjects().getByType(RectangleMapObject.class)) {
            if (Intersector.overlaps(rectangle, gateObject.getRectangle()) && Gates.getInstance().get(gateObject.getName()) != null)
                return false;
        }

        return true;
    }

    private Rectangle defineRect(Vector2 cells) {
        Rectangle zone = new Rectangle();
        zone.width = 32;
        zone.height = 32;
        zone.x = 32 * cells.x;
        zone.y = 32 * cells.y;

        return zone;
    }

    public void addCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public void setStory(Story s) {
        this.story = s;
    }

    public void debug(ShapeRenderer shapeRenderer) {
        map.getLayerManager().debugObjectsLayer(shapeRenderer);

        //TODO : draw zones, items, players, ... ( every thing collision related )
    }

    public void findActionFor(Character p) {

       /* Item.ItemInstance instance = Items.getInstance().getInstanceAt(p.getCellPos());
        if (instance != null){
            instance.onGrab(new MapEvent(p, map, story, game));
        }*/

        for (RectangleMapObject rectangleMapObject : map.getLayerManager().getCurrentZoneLayer().getObjects().getByType(RectangleMapObject.class)) {
            if (Zones.getInstance().exist(rectangleMapObject.getName())) {
                Zone zone = Zones.getInstance().get(rectangleMapObject.getName());

                Debug.log("ZONE", zone.getName() + ", isIn : " + String.valueOf(zone.isIn()));

                if (zone.isIn()) {
                    if (!Intersector.overlaps(rectangleMapObject.getRectangle(), p.getRectangle(true)) && zone.canLeave()) {
                        zone.onLeave();
                    }
                } else {
                    if (Intersector.overlaps(rectangleMapObject.getRectangle(), p.getRectangle(true)) && zone.canEnter()) {
                        zone.onEnter(p.getBatch(), p.getShapeRender());
                    }
                }
            }
        }

        for (RectangleMapObject gateObject : map.getLayerManager().getCurrentGateLayer().getObjects().getByType(RectangleMapObject.class)) {
            Gate g = Gates.getInstance().get(gateObject.getName());
            if (g == null)
                continue;

            if (Intersector.overlaps(p.getRectangle(true), gateObject.getRectangle()) && g.isOpened()) {
                g.onEnter(new MapEvent(p, map, story, game));
                break;
            }
        }

    }

    public void doGrab(Player p) {
        Debug.log("GRAB", "Try grab ...");
        Vector2 pos = new Vector2();
        pos.x = p.getCellPos().x;
        pos.y = p.getCellPos().y;
        switch (p.getView()) {
            case CharacterMove.DOWN:
                pos.y -= 1;
                break;
            case CharacterMove.RIGHT:
                pos.x += 1;
                break;
            case CharacterMove.UP:
                pos.y += 1;
                break;
            case CharacterMove.LEFT:
                pos.x -= 1;
                break;
        }

        if (inMap(pos) && !cellEmpty(pos)) {
            Debug.log("CHeck instace d");
            Item.ItemInstance instance = Items.getInstance().getInstanceAt(pos);
            if (instance != null) {
                instance.onGrab(new MapEvent(p, map, story, game));
                map.setGridUpdate(true);
            }
        }
    }


    public Boolean inMap(Vector2 cells){
        return !(cells.x >= map.getMapHeight() || cells.y >= map.getMapWidth() || cells.x < 0 || cells.y < 0);
       /* */
    }

    public Boolean cellEmpty(Vector2 cells){
        return map.getLayerManager().getCurrentTileLayers().get(0).getCell((int) cells.x, (int) cells.y) == null;
    }
}