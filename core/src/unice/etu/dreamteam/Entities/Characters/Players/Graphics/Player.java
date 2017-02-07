package unice.etu.dreamteam.Entities.Characters.Players.Graphics;

import unice.etu.dreamteam.Entities.Characters.Players.PlayerHolder;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Inventory;
import unice.etu.dreamteam.Entities.Items.Item;
import unice.etu.dreamteam.Entities.Items.Items;
import unice.etu.dreamteam.Entities.Weapons.Weapon;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Player extends Character {
    private ArrayList<Weapon> weapons;
    private Inventory inventory;

    public Player(PlayerHolder h) {
        super(h);

        weapons = h.getWeapons();
        inventory = new Inventory();

        inventory.add(Items.getInstance().get(0));
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

    public Inventory getInventory() {
        return inventory;
    }
}
