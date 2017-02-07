package unice.etu.dreamteam.Entities.Characters.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Map.Assets;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

import java.util.ArrayList;

/**
 * Created by Guillaume on 07/02/2017.
 */
public class TwoDimensionalModel implements Model, Disposable {

    private final String modelName;
    private final ArrayList<String> callList;
    private final ArrayList<TwoDimensionalData> modelData;
    private final float framerate;
    private Animation currentAnimation;
    private TwoDimensionalData currentDataSet;
    private float time = 0;
    private float angle = 0;

    public TwoDimensionalModel(String modelName) {

        this.callList = new ArrayList<>();
        this.modelData = new ArrayList<>();

        this.modelName = modelName;
        FileHandle file = Gdx.files.internal(GameInformation.getGamePackage().getPackagePath() + "/models/" + modelName + "/info.json");
        JsonValue data = new JsonReader().parse(file.readString());

        this.framerate = 1/data.getFloat("framerate", 30);

        Debug.log("MODEL_2D", "frameRate :" + this.framerate);

        for (JsonValue v : data.get("textures").iterator()) {
            if (v.has("load")) {
                if (v.getBoolean("load", false)) {
                    callList.add(v.name());
                    TwoDimensionalData twoDimensionalData = new TwoDimensionalData();
                    twoDimensionalData.setFrameRate(framerate);

                    String vZ = v.get("Z").getString("atlas");
                    String vS = v.get("S").getString("atlas");
                    String vQ = v.get("Q").getString("atlas");
                    String vD = v.get("D").getString("atlas");

                    Debug.log("MODEL_2D", " anim " + v.name() + " " + vZ + " " + vS + " " + vD + " " + vQ);

                    twoDimensionalData.setzAtlas(Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath() + "/models/" + modelName + "/" + vZ, TextureAtlas.class));
                    twoDimensionalData.setsAtlas(Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath() + "/models/" + modelName + "/" + vS, TextureAtlas.class));
                    twoDimensionalData.setqAtlas(Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath() + "/models/" + modelName + "/" + vQ, TextureAtlas.class));
                    twoDimensionalData.setdAtlas(Assets.getInstance().getResource(GameInformation.getGamePackage().getPackagePath() + "/models/" + modelName + "/" + vD, TextureAtlas.class));

                    modelData.add(twoDimensionalData);

                }
            }
        }
    }

    @Override
    public void setAnimation(String name) {
        if (callList.contains(name)) {
            int pos = callList.indexOf(name);
            this.currentDataSet = this.modelData.get(pos);
            this.setRotation(this.angle);
        }
    }


    @Override
    public void resize(float width, float height) {

    }

    @Override
    public void update(float delta) {
        this.time +=  Gdx.graphics.getDeltaTime();;
    }

    @Override
    public void setRotation(float angle) {

        this.angle = angle;

        if (angle == 0) {
            this.currentAnimation = this.currentDataSet.getAnimationS();
        } else if (angle == 90) {
            this.currentAnimation = this.currentDataSet.getAnimationD();
        } else if (angle == 180) {
            this.currentAnimation = this.currentDataSet.getAnimationZ();
        } else if (angle == 270) {
            this.currentAnimation = this.currentDataSet.getAnimationQ();
        }
    }

    @Override
    public TextureRegion getFrame() {
        return (TextureRegion) this.currentAnimation.getKeyFrame(this.time, true);
    }

    @Override
    public void dispose() {
        for (TwoDimensionalData d : modelData) {
            d.dispose();
        }
    }

}
