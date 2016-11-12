package unice.etu.dreamteam.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import unice.etu.dreamteam.Utils.Debug;

/**
 * Created by Guillaume on 12/11/2016.
 */
public class UiManager {

    private static UiManager uiManager;
    private static Skin skin;
    private static CustomButtonSkin customButtonSkin;


    public static void initialise() {
        uiManager = new UiManager();
        skin = new Skin(Gdx.files.internal("assets/ui/default/uiskin.json"));
        customButtonSkin = new CustomButtonSkin();
    }

    public void dispose() {
        Debug.log("Dispose UIElement ! ");
        skin.dispose();
        customButtonSkin.dispose();
    }


    public static UiManager getInstance() {
        if (uiManager != null)
            initialise();
        return uiManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public TextButton createCustomButton(String text) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = customButtonSkin.getDrawable("buttonOff");
        style.down = customButtonSkin.getDrawable("buttonOn");
        style.font = customButtonSkin.get("font", BitmapFont.class);

        return new TextButton(text, style);

    }

    public Dialog alertDialog(String title, String message, Viewport viewport){
        Dialog dialog = new Dialog("Erreur", skin, "dialog") {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        dialog.setSize(300,75 );
        dialog.setMovable(false);
        dialog.text("username deja utilise").setPosition(viewport.getScreenWidth()/2-dialog.getWidth()/2, viewport.getScreenHeight()/2-dialog.getHeight()/2);
        dialog.button("Ok", true); //sends "true" as the result
        dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        return dialog;
    }
}
