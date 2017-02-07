package unice.etu.dreamteam.Entities.Characters;

/**
 * Created by Dylan on 07/02/2017.
 */
public class CharacterStats {
    private int health;
    private int xp;

    private Boolean alive;

    public CharacterStats(int healt, int xp) {
        this.health = healt;
        this.xp = xp;

        this.alive = true;
    }

    public int getHealth() {
        return this.health;
    }

    public int getXp() {
        return this.xp;
    }

    public void injuries(int damage) {
        this.health = this.health - damage;

        if(this.health < 0)
            this.alive = false;
    }

    public Boolean isAlive() {
        return this.alive;
    }

    public void earnXp(int xp) {
        this.xp += xp;
    }
}
