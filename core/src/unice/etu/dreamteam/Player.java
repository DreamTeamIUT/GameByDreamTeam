package unice.etu.dreamteam;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Guillaume on 22/10/2016.
 */
public class Player {
    private Vector2 curentCells;

    public Player() {
        curentCells = new Vector2(0, 0);
    }

    public void moveToLeft() {
        curentCells.x--;
    }

    public void moveToRight() {
        curentCells.x++;
    }

    public void moveToUp() {
        curentCells.y++;
    }

    public void moveToDown() {
        curentCells.y--;
    }

    public Vector2 getCurentCells() {
        return curentCells;
    }


    public void setCell(int x, int y) {
        curentCells.x = x;
        curentCells.y = y;
    }
}
