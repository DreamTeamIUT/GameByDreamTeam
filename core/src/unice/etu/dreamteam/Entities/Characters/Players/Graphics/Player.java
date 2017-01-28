package unice.etu.dreamteam.Entities.Characters.Players.Graphics;

import unice.etu.dreamteam.Entities.Characters.Players.PlayerHolder;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Weapons.Weapon;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Player extends Character {
    private ArrayList<Weapon> weapons;

    public Player(PlayerHolder h) {
        super(h);

        weapons = h.getWeapons();
    }

    private void setWeapon(String name, int powerful) {
        Boolean found = false;

        if (weapons != null) {
            for (Weapon weapon : weapons) {
                if (weapon != null && weapon.getName().equals(name)) {
                    found = true;
                    setWeapon(weapon, powerful);
                }
            }

            if (!found)
                setWeapon(weapons.get(0), powerful);
        }
        else
            Debug.log("Player", "Empty weapons");
    }

    public void setWeapon(String name) {
        setWeapon(name, -1);
    }
}
