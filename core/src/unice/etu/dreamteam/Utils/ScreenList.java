package unice.etu.dreamteam.Utils;

import unice.etu.dreamteam.Screens.*;


public enum ScreenList { //prédéfinition des screens dune liste
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },

    GAME {
        public AbstractScreen getScreen(Object... params) { //retourne une nouvelle instance avec les paramètres du screen
            return new GameScreen();
        }
    },

    Game2 {
        public AbstractScreen getScreen(Object... params) { //retourne une nouvelle instance avec les paramètres du screen
            return new GameTestScreen();
        }
    },

    SLASH_SCREEN {
        public AbstractScreen getScreen(Object... params) { //Première écran
            return new SplashScreen();
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}
