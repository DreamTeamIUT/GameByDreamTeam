package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Romain on 11/11/2016.
 */
public class Settings {

    public static Preferences prefs = Gdx.app.getPreferences("GameSettings");
    public static boolean isOpen;

    public static Actor createWindow(Viewport viewport) {
        isOpen = true;
        Skin skin = new Skin(Gdx.files.internal("assets/ui/default/uiskin.json"));

        Window dialog = new Window("Settings", skin) {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        Debug.log(viewport.getScreenWidth() + " " + viewport.getScreenHeight());
        dialog.debugAll();
        dialog.row().fill().expandX();
        dialog.setSize(viewport.getScreenWidth(), viewport.getScreenHeight());
        dialog.add(new Label("Audio", skin, "default-font", Color.ORANGE)).colspan(3);
        dialog.row();
        dialog.columnDefaults(0).padRight(10);
        dialog.columnDefaults(1).padRight(10);
        CheckBox chkSound = new CheckBox("", skin);
        dialog.add(chkSound);
        dialog.add(new Label("Sound", skin));
        Slider sldEffects = new Slider(0.0f, 1.0f, 0.1f, false, skin);
        dialog.add(sldEffects);
        sldEffects.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putFloat("effectsVolume", sldEffects.getValue());
            }
        });
        dialog.row();
        CheckBox chkMusic = new CheckBox("", skin);
        dialog.add(chkMusic);
        dialog.add(new Label("Music", skin));
        Slider sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skin);

        sldMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putFloat("musicVolume", sldMusic.getValue());
            }
        });
        dialog.add(sldMusic);
        dialog.row().bottom().fillY().expandY().align(Align.bottom);
        TextButton btn = new TextButton("Save and exit", skin);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.flush();
                AudioManager.getInstance().reload();
                dialog.remove();
                isOpen = false;
            }

        });
        dialog.add(btn).colspan(3);



        return dialog;
    }
}
