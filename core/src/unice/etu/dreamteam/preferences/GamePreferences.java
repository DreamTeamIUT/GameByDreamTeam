package unice.etu.dreamteam.preferences;

import com.badlogic.gdx.Gdx;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by Romain on 16/09/2016.
 */
public class GamePreferences {


    public static final String KEY_VOLUME= "volume";
    public static final String PREFERENCE_NAME = "Gamr";

   /* private static GamePreferences instance;
    private com.badlogic.gdx.Preferences gamePreference;

    private GamePreferences() {
        super();
        gamePreference = Gdx.app.getPreferences("My Preferences");
    }

    public static GamePreferences getInstance() {
        if (instance == null) //Verifie que la variable de classe est bien crée.
            instance = new GamePreferences();

        return instance;
    }

    public GamePreferences setVolume(int volume) {
        this.gamePreference.putInteger("volume", volume);
        return this;
    }

    public int getVolume(){
        return this.gamePreference.getInteger(KEY_VOLUME);
    }

    @Override
    public void save() {
        this.gamePreference.flush();
    }*/
}
