package unice.etu.dreamteam.Utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Guillaume on 02/11/2016.
 */
public class SaveManager {

    public static void addJsonSaves(String path, String name, String lastSave, String folder) throws IOException {
        File file = null;
        String chaine = "";
        try {
            InputStream ips = new FileInputStream(path);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                chaine += ligne + "\n";
            }

            br.close();

            chaine = chaine.substring(0, chaine.length() - 2) + ",\n\t" + "\"" + name + "\": {" + "\n\t\t" + "\"last-save\": " + "\"" + lastSave + "\"," + "\n\t\t" + "\"folder\": " + "\"" + folder + "\"\n}\n}";

            file = new File(path);

            try {
                if (file.exists()) {
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    writer.write(chaine);
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Erreur: impossible de créer le fichier '"
                        + path + "'");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void createJson(File file) {
        String jsonContent = "{\n\t\"level\": 1\n}";
        try {
            if (!file.exists()) {
                file.createNewFile();

                FileWriter writer = new FileWriter(file);
                writer.write(jsonContent);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Error in the creation of file");
        }
    }

    public static JsonValue getSaves() {
        FileHandle file = Gdx.files.internal("assets/saves/saves.json");
        JsonValue saves = new JsonReader().parse(file.readString());
        return saves;
    }

    public static JsonValue getSave(String username) {
        FileHandle file = Gdx.files.internal("assets/saves/saves.json");
        JsonValue save = new JsonReader().parse(file.readString()).get(username);
        return save;
    }

    public static JsonValue getInfoSave(String username, String playerId) {
        JsonValue info = getSave(username);

        String folderName = info.getString("folder", "");

        FileHandle file = Gdx.files.internal("assets/saves/" + folderName + "/" + playerId + "_" + GameInformation.getPackageName() + ".json");
        return new JsonReader().parse(file.readString());
    }

    public static void createSaves(String username) throws IOException {
        String pathFile = "assets/saves/" + username.toUpperCase();
        File file = null;

        try {
            file = new File(pathFile);
            file.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Packages p : Packages.getPackages()) {
            for (JsonValue player : p.getPlayers().all()) {

                //TODO : Use JSON !!
                file = new File(pathFile + "/" + player.getString("name") + "_" + p.getFolderName() + ".json");
                createJson(file);
            }
        }

        String fichier = "assets/saves/saves.json";
        Date d = new Date(file.lastModified());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println(dateFormat.format(d));
        SaveManager.addJsonSaves(fichier, username.toLowerCase(), String.valueOf(dateFormat.format(d)), username.toUpperCase());

    }

    //lecture du fichier texte
        /*try{
            InputStream ips=new FileInputStream(fichier);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String ligne;
            while ((ligne=br.readLine())!=null){
                System.out.println(ligne);
                chaine+=ligne+"\n";
            }
            br.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }*/

    public static void info() {

    }

    public static void load() {

    }
}
