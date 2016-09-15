package unice.etu.dreamteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.ScreenList;
import unice.etu.dreamteam.Utils.ScreenManager;

public class Main extends Game {
    private static long SPLASH_SCREEN_DURATION = 2000L;

    public Main() {
        super();
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Debug.log("Create");

        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen(ScreenList.SLASH_SCREEN);

        final long startTime = System.currentTimeMillis();

        new Thread(new Runnable() {

            @Override   //Sous processus java démarré
            public void run() {

                Gdx.app.postRunnable(new Runnable() { //avoir le droit de dessiner dans l'écran, rattache sous le process

                    @Override
                    public void run() {//calcule le temps qui s'écoule: 2 sec

                        long elaspedTime = System.currentTimeMillis() - startTime; //Temps OS (ms) - temps (ms) depuis 1970

                        if (elaspedTime < Main.SPLASH_SCREEN_DURATION) { //Récupérer le temps (ms)
                            Timer.schedule( //Gestion du temps
                                    new Timer.Task() {
                                        @Override
                                        public void run() { //ScreenManager: affichage différents screens à n'importe quel moment.
                                            ScreenManager.getInstance().showScreen(ScreenList.GAME);
                                        }

                                    }, (float) (Main.SPLASH_SCREEN_DURATION - elaspedTime) / 1000f);

                        } else {
                            ScreenManager.getInstance().showScreen(ScreenList.MAIN_MENU);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void dispose() {
        getScreen().dispose();
        Gdx.app.exit();

    }
}
