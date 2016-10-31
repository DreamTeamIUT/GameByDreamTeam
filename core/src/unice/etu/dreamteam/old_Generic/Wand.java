package unice.etu.dreamteam.old_Generic;

import java.util.Random;

/**
 * Created by Romain on 17/09/2016.
 */
public class Wand extends Equipment{
    private static final int AGILITY=20;
    private static final int STRENGTH = 10;
    private static final int CLEVERNESS = 50;
    private static final int HP=50;
    private static final int MANA=130;
    private static final int DAMAGE=40;

    public Wand(String path, String name){
        super(path,name);
    }

    public void addCaracteristic() { //addequipmentCaracteristic(int agility, int strength, int cleverness, int hp, int mana, int damage)
        Random rand = new Random();//rand.nextInt(max - min + 1) + min
        int agilityWand = rand.nextInt(AGILITY);
        int strengthWand = rand.nextInt(STRENGTH);
        int clevernessWand = rand.nextInt(CLEVERNESS);
        int hpWand = rand.nextInt(HP);
        int manaWand = rand.nextInt(MANA);
        int damageWand = rand.nextInt(DAMAGE);

        switch (levelRequired) { //rand.nextInt(51), rand.nextInt(11), rand.nextInt(31), rand.nextInt(61), rand.nextInt(111), rand.nextInt(41)
            case 0:
                super.addequipmentCaracteristic(agilityWand, strengthWand, clevernessWand, hpWand, manaWand, damageWand);
                break;
            case 5:
                super.addequipmentCaracteristic(agilityWand + AGILITY, strengthWand + STRENGTH, clevernessWand + CLEVERNESS, hpWand + HP, manaWand + MANA, damageWand + DAMAGE);
                break;
            case 10:
                super.addequipmentCaracteristic(agilityWand + EVOLUTION_FACTOR * AGILITY, strengthWand + EVOLUTION_FACTOR * STRENGTH, clevernessWand + EVOLUTION_FACTOR * CLEVERNESS, hpWand + EVOLUTION_FACTOR * HP, manaWand + EVOLUTION_FACTOR * MANA, damageWand + EVOLUTION_FACTOR * DAMAGE);
                break;
            case 15:
                super.addequipmentCaracteristic((int) (agilityWand + Math.pow(EVOLUTION_FACTOR,2d) * AGILITY), (int) (strengthWand + Math.pow(EVOLUTION_FACTOR,2d) * STRENGTH), (int) (clevernessWand + Math.pow(EVOLUTION_FACTOR,2d) * CLEVERNESS), (int) (hpWand + Math.pow(EVOLUTION_FACTOR,2d) * HP), (int) (manaWand + Math.pow(EVOLUTION_FACTOR,2d) * MANA), (int) (damageWand + Math.pow(EVOLUTION_FACTOR,2d) * DAMAGE));
                break;
            case 20:
                super.addequipmentCaracteristic((int) (agilityWand + Math.pow(EVOLUTION_FACTOR,3d) * AGILITY), (int) (strengthWand + Math.pow(EVOLUTION_FACTOR,3d) * STRENGTH), (int) (clevernessWand + Math.pow(EVOLUTION_FACTOR,3d) * CLEVERNESS), (int) (hpWand + Math.pow(EVOLUTION_FACTOR,3d) * HP), (int) (manaWand + Math.pow(EVOLUTION_FACTOR,3d) * MANA), (int) (damageWand + Math.pow(EVOLUTION_FACTOR,3d) * DAMAGE));
                break;
            case 25:
                super.addequipmentCaracteristic((int) (agilityWand + Math.pow(EVOLUTION_FACTOR,4d) * AGILITY), (int) (strengthWand + Math.pow(EVOLUTION_FACTOR,4d) * STRENGTH), (int) (clevernessWand + Math.pow(EVOLUTION_FACTOR,4d) * CLEVERNESS), (int) (hpWand + Math.pow(EVOLUTION_FACTOR,4d) * HP), (int) (manaWand + Math.pow(EVOLUTION_FACTOR,4d) * MANA), (int) (damageWand + Math.pow(EVOLUTION_FACTOR,4d) * DAMAGE));
                break;
            case 30:
                super.addequipmentCaracteristic((int) (agilityWand + Math.pow(EVOLUTION_FACTOR,5d) * AGILITY), (int) (strengthWand + Math.pow(EVOLUTION_FACTOR,5d) * STRENGTH), (int) (clevernessWand + Math.pow(EVOLUTION_FACTOR,5d) * CLEVERNESS), (int) (hpWand + Math.pow(EVOLUTION_FACTOR,5d) * HP), (int) (manaWand + Math.pow(EVOLUTION_FACTOR,5d) * MANA), (int) (damageWand + Math.pow(EVOLUTION_FACTOR,5d) * DAMAGE));
                break;
            case 35:
                super.addequipmentCaracteristic((int) (agilityWand + Math.pow(EVOLUTION_FACTOR,6d) * AGILITY), (int) (strengthWand + Math.pow(EVOLUTION_FACTOR,6d) * STRENGTH), (int) (clevernessWand + Math.pow(EVOLUTION_FACTOR,6d) * CLEVERNESS), (int) (hpWand + Math.pow(EVOLUTION_FACTOR,6d) * HP), (int) (manaWand + Math.pow(EVOLUTION_FACTOR,6d) * MANA), (int) (damageWand + Math.pow(EVOLUTION_FACTOR,6d) * DAMAGE));
                break;
            case 40:
                super.addequipmentCaracteristic((int) (agilityWand + Math.pow(EVOLUTION_FACTOR,7d) * AGILITY), (int) (strengthWand + Math.pow(EVOLUTION_FACTOR,7d) * STRENGTH), (int) (clevernessWand + Math.pow(EVOLUTION_FACTOR,7d) * CLEVERNESS), (int) (hpWand + Math.pow(EVOLUTION_FACTOR,7d) * HP), (int) (manaWand + Math.pow(EVOLUTION_FACTOR,7d) * MANA), (int) (damageWand + Math.pow(EVOLUTION_FACTOR,7d) * DAMAGE));
                break;
            case 45:
                super.addequipmentCaracteristic((int) (agilityWand + Math.pow(EVOLUTION_FACTOR,8d) * AGILITY), (int) (strengthWand + Math.pow(EVOLUTION_FACTOR,8d) * STRENGTH), (int) (clevernessWand + Math.pow(EVOLUTION_FACTOR,8d) * CLEVERNESS), (int) (hpWand + Math.pow(EVOLUTION_FACTOR,8d) * HP), (int) (manaWand + Math.pow(EVOLUTION_FACTOR,8d) * MANA), (int) (damageWand + Math.pow(EVOLUTION_FACTOR,8d) * DAMAGE));
                break;
            case 50:
                super.addequipmentCaracteristic((int) (agilityWand + Math.pow(EVOLUTION_FACTOR,9d) * AGILITY), (int) (strengthWand + Math.pow(EVOLUTION_FACTOR,9d) * STRENGTH), (int) (clevernessWand + Math.pow(EVOLUTION_FACTOR,9d) * CLEVERNESS), (int) (hpWand + Math.pow(EVOLUTION_FACTOR,9d) * HP), (int) (manaWand + Math.pow(EVOLUTION_FACTOR,9d) * MANA), (int) (damageWand + Math.pow(EVOLUTION_FACTOR,9d) * DAMAGE));
                break;
        }
    }
    
}
