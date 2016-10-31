package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Iterator;

public class Debug { //que du statique, raccourci debug.
    public static void log(String tag, String message) {
        Gdx.app.log(tag, message); //syntaxe pour debug libgdx
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


    public static void position(String tag, float... positions) {
        String message = "";
        for (float pos : positions) {
            message += " " + pos;
        }
        log(tag, message);
    }

    public static void position(float... pos) {
        position("Logger-Position", pos);
    }

    public static void vector(Vector3 v) {
        log("Logger-Vector3", "x:" + v.x + " y:" + v.y + " z:" + v.z);
    }

    public static void vector(Vector2 v) {
        log("Logger-Vector2", "x:" + v.x + " y:" + v.y);
    }

    public static String iteratorToString(Iterator i) {
        String str = "[";
        if (i.hasNext())
            str += i.next().toString();

        while (i.hasNext()) {
            str += ",";
            Object element = i.next();
            str += element.toString();
        }
        str += "]";
        return str;
    }
}
