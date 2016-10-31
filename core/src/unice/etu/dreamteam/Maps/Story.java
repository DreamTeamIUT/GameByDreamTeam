package unice.etu.dreamteam.Maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.Entities;
import unice.etu.dreamteam.Entities.*;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Story {

    private Players players;
    private Mobs mobs;
    private Zones zones;
    private Gates gates;
    private Sounds sounds;
    private String mapPath;
    private String name;
    private String packageName;


    public Story() {

    }

    public Players getPlayers() {
        return players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public Entities getMobs() {
        return mobs;
    }

    public void setMobs(Mobs mobs) {
        this.mobs = mobs;
    }

    public Zones getZones() {
        return zones;
    }

    public void setZones(Zones zones) {
        this.zones = zones;
    }

    public Gates getGates() {
        return gates;
    }

    public void setGates(Gates gates) {
        this.gates = gates;
    }

    public Sounds getSounds() {
        return sounds;
    }

    public void setSounds(Sounds sounds) {
        this.sounds = sounds;
    }

    public String getMapPath() {
        return getPackageName() + "/maps/" + mapPath;
    }

    public void setMapPath(String mapPath) {
        this.mapPath = mapPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Story load(String storyFile, String packageName) {

        Story story = new Story();

        FileHandle file = Gdx.files.internal("assets/" + packageName + "/stories/" + storyFile);

        JsonValue jsonStory = new JsonReader().parse(file.readString());

        //TODO : Add item
        story.setPackageName(packageName);
        story.setName(jsonStory.getString("name", ""));
        story.setMapPath(jsonStory.getString("map", null));
        story.setMobs(new Mobs(jsonStory.get("mobs").iterator(), packageName));
        story.setSounds(new Sounds(jsonStory.get("sounds").iterator()));
        story.setZones(new Zones(jsonStory.get("zones").iterator()));
        story.setGates(new Gates(jsonStory.get("gates").iterator()));
        story.setPlayers(new Players(jsonStory.get("players").iterator(), packageName));

        return story;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }
}
