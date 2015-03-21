package simulations;

import java.util.List;

/**
 * Class used in SugarScape model
 * @author minkwonlee
 *
 */
public class SugarPatch extends Unit {
    protected List<Unit> cellList;
    protected List<Unit> patchList;
    protected int sugarCapacity;
    protected int sugarGrowBackRate;
    protected int sugarGrowBackInterval;
    protected int framecounter = 0;

    public SugarPatch (String info, List<Unit> cells, List<Unit> patches, int[] gridDimensions) {

        cellList = cells;
        patchList = patches;

        // [IMPORTANT]
        // form: x/y/state/sugarCapacity sugarGrowBackRate sugarGrowBackInterval
        String[] cellInfo = info.split("/");
        x = Integer.parseInt(cellInfo[0]);
        y = Integer.parseInt(cellInfo[1]);
        state = Integer.parseInt(cellInfo[2]);
        String[] parameters = cellInfo[3].split(" ");
        sugarCapacity = Integer.parseInt(parameters[3]);
        sugarGrowBackRate = Integer.parseInt(parameters[4]);
        sugarGrowBackInterval = Integer.parseInt(parameters[5]);
    }

    @Override
    protected void updateState () {
        if (framecounter > 0 && framecounter % sugarGrowBackInterval == 0 && state < sugarCapacity) {
            state += sugarGrowBackRate;
        }
        framecounter++;
    }

    protected int getSugarCapacity () {
        return sugarCapacity;
    }

    protected void seSugarCapacity (int x) {
        sugarCapacity = x;
    }

}
