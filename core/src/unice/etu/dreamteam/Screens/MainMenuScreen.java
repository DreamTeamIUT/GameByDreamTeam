package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import unice.etu.dreamteam.Ui.UiManager;
import unice.etu.dreamteam.Entities.Sounds.Manager.AudioManager;
import unice.etu.dreamteam.Utils.ScreenList;
import unice.etu.dreamteam.Utils.ScreenManager;
import unice.etu.dreamteam.Ui.Settings;

public class MainMenuScreen extends AbstractScreen {

    private SpriteBatch batch;
    private Sprite backgroundSprite;
    private Texture backgroundTexture;


    public MainMenuScreen() {
        super();
    }

    @Override
    public void buildStage() {

        TextButton newGameButton = UiManager.getInstance().createCustomButton("NOUVELLE PARTIE");
        TextButton replayButton = UiManager.getInstance().createCustomButton("CONTINUER");
        TextButton exitButton = UiManager.getInstance().createCustomButton("QUITTER");
        TextButton settingsButton = UiManager.getInstance().createCustomButton("PARAMETRES");


        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenList.PLAYER_CREATION_SCREEN);
                AudioManager.getInstance().createSound("assets/audio/sound/clic.mp3");
            }

        });

        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenList.PLAYER_SELECT_SCREEN);
                AudioManager.getInstance().createSound("assets/audio/sound/clic.mp3");
            }

        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.getInstance().createSound("assets/audio/sound/clic.mp3");
                Gdx.app.exit();
            }

        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.getInstance().createSound("assets/audio/sound/clic.mp3");
                addActor(Settings.createWindow(getViewport()));
            }

        });

        Table table = new Table();
        table.setFillParent(true);
        //table.debugAll();
        table.defaults().pad(10);
        table.add(newGameButton);
        table.row();
        table.add(replayButton);
        table.center();
        table.row();
        table.add(settingsButton);
        table.row();
        table.add(exitButton);
        //mainTable.defaults().width(115).height(75);
        //mainTable.add(playButton);
        //mainTable.row();
        //mainTable.add(exitButton);

        //MoveToAction moveAction = new MoveToAction();//Add dynamic movement effects to button
        //moveAction.setPosition(50, 50);
        //moveAction.setDuration(.5f);
        //playButton.addAction(moveAction);


        addActor(table);

        AudioManager.getInstance().createMusic("assets/audio/music/Music_log_intro.mp3");

        batch = new SpriteBatch();
        backgroundTexture = new Texture("assets/ui/images/wallpaper0.jpg");
        backgroundSprite = new Sprite(backgroundTexture);
    }


    @Override
    public void render(float delta) {
        super.clearScreen();
        super.act(delta);
        batch.begin();
        batch.draw(backgroundSprite, 0, 0);
        batch.end();
        super.draw();
    }


    @Override
    public void dispose() { //Appelle le dispose de abstract, stage ...
        super.dispose();
        backgroundTexture.dispose();
    }

}
//tous les screens du jeu => ce format