package simulations;

import javafx.scene.shape.Shape;

/**
 * Class encloses a shape object and makes it easy to assign & retrieve the
 * shape on a Grid using x and y coordinates
 * Also makes it easy to place other shapes on top of the current shape
 *
 * @author allankiplagat
 *
 */
public class GridElement {
    private Shape shape;
    private int x;
    private int y;

    public GridElement (int newX, int newY, Shape newShape) {
        shape = newShape;
        x = newX;
        y = newY;
    }

    public Shape getShape () {
        return shape;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

}
