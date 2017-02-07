package unice.etu.dreamteam.Entities;

import unice.etu.dreamteam.Entities.Items.Item;

import java.util.ArrayList;

/**
 * Created by Romain on 03/02/2017.
 */
public class Inventory extends ArrayList<Item>{

    private static final int MAX_SIZE  = 20;

    public Inventory(){
        super();
    }

    @Override
    public boolean add(Item item) {
        if (this.size()< MAX_SIZE)
            return super.add(item);

        return false;
    }
}
