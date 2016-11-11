package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.Settings;

/**
 * Created by Romain on 11/11/2016.
 */
public class GameInputProcessor implements InputProcessor {
    private final GameScreen gameScreen;

    public GameInputProcessor(GameScreen gameScreen) {
        this.gameScreen =  gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        Debug.log("In keyup !");
        switch (keycode){
            case Input.Keys.ESCAPE:
                gameScreen.addActor(Settings.createWindow(gameScreen.getViewport()));

        }
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
}
