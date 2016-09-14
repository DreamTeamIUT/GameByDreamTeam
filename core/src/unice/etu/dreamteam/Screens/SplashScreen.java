package unice.etu.dreamteam.Screens;

import unice.etu.dreamteam.Utils.Debug;

public class SplashScreen extends AbstractScreen {

    public SplashScreen(){
        //Ici on load les assets
        Debug.log("Splash Constructor");
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        //Ici on vide !
    }
}
