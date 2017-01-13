package unice.etu.dreamteam.Saves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import unice.etu.dreamteam.Entities.GamesPackages.GamePackage;
import unice.etu.dreamteam.Entities.Characters.Players.PlayerHolder;
import unice.etu.dreamteam.Entities.GamesPackages.GamePackages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Guillaume on 12/11/2016.
 */
public class Saves {

    private static final String DEFAULT_PATH = "assets/saves/saves.json";
    private static Saves currentSaves;
    private ArrayList<Save> saves;

    public Saves() {
        saves = new ArrayList<>();
    }

    public Save get(int i) {
        return saves.get(i);
    }

    public Save get(String username) {
        for (Save tempSave : saves) {
            if (tempSave.getUsername().equals(username))
                return tempSave;
        }

        return null;
    }

    public PlayerSave get(String username, String packageName, String playerName) {
        for (Save save : saves) {
            if(save.getUsername().equals(username))
                return save.getPlayerSave(packageName, playerName);
        }

        return null;
    }

    public static Saves getSaves() {
        if (currentSaves == null)
        {
            FileHandle file = Gdx.files.internal("assets/saves/saves.json");
            currentSaves = new Json().fromJson(Saves.class, file.readString());
        }
        return currentSaves;
    }

    public ArrayList<Save> all(){
        return saves;
    }

    public void initialize(String username){
        Save save = new Save(username);
        for (GamePackage p : GamePackages.getPackages()) {
            for (PlayerHolder player : p.getPlayers().all()) {
                PlayerSave playerSave = new PlayerSave();
                playerSave.setDefaults();

                save.addPlayerSave(p.getName(), player.getName(), playerSave);

            }
        }
        addSave(save);
        write();
    }

    public void addSave(Save save){
        saves.add(save);
    }

    public void write(){
        File f = new File(DEFAULT_PATH);
        try {
            PrintWriter pw = new PrintWriter(f);
            pw.print(new Json().prettyPrint(this));
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
