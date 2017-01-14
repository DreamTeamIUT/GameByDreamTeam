package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import unice.etu.dreamteam.Entities.Sounds.Manager.AudioManager;
import unice.etu.dreamteam.Entities.GamesPackages.GamePackage;
import unice.etu.dreamteam.Ui.Settings;

/**
 * Created by Guillaume on 01/11/2016.
 */
public class GameInformation {
    private static GamePackage gamePackage;
    private static int viewportWidth;
    private static int viewportHeight;
    private static final Boolean debugMode = true;
    private Preferences prefs;
    public static final int KEY_LEFT = 0;


    public static GamePackage getGamePackage() {
        return gamePackage;
    }

    public static void setGamePackage(GamePackage gamePackage) {
        GameInformation.gamePackage = gamePackage;
        AudioManager.getInstance().updatePackage();
    }

    public static void setViewportWidth(int viewportWidth) {
        GameInformation.viewportWidth = viewportWidth;
    }

    public static void setViewportHeight(int viewportHeight) {
        GameInformation.viewportHeight = viewportHeight;
    }

    public static int getViewportHeight() {
        return viewportHeight;
    }

    public static int getViewportWidth() {
        return viewportWidth;
    }

    public static Boolean getDebugMode() {
        return debugMode;
    }

}
