package unice.etu.dreamteam.Utils;

import unice.etu.dreamteam.Screens.AbstractScreen;
import unice.etu.dreamteam.Screens.GameScreen;
import unice.etu.dreamteam.Screens.MainMenuScreen;
import unice.etu.dreamteam.Screens.SplashScreen;


public enum ScreenList {
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },

    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen();
        }
    },

    SLASH_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new SplashScreen();
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}
