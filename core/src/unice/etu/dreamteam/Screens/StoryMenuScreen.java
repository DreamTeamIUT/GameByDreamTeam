package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gargoylesoftware.htmlunit.javascript.host.Event;
import unice.etu.dreamteam.Map.Story;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;
import unice.etu.dreamteam.Utils.Packages;

import java.io.File;
import java.util.ArrayList;

public class StoryMenuScreen extends AbstractScreen {

    private Table table2;

    public StoryMenuScreen() {
        super();
    }

    @Override
    public void buildStage() {
        final ArrayList<Packages> packages = Packages.getPackages();

        Skin skin = new Skin(Gdx.files.internal("assets/ui/default/uiskin.json"));

        TextureAtlas buttonsAtlas = new TextureAtlas("assets/ui/button/button.pack"); //**button atlas image **//
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        BitmapFont font = new BitmapFont(Gdx.files.internal("assets/ui/button/default.fnt"), false); //** font **//

        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("buttonOff");
        style.down = buttonSkin.getDrawable("buttonOn");

        style.font = font;


        SelectBox.SelectBoxStyle styleb = new SelectBox.SelectBoxStyle(skin.get(SelectBox.SelectBoxStyle.class));

        ArrayList<String> packagesStrings = new ArrayList<String>();
        for (Packages p : packages) {
            packagesStrings.add(p.getName());
        }

        table2 = new Table();

        final SelectBox<String> ballspeedbox = new SelectBox<String>(styleb);
        ballspeedbox.setItems(packagesStrings.toArray(new String[packagesStrings.size()]));

        ballspeedbox.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                int i = ballspeedbox.getSelectedIndex();
                table2.clear();
                Debug.log("i:" + i);
                GameInformation.setPackageName(packages.get(i).getFolderName());
                for (Story s : packages.get(i).getStories())
                    table2.add(new TextButton(s.getName(), style));

            }
        });


        Table table = new Table();
        table.setFillParent(true);
        //table.debugAll();
        table.defaults().pad(10);
        table.add(ballspeedbox);
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
