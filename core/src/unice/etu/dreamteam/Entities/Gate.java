package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.Character;
import unice.etu.dreamteam.Map.CollisionsManager;
import unice.etu.dreamteam.Map.Map;
import unice.etu.dreamteam.Map.MapEvent;
import unice.etu.dreamteam.Screens.GameScreen;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.ScreenList;
import unice.etu.dreamteam.Utils.ScreenManager;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Gate extends Entity {
    private final String nextGate;
    private final GateState gateState;
    private final String nextMap;

    //TODO : gates haves start point and a destination point
    //TODO : gates can be closed
    //TODO : gates can disapear

    public Gate(JsonValue value) {
        super(value);
        Debug.log(this.getName());
        String[] tmp = value.getString("goto").split(":");
        nextGate = tmp[1];
        nextMap = tmp[0];

        gateState = new GateState();
        gateState.isOpen = value.getBoolean("isOpen");
    }

    public Boolean isOpen() {
        return getGateState().isOpen;
    }

    public Boolean isAlive() {
        return false;
    }

    public long getRemainingTime() {
        return 0;
    }

    public void onPass(MapEvent event) {
        getGateState().countPass++;

        if (event.getStory().getMaps().get(this.nextMap) != null) {
            Debug.log("lasdfsdfsdfdf");
            Debug.log(this.nextMap + " next map" + event.getMap().getMapInfo().getName()  + " current map ");
            if (!this.nextMap.equals(event.getMap().getMapInfo().getName())) {
                Debug.log("la");
                ScreenManager.getInstance().showScreen(ScreenList.GAME, this.nextMap, GameScreen.TYPE_MAP);
            }
        }

        RectangleMapObject nextGateObject = (RectangleMapObject) event.getGame().getMap().getLayerManager().getCurrentGateLayer().getObjects().get(this.nextGate);

        Debug.log(nextGateObject + " -> next Gate Obj");
        if (nextGateObject != null) {
            Vector2 v = Map.pixelToCell(nextGateObject.getRectangle().getX(), nextGateObject.getRectangle().getY());
            event.getCharacter().setCellPos(v);
        }
    }

    public GateState getGateState() {
        return gateState;
    }

    public class GateState {
        boolean isOpen = false;
        int countPass = 0;
    }
}
