package peggame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

public class MoveTest {

    private Location from;
    private Location to;
    @Test
    public void testGetFrom() {

        Move move = new Move(to, from); // Assuming Location constructor takes col and row as arguments
        Assert.assertEquals(from, move.getFrom());

    }

    @Test
    public void testGetPossibleMoves() {

        Location from = new Location(0, 0);
        Location to = new Location(0, 0);

         // Create a new Move object and set the possible moves
        Move move = new Move(to, from);

        // Test that the possible moves are correctly retrieved
        Location expectedLocation1 = new Location(0, 0);
        Location expectedLocation2 = new Location(0, 1);
        List<Location> expectedMoves = Arrays.asList(expectedLocation1, expectedLocation2);
        
        // Test that the possible moves are correctly retrieved
        assertEquals(expectedMoves, Move.getPossibleMoves(null));

    }

    @Test
    public void testGetTo() {

        Move move = new Move(to, from); // Assuming Location constructor takes col and row as arguments
        Assert.assertEquals(to, move.getTo());

    }

    @Test
    public void testMakeMove() throws PegGameException {

        PegGame game = new PegGameImpl();
        Location from = new Location(0, 0);
        Location to = new Location(1, 1);
        Move move = new Move(to, from);

        // Make the move
        game.makeMove(move);

        // Verify that the move was made correctly
        Assert.assertFalse(game.getPegLocations().contains(from));
        Assert.assertTrue(game.getPegLocations().contains(to));
        Assert.assertEquals(GameState.IN_PROGRESS, game.getGameState());

    }
}