package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Game;
import unice.etu.dreamteam.Screens.AbstractScreen;


public class ScreenManager { //S'autocrée, se garde en mémore toute seule : variable de classe de ce type avec l'instance screen manager
    //Variable de classe déjà statique => permet de récupérer l'instance déjà donné.

    private static ScreenManager instance;
    private Game game;

    private ScreenManager() {
        super();
    }

    public static ScreenManager getInstance() {
        if (instance == null) //Verifie que la variable de classe est bien crée.
            instance = new ScreenManager();

        return instance;
    }

    public void initialize(Game game) {
        this.game = game;
    }

    public void showScreen(ScreenList screenList, Object... params) { //afficher un autre screen, infinité de paramètre, object : classe la plus basse possible

        AbstractScreen currentScreen = (AbstractScreen) game.getScreen(); //récupère le screen actuel,
        if (currentScreen != null)
            currentScreen.ready = false;

        AbstractScreen newScreen = screenList.getScreen(params); //création d'un screen temporaire.

        newScreen.buildStage(); //première fonction appellé du screen, pour quelque chose de statique, pas mobile ..
        game.setScreen(newScreen); //définit le nouveau screen chargé

        if (currentScreen != null)
            currentScreen.dispose(); //vider l'ancien screen de la mémoire
    }

    public Game getGame() {
        return game;
    }
}