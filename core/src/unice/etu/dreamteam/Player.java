package unice.etu.dreamteam;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Guillaume on 22/10/2016.
 */
public class Player {
    private Vector2 curentCells;
    private Rectangle playerZone;

    public Player() {
        curentCells = new Vector2(0, 0);
        playerZone = new Rectangle();
        updatePlayerZone();
        playerZone.width = 32;
        playerZone.height = 32;
    }

    private void updatePlayerZone() {
        playerZone.x = 32 * getCurentCells().x;
        playerZone.y = 32 * getCurentCells().y;
    }

    public void moveToLeft() {
        curentCells.x--;
        updatePlayerZone();
    }

    public void moveToRight() {
        curentCells.x++;
        updatePlayerZone();
    }

    public void moveToUp() {
        curentCells.y++;
        updatePlayerZone();
    }

    public void moveToDown() {
        curentCells.y--;
        updatePlayerZone();
    }

    public Vector2 getCurentCells() {
        return curentCells;
    }


    public void setCell(int x, int y) {
        curentCells.x = x;
        curentCells.y = y;
    }


    public Rectangle getRectangle() {
        return playerZone;
    }
}
