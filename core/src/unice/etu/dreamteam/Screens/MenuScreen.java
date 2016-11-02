package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import unice.etu.dreamteam.Utils.Debug;

/**
 * Created by Dylan on 31/10/2016.
 */
public class MenuScreen extends AbstractScreen {
    private final TextButton.TextButtonStyle textButtonStyle;

    public MenuScreen() {
        Debug.log("Menu screen");

        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("assets/ui/button/button.pack"));

        Skin skin = new Skin();
        skin.addRegions(buttonAtlas);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonOn");
        textButtonStyle.down = skin.getDrawable("buttonOff");

        textButtonStyle.font = new BitmapFont(Gdx.files.internal("assets/ui/button/new.fnt"), false);
    }

    @Override
    public void buildStage() {
        Debug.log("Menu screen2");

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();

        //TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        //style.up = new TextureRegionDrawable(upRegion);
        //style.down = new TextureRegionDrawable(downRegion);
        //style.font = buttonFont;

        //Create buttons
        //TextButton playButton = new TextButton("START", textButtonStyle);

        //playButton.setHeight(Gdx.graphics.getHeight()/3);
        //playButton.setWidth(Gdx.graphics.getWidth()/4);

        //playButton.setPosition();
        /*
        TextButton optionsButton = new TextButton("Options", textButtonStyle);
        TextButton exitButton = new TextButton("Exit", textButtonStyle);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Debug.log("Play button");
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Debug.log("Exit button");
            }
        });
        */

        //Add buttons to table
        //mainTable.add(playButton);
        //mainTable.row();
        //mainTable.add(optionsButton);
        //mainTable.row();
        //mainTable.add(exitButton);

        //this.addActor(playButton);

        TextureAtlas buttonsAtlas = new TextureAtlas("assets/ui/button/button.pack"); //**button atlas image **//
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        BitmapFont font = new BitmapFont(Gdx.files.internal("assets/ui/button/default.fnt"), false); //** font **//

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("buttonOff");
        style.down = buttonSkin.getDrawable("buttonOn");

        style.font = font;

        TextButton playButton = new TextButton("JOUER", style);
        TextButton exitButton = new TextButton("QUITTER", style);
        //** Button text and style **//
        playButton.setHeight(75); //** Button Height **//
        playButton.setWidth(110); //** Button Width **//

        //button.setPosition(Gdx.graphics.getWidth()/2-button.getWidth()/2, Gdx.graphics.getHeight());

        //mainTable.defaults().width(115).height(75);
        //mainTable.add(playButton);
        //mainTable.row();
        //mainTable.add(exitButton);



        //MoveToAction moveAction = new MoveToAction();//Add dynamic movement effects to button
        //moveAction.setPosition(50, 50);
        //moveAction.setDuration(.5f);
        //playButton.addAction(moveAction);

        this.addActor(playButton);
    }

    @Override
    public void render(float delta) {
        super.render(delta); //Fait appel à la fonction de abstractScreen
        //map.render();
        //Fait appel à la fonction render de map.
    } //fonction appellé toutes les frames, pour actualiser l'affichage.

    @Override
    public void dispose() {
        super.dispose();
        //Ici on vide !
    }
}

