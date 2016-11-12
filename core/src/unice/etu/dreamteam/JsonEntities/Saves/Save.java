package unice.etu.dreamteam.JsonEntities.Saves;

import com.badlogic.gdx.utils.ObjectMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Guillaume on 12/11/2016.
 */
public class Save {
    private String username;
    private String lastSave;
    private ObjectMap<String, PackageSave> packages;


    public Save() {
    }

    public Save(String username) {
        this.username = username;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.lastSave = dateFormat.format(date);
    }

    {
        packages = new ObjectMap<>();
    }

    public void addPlayerSave(String packageName, String playerName, PlayerSave save) {
        PackageSave temp = getPackages().get(packageName);
        if (temp == null) {
            getPackages().put(packageName, new PackageSave());
            temp = getPackages().get(packageName);
        }
        temp.addPlayerSave(playerName, save);
    }

    public String getUsername() {
        return username;
    }

    public String getLastSave() {
        return lastSave;
    }

    public ObjectMap<String, PackageSave> getPackages() {
        return packages;
    }
}
