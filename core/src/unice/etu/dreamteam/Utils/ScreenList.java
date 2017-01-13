package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import unice.etu.dreamteam.Map.Story;
import unice.etu.dreamteam.Screens.*;

import javax.swing.*;


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

            if (params.length == 2) {
                Object p1 = params[0];
                Object p2 = params[1];

                if (p1 instanceof String && p2 instanceof Integer) {
                    return new GameScreen((String) p1, (Integer) p2);
                } else if (p1 instanceof String && p2 instanceof String) {
                    return new GameScreen((String) p1, (String) p2);
                } else if (p1 instanceof Story && p2 instanceof Integer && (Integer)p2 == GameScreen.TYPE_STORY) {
                   return new GameScreen((Story)p1);
                }
            } else if (params.length == 3) {
                Object p1 = params[0];
                Object p2 = params[1];
                Object p3 = params[2];

                if (p1 instanceof String && p2 instanceof Integer && p3 instanceof ActionContainer) {
                    return new GameScreen((String) p1, (Integer) p2, (ActionContainer) p3);
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
