package unice.etu.dreamteam.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import javafx.fxml.LoadException;
import unice.etu.dreamteam.Entities.Bullets.Bullets;
import unice.etu.dreamteam.Entities.Characters.Mobs.Mobs;
import unice.etu.dreamteam.Entities.Characters.Players.Players;
import unice.etu.dreamteam.Entities.Gates.Gates;
import unice.etu.dreamteam.Entities.Items.Items;
import unice.etu.dreamteam.Entities.Maps.Maps;
import unice.etu.dreamteam.Entities.Sounds.Sounds;
import unice.etu.dreamteam.Entities.Weapons.Graphics.Weapon;
import unice.etu.dreamteam.Entities.Weapons.Weapons;
import unice.etu.dreamteam.Entities.Zones.Zones;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Story {

    private static Story story;
    private Players players;
    private Mobs mobs;
    private Zones zones;
    private Gates gates;
    private Sounds sounds;
    private Maps maps;
    private Items items;
    private String name;
    private String packageName;
    private int minimumLevel;

    public static Story getStory() throws LoadException {
        if (story == null){
            throw new LoadException("The story has not been loaded yet. Call Story.load() before !");
        }
        return story;
    }

    public Story() {

    }

    public Players getPlayers() {
        return players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public Mobs getMobs() {
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

    public Maps getMaps() {
        return this.maps;
    }

    public void setMaps(Maps m) {
        maps = m;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Story load(String storyFile) {

        Debug.log("STORY FILE ", storyFile);

        story = new Story();

        FileHandle file = Gdx.files.internal(GameInformation.getGamePackage().getPackagePath() + "/stories/" + storyFile);

        JsonValue jsonStory = new JsonReader().parse(file.readString());

        story.setPackageName(GameInformation.getGamePackage().getName());
        story.setName(jsonStory.getString("name", ""));

        Debug.log("MAPS " , Debug.iteratorToString(jsonStory.get("maps").iterator()));

        story.setMaps(new Maps(jsonStory.get("maps").iterator() ,jsonStory.getString("default-map")));
        story.setMobs(new Mobs(jsonStory.get("mobs").iterator()));
        story.setSounds(new Sounds(jsonStory.get("sounds").iterator()));
        story.setZones(new Zones(jsonStory.get("zones").iterator()));
        story.setGates(new Gates(jsonStory.get("gates").iterator()));
        story.setItems(Items.loadFromDir());
        story.setMinimumLevel(jsonStory.getInt("minimum-level", 0));

        Bullets.getInstance().add(jsonStory.get("bullets").iterator());
        Weapons.getInstance().add(jsonStory.get("weapons").iterator());


        return story;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setMinimumLevel(int minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public int getMinimumLevel() {
        return minimumLevel;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
