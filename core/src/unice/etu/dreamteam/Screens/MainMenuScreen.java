package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import unice.etu.dreamteam.Ui.Button;

public class MainMenuScreen extends AbstractScreen {

    private TextureAtlas buttonsAtlas;
    private BitmapFont font;

    public MainMenuScreen(){
        super();
    }

    @Override
    public void buildStage() {
        //Button b = new Button("Truc !");
        //addActor(b);

        buttonsAtlas = new TextureAtlas("assets/ui/button/button.pack"); //**button atlas image **//
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        font = new BitmapFont(Gdx.files.internal("assets/ui/button/default.fnt"), false); //** font **//

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("buttonOff");
        style.down = buttonSkin.getDrawable("buttonOn");

        style.font = font;

        TextButton playButton = new TextButton("JOUER", style);
        TextButton exitButton = new TextButton("QUITTER", style);
        TextButton creditsButton = new TextButton("CREDITS", style);
        //** Button text and style **//
        //playButton.setHeight(75); //** Button Height **//
        // playButton.setWidth(300); //** Button Width **//

        //button.setPosition(Gdx.graphics.getWidth()/2-button.getWidth()/2, Gdx.graphics.getHeight());

        Table table = new Table();
        table.setFillParent(true);
        //table.debugAll();
        table.defaults().pad(10);
        table.add(playButton);
        table.row();
        table.add(creditsButton);
        table.row();
        table.add(exitButton);
        table.center();
        //mainTable.defaults().width(115).height(75);
        //mainTable.add(playButton);
        //mainTable.row();
        //mainTable.add(exitButton);


        //MoveToAction moveAction = new MoveToAction();//Add dynamic movement effects to button
        //moveAction.setPosition(50, 50);
        //moveAction.setDuration(.5f);
        //playButton.addAction(moveAction);

        this.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }


    @Override
    public void dispose() { //Appelle le dispose de abstract, stage ...
        super.dispose();
        buttonsAtlas.dispose();
        font.dispose();
    }
}
//tous les screens du jeu => ce format