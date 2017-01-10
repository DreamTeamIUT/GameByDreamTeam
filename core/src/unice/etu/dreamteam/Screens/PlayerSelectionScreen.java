package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import unice.etu.dreamteam.Audio.AudioManager;
import unice.etu.dreamteam.Entities.GamePackage;
import unice.etu.dreamteam.Entities.GamePackages;
import unice.etu.dreamteam.Entities.PlayerHolder;
import unice.etu.dreamteam.JsonEntities.Saves.Save;
import unice.etu.dreamteam.JsonEntities.Saves.Saves;
import unice.etu.dreamteam.Ui.UiManager;
import unice.etu.dreamteam.Utils.*;

import java.util.ArrayList;

public class PlayerSelectionScreen extends AbstractScreen {

    private Table table2;
    private GamePackages packages;
    private SelectBox<String> userSelectionBox;
    private SelectBox<String> packagesNameBox;

    public PlayerSelectionScreen() {
        super();
    }

    @Override
    public void buildStage() {
        packages = GamePackages.getPackages();

        ArrayList<String> saveName = new ArrayList<String>();
        for (Save s : Saves.getSaves().all()) {
            saveName.add(s.getUsername());
        }

        Debug.log(saveName.toString());

        ArrayList<String> packageNames = new ArrayList<String>();
        for (GamePackage p : GamePackages.getPackages()) {
            packageNames.add(p.getDisplayName());
        }


        table2 = new Table();

        userSelectionBox = new SelectBox<String>(UiManager.getInstance().getSkin());
        packagesNameBox = new SelectBox<String>(UiManager.getInstance().getSkin());


        userSelectionBox.setItems(saveName.toArray(new String[saveName.size()]));
        packagesNameBox.setItems(packageNames.toArray(new String[packageNames.size()]));

        packagesNameBox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                updateTable();
            }
        });

        userSelectionBox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                updateTable();
            }
        });

        TextButton ReturnButton = UiManager.getInstance().createCustomButton("RETOUR");

        ReturnButton.addListener(new ClickListener() {
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
        table.add(userSelectionBox);
        table.row();
        table.add(packagesNameBox);
        table.row();
        table.add(table2);
        table.top();
        table.row();
        table.add(ReturnButton);
        table.center();
        addActor(table);

        userSelectionBox.fire(new ChangeListener.ChangeEvent());
        packagesNameBox.fire(new ChangeListener.ChangeEvent());

    }

    private void updateTable() {
        //TODO : Do not change package it create some not wanted behaviour..
        int i = packagesNameBox.getSelectedIndex();
        table2.clear();
        Debug.log("i:" + i);
        GameInformation.setGamePackage(packages.get(i));

        final String username = userSelectionBox.getSelected();
        for (final PlayerHolder player : packages.get(i).getPlayers().all()) {
            String realName = player.getRealName();
            int level = Saves.getSaves().get(username).getPackages().get(packages.get(i).getName()).getPlayers().get(player.getName()).getLevel();

            TextButton btn = UiManager.getInstance().createCustomButton(realName + " " + "(" + level + ")");
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ScreenManager.getInstance().showScreen(ScreenList.STORY_NENU, player.getName(), username);
                }
            });

            table2.add(btn);
        }

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
