package unice.etu.dreamteam.Generic;

import java.util.Random;

/**
 * Created by Romain on 17/09/2016.
 */
public class Sword extends Equipment{
    private static final int AGILITY = 30;
    private static final int STRENGTH = 50;
    private static final int CLEVERNESS = 10;
    private static final int HP = 130;
    private static final int MANA = 30;
    private static final int DAMAGE = 50;

    
    public Sword(String path, String name){
        super(path,name);
    }

    public void addCaracteristic() {//addequipmentCaracteristic(int agility, int strength, int cleverness, int hp, int mana, int damage)
        Random rand = new Random();//rand.nextInt(max - min + 1) + min
        int agilitySword = rand.nextInt(AGILITY);
        int strengthSword = rand.nextInt(STRENGTH);
        int clevernessSword = rand.nextInt(CLEVERNESS);
        int hpSword = rand.nextInt(HP);
        int manaSword = rand.nextInt(MANA);
        int damageSword = rand.nextInt(DAMAGE);
        switch (levelRequired) { //rand.nextInt(51), rand.nextInt(11), rand.nextInt(31), rand.nextInt(61), rand.nextInt(111), rand.nextInt(41)
            case 0:
                super.addequipmentCaracteristic(agilitySword, strengthSword, clevernessSword, hpSword, manaSword, damageSword);
                break;
            case 5:
                super.addequipmentCaracteristic(agilitySword + AGILITY, strengthSword + STRENGTH, clevernessSword + CLEVERNESS, hpSword + HP, manaSword + MANA, damageSword + DAMAGE);
                break;
            case 10:
                super.addequipmentCaracteristic(agilitySword + EVOLUTION_FACTOR * AGILITY, strengthSword + EVOLUTION_FACTOR * STRENGTH, clevernessSword + EVOLUTION_FACTOR * CLEVERNESS, hpSword + EVOLUTION_FACTOR * HP, manaSword + EVOLUTION_FACTOR * MANA, damageSword + EVOLUTION_FACTOR * DAMAGE);
                break;
            case 15:
                super.addequipmentCaracteristic((int) (agilitySword + Math.pow(EVOLUTION_FACTOR,2d) * AGILITY), (int) (strengthSword + Math.pow(EVOLUTION_FACTOR,2d) * STRENGTH), (int) (clevernessSword + Math.pow(EVOLUTION_FACTOR,2d) * CLEVERNESS), (int) (hpSword + Math.pow(EVOLUTION_FACTOR,2d) * HP), (int) (manaSword + Math.pow(EVOLUTION_FACTOR,2d) * MANA), (int) (damageSword + Math.pow(EVOLUTION_FACTOR,2d) * DAMAGE));
                break;
            case 20:
                super.addequipmentCaracteristic((int) (agilitySword + Math.pow(EVOLUTION_FACTOR,3d) * AGILITY), (int) (strengthSword + Math.pow(EVOLUTION_FACTOR,3d) * STRENGTH), (int) (clevernessSword + Math.pow(EVOLUTION_FACTOR,3d) * CLEVERNESS), (int) (hpSword + Math.pow(EVOLUTION_FACTOR,3d) * HP), (int) (manaSword + Math.pow(EVOLUTION_FACTOR,3d) * MANA), (int) (damageSword + Math.pow(EVOLUTION_FACTOR,3d) * DAMAGE));
                break;
            case 25:
                super.addequipmentCaracteristic((int) (agilitySword + Math.pow(EVOLUTION_FACTOR,4d) * AGILITY), (int) (strengthSword + Math.pow(EVOLUTION_FACTOR,4d) * STRENGTH), (int) (clevernessSword + Math.pow(EVOLUTION_FACTOR,4d) * CLEVERNESS), (int) (hpSword + Math.pow(EVOLUTION_FACTOR,4d) * HP), (int) (manaSword + Math.pow(EVOLUTION_FACTOR,4d) * MANA), (int) (damageSword + Math.pow(EVOLUTION_FACTOR,4d) * DAMAGE));
                break;
            case 30:
                super.addequipmentCaracteristic((int) (agilitySword + Math.pow(EVOLUTION_FACTOR,5d) * AGILITY), (int) (strengthSword + Math.pow(EVOLUTION_FACTOR,5d) * STRENGTH), (int) (clevernessSword + Math.pow(EVOLUTION_FACTOR,5d) * CLEVERNESS), (int) (hpSword + Math.pow(EVOLUTION_FACTOR,5d) * HP), (int) (manaSword + Math.pow(EVOLUTION_FACTOR,5d) * MANA), (int) (damageSword + Math.pow(EVOLUTION_FACTOR,5d) * DAMAGE));
                break;
            case 35:
                super.addequipmentCaracteristic((int) (agilitySword + Math.pow(EVOLUTION_FACTOR,6d) * AGILITY), (int) (strengthSword + Math.pow(EVOLUTION_FACTOR,6d) * STRENGTH), (int) (clevernessSword + Math.pow(EVOLUTION_FACTOR,6d) * CLEVERNESS), (int) (hpSword + Math.pow(EVOLUTION_FACTOR,6d) * HP), (int) (manaSword + Math.pow(EVOLUTION_FACTOR,6d) * MANA), (int) (damageSword + Math.pow(EVOLUTION_FACTOR,6d) * DAMAGE));
                break;
            case 40:
                super.addequipmentCaracteristic((int) (agilitySword + Math.pow(EVOLUTION_FACTOR,7d) * AGILITY), (int) (strengthSword + Math.pow(EVOLUTION_FACTOR,7d) * STRENGTH), (int) (clevernessSword + Math.pow(EVOLUTION_FACTOR,7d) * CLEVERNESS), (int) (hpSword + Math.pow(EVOLUTION_FACTOR,7d) * HP), (int) (manaSword + Math.pow(EVOLUTION_FACTOR,7d) * MANA), (int) (damageSword + Math.pow(EVOLUTION_FACTOR,7d) * DAMAGE));
                break;
            case 45:
                super.addequipmentCaracteristic((int) (agilitySword + Math.pow(EVOLUTION_FACTOR,8d) * AGILITY), (int) (strengthSword + Math.pow(EVOLUTION_FACTOR,8d) * STRENGTH), (int) (clevernessSword + Math.pow(EVOLUTION_FACTOR,8d) * CLEVERNESS), (int) (hpSword + Math.pow(EVOLUTION_FACTOR,8d) * HP), (int) (manaSword + Math.pow(EVOLUTION_FACTOR,8d) * MANA), (int) (damageSword + Math.pow(EVOLUTION_FACTOR,8d) * DAMAGE));
                break;
            case 50:
                super.addequipmentCaracteristic((int) (agilitySword + Math.pow(EVOLUTION_FACTOR,9d) * AGILITY), (int) (strengthSword + Math.pow(EVOLUTION_FACTOR,9d) * STRENGTH), (int) (clevernessSword + Math.pow(EVOLUTION_FACTOR,9d) * CLEVERNESS), (int) (hpSword + Math.pow(EVOLUTION_FACTOR,9d) * HP), (int) (manaSword + Math.pow(EVOLUTION_FACTOR,9d) * MANA), (int) (damageSword + Math.pow(EVOLUTION_FACTOR,9d) * DAMAGE));
                break;
        }
    }
}
