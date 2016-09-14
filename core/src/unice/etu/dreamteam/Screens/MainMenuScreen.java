package unice.etu.dreamteam.Screens;

public class MainMenuScreen extends AbstractScreen {

    public MainMenuScreen(){
        //Ici on load les assets
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    } //delta:espace de temps


    @Override
    public void dispose() { //Appelle le dispose de abstract, stage ...
        super.dispose();
        //Ici on vide !
    }
}
//tous les screens du jeu => ce format