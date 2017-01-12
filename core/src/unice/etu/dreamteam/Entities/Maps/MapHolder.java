package unice.etu.dreamteam.Entities.Maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Map.Map;

/**
 * Created by Romain on 05/01/2017.
 */
public class MapHolder extends Entity {
    private final String filename;
    private final Vector2 startPoint;

    public MapHolder(JsonValue value) {
        super(value);
        this.filename = value.getString("file");
        JsonValue tmp = value.get("start");
        this.startPoint = new Vector2(tmp.getInt("x", 0), tmp.getInt("y", 0));
    }

    public Vector2 getStartPoint() {
        return startPoint;
    }

    public String getFilename() {
        return filename;
    }

    public Map load(){
        return Map.load(this.filename, this);
    }
}