package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import unice.etu.dreamteam.Map.Story;
import unice.etu.dreamteam.Ui.UiManager;
import unice.etu.dreamteam.Utils.*;

import java.io.File;
import java.util.ArrayList;

public class StoryMenuScreen extends AbstractScreen {

    private final String name;
    private final String playerName;
    private Table table2;

    public StoryMenuScreen(Object[] params) {
        super();
        name = (String) params[0];
        playerName = (String) params[1];
    }

    @Override
    public void buildStage() {
        final Packages packages = new Packages(GameInformation.getPackageName());

        ArrayList<Story> stories = packages.getStories();

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10);
        for (Story story : stories) {
            TextButton btn = UiManager.getInstance().createCustomButton(story.getName());
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    AudioManager.getInstance().createSound("assets/audio/sound/clic.mp3");
                    ScreenManager.getInstance().showScreen(ScreenList.GAME);
                }

            });
            table.add(btn);
            table.row();
        }


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
