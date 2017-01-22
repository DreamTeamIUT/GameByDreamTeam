package unice.etu.dreamteam.Entities.Characters.Mobs.Graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonValue;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import unice.etu.dreamteam.Entities.Characters.Mobs.MobHolder;
import unice.etu.dreamteam.Map.PathFinder;
import unice.etu.dreamteam.Entities.Characters.Graphics.Character;
import unice.etu.dreamteam.Entities.Characters.Graphics.CharacterMove;
import unice.etu.dreamteam.Entities.Characters.Players.Graphics.Player;
import unice.etu.dreamteam.Utils.Debug;

import java.util.List;

/**
 * Created by Dylan on 01/10/2016.
 */
public class Mob extends Character {
    private JsonValue informations;
    private List<GridCell> gridCells;
    private int currentCell;
    private Boolean ready;

    private int proximityRange = 2;
    private int detectionRange = 10;

    public Mob(MobHolder holder) {
        super(holder);
        //this.forces = holder.getForces();

        ready = false;
        speed = 0.05f;
    }

    public void setForce() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(currentMove == CharacterMove.NONE && ready) {
            if(!detectionRange() || proximityRange())
                return;

            if(currentCell >= gridCells.size())
                return;

            GridCell gridCell = gridCells.get(currentCell);

            if(cellPos.x == gridCell.getX() && cellPos.y == gridCell.getY())
                return;

            Debug.log(gridCell.toString());

            if(cellPos.x > gridCell.getX())
                moveTo(CharacterMove.LEFT);
            else if(cellPos.x < gridCell.getX())
                moveTo(CharacterMove.RIGHT);
            else if(cellPos.y > gridCell.getY())
                moveTo(CharacterMove.DOWN);
            else if(cellPos.y < gridCell.getY())
                moveTo(CharacterMove.UP);
            else
                return;

            currentCell++;
        }
    }

    private boolean proximityRange() {
        return (gridCells.size() - currentCell) <= proximityRange;
    }

    private boolean detectionRange() {
        return gridCells.size() <= detectionRange;
    }

    public void setPathToPlayer(NavigationGrid navigationGrid, Player p) {
        gridCells = PathFinder.current().findPath(this.cellPos, p.getCellPos(), navigationGrid);
        currentCell = 0;

        ready = true;

        Debug.log("------------------ find path ----------");
        Debug.log(Debug.iteratorToString(gridCells.iterator()));

    }

    @Override
    protected void drawDebug() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0.5f, 1);
        shapeRenderer.rect(getRectangle().x - 16, getRectangle().y + 16, getRectangle().getWidth(), getRectangle().getHeight());
        shapeRenderer.setColor(0.5f, 0, 1f, 1);
        shapeRenderer.rect(getRectangle().x - 16 - 2 * 16, getRectangle().y + 16 + 2 * 16, getRectangle().getWidth(), getRectangle().getHeight());
        shapeRenderer.end();
    }
}
