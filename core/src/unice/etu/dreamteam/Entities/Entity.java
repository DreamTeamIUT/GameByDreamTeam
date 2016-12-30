package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.utils.JsonValue;

//TODO : Maybe add/implement an "action listener" in order to unify calls in colisions manager ?


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
