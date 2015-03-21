package simulations;

import java.util.List;


/**
 * Class generates Unit objects
 *
 * @author allankiplagat
 *
 */
public class UnitFactory {

    public Unit createUnit (String unitType,
                            String unitInfo,
                            List<Unit> cellList,
                            List<Unit> patchList,
                            int[] gridDimensions) {
        switch (unitType) {
            case "Agent":
                return new Agent(unitInfo, cellList, patchList, gridDimensions);
            case "LifeCell":
                return new LifeCell(unitInfo, cellList, patchList, gridDimensions);
            case "Wator":
                return new Wator(unitInfo, cellList, patchList, gridDimensions);
            case "Fire":
                return new Fire(unitInfo, cellList, patchList, gridDimensions);
            case "Patch":
                return new Patch(unitInfo, cellList, patchList, gridDimensions);
            case "SugarAgent":
                return new SugarAgent(unitInfo, cellList, patchList, gridDimensions);
            case "SugarPatch":
                return new SugarPatch(unitInfo, cellList, patchList, gridDimensions);
        }
        return null;
    }
}
