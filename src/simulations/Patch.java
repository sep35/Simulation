package simulations;

import java.util.List;


/**
 * Class for patches in simulations
 *
 * @author minkwonlee, stevenpierre, allankiplagat
 *
 */
public class Patch extends Unit {
    public static final int OCCUPIED = 1;
    public static final int VACANT = 0;

    protected Patch (String patchInfo, List<Unit> cellList, List<Unit> patchList,
                     int[] gridDimensions) {
        String[] patch = patchInfo.split("/");
        x = Integer.parseInt(patch[0]);
        y = Integer.parseInt(patch[1]);
        state = Integer.parseInt(patch[2]);
    }

    @Override
    protected void updateState () {
        // TODO Auto-generated method stub

    }

}
