package simulations;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;


/**
 * Hexagonal grid class
 *
 * @author allankiplagat
 *
 */
public class HexagonalGrid extends Grid {
    /**
     * Vertices for the hexagon edges from top, clockwise;
     * hexagon placed with vertical left and right sides
     */
    private double[] v1;
    private double[] v2;
    private double[] v3;
    private double[] v4;
    private double[] v5;
    private double[] v6;

    // distance from left vertical side to right vertical side
    private double hexWidth;
    private double vertSideLength;

    
    public HexagonalGrid () {
        super();
    }

    public HexagonalGrid (int xCount, int yCount) {
        super(xCount, yCount);
    }

    @Override
    protected void setDefaultGridDimensions () {
        hexWidth = gridWidth / xElementsCount;
        vertSideLength = gridHeight / (yElementsCount * THREE_HALVES);
        v1 = new double[] { hexWidth * HALF, ZERO };
        v2 = new double[] { hexWidth, vertSideLength * HALF};
        v3 = new double[] { hexWidth, vertSideLength * THREE_HALVES};
        v4 = new double[] { hexWidth * HALF, vertSideLength * TWO };
        v5 = new double[] { ZERO, vertSideLength * THREE_HALVES };
        v6 = new double[] { ZERO, vertSideLength * HALF };
        placeDefaultGridElements();
    }

    @Override
    protected Shape createShape (int x, int y) {
        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(new Double[] {
                                                 v1[0], v1[1], v2[0], v2[1], v3[0], v3[1],
                                                 v4[0], v4[1], v5[0], v5[1], v6[0], v6[1]
        });
        hexagon.setLayoutX(calculateLayoutX(x, y));
        hexagon.setLayoutY(y * THREE_HALVES * vertSideLength);
        return hexagon;
    }

    public double calculateLayoutX (int x, int y) {
        double offset = ZERO;
        if (y % 2 == 1) {
            offset = HALF * hexWidth;
        }
        return x * hexWidth + offset;
    }

    @Override
    protected void callSetNeighborDeltas () {
        int[] deltaX = { 0, 1, -1, 1, 0, 1 };
        int[] deltaY = { 1, 1, 0, 0, -1, -1 };
        super.setNeighborDeltas(deltaX, deltaY);
    }

}
