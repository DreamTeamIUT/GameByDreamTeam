package unice.etu.dreamteam.Map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import unice.etu.dreamteam.Characters.Player;
import unice.etu.dreamteam.Entities.Character;

import java.util.ArrayList;

/**
 * Created by Guillaume on 02/11/2016.
 */
public class ColisionsManager {
    private LayerManager layerManager;
    private ArrayList<Character> characters;
    private Story story;

    //TODO : make a generic class that can be used for all other classes/entities/players/mobs
    public ColisionsManager(){

    }


    public void addObjectAtLevel(){

    }


    public void update(float delta){

    }

    public Object hasColisionWith(Player p){
        // return null if no colid, if colid : return the object that colid with ( item, zone, ... )
        //todo : read memory and grab position of any object ( bullet, player, holes, objects,)
        return null;
    }

    public void addCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public void addMapLayer(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public void addStory(Story s) {
        this.story = s;
    }

    public void debug(ShapeRenderer shapeRenderer) {
        layerManager.debugObjectLayer(shapeRenderer);

        //TODO : draw zones, items, players, ... ( every thing colision related )
    }
}
