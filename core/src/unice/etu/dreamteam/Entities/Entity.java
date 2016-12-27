package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;

public abstract class Entity {

    private String name;

    public Entity(JsonValue value){
        this.name = value.getString("name", "");
        this.name = (this.name.equals(""))? value.name : this.name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
