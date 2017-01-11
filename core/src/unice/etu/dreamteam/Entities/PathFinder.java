package unice.etu.dreamteam.Entities;

import com.badlogic.gdx.math.Vector2;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;
import unice.etu.dreamteam.Utils.Debug;

import java.util.List;

/**
 * Created by Dylan on 11/01/2017.
 */
public class PathFinder {
    public static PathFinder pathFinder;
    private AStarGridFinder<GridCell> finder;

    public PathFinder() {
        GridFinderOptions opt = new GridFinderOptions();
        opt.allowDiagonal = false;

        finder = new AStarGridFinder(GridCell.class, opt);
    }

    public static PathFinder current() {
        if(pathFinder == null)
            pathFinder = new PathFinder();

        return pathFinder;
    }

    public List<GridCell> findPath(Vector2 m, Vector2 p, NavigationGrid navigationGrid) {
        Debug.vector(m);
        Debug.vector(p);
        return finder.findPath((int) m.x, (int) m.y, (int) p.x, (int) p.y, navigationGrid);
    }
}
