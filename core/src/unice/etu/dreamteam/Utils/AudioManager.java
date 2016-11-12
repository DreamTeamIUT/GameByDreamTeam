package unice.etu.dreamteam.Utils;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import unice.etu.dreamteam.Screens.AbstractScreen;
import unice.etu.dreamteam.Ui.Settings;

/**
 * Created by Romain on 11/11/2016.
 */
public class AudioManager {

    private static AudioManager instance;
    private static Array<Sound> sounds;
    private Game game;
    private Array<Music> songs;
    private int currentSongIdx;
    private float volume;
    private Listener listener;


    private AudioManager() {
        super();
    }

    public static AudioManager getInstance() {
        if (instance == null) //Verifie que la variable de classe est bien crée.
            initialize();
        return instance;
    }

    public static void initialize() {
        instance = new AudioManager();
    }

    public void showScreen(ScreenList screenList, Object... params) { //afficher un autre screen, infinité de paramètre, object : classe la plus basse possible

        Screen currentScreen = game.getScreen(); //récupère le screen actuel,

        AbstractScreen newScreen = screenList.getScreen(params); //création d'un screen temporaire.

        newScreen.buildStage(); //première fonction appellé du screen, pour quelque chose de statique, pas mobile ..
        game.setScreen(newScreen); //définit le nouveau screen chargé

        if (currentScreen != null)
            currentScreen.dispose(); //vider l'ancien screen de la mémoire
    }

    void playSong(int songIdx) {
        Music song = songs.get(currentSongIdx);
        song.setOnCompletionListener(null);
        song.stop();
        currentSongIdx = songIdx;
        song = songs.get(currentSongIdx);
        song.play();
        song.setVolume(volume);
        song.setOnCompletionListener((Music.OnCompletionListener) listener);
    }

    public void createMusic(String path) {
        songs = new Array<Music>();
        songs.add(Gdx.audio.newMusic(Gdx.files.internal(path)));


        currentSongIdx = 0;
        volume = Settings.prefs.getFloat("musicVolume", 0.5f);

        Debug.log("Music", "Information");
        playSong(0);
    }

    public void createSound(String path) {
        sounds = new Array<Sound>();
        sounds.add(Gdx.audio.newSound(Gdx.files.internal(path)));

        playSound(0);
    }

    private  void playSound(int index) {
        Sound sound = sounds.get(index);
        sound.play(volume);
    }

    public void dispose() {
        for (Music song : songs) {
            song.dispose();
        }
    }

    public void reload() {

    }
}

