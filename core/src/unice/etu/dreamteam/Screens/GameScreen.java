package unice.etu.dreamteam.Screens;

import unice.etu.dreamteam.Maps.TiledMapSample;
import unice.etu.dreamteam.audio.MusicSample;

public class GameScreen extends AbstractScreen {

    private TiledMapSample map;

    public GameScreen(){
        //Ici on load les assets


        new MusicSample();
    }

    @Override
    public void buildStage() {
         map = new TiledMapSample();
    }

    @Override
    public void render(float delta) {
       super.render(delta); //Fait appel à la fonction de abstractScreen
        map.render();
        //Fait appel à la fonction render de map.
    } //fonction appellé toutes les frames, pour actualiser l'affichage.

    @Override
    public void dispose() {
        super.dispose();
        //Ici on vide !
    }
}
