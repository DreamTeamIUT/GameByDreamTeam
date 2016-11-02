package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.ModelAnimationManager;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.ModelConverter;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Character implements Disposable {

    private ModelConverter modelConverter;
    private ModelAnimationManager animationManager;
    private Vector2 cellPos;
    private Vector2 realPos;
    private Rectangle playerZone;
    private String modelName;
    private String name;
    private SpriteBatch batch;
    private Boolean debug;
    private ShapeRenderer shapeRenderer;

    public Character(JsonValue informations) {
        Debug.log("Load character");
        loadJson(informations);
        cellPos = new Vector2(0, 0);
        animationManager = new ModelAnimationManager(modelName);
        modelConverter = new ModelConverter(animationManager);
        playerZone = new Rectangle();
        updatePlayerZone();
        playerZone.width = 32;
        playerZone.height = 32;
    }

    private void updatePlayerZone() {
        playerZone.x = 32 * getCellPos().x;
        playerZone.y = 32 * getCellPos().y;
    }

    public void moveToLeft() {
        cellPos.x--;
    }

    public void moveToRight() {
        cellPos.x++;
    }

    public void moveToUp() {
        cellPos.y++;
    }

    public void moveToDown() {
        cellPos.y--;
    }

    public void setCell(int x, int y) {
        cellPos.x = x;
        cellPos.y = y;
    }


    public Rectangle getRectangle() {
        return playerZone;
    }

    public ModelAnimationManager getAnimationManager() {
        return animationManager;
    }

    public ModelConverter getModelConverter() {
        return modelConverter;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    protected void loadJson(JsonValue informations) {
        this.modelName = informations.getString("modelName");
        this.name = informations.getString("name");
    }

    @Override
    public void dispose() {
        modelConverter.dispose();
        animationManager.dispose();
    }

    public Vector2 getRealPos() {
        return realPos;
    }

    public void setRealPos(Vector2 realPos) {
        this.realPos = realPos;
    }

    public void setRealPos(float x, float y) {
        this.realPos.x = x;
        this.realPos.y = y;
    }

    public Vector2 getCellPos() {
        return cellPos;
    }

    public void setCellPos(Vector2 cellPos) {
        this.cellPos = cellPos;
    }

    public void setCellPos(int x, int y) {
        this.cellPos.x = x;
        this.cellPos.y = y;
    }

    private void update(float delta) {
        modelConverter.update(delta);
        updatePlayerZone();
    }

    public void render(float delta) {
        this.update(delta);
        modelConverter.render();

        if (debug)
            drawDebug();

        getBatch().begin();
        //TODO : calc the best pos ! Cneter of frame buffer = center perso, locate center and draw to center of cell.
        getBatch().draw(modelConverter.getCurrentTexture(), getRectangle().x, getRectangle().y);
        getBatch().end();

    }

    private void drawDebug() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 1f, 1);
        shapeRenderer.rect(getRectangle().x - 16, getRectangle().y + 16, getRectangle().getWidth(), getRectangle().getHeight());
        shapeRenderer.setColor(0, 0, 1f, 1);
        shapeRenderer.rect(getRectangle().x - 16 - 2 * 16, getRectangle().y + 16 + 2 * 16, getRectangle().getWidth(), getRectangle().getHeight());
        shapeRenderer.end();
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }
}
