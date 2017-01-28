package unice.etu.dreamteam.Map;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by Dylan on 28/01/2017.
 */
public class Assets extends AssetManager {
    private static Assets assets;

    public Assets() {
        super();
    }

    public static Assets getInstance() {
        if(assets == null)
            assets = new Assets();

        return assets;
    }

    public <E> E getResource(String fileName, Class<E> tClass) {
        if(!isLoaded(fileName)) {
            load(fileName, tClass);
            finishLoading();
        }

        return get(fileName, tClass);
    }
}
