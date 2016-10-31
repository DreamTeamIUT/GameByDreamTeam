package unice.etu.dreamteam.Maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import unice.etu.dreamteam.old_Generic.Player;

/**
 * Created by Romain on 15/09/2016.
 */
public class TiledMapSample extends Stage {
    private static final float VIRTUAL_WIDTH = 384.0f; //Taile du "point de vue" : "Distance" de la map
    private static final float VIRTUAL_HEIGHT = 216.0f; //De même ici

    private static final float CAMERA_SPEED = 5.0f; //Pour bouger la caméra

    private TiledMap map;
    OrthogonalTiledMapRenderer renderer; //Pour dessiner l'instance TiledMap sur l'écran.
    private Vector2 direction; //Pour controler le mouvement de la caméra

    private OrthographicCamera camera;

    private BitmapFont fpsFont;
    private SpriteBatch batch;

    private Player test;


    public TiledMapSample() {
        //...
        //utilise les fichiers d'extension .tmx d'où sa mise en place ici.
        map = new TmxMapLoader().load("assets/maps/desert.tmx"); //permet de charger la map depuis le fichier fournis en paramètre et réaliser sur tiled.

        camera = new OrthographicCamera();
        camera.setToOrtho(false, getViewport().getWorldWidth(), getViewport().getWorldHeight());
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map);

        direction = new Vector2();

        fpsFont = new BitmapFont();
        batch = new SpriteBatch();

        InputsMap inputs = new InputsMap(camera);

        Gdx.input.setInputProcessor(inputs);

        //déclaration test du player
        test = new Player("assets/sprites/player01.png", "Player");
        test.setPosition(50, 50);
    }

    public void render() {
        //updateCamera();
        //camera.update();

        camera.update();

        renderer.setView(camera);
        renderer.render();

        batch.begin();
        test.drawPlayer(batch, Gdx.graphics.getDeltaTime());
        fpsFont.draw(batch, "FPS:" + Gdx.graphics.getFramesPerSecond(), 15, 15);
        batch.end();
    }

    public void updateCamera() {

        /*Explications
Tout d'abord, le vecteur de direction est mis à zéro (0, 0) et il va être régler en fonction des touches directionnelles qui
sont pressées ou lorsque l'utilisateur est en contact des bords de l'écran.
Nous normalisons le vecteur donc sa longueur est égale à 1.
Enfin, nous determinons la vitesse de la caméra à l'aide du temps delta et l'ajoutons à la position de la caméra:
        */

        direction.set(0.0f, 0.0f);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isTouched() && mouseX < width * 0.25f)) {
            direction.x = -1;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (Gdx.input. isTouched() && mouseX > width * 0.75f)) {
            direction.x = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || (Gdx.input.isTouched() && mouseY < height * 0.25f)) {
            direction.y = 1;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || (Gdx.input. isTouched() && mouseY > height * 0.75f)) {
            direction.y = -1;
        }

        direction.nor().scl(CAMERA_SPEED * Gdx.graphics.getDeltaTime());

        camera.position.x += direction.x;
        camera.position.y += direction.y;

        camera.update();
    }


    public void getLayer() {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        float cameraMinX = getViewport().getWorldWidth() * 0.5f;
        float cameraMinY = getViewport().getWorldHeight() * 0.5f;
        float cameraMaxX = layer.getWidth() * layer.getTileWidth() - cameraMinX;
        float cameraMaxY = layer.getHeight() * layer.getTileHeight() - cameraMinY;

        camera.position.x = MathUtils.clamp(camera.position.x, cameraMinX, cameraMaxX);
        camera.position.y = MathUtils.clamp(camera.position.y, cameraMinY, cameraMaxY);

        camera.update();
}




}

