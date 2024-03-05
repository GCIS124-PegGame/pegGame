package PEGGAME2;

// PegGameException class thrown when an invalid move is attempted on the game board
class PegGameException extends Exception {
    public PegGameException(String message) {
        super(message);
    }
}