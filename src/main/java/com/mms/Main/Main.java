package main.java.com.mms.Main;

import main.java.com.mms.Board.Board;

public class Main {

    private static int size;

    private static int fullSize;

    public static void main(String[] args) {

        Board board = new Board(3);
        board.generateBoard();
        size = board.getSize();
        fullSize = size * size;


    }

}