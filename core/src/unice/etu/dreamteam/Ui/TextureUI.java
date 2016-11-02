package unice.etu.dreamteam.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Guillaume on 02/11/2016.
 */
public enum TextureUI {
    BUTTON {
        public TextButton.TextButtonStyle getStyle() {
            TextureAtlas buttonsAtlas = new TextureAtlas("assets/ui/button/button.pack"); //**button atlas image **//
            Skin buttonSkin = new Skin();
            buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
            BitmapFont font = new BitmapFont(Gdx.files.internal("assets/ui/button/default.fnt"), false); //** font **//

            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
            style.up = buttonSkin.getDrawable("buttonOff");
            style.down = buttonSkin.getDrawable("buttonOn");

            style.font = font;

            buttonsAtlas.dispose();
            font.dispose();

            return style;
        }

    };

    public abstract TextButton.TextButtonStyle getStyle();
}
