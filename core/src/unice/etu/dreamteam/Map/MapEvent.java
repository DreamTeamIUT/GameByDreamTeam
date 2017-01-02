package unice.etu.dreamteam.Map;

import unice.etu.dreamteam.Characters.Character;

/**
 * Created by Guillaume on 02/01/2017.
 */
public class MapEvent {

    public static final int EVENT_TYPE_GATE = 304;

    private Character character;
    private Map map;

    public MapEvent(Character p, Map map){
        this.map = map;
        this.character = p;
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
}
