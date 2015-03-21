package simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Class used in Foraging simulation
 *
 * @author stevenpierre
 *
 */
public class Fire extends Unit {

    protected List<Unit> cellList;
    protected List<Unit> patchList;
    protected int framecounter = 0;
    private double threshold;
    private int agentType;
    private Random rand = new Random();
    public static final int ASH = 0;
    public static final int TREE = 1;
    public static final int FIRE = 2;

    public Fire (String info, List<Unit> cells, List<Unit> patches, int[] dim) {
        cellList = cells;
        patchList = patches;
        // strip cellInfo string of information and store as instance variables
        String[] cellInfo = info.split("/");
        x = Integer.parseInt(cellInfo[0]);
        y = Integer.parseInt(cellInfo[1]);
        agentType = Integer.parseInt(cellInfo[2]);
        String[] parameters = cellInfo[3].split(" ");
        threshold = Integer.parseInt(parameters[0]) / Unit.NORMALIZER;
        state = agentType;
        initialState = agentType;
    }

    protected int getAgentType () {
        return agentType;
    }

    protected List<Unit> findNeighbors (List<Unit> cells) {
        List<Unit> allNeighboors = super.getNeighbors(cells);
        List<Unit> neighboors = new ArrayList<Unit>();
        for (Unit nghbr : allNeighboors) {
            if (nghbr.x == x + 1 && nghbr.y == y) {
                neighboors.add(nghbr);
            }
            if (nghbr.x == x - 1 && nghbr.y == y) {
                neighboors.add(nghbr);
            }
            if (nghbr.x == x && nghbr.y == y + 1) {
                neighboors.add(nghbr);
            }
            if (nghbr.x == x && nghbr.y == y - 1) {
                neighboors.add(nghbr);
            }
        }
        return neighboors;
    }

    protected boolean onFire (List<Unit> neighbors) {
        boolean check = false;
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).initialState == FIRE) {
                check = true;
            }
        }
        return check;
    }

    @Override
    protected void updateState () {
        initialState = agentType;
        // get list of all agent neighbors
        List<Unit> ngh = findNeighbors(cellList);
        // Generate random number between 0 and 1
        double rando = rand.nextDouble();
        if (agentType == TREE && rando < threshold && onFire(ngh)) {
            agentType = FIRE;
        }
        else if (framecounter > 0 && agentType == FIRE) {
            agentType = ASH;
        }
        framecounter++;
    }
}
