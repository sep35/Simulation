package simulations;

import java.util.List;


/**
 * Class used in Game of Life simulation
 *
 * @author minkwonlee
 *
 */
public class LifeCell extends Unit {
    private List<Unit> cellList;
    protected List<Unit> patchList;
    protected int[] gridDimensions;
    protected boolean changeState;
    protected int framecounter = 0;
    protected boolean changingState = false;
    protected boolean updatePhase = false;
    public static final int ALIVE = 1;
    public static final int DEAD = 0;

    public LifeCell (String info, List<Unit> cells, List<Unit> patches, int dim[]) {

        cellList = cells;
        patchList = patches;
        gridDimensions = dim;
        String[] cellInfo = info.split("/");
        x = Integer.parseInt(cellInfo[0]);
        y = Integer.parseInt(cellInfo[1]);
        // dead(0) or alive(1)
        state = Integer.parseInt(cellInfo[2]);
    }

    @Override
    protected void updateState () {
        if (framecounter > 0 && updatePhase) {
            changeState();
        }
        else {

            List<Unit> neighborList = getNeighbors(cellList);
            int numAlive = 0;
            for (int i = 0; i < neighborList.size(); i++) {
                if (neighborList.get(i).state == ALIVE) {
                    numAlive++;
                }
            }

            if (state == ALIVE) {
                changeState = (numAlive < 2 || numAlive > 3);

            }
            else if (state == DEAD) {
                changeState = (numAlive == 3);

            }
            updatePhase = true;
            framecounter++;
        }
    }

    protected void changeState () {
        if (changeState == true) {
            if (state == ALIVE) {
                state = DEAD;
            }
            else {
                state = ALIVE;
            }
        }

    }
}
