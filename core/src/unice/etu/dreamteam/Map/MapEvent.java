package unice.etu.dreamteam.Map;

import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Screens.GameScreen;

/**
 * Created by Guillaume on 02/01/2017.
 */
public class MapEvent {

    public static final int EVENT_TYPE_GATE = 304;
    private final GameScreen game;

    private Story story;
    private Character character;
    private Map map;

    public MapEvent(Character p, Map map, Story s, GameScreen game){
        this.map = map;
        this.character = p;
        this.story = s;
        this.game = game;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Story getStory() {
        return story;
    }

    public GameScreen getGame() {
        return game;
    }
}
