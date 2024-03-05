package PEGGAME2;

import java.util.Collection;
import java.util.Scanner;
import java.util.ArrayList;

public class PegGameImpl implements PegGame {
    private GameState gameState;
    private Collection<Location> pegLocations;

    public PegGameImpl() {
        initializeBoard();
        gameState = GameState.NOT_STARTED;
    }

    // Initialize board with a collection of locations
    public PegGameImpl(Collection<Location> initialPegLocations) {
        pegLocations = new ArrayList<>(initialPegLocations);
        gameState = GameState.NOT_STARTED;
    }


    private void initializeBoard() {
        pegLocations = new ArrayList<>();
        int rows = 4;
        int cols = 4;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (i != rows || j != cols) {
                    pegLocations.add(new Location(i, j));
                }
            }
        }
    }

    @Override
    public Collection<Move> getPossibleMoves() {
        Collection<Move> possibleMoves = new ArrayList<>();

        for (Location from : pegLocations) {
            for (Location to : pegLocations) {
                if (isValidMove(from, to)) {
                    possibleMoves.add(new Move(from, to));
                }
            }
        }
        return possibleMoves;
    }

    private boolean isValidMove(Location from, Location to) {
        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());

        return rowDiff == 2 && colDiff == 0 || rowDiff == 0 && colDiff == 2;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void makeMove(Move move) throws PegGameException {
        Location from = move.getFrom();
        Location to = move.getTo();

        if (!isValidMove(from, to)) {
            throw new PegGameException("Invalid move. Pegs can only move by jumping over one peg.");
        }

        if (!pegLocations.contains(from) || !pegLocations.contains(to)) {
            throw new PegGameException("Invalid move. Pegs must be on the board.");
        }

        // Remove the peg that is jumped over
        Location middle = new Location((from.getRow() + to.getRow()) / 2, (from.getCol() + to.getCol()) / 2);
        if (!pegLocations.contains(middle)) {
            throw new PegGameException("Invalid move. No peg to jump over.");
        }

        pegLocations.remove(from);
        pegLocations.remove(middle);
        pegLocations.add(to);

        if (pegLocations.size() == 1 && pegLocations.contains(new Location(4, 4))) {
            gameState = GameState.WON;
        } else if (getPossibleMoves().isEmpty()) {
            gameState = GameState.STALEMATE;
        } else {
            gameState = GameState.IN_PROGRESS;
        }
    }

    @Override
    public Collection<Location> getPegLocations() {
        return pegLocations;
    }

    private static void printInstructions() {
        System.out.println("Hello User! Welcome to the Peg Game.");
        System.out.println("Rules of the Peg Game:");
        System.out.println("> Board Setup:");
        System.out.print("  - The game is typically played on a board with holes across the board, traditionally in a square.\n");
        System.out.println("> Peg Placement:");
        System.out.print("  - At the beginning of the game, all holes on the board are filled with pegs except for one hole, usually at the center of the board.\n");
        System.out.println("> Peg Movement:");
        System.out.print("  - Pegs can only move by jumping over other pegs, similar to checkers.\n");
        System.out.print("  - A peg can jump over an adjacent peg horizontally or vertically to land in an empty hole, provided there is an empty hole immediately on the other side of the peg being jumped over.\n");
        System.out.print("  - The jumped-over peg is removed from the board.\n");
        System.out.println("> Objective:");
        System.out.print("  - The goal of the game is to remove as many pegs as possible by jumping over them until no more moves are possible.\n");
        System.out.print("  - The ideal outcome is to end the game with only one peg left on the board, preferably in the center hole.\n");
        System.out.println("> Valid Moves:");
        System.out.print("  - A peg cannot jump over another peg diagonally.\n");
        System.out.print("  - A peg cannot move to an adjacent hole if it is not directly in line with the peg and an empty hole.\n");
        System.out.println("> End of the Game:");
        System.out.print("  - The game ends when no more valid moves are possible.\n");
        System.out.print("  - If there is only one peg left on the board, the player wins. However, if more than one peg remains, the player loses.\n");
        System.out.println("Good Luck, Player! Below is a dummy board to assist you with your moves:");
    }

    private static void printBoard(Collection<Location> pegLocations, int rows, int cols) {
        // Create a 2D char array to represent the board
        char[][] board = new char[rows][cols];
    
        // Initialize the board with holes represented by '.'
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = 'o'; // Assume all cells initially have pegs
            }
        }
    
        // Set the last cell as a hole
        board[rows - 1][cols - 1] = '.';
    
        // Print the board
        System.out.println("Current Board:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isValidMove(int r1, int c1, int r2, int c2, Collection<Location> pegLocations) {
        // Pegs can only move horizontally or vertically
        if (!(r1 == r2 || c1 == c2)) {
            return false;
        }

        // Pegs must jump over another peg
        if (Math.abs(r1 - r2) == 2 && Math.abs(c1 - c2) == 0) {
            int middleRow = (r1 + r2) / 2;
            if (!pegLocations.contains(new Location(middleRow, c1))) {
                return false;
            }
        } else if (Math.abs(c1 - c2) == 2 && Math.abs(r1 - r2) == 0) {
            int middleCol = (c1 + c2) / 2;
            if (!pegLocations.contains(new Location(r1, middleCol))) {
                return false;
            }
        } else {
            return false;
        }

        // Destination must be a hole
        Location destination = new Location(r2, c2);
        return !pegLocations.contains(destination);
    }

    public void playingTheGame() throws PegGameException {
        int rows = 4; // Define the number of rows in your game board
        int cols = 4; // Define the number of columns in your game board
        FileHandler fileHandler = new FileHandlerImpl(); // Create an instance of FileHandler

        try (Scanner scanner = new Scanner(System.in)) {
            // Ask the user for the file name and create the file with game instructions and dummy board
            String fileName = fileHandler.askUserForFileNameAndAddContent();

            // If the file creation was successful, proceed with the game
            if (fileName != null) {
                Collection<Location> initialPegLocations = fileHandler.readFromFile(fileName); // Read peg locations from file
                PegGameImpl game = new PegGameImpl(initialPegLocations); // Initialize the game with initial peg locations

                printInstructions();
                printBoard(game.getPegLocations(), rows, cols); // Print the initial board

                while (true) {
                    System.out.println("Enter the row and column of the peg you want to choose:");
                    
                    int r1 = scanner.nextInt();
                    int c1 = scanner.nextInt();
                
                    System.out.println("Enter the row and column where you want to move the peg:");
                    int r2 = scanner.nextInt();
                    int c2 = scanner.nextInt();
                
                    // Validate the move
                    if (isValidMove(r1, c1, r2, c2, game.getPegLocations())) {
                        Location from = new Location(r1, c1);
                        Location to = new Location(r2, c2);
                        Move move = new Move(from, to); // Create a move object
                        game.makeMove(move); // Make the move
                        printBoard(game.getPegLocations(), rows, cols); // Print the updated board
                    } else {
                        System.out.println("Invalid move: Pegs can only move horizontally or vertically and must jump over another peg.");
                    }
                }                                           
            }
        }
    }
}
