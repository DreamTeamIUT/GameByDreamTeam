package unice.etu.dreamteam.Utils;

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
            return new GameScreen();
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
