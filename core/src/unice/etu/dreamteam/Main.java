package unice.etu.dreamteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.ScreenList;
import unice.etu.dreamteam.Utils.ScreenManager;

public class Main extends Game {

    public Main() {
        super();
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Debug.log("Create");

        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen(ScreenList.Game2);

    }

    @Override
    public void dispose() {
        getScreen().dispose();
        Gdx.app.exit();

    }
}
