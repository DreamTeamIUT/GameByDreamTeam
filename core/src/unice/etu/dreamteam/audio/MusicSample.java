package unice.etu.dreamteam.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Romain on 16/09/2016.
 */
public class MusicSample {
    private static final float VOLUME_CHANGE = 0.2f;

    private Array<Music> songs;
    private int currentSong;
    private float volume;
    private SongListener listener;

    public MusicSample() {
        listener = new SongListener();

        songs = new Array<Music>();
        songs.add(Gdx.audio.newMusic(Gdx.files.internal("assets/music/Test.mp3")));

        currentSong = 0;
        volume = 1.0f;

        Gdx.app.log("MusicSample", "Instructions");

        playSong();
    }

    public void dispose() {
        for (Music song : songs) {
            song.dispose();
        }

    }

    public void playSong() {
        Music song = songs.get(currentSong);
        song.setLooping(true);
        song.play();
        song.setVolume(volume);
        song.setOnCompletionListener(listener);
    }

    void changeVolume(float volumeChange) {
        Music song = songs.get(currentSong);
        volume = MathUtils.clamp(song.getVolume() + volumeChange,0.0f, 1.0f);
        song.setVolume(volume);
    }



}
