package unice.etu.dreamteam.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Guillaume on 12/11/2016.
 */
public class CustomButtonSkin extends Skin{
    private final TextureAtlas buttonsAtlas;
    private final BitmapFont font;

    public CustomButtonSkin(){
        super();
        font = new BitmapFont(Gdx.files.internal("assets/ui/button/default.fnt"), false); //** font **//
        buttonsAtlas = new TextureAtlas("assets/ui/button/button.pack"); //**button atlas image **//
        addRegions(buttonsAtlas); //** skins for on and off **//
        add("font", font);
    }

    public void dispose(){
        buttonsAtlas.dispose();
        font.dispose();
        super.dispose();

    }
}
