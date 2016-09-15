package unice.etu.dreamteam.Generic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import unice.etu.dreamteam.Objects.ObjectGameAnimation;

import java.util.ArrayList;

/**
 * Created by Romain on 15/09/2016.
 */
public class Player extends ObjectGameAnimation {

    public static final int DEFAULT = 0;
    public static final int LEVEL1 = 1;
    public static final int CARACTERISTIC = 20;
    public static int HP = 500;
    public static int MANA = 250;
    public static float DAMAGE = 30;

    private int nextLevel = 500;

    private int posX;
    private int posY;


    private String name;

    private int level, strength, agility, hp, mana, cleverness, experience, takeDamage;

    private float damage;

    private double evadeRate, criticalRate;

    ArrayList<Equipment> equipments;

    public Player(String path, String name) {
        super(path, 5, 5, 50, 50);

        this.name = name;
        this.level = LEVEL1;
        this.strength = CARACTERISTIC;
        this.agility = CARACTERISTIC;
        this.cleverness = CARACTERISTIC;
        this.experience = DEFAULT;
        this.hp = HP;
        this.mana = MANA;
        this.criticalRate = 0;
        this.evadeRate = 0;
        this.damage = DAMAGE;
        this.takeDamage=0;
        equipments = new ArrayList<>();

    }

    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
    }

    public void calculEvadeRate() {

        this.evadeRate = this.evadeRate + this.agility * 0.4;

    }

    public void calculCriticalRate() {
        this.criticalRate = (this.agility + this.strength + this.cleverness) / 12;
    }

    public void addExp() {
        if (this.experience >= nextLevel) {
            this.level++;
            this.experience = this.experience - nextLevel;
            nextLevel = 2 * nextLevel - (1 / 2) * nextLevel;
        }
    }

    public void calculMana() {
        this.mana = this.mana + this.cleverness * 5 ;
    }

    public void calculHp() {
        this.hp= this.hp + this.strength * 5;
    }
    public void calculDamage() {
        this.damage= this.damage + (this.agility + this.strength + this.cleverness) / 3;
    }
    public void takeDamage(int takeDamage) {
        this.hp= this.hp - takeDamage;
    }

    public boolean isDead() {
        return this.hp == 0;
    }

    public void drawPlayer(SpriteBatch batch, float delta) {
        super.drawAnimation(batch, delta);
    }
}