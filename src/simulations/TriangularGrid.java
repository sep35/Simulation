package simulations;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * Class used in Triangular grid simulation
 * @author allankiplagat
 *
 */
public class TriangularGrid extends Grid {
    private double triBase;
    private double triHeight;
    // coordinates of triangle at top left grid location (upright)
    // used as reference for all other triangles
    private double[] lBaseCoord;
    private double[] rBaseCoord;
    private double[] topCoord;

    public TriangularGrid () {
        super();
    }

    public TriangularGrid (int xCount, int yCount) {
        super(xCount, yCount);
    }

    @Override
    public void setDefaultGridDimensions () {
        // constant adjustments to triBase to have all triangles visible
        triBase = (TWO * gridWidth) / (xElementsCount + 1);
        triHeight = gridHeight / yElementsCount;
        lBaseCoord = new double[] { ZERO, triHeight };
        rBaseCoord = new double[] { triBase, triHeight };
        topCoord = new double[] { triBase * HALF, ZERO };
        placeDefaultGridElements();
    }

    @Override
    public Shape createShape (int x, int y) {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(calculateCoordinates(x, y));
        triangle.setLayoutX(x * triBase * HALF);
        triangle.setLayoutY(y * triHeight);
        return triangle;
    }

    /**
     * Method returns either the coordinates of an upright or inverted triangle
     * based on the x and y entry
     *
     * @param x
     * @param y
     * @return
     */
    public Double[] calculateCoordinates (int x, int y) {
        // upright triangle
        double[] lBase = new double[] { lBaseCoord[0], lBaseCoord[1] };
        double[] rBase = new double[] { rBaseCoord[0], rBaseCoord[1] };
        double[] top = new double[] { topCoord[0], topCoord[1] };
        // inverted triangle
        if (((x + y) % 2) == 1) {
            lBase[1] = lBase[1] - triHeight;
            rBase[1] = rBase[1] - triHeight;
            top[1] = top[1] + triHeight;
            System.out.println("Flipped\n");
        }

        return new Double[] { lBase[0], lBase[1], rBase[0], rBase[1], top[0], top[1] };
    }

    @Override
    protected void callSetNeighborDeltas () {
        int[] deltaX = { -1, 0, 1, -1, 1, -1, 0, 1 };
        int[] deltaY = { -1, -1, -1, 0, 0, 1, 1, 1 };
        super.setNeighborDeltas(deltaX, deltaY);
    }
}
