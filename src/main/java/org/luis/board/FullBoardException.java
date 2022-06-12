package org.luis.board;

public class FullBoardException extends RuntimeException {

    public FullBoardException() {
        super("The board is full!");
    }

    public FullBoardException(String message) {
        super(message);
    }
}
