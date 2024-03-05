package PEGGAME2;

import java.util.Collection;

// PegGame interface for any Peg Game
public interface PegGame {
    Collection<Move> getPossibleMoves();

    GameState getGameState();

    void makeMove(Move move) throws PegGameException;

    Collection<Location> getPegLocations();
}
