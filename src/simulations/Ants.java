package simulations;

import java.util.List;


/**
 * Class used in Foraging simulation
 *
 * @author stevenpierre
 *
 */
public class Ants extends Unit {
    protected boolean hasFood;
    protected int framecounter;
    protected List<Unit> patchList, neighboors;
    protected int[] Orientation = new int[3];

    public static final int MAX_ANTS = 10;

    public Ants (String info, List<Unit> cells, List<Unit> patches, int[] dim) {
        String[] cellInfo = info.split("/");
        x = Integer.parseInt(cellInfo[0]);
        y = Integer.parseInt(cellInfo[1]);
        state = Integer.parseInt(cellInfo[2]);
        patchList = patches;
        hasFood = false;
    }

    @Override
    public void updateState () {
        findOrientation();
        if (hasFood) {
            returnHome();
        }
        else {
            findFood();
        }
    }

    public void findFood () {

    }

    public void returnHome () {

    }

    public void selectLocation () {
        if (neighboors.size() > 0) {
            double max = 0;
            int index = 0;
            for (Unit ngh : neighboors) {
                AntsPatch neigh = (AntsPatch) ngh;
                double val = Math.pow((neigh.K + neigh.foodPheromone), neigh.N);
                if (val > max) {
                    max = val;
                    Orientation[3] = index;
                }
                index++;
            }
        }
    }

    public void findOrientation () {
        neighboors = getNeighbors(patchList);
        double maxFood = 0;
        double maxHome = 0;
        int index = -1;
        for (Unit ngh : neighboors) {
            AntsPatch neigh = (AntsPatch) ngh;
            if (neigh.foodPheromone > maxFood && neigh.ants < MAX_ANTS) {
                maxFood = neigh.foodPheromone;
                Orientation[0] = index;
            }
            if (neigh.homePheromone > maxHome && neigh.ants < MAX_ANTS) {
                maxHome = neigh.homePheromone;
                Orientation[1] = index;
            }
            index++;
        }
    }

}
