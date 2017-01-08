package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Gdx;
import unice.etu.dreamteam.Screens.*;


public enum ScreenList { //prédéfinition des screens dune liste
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },

    STORY_NENU {
        public AbstractScreen getScreen(Object... params) {
            return new StoryMenuScreen(params);
        }
    },

    GAME {
        public AbstractScreen getScreen(Object... params) { //retourne une nouvelle instance avec les paramètres du screen
            if (params.length != 2)
            {
                Debug.error("Invalid parametter !! ");
                Gdx.app.exit();
            }
            else {
               Object p1 = params[0];
               Object p2 = params[1];

               if (p1 instanceof String && p2 instanceof Integer){
                   return new GameScreen((String) p1, (Integer) p2);
               }
               else if (p1 instanceof String && p2 instanceof String){
                   return new GameScreen((String) p1, (String) p2);
               }
            }
            return null;
        }
    },

    PLAYER_SELECT_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new PlayerSelectionScreen();
        }
    },

    PLAYER_CREATION_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new PlayerCreationScreen();
        }
    },

    SPLASH_SCREEN {
        public AbstractScreen getScreen(Object... params) { //Première écran
            return new SplashScreen();
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}
