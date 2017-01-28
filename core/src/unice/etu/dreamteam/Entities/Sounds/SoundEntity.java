package unice.etu.dreamteam.Entities.Sounds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Entities.Entity;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 31/10/2016.
 */
public class SoundEntity extends Entity {
    private Sound sound;

    public SoundEntity(JsonValue value) {
        super(value);

        Debug.log("SoundEntity", value.toString());
        Debug.log("SoundEntity", value.name);

        FileHandle file = Gdx.files.internal(GameInformation.getGamePackage().getPackagePath("sounds") + value.getString("src"));

        Debug.log("SoundEntity", GameInformation.getGamePackage().getPackagePath("sounds") + value.getString("src"));
        Debug.log("SoundEntity", String.valueOf(file.exists()));

        if(file.exists())
            sound = Gdx.audio.newSound(file);

        Debug.log("SoundEntity", String.valueOf(sound != null));
    }

    public void play() {
        Debug.log("SoundEntity", "play : " + this.getName() + " is defined : " + String.valueOf(sound != null));

        if(sound != null)
            sound.play();
    }
}