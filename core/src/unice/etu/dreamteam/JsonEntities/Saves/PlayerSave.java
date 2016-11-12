package unice.etu.dreamteam.JsonEntities.Saves;

/**
 * Created by Guillaume on 12/11/2016.
 */
public class PlayerSave {
    private int level;

    public PlayerSave() {
    }

    public void setDefaults() {
        level = 1;
    }

    public int getLevel() {
        return level;
    }
}
