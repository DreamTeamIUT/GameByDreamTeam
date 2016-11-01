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
import unice.etu.dreamteam.Exceptions.ParameterException;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.GameInformation;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class ModelAnimationManager implements Disposable {
    private ArrayList<ModelInstance> instances;
    private ArrayList<AnimationController> controllers;
    private ArrayList<Integer> currentInstance;
    private ArrayList<String> callList;
    private ArrayList<AnimationController.AnimationDesc> animations;
    private AssetManager assetManager;
    private String modelName;

    public ModelAnimationManager(CharacterList character) {
        init(character.getName());
    }


    public ModelAnimationManager(String modelName) {
        init(modelName);
    }

    private void init(String modelName) {
        this.modelName = modelName;
        assetManager = new AssetManager();
        FileHandle file = Gdx.files.internal("assets/" + GameInformation.getPackageName() + "/models/" + modelName + "/info.json");
        JsonValue dat = new JsonReader().parse(file.readString());

        controllers = new ArrayList<AnimationController>();
        animations = new ArrayList<AnimationController.AnimationDesc>();
        callList = new ArrayList<String>();
        instances = new ArrayList<ModelInstance>();
        currentInstance = new ArrayList<Integer>();

        for (JsonValue v : dat.get("textures").iterator()) {

            if (v.has("load")) {
                if (v.getBoolean("load")) {
                    assetManager.load(getFilePath(v.getString("file")), Model.class);
                    assetManager.finishLoading();
                    callList.add(v.name());
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
        return "assets/" + GameInformation.getPackageName() + "/models/" + modelName + "/" + modelName + "@" + file + ".g3db";
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
        try {
            animations.get(getAnimationPos(animation)).listener = new EmptyAnimationListener();
            animations.get(getAnimationPos(animation)).speed = 1f;
            animations.get(getAnimationPos(animation)).loopCount = -1;
            if (currentInstance.size() == 0)
                currentInstance.add(getAnimationPos(animation));
            else
                currentInstance.set(0, getAnimationPos(animation));
            clearArray(currentInstance);
        } catch (ParameterException e) {
            e.printStackTrace();
        }

    }

    public ModelAnimationManager setAnimation(String animation, int repeat) {
        try {
            currentInstance.add(getAnimationPos(animation));
            animations.get(getAnimationPos(animation)).loopCount = repeat;
            animations.get(getAnimationPos(animation)).listener = new ChainedAnimationListener();
        } catch (ParameterException e) {
            e.printStackTrace();
        }

        return this;
    }

    private int getAnimationPos(String animation) throws ParameterException {
        if (callList.contains(animation))
            return callList.indexOf(animation);
        else
            throw new ParameterException("Animation name not found : " + animation);
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
