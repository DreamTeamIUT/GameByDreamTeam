package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Guillaume on 01/11/2016.
 */
public class GameInformation {
    private static String packageName;
    private static int viewportWidth;
    private static int viewportHeight;
    private static final Boolean debugMode = true;

    //TODO : Add other variable if necessary.
    public static String getPackageName() {
        return packageName;
    }

    public static String getPackagePath() {
        return "packages/" + packageName;
    }

    public static void setPackageName(String packageName) {
        GameInformation.packageName = packageName;
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
