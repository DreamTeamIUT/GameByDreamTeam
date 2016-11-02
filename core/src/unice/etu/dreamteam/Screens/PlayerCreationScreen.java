package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;
import unice.etu.dreamteam.Utils.Packages;
import unice.etu.dreamteam.Utils.SaveManager;

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


        table2 = new Table();

        final SelectBox<String> ballspeedbox = new SelectBox<String>(styleb);
        ballspeedbox.setItems(packagesStrings.toArray(new String[packagesStrings.size()]));

        ballspeedbox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                int i = ballspeedbox.getSelectedIndex();
                table2.clear();
                Debug.log("i:" + i);
                GameInformation.setPackageName(packages.get(i).getFolderName());
            }
        });


        Table table = new Table();
        table.setFillParent(true);
        //table.debugAll();
        table.defaults().pad(10);
        table.add(ballspeedbox).spaceRight(20);
        table.row();
        table.add(field);
        table.row();
        table.add(table2);
        table.top();

        addActor(table);

        ballspeedbox.fire(new ChangeListener.ChangeEvent());

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
