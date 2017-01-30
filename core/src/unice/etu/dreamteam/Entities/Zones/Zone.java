package unice.etu.dreamteam.Entities.Zones;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Characters.Mobs.Graphics.Mob;
import unice.etu.dreamteam.Entities.Items.Items;
import unice.etu.dreamteam.Map.GraphicalInstances;
import unice.etu.dreamteam.Entities.Characters.Mobs.Mobs;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Entities.Gates.Gate;
import unice.etu.dreamteam.Entities.Gates.Gates;
import unice.etu.dreamteam.Entities.Sounds.Sounds;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.Timers;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class Zone extends Entity {

    private ArrayList<JsonValue> attacks;
    private ArrayList<JsonValue> gates;
    private ArrayList<String> sounds;

    public int maxEnter;
    public int maxExecute;

    public Boolean general;
    public Boolean blocked;

    public int timeIn;

    private ZoneState zoneState;

    public Zone(JsonValue zone) {
        super(zone);

        if(zone.has("attacks"))
            loadAttacks(zone.get("attacks").iterator());

        if(zone.has("gates"))
            loadGates(zone.get("gates").iterator());

        if(zone.has("sounds"))
            loadSounds(zone.get("sounds").iterator());

        this.maxEnter = zone.getInt("max-enter", -1);
        this.maxExecute = zone.getInt("max-execute", -1);

        this.general = zone.getBoolean("general", true);
        this.blocked = zone.getBoolean("blocked", false);

        this.timeIn = zone.getInt("time-in", -1);

        this.zoneState = new ZoneState();
    }

    public void onEnter(Rectangle rectangle, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Debug.log("ZONE", "onEnter : " + getName());

        getZoneState().isIn = true;
        getZoneState().countEnter++;

        Debug.log("ZONE", "enter : " + String.valueOf(getZoneState().countEnter));

        if(getZoneState().countExecute < maxExecute || maxEnter < 0) {
            getZoneState().countExecute++;

            Debug.log("ZONE", "execute : " + String.valueOf(getZoneState().countExecute));

            triggerAttacks(rectangle, spriteBatch, shapeRenderer);
            updateGates();
            playSounds();
        }
    }

    public void onLeave() {
        Debug.log("ZONE", "onLeave : " + getName());

        getZoneState().isIn = false;
    }

    public Boolean canEnter(Boolean general) {
        Debug.log("ZONE", getZoneState().countEnter + " " + maxEnter + " " + String.valueOf(general ? this.general : getZoneState().countEnter < maxEnter || maxEnter < 0));

        return general ? this.general : getZoneState().countEnter < maxEnter || maxEnter < 0;
    }

    public Boolean canEnter() {
        return canEnter(false);
    }

    public Boolean canLeave() {
        return !blocked || !getZoneState().blocked;
    }

    public ZoneState getZoneState() {
        return zoneState;
    }

    public Boolean isIn() {
        return getZoneState().isIn;
    }

    public void setBlockedZone(Boolean is) {
        getZoneState().blocked = is;
    }

    private void loadAttacks(JsonValue.JsonIterator jsonIterator) {
        this.attacks = new ArrayList<>();

        for (JsonValue value : jsonIterator)
            this.attacks.add(value);
    }

    private void triggerAttacks(Rectangle rectangle, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        if (this.attacks != null) {
            for (JsonValue value : this.attacks) {
                if (value.has("mob") && Mobs.getInstance().exist(value.getString("mob"))) {
                    if(value.has("force"))
                        Mobs.getInstance().get(value.getString("mob")).setForce(value.getInt("force", 0));

                    for (int i = 0; i < value.getInt("count", 1); i++) {
                        Mob mob = (Mob)Mobs.getInstance().get(value.getString("mob")).create(spriteBatch, shapeRenderer);
                        mob.setCellPos(getRandomPosition(rectangle));

                        GraphicalInstances.getInstance().getMobs().add(mob);
                    }
                }
            }

            Debug.log("GraphicalInstances", String.valueOf(GraphicalInstances.getInstance().getMobs().size()));
        }
    }

    private Vector2 getRandomPosition(Rectangle rectangle) {
        Vector2 position = new Vector2(Math.round(rectangle.getX() / 32), Math.round(rectangle.getY() / 32));
        Vector2 cases = new Vector2(Math.round(rectangle.getWidth() / 32), Math.round(rectangle.getHeight() / 32));

        Debug.vector("ZONE", position);
        Debug.vector("ZONE", cases);

        Random random = new Random();

        Vector2 randomPosition = new Vector2((random.nextInt((int)cases.x) + 1) + position.x, (random.nextInt((int)cases.y) + 1) + position.y);

        Debug.vector("ZONE", randomPosition);

        return randomPosition;
    }

    private void loadGates(JsonValue.JsonIterator jsonIterator) {
        this.gates = new ArrayList<>();

        for (JsonValue value : jsonIterator)
            this.gates.add(value);
    }

    private void updateGates() {
        if (this.gates  != null) {
            for (JsonValue value : this.gates) {
                if (Gates.getInstance().exist(value.name)) {
                    Gate gate = Gates.getInstance().get(value.name);

                    if(value.has("opened"))
                        gate.setOpened(value.getBoolean("opened", true));
                }
            }
        }
    }

    private void loadSounds(JsonValue.JsonIterator sounds) {
        this.sounds = new ArrayList<>();

        for (JsonValue sound : sounds)
            this.sounds.add(sound.toString());
    }

    private void playSounds() {
        if (this.sounds != null) {
            for (String sound : this.sounds)
                Sounds.getInstance().get(sound).play();
        }
    }

    public void setTimeIn(final Character character) {
        if (this.timeIn > 0) {
            final Vector2 oldPositions = character.getBackPos();

            Timers.getInstance().setTimer(new Timers.TimerFunction() {
                @Override
                public void run() {
                    character.setCellPos(oldPositions);
                }
            }, this.timeIn);
        }
    }

    public void drawItems(Rectangle rectangle) {
        GraphicalInstances.getInstance().getItemInstances().add(Items.getInstance().get("item01").addInstance(getRandomPosition(rectangle)));
    }

    private class ZoneState {
        Boolean isIn = false;
        Boolean blocked = false;
        int countEnter = 0;
        int countExecute = 0;
    }
}
