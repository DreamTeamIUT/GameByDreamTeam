package unice.etu.dreamteam.Ui;

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
import unice.etu.dreamteam.Entities.Sounds.Manager.AudioManager;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;

/**
 * Created by Romain on 11/11/2016.
 */
public class Settings {

    public static Preferences prefs = Gdx.app.getPreferences("GameSettings");
    public static boolean isOpen;

    public static final String KEY_RIGHT = "right";
    public static final String KEY_DOWN = "down";
    public static final String KEY_LEFT = "left";
    public static final String KEY_UP = "up";
    public static final java.lang.String KEY_GRAB = "grab";


    public static int COLSPANPARAMETER = 4;
    public static int DEFAULTSETTINGSUP = Input.Keys.Z;
    public static int DEFAULTSETTINGSDOWN = Input.Keys.S;
    public static int DEFAULTSETTINGSLEFT = Input.Keys.Q;
    public static int DEFAULTSETTINGSRIGHT = Input.Keys.D;
    public static final int DEFAULTSETTINGSGRAB = Input.Keys.E;



    public static boolean isValid(int valueToCompare, int defaultSettings1, int defaultSettings2, int defaultSettings3 ,Viewport viewport) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(defaultSettings1);
        list.add(defaultSettings2);
        list.add(defaultSettings3);
        return !list.contains(valueToCompare);

    }

    public static Actor createWindow(final Viewport viewport) {
        isOpen = true;

        final Window dialog = new Window("Settings", UiManager.getInstance().getSkin());

        final TextField field_right = new TextField(Input.Keys.toString(prefs.getInteger(KEY_RIGHT, DEFAULTSETTINGSRIGHT)), UiManager.getInstance().getSkin());
        field_right.setAlignment(Align.center);
        field_right.setMaxLength(1);

        final TextField field_down = new TextField(Input.Keys.toString(prefs.getInteger(KEY_DOWN, DEFAULTSETTINGSDOWN)), UiManager.getInstance().getSkin());
        field_down.setAlignment(Align.center);
        field_down.setMaxLength(1);

        final TextField field_up = new TextField(Input.Keys.toString(prefs.getInteger(KEY_UP, DEFAULTSETTINGSUP)), UiManager.getInstance().getSkin());
        field_up.setAlignment(Align.center);
        field_up.setMaxLength(1);

        final TextField field_left = new TextField(Input.Keys.toString(prefs.getInteger(KEY_LEFT, DEFAULTSETTINGSLEFT)), UiManager.getInstance().getSkin());
        field_left.setAlignment(Align.center);
        field_left.setMaxLength(1);

        Debug.log(viewport.getScreenWidth() + " " + viewport.getScreenHeight());
        dialog.debugAll();
        dialog.row().fill().expand();

        Label label = new Label("Commande", UiManager.getInstance().getSkin(), "default-font", Color.ORANGE);
        dialog.add(label).colspan(COLSPANPARAMETER);

        dialog.row().expand();
        Label labelUp = new Label("Avancer", UiManager.getInstance().getSkin());
        dialog.add(labelUp);

        Label labelDown = new Label("Reculer", UiManager.getInstance().getSkin());
        dialog.add(labelDown);

        Label labelRight = new Label("Gauche", UiManager.getInstance().getSkin());
        dialog.add(labelRight);

        Label labelLeft = new Label("Droite", UiManager.getInstance().getSkin());
        dialog.add(labelLeft);

        dialog.row().expand();
        dialog.add(field_up).top();
        final int field_up1 = Input.Keys.valueOf(field_up.getText());
        field_up.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Debug.log(KEY_UP, field_up.getText());
                prefs.putInteger(KEY_UP, Input.Keys.valueOf(field_up.getText()));
                if (!isValid(Input.Keys.valueOf(field_up.getText()),Input.Keys.valueOf(field_down.getText()), Input.Keys.valueOf(field_right.getText()), Input.Keys.valueOf(field_left.getText()), viewport)){
                    prefs.putInteger(KEY_UP, field_up1);
                }
            }
        });
        dialog.add(field_down).top();
        Debug.log(field_down.getMessageText());
        final int field_down1 = Input.Keys.valueOf(field_down.getText());
        field_down.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Debug.log(KEY_DOWN, field_down.getText());
                prefs.putInteger(KEY_DOWN, Input.Keys.valueOf(field_down.getText()));
                if (!isValid(Input.Keys.valueOf(field_down.getText()),Input.Keys.valueOf(field_up.getText()), Input.Keys.valueOf(field_right.getText()), Input.Keys.valueOf(field_left.getText()), viewport)){
                    prefs.putInteger(KEY_DOWN, field_down1);
                }
            }
        });
        dialog.add(field_left).top();
        final int field_left1 = Input.Keys.valueOf(field_left.getText());
        field_left.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Debug.log(KEY_DOWN, field_left.getText());
                prefs.putInteger(KEY_LEFT, Input.Keys.valueOf(field_left.getText()));
                if (!isValid(Input.Keys.valueOf(field_left.getText()),Input.Keys.valueOf(field_down.getText()), Input.Keys.valueOf(field_right.getText()), Input.Keys.valueOf(field_up.getText()), viewport)){
                    prefs.putInteger(KEY_LEFT, field_left1);
                }
            }
        });
        dialog.add(field_right).top();
        final int field_right1 = Input.Keys.valueOf(field_right.getText());
        field_right.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Debug.log(KEY_DOWN, field_right.getText());
                prefs.putInteger(KEY_RIGHT, Input.Keys.valueOf(field_right.getText()));
                if (!isValid(Input.Keys.valueOf(field_right.getText()),Input.Keys.valueOf(field_down.getText()), Input.Keys.valueOf(field_up.getText()), Input.Keys.valueOf(field_left.getText()), viewport)){
                    prefs.putInteger(KEY_RIGHT, field_right1);
                }
            }
        });

        dialog.row().expand().left().fill();
        dialog.setSize(viewport.getScreenWidth(), viewport.getScreenHeight());
        dialog.add(new Label("Audio", UiManager.getInstance().getSkin(), "default-font", Color.ORANGE)).colspan(COLSPANPARAMETER);

        dialog.row().expand();
        CheckBox chkSound = new CheckBox("", UiManager.getInstance().getSkin());
        dialog.add(chkSound);
        dialog.add(new Label("Sound", UiManager.getInstance().getSkin()));
        final Slider sldEffects = new Slider(0.0f, 1.0f, 0.1f, false, UiManager.getInstance().getSkin());
        dialog.add(sldEffects).colspan(COLSPANPARAMETER);
        sldEffects.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putFloat("effectsVolume", sldEffects.getValue());
            }
        });
        dialog.row().expand();
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
        dialog.add(sldMusic).colspan(COLSPANPARAMETER);

        dialog.row().bottom().expand();
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
        dialog.add(btn).colspan(COLSPANPARAMETER);

        return dialog;
    }
}
