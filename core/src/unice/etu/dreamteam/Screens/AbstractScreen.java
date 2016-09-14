package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class AbstractScreen extends Stage implements Screen {

    protected AssetManager assetManager; //gestionnaire de ressources

    protected AbstractScreen() {
        super(new StretchViewport(320.0f, 240.0f, new OrthographicCamera())); //Création de "l'écran".
        assetManager = new AssetManager();
    }

    public abstract void buildStage(); //appelé au moment où le screen est chargé.

    @Override
    public void render(float delta) { //Gère toutes les images.
        Gdx.gl.glClearColor(0, 0, 0, 1); //gl: moteur 3d, fonction qui vide...
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.act(delta);
        super.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    } //Screen affiché, attribution de tous les êvenements.

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height);
    }//mise à jour taille.

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose(){ //vidage de mémoire
        super.dispose();
        assetManager.clear();
        assetManager.dispose();
    }

}