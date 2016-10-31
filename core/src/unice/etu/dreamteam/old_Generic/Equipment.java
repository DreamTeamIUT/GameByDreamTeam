package unice.etu.dreamteam.old_Generic;

import unice.etu.dreamteam.Objects.ObjectGameAnimation;

/**
 * Created by Romain on 15/09/2016.
 */
public class Equipment extends ObjectGameAnimation {
    public static final double  EVOLUTION_FACTOR= 1.25;
    private static final int DEFAULT_VALUE = 0;
    private static final int DURABILITY = 100;
    protected int levelRequired, equipmentLevel, durability;
    protected String name;
    public float agility, strength, cleverness, hp, mana, damage;

    
    public Equipment(String path, String name){
        super(path,2,2,10,5);
        this.name=name;
        this.levelRequired=DEFAULT_VALUE;
        this.equipmentLevel=DEFAULT_VALUE;
        this.agility=DEFAULT_VALUE;
        this.strength=DEFAULT_VALUE;
        this.cleverness=DEFAULT_VALUE;
        this.hp=DEFAULT_VALUE;
        this.mana=DEFAULT_VALUE;
        this.damage=DEFAULT_VALUE;
        this.durability=DURABILITY;
    }
    
    public void addequipmentLevel() {
        equipmentLevel++;
    }

    public void addequipmentCaracteristic(double agility, double strength, double cleverness, double hp, double mana, double damage) {
        this.agility= (float) agility;
        this.strength= (float) strength;
        this.cleverness= (float) cleverness;
        this.hp= (float) hp;
        this.mana= (float) mana;
        this.damage= (float) damage;
    }

    public boolean BrokenEquipment() {
        if(this.durability==DEFAULT_VALUE){
            return true;
        }
        else {
            return false;
        }
    }


}
