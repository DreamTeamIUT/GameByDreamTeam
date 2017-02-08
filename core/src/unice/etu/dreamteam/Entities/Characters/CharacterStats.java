package unice.etu.dreamteam.Entities.Characters;

import unice.etu.dreamteam.Utils.Debug;

/**
 * Created by Dylan on 07/02/2017.
 */
public class CharacterStats {
    private int health;
    private int maxHealth;

    private int xp;

    private Boolean alive;

    public CharacterStats(int health, int xp) {
        this.health = health;
        this.maxHealth = health;

        this.xp = xp;

        this.alive = true;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getXp() {
        return this.xp;
    }

    public void injuries(int damage) {
        this.health = this.health - damage;

        Debug.log("CharacterStats", "Health : " + this.health + " max health : " + this.maxHealth);

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
