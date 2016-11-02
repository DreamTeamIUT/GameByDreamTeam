package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Characters.Player;
import unice.etu.dreamteam.Map.Story;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;
import unice.etu.dreamteam.Utils.Packages;
import unice.etu.dreamteam.Utils.SaveManager;

import java.util.ArrayList;

public class PlayerSelectionScreen extends AbstractScreen {

    private Table table2;
    private ArrayList<Packages> packages;
    private TextButton.TextButtonStyle style;
    private SelectBox<String> userSelectionBox;
    private SelectBox<String> packagesNameBox;

    public PlayerSelectionScreen() {
        super();
    }

    @Override
    public void buildStage() {
        packages = Packages.getPackages();

        final Skin skin = new Skin(Gdx.files.internal("assets/ui/default/uiskin.json"));

        TextureAtlas buttonsAtlas = new TextureAtlas("assets/ui/button/button.pack"); //**button atlas image **//
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        BitmapFont font = new BitmapFont(Gdx.files.internal("assets/ui/button/default.fnt"), false); //** font **//

        style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("buttonOff");
        style.down = buttonSkin.getDrawable("buttonOn");

        style.font = font;


        final SelectBox.SelectBoxStyle styleb = new SelectBox.SelectBoxStyle(skin.get(SelectBox.SelectBoxStyle.class));

        ArrayList<String> saveName = new ArrayList<String>();
        for (JsonValue v : SaveManager.getSaves()) {
            saveName.add(v.name());
        }

        Debug.log(saveName.toString());

        ArrayList<String> packageNames = new ArrayList<String>();
        for (Packages p : Packages.getPackages()) {
            packageNames.add(p.getName());
        }


        table2 = new Table();

        userSelectionBox = new SelectBox<String>(styleb);
        packagesNameBox = new SelectBox<String>(styleb);

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

        addActor(table);

        userSelectionBox.fire(new ChangeListener.ChangeEvent());
        packagesNameBox.fire(new ChangeListener.ChangeEvent());

    }

    private void updateTable() {
        int i = packagesNameBox.getSelectedIndex();
        table2.clear();
        Debug.log("i:" + i);
        GameInformation.setPackageName(packages.get(i).getFolderName());

        String username = userSelectionBox.getSelected();
        for (JsonValue v : packages.get(i).getPlayers().all()) {
            String realName = v.getString("real-name");
            String level = SaveManager.getInfoSave(username, v.getString("name")).getString("level");
            TextButton btn = new TextButton(realName + " " + "(" + level + ")", style);
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
