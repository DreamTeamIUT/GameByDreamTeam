package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sun.javafx.scene.paint.GradientUtils;
import unice.etu.dreamteam.Characters.CharacterList;
import unice.etu.dreamteam.Characters.ModelAnimationManager;
import unice.etu.dreamteam.Utils.Debug;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.graphics.g2d.ParticleEmitter.SpawnShape.point;

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
    private Integer mapHeight;


    public IsometricCalc() {
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void buildStage() {

        isoTransform = new Matrix4();
        isoTransform.idt();

        // isoTransform.translate(0, 32, 0);
        isoTransform.scale((float) (Math.sqrt(2.0) / 2.0), (float) (Math.sqrt(2.0) / 4.0), 1.0f);
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


        spriteBatch = new SpriteBatch();


        renderer = new IsometricTiledMapRenderer(map, spriteBatch);

        globCam.setToOrtho(false, mapWidth, mapHeight);
        globCam.zoom = 1f;
        globCam.update();
        renderer.setView(globCam);

        shapeRenderer = new ShapeRenderer();

        input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);


        globCam.update();

        spriteBatch.begin();

        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("individual_tiles/tile__64.png")));
        sprite.setPosition(pos.x, pos.y);
        sprite.setColor(Color.WHITE);
        sprite.draw(spriteBatch);

        spriteBatch.end();

        renderer.setView(globCam);
        renderer.render();


        getCurrentCell();


    }

    public void getCurrentCell() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 point = new Vector3(screenX + 16, screenY + 32, 0);

        globCam.unproject(point);
        point.x /= tileWidth;
        point.y = (point.y - tileHeight / 2) / tileHeight + point.x;
        point.x -= point.y - point.x;
        Debug.log(" x:" + point.x + " y:" + point.y);

        TiledMapTileLayer l = (TiledMapTileLayer) map.getLayers().get(0);
        TiledMapTileLayer.Cell cell = l.getCell((int) Math.floor(point.x), (int) Math.floor(point.y));
        TiledMapTile tile = map.getTileSets().getTileSet(0).getTile(2);
        if (cell != null)
            cell.setTile(tile);
        else {
            cell = new TiledMapTileLayer.Cell();
            cell.setTile(tile);
            l.setCell((int) Math.floor(point.x), (int) Math.floor(point.y), cell);
        }

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

/*    private float getRelativeAngle(float anglesDest) {
        return anglesDest - anglePerso;
    }
*/

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
        spriteBatch.dispose();
    }
}
