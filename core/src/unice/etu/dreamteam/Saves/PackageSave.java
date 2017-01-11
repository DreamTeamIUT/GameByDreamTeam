package unice.etu.dreamteam.Saves;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Guillaume on 12/11/2016.
 */
public class PackageSave {
    private ObjectMap<String, PlayerSave> players;

    public PackageSave() {
        players = new ObjectMap<>();
    }

    public void addPlayerSave(String playerName, PlayerSave save) {
        this.players.put(playerName, save);
    }

    public ObjectMap<String, PlayerSave> getPlayers() {
        return players;
    }
}
