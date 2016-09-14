package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Gdx;

public class Debug {
    public static void log(String tag, String message) {
        Gdx.app.log(tag, message);
    }

    public static void log(String message) {
        Gdx.app.log("Logger", message);
    }

    public static void error(String tag, String message) {
        Gdx.app.error(tag, message);
    }

    public static void error(String message) {
        Gdx.app.error("Error", message);
    }

    public static void debug(String tag, String message) {
        Gdx.app.debug(tag, message);
    }

    public static void debug(String message) {
        Gdx.app.debug("Debug", message);
    }
}
