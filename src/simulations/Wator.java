package simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Class used in Predator-Prey model
 *
 * @author minkwonlee
 *
 */
public class Wator extends Unit {
    protected List<Unit> cellList;
    protected List<Unit> patchList;
    protected int framecounter;
    protected int[] gridDimensions;
    private int reproduction;
    private boolean canMove;
    private boolean canReproduce;
    private int initialEnergy;
    private int initialReproduction;
    private int energy;

    private Random rand;
    public static final int FISH = 0;
    public static final int SHARK = 1;

    public Wator (String info, List<Unit> cells, List<Unit> patches, int[] dim) {
        cellList = cells;
        framecounter = 0;
        patchList = patches;
        gridDimensions = dim;
        rand = new Random();
        String[] cellInfo = info.split(XMLFileReader.FIELD_SEPARATOR);
        x = Integer.parseInt(cellInfo[0]);
        y = Integer.parseInt(cellInfo[1]);
        // 0 for fish and 1 for shark
        state = Integer.parseInt(cellInfo[2]);
        String[] parameters = cellInfo[3].split(" ");
        energy = Integer.parseInt(parameters[0]);
        reproduction = Integer.parseInt(parameters[1]);
        initialEnergy = energy;
        initialReproduction = reproduction;

    }

    @Override
    protected void updateState () {
        updateLocation();
        if (state == FISH) {
            canMove = (getEmptyNeighbors().size() > 0);
        }
        else if (state == SHARK) {
            canMove = (getFishNeighbors().size() > 0);
            if (!canMove) {
                canMove = (getEmptyNeighbors().size() > 0);
            }
            energy--;
            if (energy == 0) {
                dies();
            }
        }
        if (reproduction <= 0 && canMove) {
            canReproduce = true;
        }
        reproduction--;
        framecounter++;
    }

    private void updateLocation () {
        int initialX = x;
        int initialY = y;
        if (framecounter > 0 && canMove) {
            if (state == SHARK && (getFishNeighbors().size() > 0)) { // shark
                // eating a
                // fish
                List<Unit> fishNeighbors = getFishNeighbors();
                int r = rand.nextInt(fishNeighbors.size());
                int tempX = fishNeighbors.get(r).x;
                int tempY = fishNeighbors.get(r).y;
                // dies
                fishNeighbors.get(r).state = DIE;
                x = tempX;
                y = tempY;
                energy = initialEnergy;
            }
            else if (state == SHARK || state == FISH) { // when there's empty
                // neighbors around a
                // shark
                List<Unit> posLocations = getEmptyNeighbors();
                if (posLocations.size() > 0) {
                    int r = rand.nextInt(posLocations.size());
                    x = posLocations.get(r).x;
                    y = posLocations.get(r).y;

                    if (canReproduce && state == FISH) {
                        String info = initialX + XMLFileReader.FIELD_SEPARATOR + initialY + XMLFileReader.FIELD_SEPARATOR + 0 + XMLFileReader.FIELD_SEPARATOR + initialEnergy
                                      + " " + initialReproduction;
                        cellList.add(new Wator(info, cellList, patchList, gridDimensions));
                        reproduction = initialReproduction;
                    }
                }
                if (state == SHARK && canReproduce) {
                    String info = initialX + XMLFileReader.FIELD_SEPARATOR + initialY + XMLFileReader.FIELD_SEPARATOR + 1 + XMLFileReader.FIELD_SEPARATOR + initialEnergy + " "
                                  + initialReproduction;
                    cellList.add(new Wator(info, cellList, patchList, gridDimensions));
                    reproduction = initialReproduction;

                }
            }
        }
    }

    private void dies () {
        state = DIE;

    }

    private List<Unit> getFishNeighbors () {
        List<Unit> fishNeighbors = new ArrayList<Unit>();
        for (int i = 0; i < cellList.size(); i++) {
            if (cellList.get(i).state == FISH) {
                fishNeighbors.add(cellList.get(i));
            }
        }
        fishNeighbors = getNeighbors(fishNeighbors);
        return fishNeighbors;
    }

    // returns a list of patch on which there is no fish or shark
    private List<Unit> getEmptyNeighbors () {
        List<Unit> emptyNeighbors = new ArrayList<Unit>();
        List<Unit> patchNeighbors = getNeighbors(patchList);
        boolean isEmpty = true;
        for (int i = 0; i < patchNeighbors.size(); i++) {
            isEmpty = true;
            for (int j = 0; j < cellList.size(); j++) {
                if (patchNeighbors.get(i).x == cellList.get(j).x
                    && patchNeighbors.get(i).y == cellList.get(j).y) {
                    isEmpty = false;
                }
            }
            if (isEmpty) {
                emptyNeighbors.add(patchNeighbors.get(i));
            }
        }
        return emptyNeighbors;
    }
}
