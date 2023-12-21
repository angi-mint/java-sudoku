package main.java.com.mms.board;

import java.util.Random;

/**
 * Represents a Sudoku board, managing the generation, modification, and manipulation of the game grid.
 */
public class Board {

    /**
     * Determines the number of rows/columns in a single subgrid.
     */
    private int size;

    /**
     * Represents the full size of the Sudoku board (size * size).
     */
    private int fullSize;

    /**
     * Represents the Sudoku board as a two-dimensional array of Field objects.
     * Each Field object corresponds to a cell on the Sudoku board.
     */
    private Field[][] board;

    /**
     * Constructs a Sudoku board with the given size.
     *
     * @param size The size of the Sudoku grid (number of rows/columns in a subgrid).
     */
    public Board(int size) {
        setSize(size);
    }

    /* GETTER AND SETTER */

    /**
     * Sets the size of the sudoku grid.
     *
     * @param size The size to be set for the Sudoku grid (number of rows/columns in
     *             a subgrid).
     */
    public void setSize(int size) {
        if (size < 1) {
            return;
        }

        this.size = size;
        this.fullSize = this.size * this.size;

    }

    /**
     * Retrieves the value at the specified coordinates (x, y) on the Sudoku board.
     *
     * @param x The x-coordinate representing the row index.
     * @param y The y-coordinate representing the column index.
     * @return The numerical value present at the given coordinates on the Sudoku
     * board.
     */
    public int getValue(int y, int x) {

        return this.board[y][x].getValue();

    }

    /* GENERAL FUNCTIONS */

    /**
     * Generates an empty Sudoku board with all cells initialized to zero.
     * Creates a 2D array of Field objects representing the Sudoku board and sets
     * each cell's value to zero.
     */
    private void generateEmptyBoard() {
        this.board = new Field[this.fullSize][this.fullSize];
        for (int i = 0; i < this.fullSize; i++) {
            for (int j = 0; j < this.fullSize; j++) {
                this.board[i][j] = new Field(0);
            }
        }
    }

    /**
     * Generates a Sudoku board by filling it with values according to Sudoku rules.
     * Uses a randomized approach to populate the board with valid values.
     * It repeatedly attempts to fill the board with values while ensuring validity.
     * If successful, the method stops; otherwise, it continues until a valid
     * solution is found.
     */
    public void generateBoard() {

        Random random = new Random();
        while (true) {

            long startTime = System.nanoTime();

            generateEmptyBoard();

            boolean success = true;

            for (int it = 0; it < this.fullSize * this.fullSize; it++) {

                updatePossibleValues(this.board);
                int[][] possibleValuesArray = new int[this.fullSize * this.fullSize][];
                for (int i = 0; i < this.board.length; i++) {
                    for (int j = 0; j < this.board.length; j++) {
                        possibleValuesArray[i * this.board.length + j] = this.board[i][j].getPossibleValues();
                    }
                }
                Field[] boardArray = to1DArray(this.board);

                int smallest = this.fullSize;
                int smallestCount = 0;

                for (int i = 0; i < possibleValuesArray.length; i++) {
                    int length = possibleValuesArray[i].length;
                    if (boardArray[i].getValue() == 0) {
                        if (length < smallest) {
                            smallest = length;
                            smallestCount = 1;
                        } else if (length == smallest) {
                            smallestCount++;
                        }
                    }
                }

                int[] smallestIndex = new int[smallestCount];
                int counter = 0;

                for (int i = 0; i < possibleValuesArray.length; i++) {
                    if (possibleValuesArray[i].length == smallest && boardArray[i].getValue() == 0) {
                        smallestIndex[counter] = i;
                        counter++;
                    }
                }

                int randomSelection = random.nextInt(smallestIndex.length);
                int index = smallestIndex[randomSelection];
                if (possibleValuesArray[index].length == 0) {
                    success = false;
                    break;
                }
                int smallestValue = possibleValuesArray[index][0];

                int[] coordinates = indexToCoordinates(index);
                this.board[coordinates[0]][coordinates[1]].setValue(smallestValue);

            }

            long time_ns = System.nanoTime() - startTime;
            System.out.println("+---===---===---===---===---===---===---+");
            System.out.println("| Grid Generation Time: " + time_ns / 1000000d + "ms");
            System.out.println("+---===---===---===---===---===---===---+");

            if (success) {
                break;
            }
        }

    }

    /**
     * Generates a puzzle of the specified difficulty by removing fields from the board.
     *
     * @param difficulty The difficulty level of the puzzle, a double value ranging from 0 to 1.
     *                   0 indicates easier puzzles, while 1 represents more challenging ones.
     */
    public void generatePuzzle(double difficulty) {

        final Random random = new Random();

        final int fieldsToRemove = ((int) (this.fullSize * this.fullSize * difficulty + 0.5)) / 2;
        int removedCounter = 0;
        final int halfSize = (int) (this.fullSize * this.fullSize * 0.5 + 0.5);

        int[] indexArray = new int[halfSize];
        for (int i = 0; i < indexArray.length; i++) {
            indexArray[i] = i;
        }

        while (true) {

            Field[][] boardCopy = copyBoard(this.board);

            int index = indexArray[random.nextInt(indexArray.length)];
            int symIndex = this.fullSize * this.fullSize - 1 - index;
            int[] coordinate = indexToCoordinates(index);
            int[] symCoordinate = indexToCoordinates(symIndex);

            boardCopy[coordinate[1]][coordinate[0]].setValue(0);
            boardCopy[symCoordinate[1]][symCoordinate[0]].setValue(0);

            Field[][] solveBoardCopy = copyBoard(boardCopy);

            boolean valid = true;

            outer_loop: while (true) {

                updatePossibleValues(solveBoardCopy);

                for (int i = 0; i < solveBoardCopy.length; i++) {
                    for (int j = 0; j < solveBoardCopy.length; j++) {

                        if (solveBoardCopy[i][j].getValue() == 0) {
                            int[] possibleValues = solveBoardCopy[i][j].getPossibleValues();

                            if (possibleValues.length == 0) {
                                valid = false;
                                break outer_loop;
                            } else if (possibleValues.length == 1) {
                                solveBoardCopy[i][j].setValue(possibleValues[0]);
                                continue outer_loop;
                            }
                        }

                    }
                }

                for (int i = 0; i < solveBoardCopy.length; i++) {
                    for (int j = 0; j < solveBoardCopy.length; j++) {

                        if (solveBoardCopy[i][j].getValue() == 0) {

                            valid = false;
                            break outer_loop;

                        }

                    }
                }

                break;

            }

            if (valid) {
                this.board = copyBoard(boardCopy);

                removedCounter++;
                if (removedCounter >= fieldsToRemove) {
                    break;
                }
            }
            indexArray = RemoveArrayElement(indexArray, index);

            if (indexArray.length == 0) {
                break;
            }

        }

    }

    /* HELPER FUNCTIONS */

    /**
     * Creates a deep copy of a 2D array of Field objects.
     *
     * @param array The original 2D array of Field objects to be copied.
     * @return A new 2D array containing copies of the Field objects from the original array.
     */
    private Field[][] copyBoard(Field[][] array) {

        Field[][] newArray = new Field[array.length][array.length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                newArray[i][j] = new Field(array[i][j]);
            }
        }

        return newArray;

    }

    /**
     * Converts a 2D array of Field objects representing the Sudoku board to a 1D
     * array.
     *
     * @param array A 2D array of Field objects representing the Sudoku board.
     * @return A 1D array containing all Field objects from the 2D array in a linear
     * sequence.
     */
    private Board.Field[] to1DArray(Board.Field[][] array) {

        int length = array.length;

        Board.Field[] newArray = new Board.Field[length * length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(array[i], 0, newArray, i * length, length);
        }

        return newArray;

    }

    /**
     * Converts a linear index to 2D coordinates representing a cell's position on
     * the Sudoku board.
     *
     * @param index The linear index representing the position of a cell in the 1D
     *              representation of the board.
     * @return An array of two integers representing the row and column coordinates
     * of the cell on the board.
     */
    private int[] indexToCoordinates(int index) {

        int[] coordinates = new int[2];

        coordinates[0] = index / this.fullSize;
        coordinates[1] = index % this.fullSize;

        return coordinates;

    }

    /**
     * Updates the possible values for each cell on the Sudoku board based on
     * current cell values and constraints.
     *
     * @param board a 2D array representing the Sudoku board with fields containing values
     */
    private void updatePossibleValues(Field[][] board) {

        for (int i = 0; i < this.fullSize; i++) {
            for (int j = 0; j < this.fullSize; j++) {

                Field field = board[i][j];

                int[] possibleValues = new int[1];

                if (field.getValue() != 0) {

                    possibleValues[0] = field.getValue();

                } else {

                    possibleValues = new int[this.fullSize];

                    for (int k = 0; k < possibleValues.length; k++) {

                        possibleValues[k] = k + 1;

                    }

                    for (Field k : board[i]) {
                        int value = k.getValue();
                        if (value != 0) {
                            if (possibleValues.length < 1) {
                                possibleValues = new int[0];
                                break;
                            }
                            possibleValues = RemoveArrayElement(possibleValues, value);
                        }
                    }

                    for (int k = 0; k < this.fullSize; k++) {
                        int value = board[k][j].getValue();
                        if (value != 0) {
                            if (possibleValues.length < 1) {
                                possibleValues = new int[0];
                                break;
                            }
                            possibleValues = RemoveArrayElement(possibleValues, value);
                        }
                    }

                    int x = j - (j % this.size);
                    int y = i - (i % this.size);

                    for (int k = 0; k < this.size; k++) {
                        for (int l = 0; l < this.size; l++) {
                            int value = board[k + y][l + x].getValue();
                            if (value != 0) {
                                if (possibleValues.length < 1) {
                                    possibleValues = new int[0];
                                    break;
                                }
                                possibleValues = RemoveArrayElement(possibleValues, value);
                            }

                        }

                    }

                }

                board[i][j].setPossibleValues(possibleValues);

            }

        }

    }

    /**
     * Removes a specified element from an integer array and returns a new array
     * without that element.
     *
     * @param array  The input integer array from which the element needs to be
     *               removed.
     * @param number The element to be removed from the array.
     * @return An updated array without the specified element. If the element
     * doesn't exist, the original array is returned.
     */
    private int[] RemoveArrayElement(int[] array, int number) {

        int[] newArray = new int[array.length - 1];
        int counter = 0;

        try {
            for (int i = 0; i < array.length; i++) {
                if (array[i] != number) {
                    newArray[counter] = array[i];
                    counter++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return array;
        }

        return newArray;

    }

    /**
     * Represents a single cell in a Sudoku board, storing its value and possible values.
     * Used for managing individual cells within the board.
     */
    private static class Field {

        /**
         * The value stored in the cell.
         */
        private int value;

        /**
         * An array containing possible values for the cell.
         */
        private int[] possibleValues;

        /**
         * Constructs a Field object with an initial value.
         *
         * @param value The initial value assigned to the cell.
         */
        public Field(int value) {

            setValue(value);
            setPossibleValues(new int[0]);

        }

        /**
         * Constructs a new Field object by copying the value and possible values from another Field object.
         *
         * @param other The Field object from which the values are copied to create a new Field instance.
         */
        public Field(Field other) {

            setValue(other.value);
            setPossibleValues(other.possibleValues);

        }

        /**
         * Retrieves the value of the cell.
         *
         * @return The value stored in the cell.
         */
        public int getValue() {

            return this.value;

        }

        /**
         * Sets the value of the cell.
         *
         * @param value The value to set in the cell.
         */
        public void setValue(int value) {

            this.value = value;

        }

        /**
         * Retrieves the possible values for the cell.
         *
         * @return An array containing possible values for the cell.
         */
        public int[] getPossibleValues() {

            return this.possibleValues;

        }

        /**
         * Sets the possible values for the cell.
         *
         * @param possibleValues An array containing possible values for the cell.
         */
        public void setPossibleValues(int[] possibleValues) {

            this.possibleValues = possibleValues;

        }

    }

}