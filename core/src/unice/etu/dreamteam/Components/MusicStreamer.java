package unice.etu.dreamteam.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Dylan on 01/10/2016.
 */
public class MusicStreamer {
    private Music music;
    private float volume = 1.0f;

    public MusicStreamer(String url) {
        loadMusic(url);
    }

    public MusicStreamer(String url, boolean looping) {
        loadMusic(url);
        setLooping(looping);
    }

    private void loadMusic(String url) {
        music = Gdx.audio.newMusic(Gdx.files.internal(url));
    }

    public void setLooping(boolean looping) {
        music.setLooping(looping);
    }

    public void play() {
        music.play();
    }

    public void stop() {
        music.stop();
    }
}
