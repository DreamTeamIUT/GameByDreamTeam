package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import unice.etu.dreamteam.Maps.TiledMapSample;

public class GameScreen extends AbstractScreen {

    private TiledMapSample map;

    public GameScreen(){
        super(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);

    }

    @Override
    public void dispose() {
        super.dispose();

    }
}