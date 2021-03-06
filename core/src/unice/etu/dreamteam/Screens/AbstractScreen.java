package unice.etu.dreamteam.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import unice.etu.dreamteam.Utils.GameInformation;

public abstract class AbstractScreen extends Stage implements Screen {

    public Boolean ready = false;

    protected AbstractScreen() {
        super(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()))); //Création de "l'écran".
    }

    protected AbstractScreen(Camera camera) {
        super(new ScreenViewport(camera));
    }

    public abstract void buildStage(); //appelé au moment où le screen est chargé.

    @Override
    public void render(float delta) { //Gère toutes les images.
        clearScreen();
        super.act(delta);
        super.draw();

    }

    protected void clearScreen(){
        Gdx.gl.glClearColor(0, 0, 0, 1); //gl: moteur 3d, fonction qui vide...
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    } //Screen affiché, attribution de tous les êvenements.

    @Override
    public void resize(int width, int height) {

        GameInformation.setViewportHeight(height);
        GameInformation.setViewportWidth(width);

        getCamera().viewportHeight = height;
        getCamera().viewportWidth = width;
        // getViewport().update(width,height);
        getCamera().update();


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
    }

}