package unice.etu.dreamteam.Screens;

public class GameScreen extends AbstractScreen {

    public GameScreen(){
        //Ici on load les assets
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
       super.render(delta); //Fait appel à la fonction de abstractScreen
    } //fonction appellé toutes les frames, pour actualiser l'affichage.

    @Override
    public void dispose() {
        super.dispose();
        //Ici on vide !
    }
}
