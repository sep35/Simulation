package simulations;

import java.util.List;

/**
 * Class used in SugarScape model
 * @author minkwonlee
 *
 */
public class SugarAgent extends Unit {
    protected List<Unit> cellList;
    protected List<Unit> patchList;
    protected int framecounter = 0;
    protected int[] gridDimensions;
    protected int sugar;
    protected int sugarMetabolism;
    protected int vision;

    public static final int EMPTY = 0;

    public SugarAgent (String info, List<Unit> cells, List<Unit> patches, int[] dim) {
        cellList = cells;
        patchList = patches;
        gridDimensions = dim;

        // strip cellInfo string of information and store as instance variables
        // form: x/y/state/sugar sugarMetabolism vision
        String[] cellInfo = info.split("/");
        x = Integer.parseInt(cellInfo[0]);
        y = Integer.parseInt(cellInfo[1]);
        state = Integer.parseInt(cellInfo[2]);
        String[] parameters = cellInfo[3].split(" ");
        sugar = Integer.parseInt(parameters[0]);
        sugarMetabolism = Integer.parseInt(parameters[1]);
        vision = Integer.parseInt(parameters[2]);

    }

    @Override
    protected void updateState () {

        SugarPatch targetPatch = findBestPatch();
        x = targetPatch.x;
        y = targetPatch.y;
        sugar += targetPatch.state - sugarMetabolism;
        targetPatch.state = EMPTY;

        if (sugar <= EMPTY) {
            dies();
        }

    }

    private void dies () {
        state = Unit.DIE;

    }

    protected SugarPatch findBestPatch () {
        int highestSugarAmount = EMPTY;
        SugarPatch targetPatch = null;

        for (int i = 1; i < vision; i++) {
            System.out.println(x + " " + y);
            if (y + i < gridDimensions[1] && findPatch(x, y + i).state > highestSugarAmount &&
                    isVacant(x, y + i)) {
                highestSugarAmount = findPatch(x, y + i).state;
                targetPatch = findPatch(x, y + i);
            }
            if (x + i < gridDimensions[0] && findPatch(x + i, y).state > highestSugarAmount &&
                    isVacant(x + i, y)) {
                highestSugarAmount = findPatch(x + i, y).state;
                targetPatch = findPatch(x + i, y);
            }
            if (x - i >= 0 && findPatch(x - i, y).state > highestSugarAmount && isVacant(x - i, y)) {
                highestSugarAmount = findPatch(x - i, y).state;
                targetPatch = findPatch(x - i, y);
            }
            if (y - i >= 0 && findPatch(x, y - i).state > highestSugarAmount && isVacant(x, y - i)) {
                highestSugarAmount = findPatch(x, y - i).state;
                targetPatch = findPatch(x, y - i);
            }
        }
        return targetPatch;
    }

    protected boolean isVacant (int x, int y) {
        boolean isVacant = true;
        for (int i = 0; i < cellList.size(); i++) {
            if (cellList.get(i).x == x && cellList.get(i).y == y) {
                isVacant = false;
            }
        }
        return isVacant;
    }

    protected SugarPatch findPatch (int x, int y) {
        for (int i = 0; i < patchList.size(); i++) {
            if (patchList.get(i).x == x && patchList.get(i).y == y) { return (SugarPatch) patchList
                    .get(i); }
        }
        return null;
    }

}
