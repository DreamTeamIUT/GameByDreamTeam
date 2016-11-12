package unice.etu.dreamteam.Ui;

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
import unice.etu.dreamteam.Utils.AudioManager;
import unice.etu.dreamteam.Utils.Debug;

/**
 * Created by Romain on 11/11/2016.
 */
public class Settings {

    public static Preferences prefs = Gdx.app.getPreferences("GameSettings");
    public static boolean isOpen;

    public static Actor createWindow(Viewport viewport) {
        isOpen = true;

        final Window dialog = new Window("Settings", UiManager.getInstance().getSkin());

        Debug.log(viewport.getScreenWidth() + " " + viewport.getScreenHeight());
        dialog.debugAll();
        dialog.row().fill().expandX();
        dialog.setSize(viewport.getScreenWidth(), viewport.getScreenHeight());
        dialog.add(new Label("Audio", UiManager.getInstance().getSkin(), "default-font", Color.ORANGE)).colspan(3);
        dialog.row();
        CheckBox chkSound = new CheckBox("", UiManager.getInstance().getSkin());
        dialog.add(chkSound);
        dialog.add(new Label("Sound", UiManager.getInstance().getSkin()));
        final Slider sldEffects = new Slider(0.0f, 1.0f, 0.1f, false, UiManager.getInstance().getSkin());
        dialog.add(sldEffects);
        sldEffects.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putFloat("effectsVolume", sldEffects.getValue());
            }
        });
        dialog.row();
        CheckBox chkMusic = new CheckBox("", UiManager.getInstance().getSkin());
        dialog.add(chkMusic);
        dialog.add(new Label("Music", UiManager.getInstance().getSkin()));
        final Slider sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, UiManager.getInstance().getSkin());

        sldMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putFloat("musicVolume", sldMusic.getValue());
            }
        });
        dialog.add(sldMusic);
        dialog.row().bottom().fillY().expandY().align(Align.bottom);
        TextButton btn = new TextButton("Save and exit", UiManager.getInstance().getSkin());
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
