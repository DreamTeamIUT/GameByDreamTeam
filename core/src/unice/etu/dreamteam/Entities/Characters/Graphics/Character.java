package unice.etu.dreamteam.Entities.Characters.Graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import unice.etu.dreamteam.Entities.Characters.CharacterHolder;
import unice.etu.dreamteam.Map.Assets;
import unice.etu.dreamteam.Map.Map;
import unice.etu.dreamteam.Entities.Weapons.Weapon;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;
import unice.etu.dreamteam.Utils.IsoTransform;
import unice.etu.dreamteam.Utils.ModelConverter;

public class Character implements Disposable {

    private ModelConverter modelConverter;
    private ModelAnimationManager animationManager;

    protected Vector2 cellPos;
    protected Vector2 realPos;
    protected Vector2 backPos;
    protected Boolean animating = false;
    protected int currentMove;
    protected int currentView;
    protected Float speed = 0.07f;

    protected String modelName;
    protected String name;
    protected SpriteBatch batch;
    protected Boolean debug;
    protected ShapeRenderer shapeRenderer;

    private Weapon.Graphic weapon;

    public Character(CharacterHolder holder) {
        Debug.log("Load character");
        this.name = holder.getName();
        this.modelName = holder.getModelName();

        cellPos = new Vector2(0, 0);
        realPos = new Vector2(0, 0);
        backPos = new Vector2(0, 0);

        animationManager = new ModelAnimationManager(modelName);
        modelConverter = new ModelConverter(animationManager);

        currentMove = CharacterMove.NONE;
        currentView = CharacterMove.NONE;
    }

    public void moveTo(int characterMove) {
        if (!animating) {
            Debug.log("moveTo");

            this.animationManager.setAnimation("WALK");

            animating = true;
            currentMove = characterMove;
            currentView = characterMove;

            backPos = cellPos;

            switch (characterMove) {
                case CharacterMove.LEFT:
                    cellPos.x -= 1;
                    animationManager.setRotation(270);
                    break;
                case CharacterMove.RIGHT:
                    cellPos.x += 1;
                    animationManager.setRotation(90);
                    break;
                case CharacterMove.UP:
                    cellPos.y += 1;
                    animationManager.setRotation(180);
                    break;
                case CharacterMove.DOWN:
                    cellPos.y -= 1;
                    animationManager.setRotation(0);
            }

            Debug.vector("realPos", realPos);
            Debug.vector("cellPos", cellPos);
        }
    }

    public Vector2 moveToCheck(int characterMove) {
        Debug.log("moveToCheck");

        Debug.vector("realPos", realPos);
        Debug.vector("cellPos", cellPos);

        if (characterMove == CharacterMove.LEFT)
            return new Vector2(cellPos.x - 1, cellPos.y);
        else if (characterMove == CharacterMove.RIGHT)
            return new Vector2(cellPos.x + 1, cellPos.y);
        else if (characterMove == CharacterMove.UP)
            return new Vector2(cellPos.x, cellPos.y + 1);
        else if (characterMove == CharacterMove.DOWN)
            return new Vector2(cellPos.x, cellPos.y - 1);
        else
            return new Vector2(cellPos.x, cellPos.y);
    }

    private void moveTransition() {
        if (animating) {
            if (currentMove == CharacterMove.LEFT) {
                if (realPos.x < cellPos.x)
                    stopAnimating();
                else
                    realPos.x -= speed;
            } else if (currentMove == CharacterMove.RIGHT) {
                Debug.vector(cellPos);
                if (realPos.x > cellPos.x)
                    stopAnimating();
                else
                    realPos.x += speed;
            } else if (currentMove == CharacterMove.UP) {
                if (realPos.y > cellPos.y)
                    stopAnimating();
                else
                    realPos.y += speed;
            } else if (currentMove == CharacterMove.DOWN) {
                if (realPos.y < cellPos.y)
                    stopAnimating();
                else
                    realPos.y -= speed;
            }
        }
    }

    private void stopAnimating() {
        currentMove = CharacterMove.NONE;
        animating = false;

        realPos.x = cellPos.x;
        realPos.y = cellPos.y;

        this.animationManager.setAnimation("STOPPED");
    }

    public Rectangle getRectangle(Boolean cell) {
        return getRectangleAt(cell ? getCellPos() : getRealPos());
    }

    public Rectangle getRectangle() {
        return getRectangle(false);
    }

    public Rectangle getRectangleAt(Vector2 cellPos) {
        Rectangle zone = new Rectangle();
        zone.width = 32;
        zone.height = 32;
        zone.x = 32 * cellPos.x;
        zone.y = 32 * cellPos.y;
        return zone;
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

    public ShapeRenderer getShapeRender() {
        return shapeRenderer;
    }

    @Override
    public void dispose() {
        modelConverter.dispose();
        animationManager.dispose();
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
    }

    public void setPos(Vector2 vector2) {
        Vector2 vector21 = new Vector2();
        vector21.x = vector2.x;
        vector21.y = vector2.y;

        this.cellPos = vector2;
        this.realPos = vector21;
    }

    public void setCellPos(int x, int y) {
        this.cellPos.x = x;
        this.cellPos.y = y;
    }

    public Vector2 getBackPos() {
        return backPos;
    }

    private void update(float delta) {
        modelConverter.update(delta);
    }

    public void render(float delta) {
        this.update(delta);

        modelConverter.render();

        moveTransition();

        if (debug)
            drawDebug();

        /*
        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.setTransformMatrix(IsoTransform.getIsoTransform());
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        spriteBatch.begin();
        spriteBatch.draw(modelConverter.getCurrentTexture(), getRectangle().x, getRectangle().y);
        spriteBatch.end();
        */

        //Matrix4 save = getBatch().getProjectionMatrix();
        //Matrix4 transform = getBatch().getTransformMatrix();

        Vector3 modelPos = modelConverter.getPos(getRealPos());

        getBatch().begin();
        //TODO : calc the best pos ! Cneter of frame buffer = center perso, locate center and draw to center of cell.
        getBatch().draw(modelConverter.getCurrentTexture(), modelPos.x-45, modelPos.y+32);
        //getBatch().draw(modelConverter.getCurrentTexture(), getRectangle().x, getRectangle().y);
        //getBatch().setTransformMatrix(transform);
        //getBatch().setProjectionMatrix(save);

        getBatch().end();

        if(this.weapon != null) {
            this.weapon.setPosition(getRealPos());
            this.weapon.render(getBatch(), getShapeRender(), delta);
        }
    }

    protected void drawDebug() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 1f, 1);
        shapeRenderer.rect(getRectangle().x - 16, getRectangle().y + 16, getRectangle().getWidth(), getRectangle().getHeight());
        shapeRenderer.setColor(0, 0, 1f, 1);
        shapeRenderer.rect(getRectangle().x - 16 - 2 * 16, getRectangle().y + 16 + 2 * 16, getRectangle().getWidth(), getRectangle().getHeight());
        drawLineforMove();
        shapeRenderer.end();
    }

    public void drawLineforMove(){
        Vector2 center = new Vector2();
        getRectangle().getCenter(center);
        shapeRenderer.setColor(0,1f,0f,1);
        center.x = center.x - 16 - 2 * 16;
        center.y = center.y + 16 + 2*16 ;

        Vector2 extrem = new Vector2();
        extrem.x = center.x;
        extrem.y = center.y;
        switch (currentView){
            case CharacterMove.DOWN:
                extrem.y = center.y - 16;
                break;
            case CharacterMove.LEFT:
                extrem.x = center.x - 16;
                break;
            case CharacterMove.RIGHT:
                extrem.x = center.x + 16;
                break;
            case CharacterMove.UP:
                extrem.y = center.y + 16;
                break;
        }

        shapeRenderer.line(center.x, center.y, extrem.x, extrem.y);
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

    public void setView(int view) {
        this.currentView = view;
    }

    public int getView() {
        return currentView;
    }

    public Weapon.Graphic getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon, int powerful) {
        this.weapon = weapon.create(powerful);
    }

    public void setWeapon(Weapon weapon) {
        setWeapon(weapon, -1);
    }
}
