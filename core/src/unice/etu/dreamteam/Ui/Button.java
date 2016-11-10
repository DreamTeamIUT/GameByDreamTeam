package unice.etu.dreamteam.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Guillaume on 02/11/2016.
 */
public class Button extends TextButton {
    public Button(String title) {
        super(title, TextureUI.BUTTON.getStyle());
    }



}


/* UIElement
        - createButton -> TextButton
        - createProgressBar


 */