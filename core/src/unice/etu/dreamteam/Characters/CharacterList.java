package unice.etu.dreamteam.Characters;

/**
 * Created by Guillaume on 18/09/2016.
 */
public enum CharacterList {
    DEAMON {
        @Override
        public String getName() {
            return "Demon";
        }
    },
    KNIGHT {
        @Override
        public String getName() {
            return "Knight";
        }
    };

    public abstract String getName();

}
