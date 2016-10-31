package unice.etu.dreamteam.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by Dylan on 01/10/2016.
 */
public class SoundEffect {
    private String name;
    private Sound sound;
    private float volume = 1.0f;

    public SoundEffect(String url) {
        loadSound(url);
    }

    public SoundEffect(String url, String name) {
        this.name = name;

        loadSound(url);
    }

    public SoundEffect(String url, boolean autoPlay) {
        loadSound(url);

        if(autoPlay)
            playSound();
    }

    private void loadSound(String url) {
        sound = Gdx.audio.newSound(Gdx.files.internal(url));
    }

    public long playSound() {
        return sound.play(volume);
    }

    public String getName() {
        return this.name;
    }

    public void stopSound() {
        sound.stop();
    }
}
