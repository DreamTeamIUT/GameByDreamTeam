package unice.etu.dreamteam.Map;

import com.badlogic.gdx.assets.AssetManager;
import unice.etu.dreamteam.Utils.Debug;
import unice.etu.dreamteam.Utils.ScreenList;

import java.util.ArrayList;

/**
 * Created by Dylan on 28/01/2017.
 */
public class Assets extends AssetManager {
    private static Assets assets;
    private ArrayList<AssetParameters> assetParametersArrayList;

    public Assets() {
        super();

        assetParametersArrayList = new ArrayList<>();
    }

    public static Assets getInstance() {
        if(assets == null)
            assets = new Assets();

        return assets;
    }

    public <E> E getResource(String fileName, Class<E> tClass) {
        if(!isLoaded(fileName)) {
            Debug.log("ASSETS", fileName+ " not loaded");
            load(fileName, tClass);
            finishLoading();
        }

        Debug.log("ASSETS", fileName+ " already loaded");


        return get(fileName, tClass);
    }

    public <E> E getResource(String fileName, Class<E> tClass, AssetParameters assetParameters) {
        assetParameters.setFileName(fileName);
        assetParametersArrayList.add(assetParameters);

        return getResource(fileName, tClass);
    }

    public void clearForScreen(ScreenList screenList) {
        for (AssetParameters assetParameters : assetParametersArrayList) {
            if (assetParameters.getScreenList() == screenList) {
                unload(assetParameters.getFileName());
                assetParametersArrayList.remove(assetParameters);
            }
        }
    }

    public AssetParameters setParameters() {
        return new AssetParameters();
    }

    private class AssetParameters {
        private String fileName;
        private ScreenList screenList;

        public ScreenList getScreenList() {
            return screenList;
        }

        public void forScreen(ScreenList screenList) {
            this.screenList = screenList;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }
}
