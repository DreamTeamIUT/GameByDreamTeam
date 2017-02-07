package unice.etu.dreamteam.Entities.Characters.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import unice.etu.dreamteam.Utils.GameInformation;

/**
 * Created by Guillaume on 07/02/2017.
 */
public class ModelLoader {
    public static Model loadModel(String modelName) {
        FileHandle file = Gdx.files.internal(GameInformation.getGamePackage().getPackagePath() + "/models/" + modelName + "/info.json");
        JsonValue dat = new JsonReader().parse(file.readString());
        if (dat.getString("type").equals("3D"))
            return new TriDimensionalModel(modelName);
        else if (dat.getString("type").equals("2D"))
            return new TwoDimensionalModel(modelName);
        return null;
    }
}
