package unice.etu.dreamteam.Entities.Items;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Map.Map;
import unice.etu.dreamteam.Map.MapEvent;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * Created by Dylan on 01/10/2016.
 */


public class Item extends Entity {

    //TODO: items have image that should be printed
    //TODO : Add properties ...

    private static final int TYPE_ITEM_SPECIAL = -1 ;
    private String tileFile;
    private int ttl;
    private int type;
    private ArrayList<ItemInstance> instances;

    public Item(JsonValue value){
        super(value);

        this.ttl = value.getInt("ttl");
        this.tileFile = value.getString("tile");

        switch (value.getInt("type")){
            case TYPE_ITEM_SPECIAL:
                this.type = TYPE_ITEM_SPECIAL;
                break;
        }


        instances = new ArrayList<>();

    }

    public ItemInstance addInstance(Vector2 pos){
        ItemInstance item = new ItemInstance(this, pos);
        this.instances.add(item);
        return item;
    }

    public ArrayList<ItemInstance> getInstances(){
        return this.instances;
    }

    public class ItemInstance{
        private final long startTime;
        private final Item item;
        private final Vector2 itemPos;
        private boolean expired;

        private Boolean drawn;

        public ItemInstance(Item i, Vector2 pos){
            this.itemPos = pos;
            this.startTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            this.item = i;

            this.drawn = false;
        }

        public long getRemainingTime(){
            long current = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            if (this.item.type == Item.TYPE_ITEM_SPECIAL)
                return 1;

            if (this.item.ttl - (current - this.startTime) > 0)
                return  this.item.ttl - (current - this.startTime);
            else
                return 0;
        }

        public void onGrab(MapEvent event){
            Debug.log("ITEM", "Yea! Item captured !");
            this.expired = true;
        }

        public void onThrown(Map map){
            if(this.isDrawn())
                return;

            this.drawn = true;

            Debug.log("ITEM", "thrown yes -> chest ! ");

            TiledMapTileLayer.Cell c = new TiledMapTileLayer.Cell();
            TiledMapTile t = map.getTileByName("chest");
            c.setTile(t);
            map.getLayerManager().getCurrentTileLayers().get(1).setCell((int)this.itemPos.x, (int)this.itemPos.y, c);
        }


        public boolean isExpired() {
            return getRemainingTime()<= 0 || expired;
        }

        public Vector2 getPos() {
            return itemPos;
        }

        public void removeFromMap(Map map) {
            Debug.log("MODEL_2D", map.toString());
            TiledMapTileLayer l = map.getLayerManager().getLayerForTypeAt("ITEM", this.itemPos);
            if (l != null)
                l.setCell((int)this.itemPos.x, (int)this.itemPos.y, null);
        }

        public Boolean isDrawn() {
            return drawn;
        }
    }


}
