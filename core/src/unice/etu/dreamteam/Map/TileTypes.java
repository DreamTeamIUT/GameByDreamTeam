package unice.etu.dreamteam.Map;

/**
 * Created by Guillaume on 02/01/2017.
 */
public enum TileTypes {
    //Add some other type if needed !
    GATE;

    public static boolean contain(TileTypes type){
        for (TileTypes t : TileTypes.values())
        {
            if (t.equals(type))
                return true;
        }
        return false;
    }

    public static boolean contain(String type){
        for (TileTypes t : TileTypes.values())
        {
            if (t.name().equals(type))
                return true;
        }
        return false;
    }
}
