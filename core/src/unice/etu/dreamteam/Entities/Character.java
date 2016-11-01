package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private String modelName;
    private String name;
    private SpriteBatch batch;

    public Character(JsonValue informations, SpriteBatch spriteBatch) {
        Debug.log("Load character");
        loadJson(informations);
        cellPos = new Vector2(0, 0);
        animationManager = new ModelAnimationManager(modelName);
        modelConverter = new ModelConverter(animationManager);
        this.batch = spriteBatch;

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
    }

    public void render(float delta) {
        this.update(delta);
        modelConverter.render();

        getBatch().begin();
        getBatch().draw(modelConverter.getCurrentTexture(), 0, 0);
        getBatch().end();
    }
}
