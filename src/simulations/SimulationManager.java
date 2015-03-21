package simulations;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


/**
 * Class manages the back-end simulation components. Defines code that runs in
 * each simulation frame
 *
 * @author allankiplagat, stevenpierre, minkwonlee
 *
 */
public class SimulationManager {
    // frame duration in milliseconds (for adjusting simulation speed)
    public static final double FRAME_DURATION = 1000;
    private SimulationScene cellScene;
    private List<Unit> cellList = new ArrayList<Unit>();
    private List<Unit> patchList = new ArrayList<Unit>();
    private int[] gridDimensions;
    private UnitFactory unitFactory;

    public SimulationManager (SimulationScene cScene) {
        cellScene = cScene;
        unitFactory = new UnitFactory();
    }

    protected void setGridDimensions (int[] dim) {
        gridDimensions = dim;
    }

    /**
     * Method creates all of the cells in the simulation
     *
     * @param cellType
     *        String of Unit type in use
     * @param cellsInfo
     *        List of unit parameters for constructing units
     */
    public void createCells (String cellType, List<String> cellsInfo) {
        for (String cellInfo : cellsInfo) {
            cellList.add(unitFactory.createUnit(cellType, cellInfo, cellList, patchList,
                                                gridDimensions));
        }
    }

    /**
     * Method creates all of the patches in the simulation
     *
     * @param patchType
     *        String of Unit type in use
     * @param patchesInfo
     *        List of unit parameters for constructing units
     */
    public void createPatches (String patchType, List<String> patchesInfo) {
        for (String patchInfo : patchesInfo) {
            patchList.add(unitFactory.createUnit(patchType, patchInfo, cellList, patchList,
                                                 gridDimensions));
        }
        cellScene.updateGridPane(cellList, patchList);
    }

    public void clearCellList () {
        cellList.clear();
    }

    public void clearPatchList () {
        patchList.clear();
    }

    /**
     * EventHandler instance defines what happens in each frame
     */
    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle (ActionEvent actionEvent) {
            // update cell states
            List<Unit> cells = new ArrayList<Unit>();
            for (Unit cell : cellList) {
                cells.add(cell);
            }

            for (Unit cell : cells) {
                cell.updateState();
                if (cell.state == Unit.DIE) {
                    cellList.remove(cell);
                }
            }

            cellScene.updateGridPane(cellList, patchList);
        }
    };

    /**
     * Method called by CellScene
     *
     * @return Returns KeyFrame
     */
    public KeyFrame initializeGrid () {
        return new KeyFrame(Duration.millis(FRAME_DURATION), oneFrame);
    }

}
