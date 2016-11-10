package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.*;

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

        final Skin skin = new Skin(Gdx.files.internal("assets/ui/default/uiskin.json"));

        TextureAtlas buttonsAtlas = new TextureAtlas("assets/ui/button/button.pack"); //**button atlas image **//
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        BitmapFont font = new BitmapFont(Gdx.files.internal("assets/ui/button/default.fnt"), false); //** font **//

        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("buttonOff");
        style.down = buttonSkin.getDrawable("buttonOn");

        style.font = font;


        final TextField field = new TextField("aaa", skin.get(TextField.TextFieldStyle.class));

        SelectBox.SelectBoxStyle styleb = new SelectBox.SelectBoxStyle(skin.get(SelectBox.SelectBoxStyle.class));

        ArrayList<String> packagesStrings = new ArrayList<String>();
        for (Packages p : packages) {
            packagesStrings.add(p.getName());
        }

        TextButton createCharacterButton = new TextButton("NOUVEAU PERSONNAGE", style);

        createCharacterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    SaveManager.createSaves(field.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ScreenManager.getInstance().showScreen(ScreenList.PLAYER_SELECT_SCREEN);
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
