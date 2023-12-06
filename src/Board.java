import java.util.Random;

public class Board {

    private int size;

    private int fullSize;

    private Field[][] board;

    public Board(int size) {
        setSize(size);
    }

    /* GETTER AND SETTER */

    /** Sets the size of the sudoku grid.
     *
     * @param size The size to be set for the Sudoku grid (number of rows/columns in a subgrid).
     * @return {@code true} if the size is valid and set successfully, {@code false} otherwise.
     */
    public boolean setSize(int size) {
        if (size < 1) {
            return false;
        }

        this.size = size;
        this.fullSize = this.size * this.size;

        return true;
    }

    /** Retrieves the size of the Sudoku grid.
     *
     * @return The size of the Sudoku grid (number of rows/columns in a subgrid).
     */
    public int getSize() {

        return this.size;

    }
    /** Retrieves the value at the specified coordinates (x, y) on the Sudoku board.
     *
     * @param x The x-coordinate representing the row index.
     * @param y The y-coordinate representing the column index.
     * @return The numerical value present at the given coordinates on the Sudoku board.
     */
    public int getValue(int x, int y) {

        return this.board[y][x].getValue();

    }
    /** Retrieves the current values present on the Sudoku board.
     *
     * @return A 2D array representing the numerical values of the Sudoku board.
     *         Each cell contains the value of the corresponding cell on the board.
     */
    public int[][] getValues() {

        int[][] array = new int[this.fullSize][this.fullSize];

        for (int i = 0; i < this.fullSize; i++) {
            for (int j = 0; j < this.fullSize; j++) {

                array[i][j] = this.board[i][j].getValue();

            }
        }

        return array;

    }

    /** Retrieves the visibility status of a cell at the specified coordinates (x, y) on the Sudoku board.
     *
     * @param x The x-coordinate representing the row index.
     * @param y The y-coordinate representing the column index.
     * @return {@code true} if the cell at the given coordinates is visible, {@code false} otherwise.
     */
    public boolean getVisibility(int x, int y) {

        return this.board[y][x].getVisibility();

    }

    /** Retrieves the visibility statuses of cells on the Sudoku board.
     *
     * @return A 2D array indicating the visibility status of each cell on the board.
     *         Each cell in the array is {@code true} if the corresponding cell on the board is visible,
     *         and {@code false} otherwise.
     */
    public boolean[][] getVisibilities() {

        boolean[][] array = new boolean[this.fullSize][this.fullSize];

        for (int i = 0; i < this.fullSize; i++) {
            for (int j = 0; j < this.fullSize; j++) {

                array[i][j] = this.board[i][j].getVisibility();

            }
        }

        return array;

    }

    /* GENERAL FUNCTIONS */

    /** Generates an empty Sudoku board with all cells initialized to zero.
     * Creates a 2D array of Field objects representing the Sudoku board and sets each cell's value to zero.
     */
    private void generateEmptyBoard() {
        this.board = new Field[this.fullSize][this.fullSize];
        for (int i = 0; i < this.fullSize; i++) {
            for (int j = 0; j < this.fullSize; j++) {
                this.board[i][j] = new Board.Field(0);
            }
        }
    }

    /** Generates a Sudoku board by filling it with values according to Sudoku rules.
     * Uses a randomized approach to populate the board with valid values.
     * It repeatedly attempts to fill the board with values while ensuring validity.
     * If successful, the method stops; otherwise, it continues until a valid solution is found.
     */
    public void generateBoard() {
        Random random = new Random();
        while (true) {

            // tmp // time measurement
            long startTime = System.nanoTime();

            generateEmptyBoard();

            boolean success = true;


            for (int it = 0; it < this.fullSize * this.fullSize; it++) {

                int[][] possibleValuesArray = updatePossibleValues();
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

            // tmp // time measurement
            long time_ns = System.nanoTime() - startTime;
            System.out.println("+---===---===---===---===---===---===---+");
            System.out.println("| Grid Generation Time: " + time_ns / 1000000d + "ms");
            System.out.println("+---===---===---===---===---===---===---+");

            if (success) {
                break;
            }
        }
    }

    public void generatePuzzle(){
        /* Kommentar bitte beim nächsten Commit löschen, und javadoc Kommentar stattdessen hinzufügen
         * Felix & Goetz, Hinweise/Hilfestellungen:
         * size und fullSize verwenden, im Falle eines 3x3 Sudoku, wäre size=3 und fullSize=9 (also 3x3)
         * this.board ändern, um das Puzzle zu generieren.
         * rausstreichen der Werte: visibility auf false setzen, mit this.board[y][x].setVisibility(false);
         * wichtig für das Backtracking:
         *    updatePossibleValues: possible Values vom Board updaten, mit this.board[y][x].getPossibleValues();
         * this.board[y][x].getValue();
         */
    }

    /* HELPER FUNCTIONS */

    private class Field {

        private int value;
        private int[] possibleValues;

        private boolean visibility;

        /** Constructs a Field object with an initial value.
         *
         * @param value The initial value assigned to the cell.
         */
        public Field(int value) {

            setValue(value);
            setPossibleValues(new int[0]);
            setVisibility(true);

        }

        /** Sets the value of the cell.
         *
         * @param value The value to set in the cell.
         */
        public void setValue(int value) {

            this.value = value;

        }

        /** Retrieves the value of the cell.
         *
         * @return The value stored in the cell.
         */
        public int getValue() {

            return this.value;

        }

        /** Sets the possible values for the cell.
         *
         * @param possibleValues An array containing possible values for the cell.
         */
        public void setPossibleValues(int[] possibleValues) {

            this.possibleValues = possibleValues;

        }

        /** Retrieves the possible values for the cell.
         *
         * @return An array containing possible values for the cell.
         */
        public int[] getPossibleValues() {

            return this.possibleValues;

        }

        /** Sets the visibility status of the cell.
         *
         * @param visibility The visibility status to set for the cell.
         */
        public void setVisibility(boolean visibility) {

            this.visibility = visibility;

        }

        /** Retrieves the visibility status of the cell.
         *
         * @return The visibility status of the cell.
         */
        public boolean getVisibility() {

            return this.visibility;

        }
    }

    /** Converts a 2D array of Field objects representing the Sudoku board to a 1D array.
     *
     * @param array A 2D array of Field objects representing the Sudoku board.
     * @return A 1D array containing all Field objects from the 2D array in a linear sequence.
     */
    private Board.Field[] to1DArray(Board.Field[][] array) {

        int length = array.length;

        Board.Field[] newArray = new Board.Field[length * length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(array[i], 0, newArray, i * length, length);
        }

        return newArray;

    }

    /** Converts a linear index to 2D coordinates representing a cell's position on the Sudoku board.
     *
     * @param index The linear index representing the position of a cell in the 1D representation of the board.
     * @return An array of two integers representing the row and column coordinates of the cell on the board.
     */
    private int[] indexToCoordinates(int index) {

        int[] coordinates = new int[2];

        coordinates[0] = index / this.fullSize;
        coordinates[1] = index % this.fullSize;

        return coordinates;

    }

    /** Updates the possible values for each cell on the Sudoku board based on current cell values and constraints.
     *
     * @return A 2D array representing the possible values for each cell on the board.
     *         Each element of the array contains an array of possible values for the corresponding cell.
     */
    private int[][] updatePossibleValues() {

        int[][] possibleValuesArray = new int[this.fullSize * this.fullSize][];

        for (int i = 0; i < this.fullSize; i++) {
            for (int j = 0; j < this.fullSize; j++) {

                Board.Field field = this.board[i][j];

                int[] possibleValues = new int[1];

                if (field.getValue() != 0) {

                    possibleValues[0] = field.getValue();

                } else {

                    possibleValues = new int[this.fullSize];

                    for (int k = 0; k < possibleValues.length; k++) {

                        possibleValues[k] = k + 1;

                    }

                    for (Board.Field k : this.board[i]) {
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
                        int value = this.board[k][j].getValue();
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
                            int value = this.board[k + y][l + x].getValue();
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

                this.board[i][j].setPossibleValues(possibleValues);
                possibleValuesArray[i * this.fullSize + j] = possibleValues;

            }
        }

        return possibleValuesArray;

    }

    /**
     * Removes a specified element from an integer array and returns a new array without that element.
     *
     * @param array  The input integer array from which the element needs to be removed.
     * @param number The element to be removed from the array.
     * @return An updated array without the specified element. If the element doesn't exist, the original array is returned.
     */
    private int[] RemoveArrayElement(int[] array, int number) {

        int[] newArray = new int[array.length - 1];
        int counter = 0;

        try {
            for (int j : array) {
                if (!(j == number)) {
                    newArray[counter] = j;
                    counter++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return array;
        }

        return newArray;

    }

    /**
     * Prints the current state of the Sudoku board to the console.
     * The board is displayed with grid lines and cell values in the console output.
     */
    public void printBoard() {

        for (int i = 0; i < this.fullSize; i++) {

            linePrinter();
            if (i % this.size == 0 && i != 0) {
                System.out.println();
                linePrinter();
            }

            System.out.print("|");
            for (int j = 0; j < this.fullSize; j++) {

                if (j % this.size == 0 && j != 0) {
                    System.out.print("   |");
                }
                System.out.print(" " + this.board[i][j].getValue() + " |");

            }
            System.out.println();

        }

        linePrinter();

    }

    /**  Prints a line segment representing the boundary or separator within the Sudoku board.
     * The line consists of grid lines and separation markings used to visually divide the board.
     */
    private void linePrinter() {
        System.out.print("+");
        for (int j = 0; j < this.fullSize; j++) {
            if (j % this.size == 0 && j != 0) {
                System.out.print("   +");
            }
            System.out.print("---+");
        }
        System.out.println();
    }

}
