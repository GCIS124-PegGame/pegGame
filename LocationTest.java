package PEGGAME2;

import org.junit.Test;
import junit.framework.Assert;
public class LocationTest {

    private int row;
    private int col;


    @Test
    public void testGetCol() {

        Location location = new Location(row, col); // Assuming Location constructor takes col and row as arguments
        Assert.assertEquals(col, location.getCol());

    }

    @Test
    public void testGetRow() {

        Location location = new Location(row, col); // Assuming Location constructor takes col and row as arguments
        Assert.assertEquals(row, location.getRow());

    

    }
}