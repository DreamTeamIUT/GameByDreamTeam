package unice.etu.dreamteam.Maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.Mob;
import unice.etu.dreamteam.Components.SoundEffect;

import java.util.ArrayList;

/**
 * Created by Dylan on 01/10/2016.
 */
public class StoryLoader {
    private TiledMap map;
    private ArrayList<SoundEffect> sounds;
    private ArrayList<Mob> mobs;

    private ArrayList<JsonValue> zones;
    private ArrayList<JsonValue> gates;

    public StoryLoader(String src) {
        FileHandle file = Gdx.files.internal("assets/stories" + src);

        JsonValue jsonStory = new JsonReader().parse(file.readString());

        //MAP
        map = new TmxMapLoader().load(jsonStory.getString("map"));

        //SOUNDS
        for(JsonValue sound : jsonStory.get("sounds").iterator())
            sounds.add(new SoundEffect(sound.getString("src"), sound.getString("name")));

        //MOBS
        for(JsonValue mob : jsonStory.get("mobs").iterator())
            mobs.add(new Mob(mob));

        //ZONES
        for(JsonValue zone : jsonStory.get("zones").iterator())
            zones.add(zone);

        //GATES
        for(JsonValue gate : jsonStory.get("gates").iterator())
            gates.add(gate);
    }

    public TiledMap getMap() {
        return map;
    }
}
