package simulations;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * Class used for Rectangular grid
 *
 * @author allankiplagat
 *
 */
public class RectangularGrid extends Grid {
    // dimension of rectangles
    private double recWidth;
    private double recHeight;

    public RectangularGrid () {
        super();
    }

    public RectangularGrid (int xCount, int yCount) {
        super(xCount, yCount);
    }

    @Override
    protected void setDefaultGridDimensions () {
        recWidth = gridWidth / xElementsCount;
        recHeight = gridHeight / yElementsCount;
        placeDefaultGridElements();
    }

    @Override
    protected Shape createShape (int x, int y) {
        Rectangle rectangle = new Rectangle(recWidth, recHeight);
        rectangle.setLayoutX(x * recWidth);
        rectangle.setLayoutY(y * recHeight);
        return rectangle;
    }

    @Override
    protected void callSetNeighborDeltas () {
        int[] deltaX = { -1, 0, 1, -1, 1, -1, 0, 1 };
        int[] deltaY = { -1, -1, -1, 0, 0, 1, 1, 1 };
        super.setNeighborDeltas(deltaX, deltaY);
    }

}
