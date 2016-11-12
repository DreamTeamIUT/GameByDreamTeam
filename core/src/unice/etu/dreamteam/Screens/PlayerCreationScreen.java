package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import unice.etu.dreamteam.Ui.UiManager;
import unice.etu.dreamteam.Utils.*;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerCreationScreen extends AbstractScreen {

    private Table table2;

    public PlayerCreationScreen() {
        super();
    }

    @Override
    public void buildStage() {
        final ArrayList<Packages> packages = Packages.getPackages();


        final TextField field = new TextField("aaa", UiManager.getInstance().getSkin());

        ArrayList<String> packagesStrings = new ArrayList<String>();
        for (Packages p : packages) {
            packagesStrings.add(p.getName());
        }

        TextButton createCharacterButton = UiManager.getInstance().createCustomButton("NOUVEAU PERSONNAGE");

        createCharacterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ArrayList<String> saveName = new ArrayList<String>();

                boolean booleen = false;

                for (JsonValue v : SaveManager.getSaves()) {
                    Debug.log(v.name());
                    if (v.name().toLowerCase().equals(field.getText().toLowerCase())) {
                        booleen = true;
                        Debug.log("True");
                        break;
                    }
                }

                Debug.log(String.valueOf(booleen));
                if (!booleen) {
                    try {
                         SaveManager.createSaves(field.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ScreenManager.getInstance().showScreen(ScreenList.PLAYER_SELECT_SCREEN);
                }
                else {
                    addActor(UiManager.getInstance().alertDialog("Erreur", "Utilisateur deja existant", getViewport()));
                }
            }
        });

        TextButton comeBackButton = UiManager.getInstance().createCustomButton("RETOUR");

        comeBackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.getInstance().createSound("assets/audio/sound/clic.mp3");
                ScreenManager.getInstance().showScreen(ScreenList.MAIN_MENU);
            }

        });


        Table table = new Table();
        table.setFillParent(true);
        //table.debugAll();
        table.defaults().pad(10);
        table.row();
        table.add(field);
        table.row();
        table.add(createCharacterButton);
        table.top();
        table.row();
        table.add(comeBackButton);
        table.center();

        addActor(table);

    }


    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
