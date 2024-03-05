package PEGGAME2;

public class Move {
    private Location from;
    private Location to;

    public Move(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    public void makeMove(char[][] board, int rows, int cols, int r1, int c1, int r2, int c2) throws PegGameException {
        char[][] previousBoard = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(board[i], 0, previousBoard[i], 0, cols);
        }
    
        if (!((Math.abs(r1 - r2) == 2 && c1 == c2) || (Math.abs(c1 - c2) == 2 && r1 == r2))) {
            throw new PegGameException("Invalid peg selected. Refer to the guidelines.");
        }
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == r1 - 1 && j == c1 - 1) {
                    board[i][j] = '.';
                } else if (i == (r1 + r2) / 2 - 1 && j == (c1 + c2) / 2 - 1) {
                    board[i][j] = '.';
                } else if (i == r2 - 1 && j == c2 - 1) {
                    board[i][j] = 'o';
                } else {
                    board[i][j] = previousBoard[i][j];
                }
            }
        }
    
        // Display the updated board
        System.out.println("Updated Board:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Location getFrom() {
        return from;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + ((to == null) ? 0 : to.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Move other = (Move) obj;
        if (from == null) {
            if (other.from != null)
                return false;
        } else if (!from.equals(other.from))
            return false;
        if (to == null) {
            if (other.to != null)
                return false;
        } else if (!to.equals(other.to))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Move [from=" + from + ", to=" + to + "]";
    }

    public Location getTo() {
        return to;
    }

     public static Move[] getPossibleMoves(char[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        
        Move[] possibleMoves = new Move[rows * cols]; // Maximum possible number of moves
        int index = 0;

        // Iterate through the board to find possible moves
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // If the current cell contains a peg ('o'), check for possible jumps
                if (board[i][j] == 'o') {
                    // Check for possible jumps vertically and horizontally
                    if (i >= 2 && board[i - 1][j] == 'o' && board[i - 2][j] == '.') {
                        possibleMoves[index++] = new Move(new Location(i, j), new Location(i - 2, j)); // Jump up
                    }
                    if (i <= rows - 3 && board[i + 1][j] == 'o' && board[i + 2][j] == '.') {
                        possibleMoves[index++] = new Move(new Location(i, j), new Location(i + 2, j)); // Jump down
                    }
                    if (j >= 2 && board[i][j - 1] == 'o' && board[i][j - 2] == '.') {
                        possibleMoves[index++] = new Move(new Location(i, j), new Location(i, j - 2)); // Jump left
                    }
                    if (j <= cols - 3 && board[i][j + 1] == 'o' && board[i][j + 2] == '.') {
                        possibleMoves[index++] = new Move(new Location(i, j), new Location(i, j + 2)); // Jump right
                    }
                }
            }
        }

        // Trim the array to remove null elements
        Move[] trimmedMoves = new Move[index];
        System.arraycopy(possibleMoves, 0, trimmedMoves, 0, index);

        return trimmedMoves;
    }
}
