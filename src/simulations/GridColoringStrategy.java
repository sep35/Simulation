package simulations;

import javafx.scene.paint.Color;

/**
 * Class adjusts Grid element colors according to unit state
 * @author allankiplagat
 *
 */
//Implement better strategy later
public class GridColoringStrategy {

    public void setGridElementColor(Unit unit, Grid grid) {
        if (unit instanceof Agent) {
            if (((Agent) unit).getAgentType() == Agent.DISSATISFIED) {
                fill(grid,unit,(Color.BLACK));
            }
            else if (((Agent) unit).getAgentType() == Agent.SATISFIED) {
                fill(grid,unit,(Color.YELLOW));
            }
        }
        else if (unit instanceof LifeCell) {
            if (((LifeCell) unit).state == LifeCell.DEAD) {
                fill(grid,unit,(Color.BEIGE));
            }
            else if (((LifeCell) unit).state == LifeCell.ALIVE) {
                fill(grid,unit,(Color.BLACK));
            }
        }
        else if (unit instanceof Wator) {
            if (((Wator) unit).state == Wator.FISH) {
                fill(grid,unit,(Color.BLUE));
            }
            else if (((Wator) unit).state == Wator.SHARK) {
                fill(grid,unit,(Color.RED));
            }
        }
        else if (unit instanceof Fire) {
            if (((Fire) unit).getAgentType() == Fire.FIRE) {
                fill(grid,unit,Color.RED);
            }
            else if (((Fire) unit).getAgentType() == Fire.TREE) {
                fill(grid,unit,(Color.YELLOW));
            }
            else if (((Fire) unit).getAgentType() == Fire.ASH) {
                fill(grid,unit,(Color.BLACK));
            }
        }
    }
    
    private void fill(Grid grid, Unit unit,Color c) {
        grid.getGridElement(unit.x, unit.y).getShape().setFill(c);
    }
}
