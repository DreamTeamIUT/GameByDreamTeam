package unice.etu.dreamteam.Audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.FileUtils;
import unice.etu.dreamteam.Utils.GameInformation;
import unice.etu.dreamteam.Utils.Packages;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Romain on 11/11/2016.
 */
public class AudioManager implements Disposable {

    private static AudioManager instance;
    private AssetManager assets;
    private ObjectMap<String, String> audios;
    private Array<Sound> effetcs;
    private Array<Music> musics;


    //TODO : Gestion des sons , de manière modulable, effet/music, ...

    private AudioManager() {
        super();
        assets = new AssetManager();
    }

    public static AudioManager getInstance() {
        if (instance == null) //Verifie que la variable de classe est bien crée.
            initialize();
        return instance;
    }

    public static void initialize() {
        instance = new AudioManager();
        instance.getAudioFiles();
    }

    private void getAudioFiles() {
        ArrayList<File> paths = new ArrayList<>();
        audios = new ObjectMap<>();

        FileUtils.listFile("assets/audio", ".mp3", true, paths);
        for (File f : paths) {
            audios.put(f.getName().replace(".mp3", ""), f.getPath());
            assets.load(f.getPath(), getType(f.getParentFile().getName()));
        }

        Debug.log(audios.toString());

    }

    public void updatePackage() {
        Debug.log("Package update");
        ArrayList<File> paths = new ArrayList<>();

        FileUtils.listFile(GameInformation.getPackagePath() + "/music/", ".mp3", true, paths);
        for (File f : paths) {
            audios.put(f.getName().replace(".mp3", ""), f.getPath());
            assets.load(f.getPath(), getType(f.getParentFile().getName()));
        }

        Debug.log(audios.toString());
    }

    private Class getType(String parent) {
        Debug.log(parent);
        switch (parent) {
            case "effects":
                return Sound.class;
            case "music":
                return Music.class;
            default:
                return Sound.class;
        }
    }


    public void load(String name) {

    }

    void playSong(int songIdx) {
       /* Music song = songs.get(currentSongIdx);
        song.setOnCompletionListener(null);
        song.stop();
        currentSongIdx = songIdx;
        song = songs.get(currentSongIdx);
        song.play();
        song.setVolume(volume);
        song.setOnCompletionListener(null);*/
    }

    public void createMusic(String path) {
        // songs = new Array<Music>();
        // songs.add(Gdx.audio.newMusic(Gdx.files.internal(path)));


        // currentSongIdx = 0;
        // volume = Settings.prefs.getFloat("musicVolume", 0.5f);

        Debug.log("Music", "Information");
        playSong(0);
    }

    public void createSound(String path) {
       /* sounds = new Array<Sound>();
        sounds.add(Gdx.audio.newSound(Gdx.files.internal(path)));

        playSound(0);*/
    }

    private void playSound(int index) {
       /* Sound sound = sounds.get(index);
        sound.play(volume);*/
    }

    public void dispose() {
        assets.dispose();
        /*
        for (Music song : songs) {
            song.dispose();
        }
        */
    }

    public void reload() {

    }
}

