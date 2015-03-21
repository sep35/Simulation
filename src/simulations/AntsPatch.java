package simulations;

import java.util.List;


/**
 * Class used in Foraging simulation
 *
 * @author stevenpierre
 *
 */
public class AntsPatch extends Unit {
    protected double homePheromone, foodPheromone, evaporation, diffusion, minPhero, maxPhero, K,
    N;
    protected List<Unit> patches;
    protected int ants;

    protected AntsPatch (String patchInfo,
                         List<Unit> cellList,
                         List<Unit> patchList,
                         int[] gridDimensions) {
        String[] patch = patchInfo.split("/");
        x = Integer.parseInt(patch[0]);
        y = Integer.parseInt(patch[1]);
        state = Integer.parseInt(patch[2]);
        String[] parameter = patch[3].split(" ");
        evaporation = Integer.parseInt(parameter[0]) / Unit.NORMALIZER;
        diffusion = Integer.parseInt(parameter[1]) / Unit.NORMALIZER;
        K = Integer.parseInt(parameter[2]) / Unit.NORMALIZER;
        N = Integer.parseInt(parameter[3]) / Unit.NORMALIZER;
        minPhero = Integer.parseInt(parameter[4]) / Unit.NORMALIZER;
        maxPhero = Integer.parseInt(parameter[5]) / Unit.NORMALIZER;
        patches = patchList;
        foodPheromone = state;
        ants = 0;
    }

    @Override
    protected void updateState () {
        // TODO Auto-generated method stub
        List<Unit> neighboors = getNeighbors(patches);
        for (Unit ngh : neighboors) {
            ((AntsPatch) ngh).foodPheromone += (diffusion * foodPheromone);
        }
    }

}
