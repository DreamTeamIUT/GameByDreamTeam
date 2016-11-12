package unice.etu.dreamteam.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Guillaume on 12/11/2016.
 */
public class FileUtils {
    public static void listFile(String startingPath, Boolean recursive, int maxLevel, ArrayList files) {
        listFile(startingPath, "", recursive, maxLevel, files);
    }

    public static void listFile(String startingPath, Boolean recursive, ArrayList files) {
        listFile(startingPath, "", recursive, -1, files);

    }

    public static void listFile(String startingPath, ArrayList<String> files) {
        listFile(startingPath, "", false, 1, files);

    }

    public static void listFile(String startingPath, String endFilter, Boolean recursive, ArrayList files) {
        listFile(startingPath, endFilter, recursive, -1, files);
    }

    public static void listFile(String startingPath, String endFilter, Boolean recursive, int maxLevel, ArrayList files) {
        listFile(startingPath, endFilter, recursive, maxLevel, 0, files);
    }

    private static void listFile(String startingPath, String endFilter, Boolean recursive, int maxLevel, Integer currentLevel, ArrayList files) {
        if (currentLevel != maxLevel)
            currentLevel++;
        else
            return;


        File directory = new File(startingPath);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile() && file.getName().endsWith(endFilter)) {
                    if (GameInformation.getDebugMode())
                        Debug.log(tabGen(currentLevel) + file.getName());
                    files.add(file);
                } else if (file.isDirectory() && recursive) {
                    if (GameInformation.getDebugMode())
                        Debug.log(tabGen(currentLevel) + "* " + file.getName());
                    listFile(file.getAbsolutePath(), endFilter, true, maxLevel, currentLevel, files);
                }
            }
        }

    }

    private static String tabGen(Integer currentLevel) {
        String tabs = "";
        int i;
        for (i = 0; i < currentLevel; i++)
            tabs += "\t";
        return tabs;
    }


    public static void writeToFile(String path, String content) {

    }

    public static void writeToFile(File file, String content) {

    }


}
