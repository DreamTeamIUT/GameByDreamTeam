package unice.etu.dreamteam.Map;

import com.sun.javaws.Main;
import unice.etu.dreamteam.Entities.Characters.Mobs.Graphics.Mob;
import unice.etu.dreamteam.Entities.Items.Item;

import java.util.ArrayList;

/**
 * Created by Dylan on 28/01/2017.
 */
public class GraphicalInstances {
    private static GraphicalInstances graphicalInstances;

    private ArrayList<Mob> mobs;
    private ArrayList<Item.ItemInstance> itemInstances;

    private GraphicalInstances() {
        super();

        mobs = new ArrayList<>();
        itemInstances = new ArrayList<>();
    }

    public static GraphicalInstances getInstance() {
        if(graphicalInstances == null)
            graphicalInstances = new GraphicalInstances();

        return graphicalInstances;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public ArrayList<Item.ItemInstance> getItemInstances() {
        return itemInstances;
    }
}
