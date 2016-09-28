package unice.etu.dreamteam.Generic;

import unice.etu.dreamteam.Objects.ObjectGameAnimation;

/**
 * Created by Romain on 18/09/2016.
 */
public class IA extends Player {
    protected double agility, strength, cleverness, hp, mana, damage,criticalRate, evadeRate, defence, damageReceived;

    protected int level;

    public IA(String path, String name,String categorie){
        super(path, name,categorie);
        this.level=1;
    }

    public void level() {
        if(super.getCategorie()=="monster"){
            this.level=super.getLevel();
        }
        else {
            this.level=super.getLevel()+5;
        }
    }

    public void Caracteristic(double agility, double strength, double cleverness, double hp, double mana, double damage, double criticalRate, double evadeRate){
        if(super.getCategorie()=="monster"){
            this.agility = agility;
            this.strength = strength;
            this.cleverness = cleverness;
            this.hp = hp + this.strength * 2;
            this.mana = mana + this.cleverness * 2;
            this.damage = hp + ( this.agility + this.strength + this.cleverness ) / 6;
            this.criticalRate = criticalRate + 0.05 * this.agility;
            this.evadeRate = evadeRate + 0.05*this.agility;
            if ( this.evadeRate >= 75 ) {
                this.evadeRate = 75;
            }
            if ( this.criticalRate >= 100 ){
                this.criticalRate = 100;
            }
        }
        else {
            this.agility = agility + super.getAgility() / 10;
            this.strength = strength + super.getStrength() / 10;
            this.cleverness = cleverness + super.getCleverness() / 10;
            this.hp = hp + this.strength * 20 + super.getStrength() / 2 ;
            this.mana = mana + this.cleverness * 20 + super.getCleverness() / 2;
            this.damage = damage + ( this.agility + this.strength + this.cleverness ) / 2;
            this.criticalRate = criticalRate + 0.05 * this.agility + 0.01*super.getAgility();
            this.evadeRate = evadeRate + 0.05*this.agility + 0.01*super.getAgility();
            if ( this.evadeRate >= 75 ) {
                this.evadeRate = 75;
            }
            if ( this.criticalRate >= 100 ){
                this.criticalRate = 100;
            }
        }
    }
}
