package unice.etu.dreamteam.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import unice.etu.dreamteam.Entities.CharacterHolder;
import unice.etu.dreamteam.Utils.Debug;

public class Character implements Disposable {

   // private ModelConverter modelConverter;
   // private ModelAnimationManager animationManager;
    protected Vector2 cellPos;
    protected Vector2 realPos;
    protected Boolean animating = false;
    protected int currentMove;
    protected Float speed = 0.20f;

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
        realPos = new Vector2(0, 0);
       // animationManager = new ModelAnimationManager(modelName);
       // modelConverter = new ModelConverter(animationManager);
        updatePlayerZone();
    }

    private void updatePlayerZone() {
        playerZone = getRectangleAt(this.getRealPos());
    }

    /*
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
    */

    public Vector2 moveToLeft() {
        //animating = true;
        cellPos.x -= 1;

        return new Vector2(cellPos.x, cellPos.y);
    }

    public Vector2 moveToRight() {
        //animating = true;
        cellPos.x += 1;

        return new Vector2(cellPos.x, cellPos.y);
    }

    public Vector2 moveToUp() {
        //animating = true;
        cellPos.y += 1;

        return new Vector2(cellPos.x, cellPos.y);
    }

    public Vector2 moveToDown() {
        //animating = true;
        cellPos.y -= 1;

        return new Vector2(cellPos.x, cellPos.y);
    }

    public void moveTo(int characterMove) {
        animating = true;
        currentMove = characterMove;

        if(characterMove == CharacterMove.LEFT)
            cellPos.x -= 1;
        else if(characterMove == CharacterMove.RIGHT)
            cellPos.x += 1;
        else if(characterMove == CharacterMove.UP)
            cellPos.y += 1;
        else if(characterMove == CharacterMove.DOWN)
            cellPos.y -= 1;
    }

    public Vector2 moveToCheck(int characterMove) {
        if(characterMove == CharacterMove.LEFT)
            return new Vector2(cellPos.x - 1, cellPos.y);
        else if(characterMove == CharacterMove.RIGHT)
            return new Vector2(cellPos.x + 1, cellPos.y);
        else if(characterMove == CharacterMove.UP)
            return new Vector2(cellPos.x, cellPos.y + 1);
        else if(characterMove == CharacterMove.DOWN)
            return new Vector2(cellPos.x, cellPos.y - 1);
        else
            return new Vector2(cellPos.x, cellPos.y);
    }

    private void moveTransition() {
        if (animating) {
            if(currentMove == CharacterMove.LEFT) {
                if(realPos.x < cellPos.x) {
                    animating = false;
                    realPos.x = cellPos.x;
                }
                else
                    realPos.x -= speed;
            }
            else if(currentMove == CharacterMove.RIGHT) {
                if(realPos.x > cellPos.x) {
                    animating = false;
                    realPos.x = cellPos.x;
                }
                else
                    realPos.x += speed;
            }
            else if(currentMove == CharacterMove.UP) {
                if(realPos.y > cellPos.y) {
                    animating = false;
                    realPos.y = cellPos.y;
                }
                else
                    realPos.y += speed;
            }
            else if(currentMove == CharacterMove.DOWN) {
                if(realPos.y < cellPos.y) {
                    animating = false;
                    realPos.y = cellPos.y;
                }
                else
                    realPos.y -= speed;
            }

            this.updatePlayerZone();
        }
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

    public Vector2 getRealPos() {
        return realPos;
    }

    public void setCellPos(Vector2 cellPos) {
        this.cellPos = cellPos;
        this.updatePlayerZone();
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

        moveTransition();

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
