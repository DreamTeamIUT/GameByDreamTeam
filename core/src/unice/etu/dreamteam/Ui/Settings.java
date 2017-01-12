package unice.etu.dreamteam.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.glass.ui.View;
import com.sun.javafx.css.Size;
import unice.etu.dreamteam.Audio.AudioManager;
import unice.etu.dreamteam.Utils.Debug;

import java.util.ArrayList;

/**
 * Created by Romain on 11/11/2016.
 */
public class Settings {

    public static Preferences prefs = Gdx.app.getPreferences("GameSettings");
    public static boolean isOpen;
    public static int COLSPANPARAMETER = 4;
    public static String DEFAULTSETTINGSUP = "Z";
    public static String DEFAULTSETTINGSDOWN = "S";
    public static String DEFAULTSETTINGSLEFT = "Q";
    public static String DEFAULTSETTINGSRIGHT = "D";

    public static boolean isValid(String valueToCompare, String defaultSettings1, String defaultSettings2, String defaultSettings3 ,Viewport viewport) {
        ArrayList<String> list = new ArrayList<>();
        list.add(defaultSettings1);
        list.add(defaultSettings2);
        list.add(defaultSettings3);
        return !list.contains(valueToCompare);

    }

    public static Actor createWindow(final Viewport viewport) {
        isOpen = true;

        final Window dialog = new Window("Settings", UiManager.getInstance().getSkin());

        final TextField field_right = new TextField(prefs.getString("right", DEFAULTSETTINGSRIGHT), UiManager.getInstance().getSkin());
        field_right.setAlignment(Align.center);
        field_right.setMaxLength(1);

        final TextField field_down = new TextField(prefs.getString("down", DEFAULTSETTINGSDOWN), UiManager.getInstance().getSkin());
        field_down.setAlignment(Align.center);
        field_down.setMaxLength(1);

        final TextField field_up = new TextField(prefs.getString("up", DEFAULTSETTINGSUP), UiManager.getInstance().getSkin());
        field_up.setAlignment(Align.center);
        field_up.setMaxLength(1);

        final TextField field_left = new TextField(prefs.getString("left", DEFAULTSETTINGSLEFT), UiManager.getInstance().getSkin());
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
        final String field_up1 = field_up.getText();
        field_up.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Debug.log("up", field_up.getText());
                prefs.putString("up", field_up.getText());
                if (!isValid(field_up.getText(),field_down.getText(), field_right.getText(), field_left.getText(), viewport)){
                    prefs.putString("up", field_up1);
                }
            }
        });
        dialog.add(field_down).top();
        Debug.log(field_down.getMessageText());
        final String field_down1 = field_down.getText();
        field_down.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Debug.log("Down", field_down.getText());
                prefs.putString("down", field_down.getText());
                if (!isValid(field_down.getText(),field_up.getText(), field_right.getText(), field_left.getText(), viewport)){
                    prefs.putString("down", field_down1);
                }
            }
        });
        dialog.add(field_left).top();
        final String field_left1 = field_left.getText();
        field_left.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Debug.log("Down", field_left.getText());
                prefs.putString("left", field_left.getText());
                if (!isValid(field_left.getText(),field_down.getText(), field_right.getText(), field_up.getText(), viewport)){
                    prefs.putString("left", field_left1);
                }
            }
        });
        dialog.add(field_right).top();
        final String field_right1 = field_right.getText();
        field_right.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Debug.log("Down", field_right.getText());
                prefs.putString("right", field_right.getText());
                if (!isValid(field_right.getText(),field_down.getText(), field_up.getText(), field_left.getText(), viewport)){
                    prefs.putString("right", field_right1);
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
