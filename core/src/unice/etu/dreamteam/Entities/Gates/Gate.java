package unice.etu.dreamteam.Entities.Gates;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Map.Map;
import unice.etu.dreamteam.Map.MapEvent;
import unice.etu.dreamteam.Screens.GameScreen;
import unice.etu.dreamteam.Utils.ActionContainer;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.ScreenList;
import unice.etu.dreamteam.Utils.ScreenManager;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Gate extends Entity {
    private String nextGate;
    private String nextMap;

    private int maxEnter;
    private int countEnter;

    private Boolean opened;

    //TODO : gates can be closed
    //TODO : gates can disapear

    public Gate(JsonValue value) {
        super(value);

        setGoto(value.getString("goto"));

        maxEnter = value.getInt("max-enter", -1);
        opened = value.getBoolean("opened");
    }

    public Gate(MapObject obj) {
        super(obj.getName());
        maxEnter = obj.getProperties().get("maxEnter", -1, Integer.class);
        opened = obj.getProperties().get("opened", false, Boolean.class);
        setGoto(obj.getProperties().get("goto", String.class));
    }

    public void onEnter(MapEvent event) {
        countEnter++;

        if (event.getStory().getMaps().get(this.nextMap) != null) {
            Debug.log("lasdfsdfsdfdf");
            Debug.log(this.nextMap + " next map" + event.getMap().getMapInfo().getName()  + " current map ");
            if (!this.nextMap.equals(event.getMap().getMapInfo().getName())) {
                ActionContainer container = new ActionContainer();
                container.moveToGate = this.nextGate;
                ScreenManager.getInstance().showScreen(ScreenList.GAME, this.nextMap, GameScreen.TYPE_MAP, container);
            }
        }
        else {
            RectangleMapObject nextGateObject = (RectangleMapObject) event.getGame().getMap().getLayerManager().getCurrentGateLayer().getObjects().get(this.nextGate);

            Debug.log(nextGateObject + " -> next Gate Obj");
            if (nextGateObject != null) {
                Vector2 v = Map.pixelToCell(nextGateObject.getRectangle().getX(), nextGateObject.getRectangle().getY());
                event.getCharacter().setCellPos(v);
            }
        }
    }

    public Boolean canEnter() {
        return countEnter < maxEnter || maxEnter < 0;
    }

    private void setGoto(String gotoValue) {
        if (gotoValue != null && gotoValue.contains(":"))
        {

            String[] tmp = gotoValue.split(":");

            nextGate = tmp[1];
            nextMap = tmp[0];

            return;
        }

        try {
            throw new Exception("Gate destination not Valid !");
        } catch (Exception e) {
            e.printStackTrace();
        }






    }

    public Boolean isOpened() {
        return opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
    }
}
