package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import unice.etu.dreamteam.Entities.Sounds.Manager.AudioManager;
import unice.etu.dreamteam.Map.Story;
import unice.etu.dreamteam.Ui.UiManager;
import unice.etu.dreamteam.Utils.*;

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

        Debug.log(GameInformation.getGamePackage().getName());

        ArrayList<Story> stories = GameInformation.getGamePackage().getStories();

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10);
        for (final Story story : stories) {
            TextButton btn = UiManager.getInstance().createCustomButton(story.getName());
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    AudioManager.getInstance().createSound("assets/audio/sound/clic.mp3");
                    ScreenManager.getInstance().showScreen(ScreenList.GAME, story , GameScreen.TYPE_STORY);
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
