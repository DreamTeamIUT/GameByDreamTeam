package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import unice.etu.dreamteam.Audio.AudioManager;
import unice.etu.dreamteam.Entities.GamePackage;
import unice.etu.dreamteam.Entities.GamePackages;
import unice.etu.dreamteam.JsonEntities.Saves.Saves;
import unice.etu.dreamteam.Ui.UiManager;
import unice.etu.dreamteam.Utils.*;

import java.util.ArrayList;

public class PlayerCreationScreen extends AbstractScreen {

    private Table table2;

    public PlayerCreationScreen() {
        super();
    }

    @Override
    public void buildStage() {


        final TextField field = new TextField("aaa", UiManager.getInstance().getSkin());

        ArrayList<String> packagesStrings = new ArrayList<String>();
        for (GamePackage p : GamePackages.getPackages()) {
            packagesStrings.add(p.getDisplayName());
        }

        TextButton createCharacterButton = UiManager.getInstance().createCustomButton("NOUVEAU PERSONNAGE");

        createCharacterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                if (Saves.getSaves().get(field.getText()) == null) {
                    Saves.getSaves().initialize(field.getText());
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
