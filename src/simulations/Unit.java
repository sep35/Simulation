package simulations;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is a superclass for the all the specific simulation cell and
 * patch classes. It contains basic information of a cell, such as its state
 * and x and y location. Other than getter and setter methods, this class
 * has getNeighbors method, which finds and returns all the units around
 * this specific unit.
 *
 * @author minkwonlee
 */

public abstract class Unit {

    protected int initialState;
    protected int finalState;
    protected int state;
    protected int x, y;
    public static final double NORMALIZER = 100;

    protected abstract void updateState ();

    public static final int DIE = 5;

    public List<Unit> getNeighbors (List<Unit> unitList) {
        List<Unit> neighbors = new ArrayList<Unit>();
        int[] xDeltas = Grid.NEIGHBOR_DELTAS.get(0);
        int[] yDeltas = Grid.NEIGHBOR_DELTAS.get(1);

        for (int i = 0; i < unitList.size(); i++) {
            if (!unitList.get(i).equals(this)) {
                for (int j = 0; j < xDeltas.length; j++) {
                    if (unitList.get(i).x == (x + xDeltas[j])
                        && unitList.get(i).y == (y + yDeltas[j])) {
                        neighbors.add(unitList.get(i));
                    }
                }
            }
        }
        return neighbors;
    }

}
