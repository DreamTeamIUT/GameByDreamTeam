package unice.etu.dreamteam.Generic;

import java.util.Random;

/**
 * Created by Romain on 17/09/2016.
 */
public class Bow extends Equipment {
    private static final int AGILITY=50;
    private static final int STRENGTH = 10;
    private static final int CLEVERNESS = 30;
    private static final int HP=60;
    private static final int MANA=110;
    private static final int DAMAGE=40;

    public Bow(String path, String name) {
        super(path, name);
    }

    public void addCaracteristic() { //addequipmentCaracteristic(int agility, int strength, int cleverness, int hp, int mana, int damage)
        Random rand = new Random();//rand.nextInt(max - min + 1) + min
        int agilityBow = rand.nextInt(AGILITY);
        int strengthBow = rand.nextInt(STRENGTH);
        int clevernessBow = rand.nextInt(CLEVERNESS);
        int hpBow = rand.nextInt(HP);
        int manaBow = rand.nextInt(MANA);
        int damageBow = rand.nextInt(DAMAGE);

        switch (levelRequired) { //rand.nextInt(51), rand.nextInt(11), rand.nextInt(31), rand.nextInt(61), rand.nextInt(111), rand.nextInt(41)
            case 0:
                super.addequipmentCaracteristic(agilityBow, strengthBow, clevernessBow, hpBow, manaBow, damageBow);
                break;
            case 5:
                super.addequipmentCaracteristic(agilityBow + AGILITY, strengthBow + STRENGTH, clevernessBow + CLEVERNESS, hpBow + HP, manaBow + MANA, damageBow + DAMAGE);
                break;
            case 10:
                super.addequipmentCaracteristic(agilityBow + EVOLUTION_FACTOR * AGILITY, strengthBow + EVOLUTION_FACTOR * STRENGTH, clevernessBow + EVOLUTION_FACTOR * CLEVERNESS, hpBow + EVOLUTION_FACTOR * HP, manaBow + EVOLUTION_FACTOR * MANA, damageBow + EVOLUTION_FACTOR * DAMAGE);
                break;
            case 15:
                super.addequipmentCaracteristic((int) (agilityBow + Math.pow(EVOLUTION_FACTOR,2d) * AGILITY), (int) (strengthBow + Math.pow(EVOLUTION_FACTOR,2d) * STRENGTH), (int) (clevernessBow + Math.pow(EVOLUTION_FACTOR,2d) * CLEVERNESS), (int) (hpBow + Math.pow(EVOLUTION_FACTOR,2d) * HP), (int) (manaBow + Math.pow(EVOLUTION_FACTOR,2d) * MANA), (int) (damageBow + Math.pow(EVOLUTION_FACTOR,2d) * DAMAGE));
                break;
            case 20:
                super.addequipmentCaracteristic((int) (agilityBow + Math.pow(EVOLUTION_FACTOR,3d) * AGILITY), (int) (strengthBow + Math.pow(EVOLUTION_FACTOR,3d) * STRENGTH), (int) (clevernessBow + Math.pow(EVOLUTION_FACTOR,3d) * CLEVERNESS), (int) (hpBow + Math.pow(EVOLUTION_FACTOR,3d) * HP), (int) (manaBow + Math.pow(EVOLUTION_FACTOR,3d) * MANA), (int) (damageBow + Math.pow(EVOLUTION_FACTOR,3d) * DAMAGE));
                break;
            case 25:
                super.addequipmentCaracteristic((int) (agilityBow + Math.pow(EVOLUTION_FACTOR,4d) * AGILITY), (int) (strengthBow + Math.pow(EVOLUTION_FACTOR,4d) * STRENGTH), (int) (clevernessBow + Math.pow(EVOLUTION_FACTOR,4d) * CLEVERNESS), (int) (hpBow + Math.pow(EVOLUTION_FACTOR,4d) * HP), (int) (manaBow + Math.pow(EVOLUTION_FACTOR,4d) * MANA), (int) (damageBow + Math.pow(EVOLUTION_FACTOR,4d) * DAMAGE));
                break;
            case 30:
                super.addequipmentCaracteristic((int) (agilityBow + Math.pow(EVOLUTION_FACTOR,5d) * AGILITY), (int) (strengthBow + Math.pow(EVOLUTION_FACTOR,5d) * STRENGTH), (int) (clevernessBow + Math.pow(EVOLUTION_FACTOR,5d) * CLEVERNESS), (int) (hpBow + Math.pow(EVOLUTION_FACTOR,5d) * HP), (int) (manaBow + Math.pow(EVOLUTION_FACTOR,5d) * MANA), (int) (damageBow + Math.pow(EVOLUTION_FACTOR,5d) * DAMAGE));
                break;
            case 35:
                super.addequipmentCaracteristic((int) (agilityBow + Math.pow(EVOLUTION_FACTOR,6d) * AGILITY), (int) (strengthBow + Math.pow(EVOLUTION_FACTOR,6d) * STRENGTH), (int) (clevernessBow + Math.pow(EVOLUTION_FACTOR,6d) * CLEVERNESS), (int) (hpBow + Math.pow(EVOLUTION_FACTOR,6d) * HP), (int) (manaBow + Math.pow(EVOLUTION_FACTOR,6d) * MANA), (int) (damageBow + Math.pow(EVOLUTION_FACTOR,6d) * DAMAGE));
                break;
            case 40:
                super.addequipmentCaracteristic((int) (agilityBow + Math.pow(EVOLUTION_FACTOR,7d) * AGILITY), (int) (strengthBow + Math.pow(EVOLUTION_FACTOR,7d) * STRENGTH), (int) (clevernessBow + Math.pow(EVOLUTION_FACTOR,7d) * CLEVERNESS), (int) (hpBow + Math.pow(EVOLUTION_FACTOR,7d) * HP), (int) (manaBow + Math.pow(EVOLUTION_FACTOR,7d) * MANA), (int) (damageBow + Math.pow(EVOLUTION_FACTOR,7d) * DAMAGE));
                break;
            case 45:
                super.addequipmentCaracteristic((int) (agilityBow + Math.pow(EVOLUTION_FACTOR,8d) * AGILITY), (int) (strengthBow + Math.pow(EVOLUTION_FACTOR,8d) * STRENGTH), (int) (clevernessBow + Math.pow(EVOLUTION_FACTOR,8d) * CLEVERNESS), (int) (hpBow + Math.pow(EVOLUTION_FACTOR,8d) * HP), (int) (manaBow + Math.pow(EVOLUTION_FACTOR,8d) * MANA), (int) (damageBow + Math.pow(EVOLUTION_FACTOR,8d) * DAMAGE));
                break;
            case 50:
                super.addequipmentCaracteristic((int) (agilityBow + Math.pow(EVOLUTION_FACTOR,9d) * AGILITY), (int) (strengthBow + Math.pow(EVOLUTION_FACTOR,9d) * STRENGTH), (int) (clevernessBow + Math.pow(EVOLUTION_FACTOR,9d) * CLEVERNESS), (int) (hpBow + Math.pow(EVOLUTION_FACTOR,9d) * HP), (int) (manaBow + Math.pow(EVOLUTION_FACTOR,9d) * MANA), (int) (damageBow + Math.pow(EVOLUTION_FACTOR,9d) * DAMAGE));
                break;
        }
    }


}