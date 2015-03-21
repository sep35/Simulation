package simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Class used in Segregation model
 *
 * @author minkwonlee, stevenpierre
 *
 */
public class Agent extends Unit {
    protected List<Unit> cellList;
    protected List<Unit> patchList;
    protected int framecounter = 0;
    protected int[] gridDimensions;
    private double threshold;
    private int agentType;
    private Random rand;
    public static final int SATISFIED = 1;
    public static final int DISSATISFIED = 0;

    public Agent (String info, List<Unit> cells, List<Unit> patches, int[] dim) {

        cellList = cells;
        patchList = patches;
        gridDimensions = dim;
        // 1=satisfied, 0=dissatisfied
        initialState = SATISFIED;
        // 1=satisfied; 0=dissatisfied
        state = SATISFIED;
        // random object for selecting which spot to move to
        rand = new Random();

        // strip cellInfo string of information and store as instance variables
        String[] cellInfo = info.split("/");
        x = Integer.parseInt(cellInfo[0]);
        y = Integer.parseInt(cellInfo[1]);
        agentType = Integer.parseInt(cellInfo[2]);
        String[] parameter = cellInfo[3].split(" ");
        threshold = Integer.parseInt(parameter[0]) / NORMALIZER;
    }

    protected int getAgentType () {
        return agentType;
    }

    @Override
    protected void updateState () {
        if (agentType <= 1) {
            updateLocation();
            // get list of all agent neighbors
            List<Unit> neighborList = getNeighbors(cellList);
            // count # of similar agents
            double numSame = 0;
            // counts all the neighbors with the agentType
            for (int i = 0; i < neighborList.size(); i++) {
                if (((Agent) neighborList.get(i)).getAgentType() == getAgentType()) {
                    numSame++;
                }
            }
            if (numSame / neighborList.size() < threshold) {
                // dissatisfied, set state
                state = DISSATISFIED;
            }
            else {
                // satisfied, set state
                state = SATISFIED;
            }
            framecounter++;
        }
    }

    // move agent to random empty location
    protected void updateLocation () {
        if (state == DISSATISFIED && framecounter > 0) {
            ArrayList<int[]> possibleLocations = new ArrayList<int[]>();
            boolean emptyspot = true;
            for (Unit patch : patchList) {
                if (patch.state == Patch.VACANT) {
                    int[] location = { patch.x, patch.y };
                    possibleLocations.add(location);
                }
            }
            // move to random empty location if available
            if (!possibleLocations.isEmpty()) {
                updateStates(emptyspot);
                int r = rand.nextInt(possibleLocations.size());
                int[] loc = possibleLocations.get(r);
                x = loc[0];
                y = loc[1];
                updateStates(!emptyspot);
            }
        }
    }

    protected void updateStates (boolean empty) {
        for (Unit patch : patchList) {
            Patch patchs = (Patch) patch;
            if (patchs.x == x && patchs.y == y) {
                if (empty) {
                    patch.state = Patch.VACANT;
                }
                else {
                    patch.state = Patch.OCCUPIED;
                }
            }
        }
        return;
    }
}
