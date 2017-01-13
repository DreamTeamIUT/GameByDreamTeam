package unice.etu.dreamteam.Entities.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.EntitiesHolder;
import unice.etu.dreamteam.Map.Map;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Items extends EntitiesHolder<Item> {

    private ArrayList<ArrayList<Item.ItemInstance>> itemInstances = new ArrayList<>();

    public Items() {
        super();
    }

    public Items(JsonValue.JsonIterator jsonValues) {
        super(jsonValues);
    }


    @Override
    public Boolean add(JsonValue value) {
        return add(new Item(value));
    }

    @Override
    public boolean add(Item i) {
        Debug.log("ITEM", "Add item" + i.getName());
        itemInstances.add(i.getInstances());
        return super.add(i);
    }

    public void updateInstances(Map map) {
        ArrayList<Item.ItemInstance> toRemove = new ArrayList<>();
        for (ArrayList<Item.ItemInstance> a : itemInstances) {
            for (Item.ItemInstance i : a) {
                if (i.isExpired()){
                    i.removeFromMap(map);
                    toRemove.add(i);
                }
            }
        }

        for (ArrayList<Item.ItemInstance> a : itemInstances) {
            a.removeAll(toRemove);
        }

    }

    public void clearInstances(Map map){
        ArrayList<Item.ItemInstance> toRemove = new ArrayList<>();
        for (ArrayList<Item.ItemInstance> a : itemInstances) {
            for (Item.ItemInstance i : a) {
                i.removeFromMap(map);
                toRemove.add(i);
            }
        }

        for (ArrayList<Item.ItemInstance> a : itemInstances) {
            a.removeAll(toRemove);
        }
    }

    public Item.ItemInstance getInstanceAt(Vector2 pos) {
        Debug.log("SIZE", itemInstances.size() + " ");
        for (ArrayList<Item.ItemInstance> a : itemInstances) {
            for (Item.ItemInstance instance : a) {
                if (instance.getPos().equals(pos)) {
                    return instance;
                }
            }
        }
        return null;
    }

    public static Items loadFromDir() {
        File directory = new File(GameInformation.getGamePackage().getPackagePath() + "/items/");
        Items items = new Items();

        File[] fList = directory.listFiles();

        for (File file : fList) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                FileHandle itemData = Gdx.files.internal(file.getPath());
                JsonValue jsonValue = new JsonReader().parse(itemData.readString());
                items.add(new Item(jsonValue));
            }
        }

        return items;
    }

    public void addInstances(HashMap<Vector2, String> currentIonstances) {
        for (Vector2 pos : currentIonstances.keySet()) {
            if (currentIonstances.get(pos) != null) {
                Debug.log("Add ok !");
                this.get(currentIonstances.get(pos)).addInstance(pos);
            }
        }
    }
}
