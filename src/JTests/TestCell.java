package JTests;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import simulations.LifeCell;
import simulations.Unit;


public class TestCell {
    @Test
    public void testSomething () {
        String info = "0/1/3";
        List<Unit> cells = new ArrayList<Unit>();
        List<Unit> patches = new ArrayList<Unit>();
        int[] dimensions = { 1, 2 };
        new LifeCell(info, cells, patches, dimensions);

        // assertEquals(3,unit.getState());
    }
}
