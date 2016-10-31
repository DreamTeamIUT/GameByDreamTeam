package unice.etu.dreamteam.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.data.ModelAnimation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.*;
import unice.etu.dreamteam.Utils.Debug;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class ModelAnimationManager implements Disposable {
    private final CharacterList character;
    private ArrayList<ModelInstance> instances;
    private ArrayList<AnimationController> controllers;
    private ArrayList<Integer> currentInstance;
    private ArrayList<String> callList;
    private ArrayList<AnimationController.AnimationDesc> animations;
    private final AssetManager assetManager;

    public ModelAnimationManager(CharacterList character) {
        this.character = character;

        assetManager = new AssetManager();
        FileHandle file = Gdx.files.internal("assets/models/" + character.getName() + "/info.json");
        JsonValue dat = new JsonReader().parse(file.readString());

        controllers = new ArrayList<AnimationController>();
        animations = new ArrayList<AnimationController.AnimationDesc>();
        callList = new ArrayList<String>();
        instances = new ArrayList<ModelInstance>();
        currentInstance = new ArrayList<Integer>();

        for (JsonValue v : dat.get("animation").iterator()) {

            if (v.has("load")) {
                if (v.getBoolean("load")) {
                    assetManager.load(getFilePath(v.getString("file")), Model.class);
                    assetManager.finishLoading();
                    callList.add(v.getString("call_name"));
                    instances.add(new ModelInstance(assetManager.get(getFilePath(v.getString("file")), Model.class)));
                    controllers.add(new AnimationController(instances.get(instances.size() - 1)));
                    animations.add(controllers.get(controllers.size() - 1).setAnimation(v.getString("name"), -1));
                }
            }
        }

        Debug.log(callList.toString());
        setAnimation(callList.get(0));
    }

    private String getFilePath(String file) {
        return "assets/models/" + character.getName() + "/" + character.getName() + "@" + file + ".g3db";
    }


    public ModelInstance getModelInstance() {
        return instances.get(currentInstance.get(0));
    }

    public AnimationController getAnimationController() {
        return controllers.get(currentInstance.get(0));
    }

    public AnimationController.AnimationDesc getAnimation() {
        return animations.get(currentInstance.get(0));
    }

    public void setAnimation(String animation) {
        animations.get(getAnimationPos(animation)).listener = new EmptyAnimationListener();
        animations.get(getAnimationPos(animation)).speed = 1f;
        animations.get(getAnimationPos(animation)).loopCount = -1;
        if (currentInstance.size() == 0)
            currentInstance.add(getAnimationPos(animation));
        else
            currentInstance.set(0, getAnimationPos(animation));
        clearArray(currentInstance);
    }

    public ModelAnimationManager setAnimation(String animation, int repeat) {
        currentInstance.add(getAnimationPos(animation));
        animations.get(getAnimationPos(animation)).loopCount = repeat;
        animations.get(getAnimationPos(animation)).listener = new ChainedAnimationListener();
        return this;
    }

    private int getAnimationPos(String animation) {
        if (callList.contains(animation))
            return callList.indexOf(animation);
        else
            throw new InvalidParameterException("Animation name not found : " + animation);
    }

    public String getAnimationsList() {
        return callList.toString();
    }

    public void setAnimationScale(float x, float y, float z) {
        for (ModelInstance instance : instances) {
            instance.transform.scale(x, y, z);
        }
    }

    public void setAnimationRotation(float angle) {
        for (ModelInstance instance : instances) {
            instance.transform.rotate(Vector3.Y, angle);
        }
    }

    @Override
    public void dispose() {

        assetManager.dispose();
    }

    private void clearArray(ArrayList<Integer> arrayList) {
        while (arrayList.size() > 1) {
            arrayList.remove(arrayList.size() - 1);
        }
    }

    private class EmptyAnimationListener implements AnimationController.AnimationListener {

        @Override
        public void onEnd(AnimationController.AnimationDesc animation) {

        }

        @Override
        public void onLoop(AnimationController.AnimationDesc animation) {

        }
    }

    private class ChainedAnimationListener implements AnimationController.AnimationListener {

        @Override
        public void onEnd(AnimationController.AnimationDesc animation) {
            Debug.log("End Animation : " + callList.get(currentInstance.get(0)));
            if (currentInstance.size() > 1)
                currentInstance.remove(0);
            else
                animations.get(currentInstance.get(0)).speed = 0;
        }

        @Override
        public void onLoop(AnimationController.AnimationDesc animation) {

        }
    }
}
