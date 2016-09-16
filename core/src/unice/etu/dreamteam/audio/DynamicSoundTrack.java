package unice.etu.dreamteam.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import unice.etu.dreamteam.preferences.GamePreferences;

/**
 * Created by Romain on 16/09/2016.
 */

//Pour les soundtracks, par exemple l'arrivée d'un boss, mini cinématique etc... Sans retirer la musique en background
public class DynamicSoundTrack {

    private final static float MAX_SPEED = 200.0f;
    private final static float ACCELERATION = 25.0f;
    private final static float FRICTION = 15.0f;
    private final static float IDLE_THRESHOLD = 0.1f;

    private float speed;
    private Sound engine;
    private Sound idle;
    private long idSound;

    public DynamicSoundTrack() {
        idle = Gdx.audio.newSound(Gdx.files.internal("data/sfx/car-idle.wav"));
        engine = Gdx.audio.newSound(Gdx.files.internal("data/sfx/car-engine.wav"));
        idSound = idle.play();
        idle.setLooping(idSound, true);


    }

    public void dispose() {
        engine.dispose();
        idle.dispose();
    }

    /*public void render() {
        updateEngine(Gdx.graphics.getDeltaTime());
        camera.update();  batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Speed: " + speed + "km/h", 20.0f,200.0f);
        font.draw(batch, "Press SPACE or touch to accelerate",20.0f, 150.0f);
        batch.end();
    }
*/


}
