package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by Dylan on 12/01/2017.
 */
public class ForceEntity extends Entity {
    private int powerful;
    private Boolean isDefault;

    public ForceEntity(JsonValue value) {
        super(value);

        powerful = value.getInt("powerful", 0);
        isDefault = value.getBoolean("default");
    }

    public int getPowerful() {
        return powerful;
    }

    public Boolean isDefault() {
        return isDefault;
    }
}
