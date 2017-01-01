package unice.etu.dreamteam.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import unice.etu.dreamteam.Entities.CharacterHolder;
import unice.etu.dreamteam.Utils.Debug;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Character implements Disposable {

   // private ModelConverter modelConverter;
   // private ModelAnimationManager animationManager;
    protected Vector2 cellPos;
    protected Vector2 realPos;
    protected Rectangle playerZone;
    protected String modelName;
    protected String name;
    protected SpriteBatch batch;
    protected Boolean debug;
    protected ShapeRenderer shapeRenderer;

    public Character(CharacterHolder holder) {
        Debug.log("Load character");
        this.name = holder.getName();
        this.modelName = holder.getModelName();
        cellPos = new Vector2(0, 0);
       // animationManager = new ModelAnimationManager(modelName);
       // modelConverter = new ModelConverter(animationManager);
        updatePlayerZone();
    }

    private void updatePlayerZone() {
        playerZone = getRectangleAt(this.getCellPos());
    }

    public Vector2 moveToLeft() {

        return new Vector2(cellPos.x-1, cellPos.y);
    }

    public Vector2 moveToRight() {
        return new Vector2(cellPos.x+1, cellPos.y);
    }

    public Vector2 moveToUp() {
        return new Vector2(cellPos.x, cellPos.y+1);
    }

    public Vector2 moveToDown() {
        return new Vector2(cellPos.x, cellPos.y-1);
    }

    public void setCell(int x, int y) {
        cellPos.x = x;
        cellPos.y = y;
    }


    public Rectangle getRectangle() {
        return playerZone;
    }

    public Rectangle getRectangleAt(Vector2 cellPos){
        Rectangle zone = new Rectangle();
        zone.width = 32;
        zone.height = 32;
        zone.x = 32 * cellPos.x;
        zone.y = 32 * cellPos.y;
        return zone;
    }

   /* public ModelAnimationManager getAnimationManager() {
        return animationManager;
    }
*/

/*
    public ModelConverter getModelConverter() {
        return modelConverter;
    }
*/

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void dispose() {
      //  modelConverter.dispose();
      //  animationManager.dispose();
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
       // modelConverter.update(delta);
        updatePlayerZone();
    }

    public void render(float delta) {
        this.update(delta);
       // modelConverter.render();

        if (debug)
            drawDebug();

        getBatch().begin();
        //TODO : calc the best pos ! Cneter of frame buffer = center perso, locate center and draw to center of cell.
       // getBatch().draw(modelConverter.getCurrentTexture(), getRectangle().x, getRectangle().y);
        getBatch().end();

    }

    protected void drawDebug() {
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
