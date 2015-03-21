package simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


/**
 * SuperClass defines general Grid state and behavior.
 * All other types of grids extend this SuperClass.
 *
 * @author allankiplagat
 *
 */
public abstract class Grid extends Pane {
    // handle for updating grid locations
    protected List<GridElement> gridElements;
    protected double gridWidth;
    protected double gridHeight;
    protected Random rand;

    public static final int DEFAULT_XGRIDCOUNT = 5;
    public static final int DEFAULT_YGRIDCOUNT = 5;

    // number of items within within view in x-direction and y-direction
    protected int xElementsCount = DEFAULT_XGRIDCOUNT;
    protected int yElementsCount = DEFAULT_YGRIDCOUNT;

    /**
     * deltax and deltay used by grid units to determine neighbors in this grid
     */
    public static List<int[]> NEIGHBOR_DELTAS;
    public static final int MAX_RGB = 256;
    
    /**
     * Constants used when creating some geometric figures
     */
    public static final double HALF = 0.5;
    public static final double THREE_HALVES = 1.5;
    public static final double ZERO = 0;
    public static final double TWO = 2;
    
    
    public Grid () {
        doDefaultConstructorActions();
    }

    public Grid (int xCount, int yCount) {
        xElementsCount = xCount;
        yElementsCount = yCount;
        doDefaultConstructorActions();
    }

    private void doDefaultConstructorActions () {
        gridElements = new ArrayList<GridElement>();
        gridWidth = SimulationScene.SCENE_WIDTH * SimulationScene.GRID_TO_SCENE_RATIO;
        gridHeight = SimulationScene.SCENE_HEIGHT * SimulationScene.GRID_TO_SCENE_RATIO;
        rand = new Random();
        callSetNeighborDeltas();
        setDefaultGridDimensions();
    }

    protected List<GridElement> getGridElements () {
        return gridElements;
        // return (List<GridElement>) Collections.unmodifiableCollection(gridElements);
    }

    /**
     * Method gets deltas used by cells to find their neighbors
     *
     * @return
     */
    protected List<int[]> getNeighborDeltas () {

        return NEIGHBOR_DELTAS;
        // return (List<int[]>) Collections.unmodifiableCollection(neighborDeltas);
    }

    /**
     * Subclasses call this method with relevant deltaX and deltaY
     */
    protected void setNeighborDeltas (int[] deltaX, int[] deltaY) {
        NEIGHBOR_DELTAS = new ArrayList<int[]>();
        NEIGHBOR_DELTAS.add(deltaX);
        NEIGHBOR_DELTAS.add(deltaY);
    }

    /**
     * Force subclasses to call super neighbors delta-setting method
     */
    protected abstract void callSetNeighborDeltas ();

    /**
     * Method called by each constructor to set up grid's default values
     */
    protected abstract void setDefaultGridDimensions ();

    /**
     * Method populates the grid with default place holders
     */
    protected void placeDefaultGridElements () {
        for (int i = 0; i < xElementsCount; i++) {
            for (int j = 0; j < yElementsCount; j++) {
                placeGridElement(i, j);
                getGridElement(i, j).getShape().setFill(Color.rgb(rand.nextInt(MAX_RGB),
                                                                  rand.nextInt(MAX_RGB),
                                                                  rand.nextInt(MAX_RGB)));
            }
        }
    }

    /**
     * Method gets a particular grid element from the grid elements list if it is available
     *
     * @param x
     * @param y
     * @return
     */
    protected GridElement getGridElement (int x, int y) {
        for (GridElement element : gridElements) {
            if (element.getX() == x && element.getY() == y) { return element; }
        }
        return null;
    }

    /**
     * Method places a new grid element onto the grid at the specified location
     *
     * @param newX
     * @param newY
     */
    protected void placeGridElement (int x, int y) {
        Shape shape = createShape(x, y);
        getChildren().add(shape);
        // wrap in GridElement for easy x-y access
        GridElement element = new GridElement(x, y, shape);
        gridElements.add(element);
    }

    protected void setGridElementsWhite () {
        for (int i = 0; i < xElementsCount; i++) {
            for (int j = 0; j < yElementsCount; j++) {
                getGridElement(i, j).getShape().setFill(Color.WHITE);
            }
        }
    }

    public void randomlyChangeGridElementColors () {
        for (int i = 0; i < xElementsCount; i++) {
            for (int j = 0; j < yElementsCount; j++) {
                getGridElement(i, j).getShape().setFill(Color.rgb(rand.nextInt(MAX_RGB),
                                                                  rand.nextInt(MAX_RGB),
                                                                  rand.nextInt(MAX_RGB)));
            }
        }
    }

    /**
     * Implementing classes should create appropriate shape, at appropriate location.
     * Shape returned is used by placeGridElement method
     *
     * @param x
     * @param y
     * @return
     */
    protected abstract Shape createShape (int x, int y);

}
