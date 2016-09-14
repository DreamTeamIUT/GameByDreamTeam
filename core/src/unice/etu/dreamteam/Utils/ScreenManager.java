package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import unice.etu.dreamteam.Screens.AbstractScreen;


public class ScreenManager {

    private static ScreenManager instance;
    private Game game;

    private ScreenManager() {
        super();
    }

    public static ScreenManager getInstance() {
        if (instance == null)
            instance = new ScreenManager();

        return instance;
    }

    public void initialize(Game game) {
        this.game = game;
    }

    public void showScreen(ScreenList screenList, Object... params) {

        Screen currentScreen = game.getScreen();

        AbstractScreen newScreen = screenList.getScreen(params);

        newScreen.buildStage();
        game.setScreen(newScreen);

        if (currentScreen != null)
            currentScreen.dispose();
    }

}