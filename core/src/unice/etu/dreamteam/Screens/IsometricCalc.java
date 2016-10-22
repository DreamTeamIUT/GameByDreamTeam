package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import unice.etu.dreamteam.Map.LayerManager;
import unice.etu.dreamteam.Player;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;
import java.util.Arrays;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by Guillaume on 26/09/2016.
 */
public class IsometricCalc extends AbstractScreen implements InputProcessor {
    private TiledMap map;
    private OrthographicCamera globCam;
    private IsometricTiledMapRenderer renderer;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private Matrix4 isoTransform;
    private Matrix4 invIsotransform;
    private Vector3 pos = new Vector3(0, 0, 0);
    private Integer tileWidth;
    private Integer tileHeight;
    private Integer mapWidth;
    private Player player;
    private Integer mapHeight;
    private java.util.List<Integer> listLayers;
    private LayerManager layerManager;


    public IsometricCalc() {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void buildStage() {

        isoTransform = new Matrix4();
        isoTransform.idt();

        // isoTransform.translate(0, 32, 0);
        isoTransform.scale((float) (Math.sqrt(2.0)), (float) (Math.sqrt(2.0) / 2), 1.0f);
        //isoTransform.scale((float) (Math.sqrt(2.0)/2), (float) (Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

        // ... and the inverse matrix
        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();

        globCam = (OrthographicCamera) getCamera();

        map = new TmxMapLoader().load("assets/map/test.tmx"); //permet de charger la map depuis le fichier fournis en paramètre et réaliser sur tiled.


        mapHeight = map.getProperties().get("height", Integer.class);
        mapWidth = map.getProperties().get("width", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);

        player = new Player();
        player.setCell(0, 0);


        spriteBatch = new SpriteBatch();

        layerManager = new LayerManager(map);
        layerManager.setLayersOpacity(0.3f);

        renderer = new IsometricTiledMapRenderer(map, spriteBatch);

        globCam.setToOrtho(false, mapWidth, mapHeight);
        globCam.zoom = 1f;
        globCam.update();
        renderer.setView(globCam);

        shapeRenderer = new ShapeRenderer();


        input.setInputProcessor(this);
    }

    private Vector3 convertPos(Rectangle r) {


        Vector3 cell = new Vector3();
        cell.x = (float) Math.floor((32 + r.getX()) / 32);
        cell.y = (float) Math.floor((32 + r.getY()) / 32);

        Debug.vector(cell);

        Vector3 dest = getPosAtCell(cell.x, cell.y);

        Debug.vector(dest);
        return dest;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        globCam.update();

        shapeRenderer.setProjectionMatrix(globCam.combined);
        shapeRenderer.setTransformMatrix(isoTransform);

        spriteBatch.begin();

        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("individual_tiles/tile__64.png")));

        sprite.setPosition(pos.x, pos.y);
        sprite.setColor(Color.WHITE);
        sprite.draw(spriteBatch);
        spriteBatch.end();

        renderer.setView(globCam);

        layerManager.debugObjectLayer(shapeRenderer);
        renderer.render(layerManager.getBeforeLayers());
        drawPlayer();

        renderer.render(layerManager.getAfterLayers());
    }


    private void drawPlayer() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 1f, 1);
        shapeRenderer.rect(player.getRectangle().x - 16, player.getRectangle().y + 16, player.getRectangle().getWidth(), player.getRectangle().getHeight());
        shapeRenderer.setColor(0, 0, 1f, 1);
        shapeRenderer.rect(player.getRectangle().x - 16 - 2 * 16, player.getRectangle().y + 16 + 2 * 16, player.getRectangle().getWidth(), player.getRectangle().getHeight());
        shapeRenderer.end();
    }

    public Vector3 getPosAtCell(float x, float y) {
        Vector3 dest = new Vector3(32 * x, 32 * y, 0);
        dest.rot(isoTransform);
        return dest;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
                player.moveToRight();
                if (detectSimpleColision(player)) {
                    player.moveToLeft();
                }
                break;
            case Input.Keys.Q:
                player.moveToLeft();
                if (detectSimpleColision(player)) {
                    player.moveToRight();
                }
                break;
            case Input.Keys.Z:
                player.moveToUp();
                if (detectSimpleColision(player)) {
                    player.moveToDown();
                }
                break;
            case Input.Keys.S:
                player.moveToDown();
                if (detectSimpleColision(player)) {
                    player.moveToUp();
                }
                break;
            case Input.Keys.E:
                layerManager.jumptToNext();
                break;
            case Input.Keys.R:
                layerManager.jumpToPrevious();
                break;
        }
        return false;
    }

    private boolean detectSimpleColision(Player player) {


        if (player.getCurentCells().x >= 10 || player.getCurentCells().y >= 10 || player.getCurentCells().x < 0 || player.getCurentCells().y < 0)
            return true;

        ArrayList<Boolean> listIntersect = new ArrayList<Boolean>();

        TiledMapTileLayer layer = map.getLayers().getByType(TiledMapTileLayer.class).get(0);
        TiledMapTileLayer.Cell currentCell = layer.getCell((int) player.getCurentCells().x, (int) player.getCurentCells().y);
        if (currentCell != null) {
            Integer type = currentCell.getTile().getProperties().get("type", Integer.class);
            if (type != null) {
                if (type == 1)
                    Debug.log("Special celll");
            }
        }


        MapObjects obj = layerManager.getCurrentObjectLayer().getObjects();
        for (RectangleMapObject rectangleObject : obj.getByType(RectangleMapObject.class)) {

            Rectangle r = rectangleObject.getRectangle();
            listIntersect.add(Intersector.overlaps(r, player.getRectangle()));
            Debug.log("Intersector", rectangleObject.getName() + ": " + listIntersect.get(listIntersect.size() - 1));
        }

        Debug.log("Intersector", "contain = " + listIntersect.contains(Boolean.TRUE));

        return listIntersect.contains(Boolean.TRUE);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Debug.vector(pos);

        Vector3 point = new Vector3(screenX + 16, screenY + 32, 0);

        globCam.unproject(point);

        point.x /= tileWidth;
        point.y = (point.y - tileHeight / 2) / tileHeight + point.x;
        point.x -= point.y - point.x;

        point.x = (float) Math.floor(point.x);
        point.y = (float) Math.floor(point.y);

        Debug.vector(point);

        pos = getPosAtCell(point.x, point.y);
        player.setCell((int) point.x, (int) point.y);


        /*TiledMapTileLayer l = (TiledMapTileLayer) map.getLayers().get(1);
        TiledMapTileLayer.Cell cell = l.getCell((int) Math.floor(point.x), (int) Math.floor(point.y));
        TiledMapTile tile = map.getTileSets().getTileSet(0).getTile(2);
        if (cell != null)
            cell.setTile(tile);
        else {
            cell = new TiledMapTileLayer.Cell();
            cell.setTile(tile);
            l.setCell((int) Math.floor(point.x), (int) Math.floor(point.y), cell);
        }*/

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {


        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        spriteBatch.dispose();
    }
}
