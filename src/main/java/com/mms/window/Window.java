/**
 * The Window class represents the graphical user interface for a Sudoku game.
 * It creates and manages the main window, grid layout, menu, and game functionalities.
 */

package main.java.com.mms.window;

import main.java.com.mms.board.Board;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;

public class Window {

    private final JFrame window;

    private JPanel grid;
    private final JPanel menu;
    private JPanel[][] boardFields;

    private int size;
    private int fullSize;

    private double difficulty;

    private String nickname;

    /**
     * Default constructor for the Window class.
     * Initializes default values for size, difficulty, and creates the window and menu.
     */
    public Window() {

        this.size = 3;
        this.fullSize = this.size * this.size;
        this.difficulty = 0.3;

        this.window = createWindow();
        this.menu = createMenu();
        this.window.add(menu);
        this.window.revalidate();
        this.window.repaint();

    }

    /**
     * Creates and configures the main JFrame window for the Sudoku game.
     * Sets window properties such as title, size, appearance, and default close operation.
     * Attempts to use the Windows look and feel for the UI, falling back to default if an exception occurs.
     *
     * @return The configured JFrame window for the Sudoku game.
     */
    private JFrame createWindow() {

        JFrame window = new JFrame();

        window.setTitle("Sudoku");
        window.setSize(new Dimension(500, 570));
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(window);
        }
        catch(Exception ignored) {

        }

        window.setVisible(true);

        return window;

    }

    /**
     * Creates and initializes the graphical representation of the Sudoku grid within a JPanel.
     * Sets up the board layout, generates the Sudoku puzzle, and prepares input fields or labels for each grid cell.
     *
     * @return The constructed JPanel containing the Sudoku grid and associated components.
     */
    private JPanel createGrid() {

        Board board = new Board(size);
        System.out.println("Generating Puzzle");
        board.generateBoard();
        board.generatePuzzle(this.difficulty);
        System.out.println("Generated");

        JPanel body = new JPanel();
        body.setVisible(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setPreferredSize(new Dimension(500, 570));

        JPanel boardLayout = new JPanel(new GridLayout(size, size, 6, 6));
        boardLayout.setBackground(Color.BLACK);
        boardLayout.setPreferredSize(new Dimension(500, 500));

        JPanel[][] segmentLayouts = new JPanel[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                segmentLayouts[i][j] = new JPanel(new GridLayout(size, size, 2, 2));
                segmentLayouts[i][j].setBackground(Color.BLACK);
            }
        }

        this.boardFields = new JPanel[this.fullSize][this.fullSize];

        for (int i = 0; i < this.fullSize; i++) {
            for (int j = 0; j < this.fullSize; j++) {

                JPanel fieldLayout = new JPanel(new GridLayout(1, 1));
                fieldLayout.setBackground(Color.WHITE);

                if (board.getValue(i, j) != 0) {

                    JLabel label = new JLabel();
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setFont(label.getFont().deriveFont(25.0f));
                    label.setForeground(Color.BLACK);
                    label.setText(Integer.toString(board.getValue(i, j)));

                    fieldLayout.add(label);

                } else {

                    NumberFormat intFormat = NumberFormat.getIntegerInstance();

                    NumberFormatter numberFormatter = new NumberFormatter(intFormat) {
                        @Override
                        public Object stringToValue(String text) throws ParseException {
                            if (text.isEmpty())
                                return null;
                            return super.stringToValue(text);
                        }
                    };
                    numberFormatter.setValueClass(Integer.class);
                    numberFormatter.setAllowsInvalid(true);
                    numberFormatter.setMinimum(1);
                    numberFormatter.setMaximum(this.fullSize);

                    JFormattedTextField input = new JFormattedTextField(numberFormatter);
                    input.setFont(input.getFont().deriveFont(25.0f));
                    input.setForeground(Color.BLUE);
                    input.setBackground(new Color(201, 207, 234));
                    input.setHorizontalAlignment(JTextField.CENTER);
                    fieldLayout.add(input);

                }

                boardFields[i][j] = fieldLayout;

                segmentLayouts[i / this.size][j / this.size].add(fieldLayout);

            }
        }

        for (JPanel[] segmentLayoutRow : segmentLayouts) {
            for (JPanel segment : segmentLayoutRow) {
                boardLayout.add(segment);
            }
        }

        body.add(boardLayout);

        JPanel buttonLayout = createButtonLayout();

        body.add(buttonLayout);

        return body;

    }

    /**
     * Constructs and configures the menu panel for the Sudoku game.
     * The menu includes options for setting the player's nickname, grid size, difficulty level, and starting the game.
     *
     * @return The JPanel representing the constructed menu panel with interactive components for game setup.
     */
    private JPanel createMenu() {
        JPanel login = new JPanel();

        login.setLayout(null);

        JLabel nickLabel = new JLabel("Nickname:");
        nickLabel.setBounds(10, 130, 170, 25);
        nickLabel.setFont(nickLabel.getFont().deriveFont(Font.BOLD,20.0f));
        login.add(nickLabel);

        JTextField nickTextField = new JTextField(20);
        nickTextField.setBounds(130, 130, 100, 25);
        login.add(nickTextField);

        JLabel gridSizeLabel = new JLabel("Grid Size:");
        gridSizeLabel.setBounds(10, 205, 400, 25);
        gridSizeLabel.setFont(gridSizeLabel.getFont().deriveFont(Font.BOLD,20.0f));
        login.add(gridSizeLabel);

        JLabel sudokuLabel = new JLabel("MasterMind Studios");
        sudokuLabel.setBounds(12, 2, 460, 100);
        sudokuLabel.setFont(sudokuLabel.getFont().deriveFont(Font.BOLD,40.0f));
        login.add(sudokuLabel);

        JLabel logoLabel = new JLabel("\uD83D\uDD8A");
        logoLabel.setBounds(310, 1, 460, 460);
        logoLabel.setFont(logoLabel.getFont().deriveFont(150.0f));
        login.add(logoLabel);

        JButton two = new JButton("2x2");
        two.setBounds(10, 235, 50, 50);
        two.addActionListener(e -> {
            this.size = 2;
            this.fullSize = this.size * this.size;
        });
        login.add(two);

        JButton three = new JButton("3x3");
        three.setBounds(70, 235, 50, 50);
        three.addActionListener(e -> {
            this.size = 3;
            this.fullSize = this.size * this.size;
        });
        login.add(three);

        JLabel difficultyLabel = new JLabel("Difficulty");
        difficultyLabel.setBounds(10, 265, 400, 80);
        difficultyLabel.setFont(difficultyLabel.getFont().deriveFont(Font.BOLD,20.0f));
        login.add(difficultyLabel);

        JButton easyButton = new JButton("EASY");
        easyButton.setBounds(10, 325, 80, 50);
        easyButton.addActionListener(e -> {
            difficulty = 0.3;
            System.out.println(difficulty);
        });
        login.add(easyButton);

        JButton middleButton = new JButton("MIDDLE");
        middleButton.setBounds(100, 325, 80, 50);
        middleButton.addActionListener(e -> {
            difficulty = 0.5;
            System.out.println(difficulty);
        });
        login.add(middleButton);

        JButton hardButton = new JButton("HARD");
        hardButton.setBounds(190, 325, 80, 50);
        hardButton.addActionListener(e -> {
            difficulty = 1.0;
            System.out.println(difficulty);
        });
        login.add(hardButton);

        JButton startButton = new JButton("START");
        startButton.setBounds(10, 420, 464, 100);
        startButton.addActionListener(e -> {
            try {
                nickname = nickTextField.getText();
            } catch (Exception error) {
                nickname = "Player";
            }

            this.menu.setVisible(false);
            this.grid = createGrid();
            this.window.add(grid);

            this.grid.setVisible(true);
        });
        login.add(startButton);

        return login;

    }

    /**
     * Creates and configures a panel layout containing buttons for game interaction.
     * Includes buttons for starting a new game, submitting the Sudoku solution,
     * clearing the input fields, and exiting the game.
     * This Panel is added underneath the grid.
     *
     * @return JPanel containing buttons for game control and interaction
     */
    private JPanel createButtonLayout() {
        JPanel buttonLayout = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonLayout.setPreferredSize(new Dimension(500, 70));

        JButton newButton = new JButton();
        newButton.setText("New Game");
        newButton.setMnemonic(KeyEvent.VK_N);
        newButton.addActionListener(e -> {
            this.window.remove(grid);
            this.grid = createGrid();
            this.window.add(grid);
            this.grid.setVisible(true);
            this.window.revalidate();
            this.window.repaint();
            System.out.println("New Game button pressed");
        });
        buttonLayout.add(newButton);

        JButton submitButton = new JButton();
        submitButton.setText("Submit");
        submitButton.setMnemonic(KeyEvent.VK_S);
        submitButton.addActionListener(e -> {

            int[][] numberField = new int[this.fullSize][this.fullSize];
            boolean success = true;

            outer_loop: for (int i = 0; i < this.fullSize; i++) {
                for (int j = 0; j < this.fullSize; j++) {

                    Component component = this.boardFields[i][j].getComponents()[0];

                    if (component instanceof JTextField textField) {

                        try {

                            numberField[i][j] = Integer.parseInt(textField.getText());

                        } catch (Exception ex) {

                            success = false;
                            break outer_loop;

                        }

                    } else if (component instanceof JLabel label) {

                        numberField[i][j] = Integer.parseInt(label.getText());

                    }

                }

            }
            if (success) {
                boolean valid = validator(numberField);
                createMessage(valid);
                if (valid) {
                    System.out.println("Success");
                } else {
                    System.out.println("Failed");
                }
            } else {
                System.out.println("Not filled out");
            }
            System.out.println("Submit button pressed");

        });
        buttonLayout.add(submitButton);

        JButton clearButton = new JButton();
        clearButton.setText("Clear Field");
        clearButton.setMnemonic(KeyEvent.VK_C);
        clearButton.addActionListener(e -> {
            System.out.println("Clear button pressed");

            for (JPanel[] containerRow : this.boardFields) {
                for (JPanel container : containerRow) {

                    Component component = container.getComponents()[0];

                    if (component instanceof JTextField textField) {

                        textField.setText(null);

                    }

                }
            }

        });
        buttonLayout.add(clearButton);

        JButton exitButton = new JButton();
        exitButton.setText("Exit Game");
        exitButton.setMnemonic(KeyEvent.VK_E);
        exitButton.addActionListener(e -> {

            this.grid.setVisible(false);
            this.menu.setVisible(true);
            this.window.revalidate();
            this.window.repaint();

        });
        buttonLayout.add(exitButton);

        return buttonLayout;

    }

    /**
     * Generates a message panel based on the validation of the submitted Sudoku solution.
     * Displays a message indicating success or failure upon checking the solution.
     * Allows the user to view the game, start a new game, or exit back to the menu.
     *
     * @param valid Indicates whether the submitted Sudoku solution is valid (true for success, false for failure).
     */
    private void createMessage(boolean valid) {
        JPanel message = new JPanel();
        message.setLayout(null);

        String bannerMessage;
        String bannerImage;

        JButton exitGame = new JButton("Exit Game");
        exitGame.setBounds(320, 195, 120, 40);
        exitGame.setMnemonic(KeyEvent.VK_E);
        exitGame.addActionListener(e -> {

            this.window.remove(message);
            this.menu.setVisible(true);
            this.window.revalidate();
            this.window.repaint();
            System.out.println("Exit Game button pressed");

        });

        JButton viewGame = new JButton("View Game");
        viewGame.setBounds(180, 195, 120, 40);
        viewGame.setMnemonic(KeyEvent.VK_V);
        viewGame.addActionListener(e -> {
            this.window.remove(message);
            this.grid.setVisible(true);
            this.window.revalidate();
            this.window.repaint();
            System.out.println("View Game button pressed");
        });
        message.add(viewGame);

        JButton newGame = new JButton("New Game");
        newGame.setBounds(40, 195, 120, 40);
        newGame.setMnemonic(KeyEvent.VK_N);
        newGame.addActionListener(e -> {
            this.window.remove(message);
            this.grid = createGrid();
            this.window.add(grid);
            this.grid.setVisible(true);
            this.window.revalidate();
            this.window.repaint();
            System.out.println("New Game button pressed");
        });

        if (valid) {
            bannerMessage = "Congratulations ";
            bannerImage = ("\uD83C\uDFC6");
            JLabel trophyLabel = new JLabel(nickname, SwingConstants.CENTER);
            trophyLabel.setBounds(210, 462, 50, 80);
            trophyLabel.setFont(trophyLabel.getFont().deriveFont(Font.BOLD, 10.0f));
            message.add(trophyLabel);

            } else {
            bannerMessage = "You Failed";
            bannerImage = ("\uD83D\uDE31");
        }

        JLabel commitMessage = new JLabel(bannerMessage, SwingConstants.CENTER);
        commitMessage.setBounds(0, 10, 500, 80);
        commitMessage.setFont(commitMessage.getFont().deriveFont(Font.BOLD,56.0f));
        message.add(commitMessage);

        JLabel nicknameMessage = new JLabel(nickname, SwingConstants.CENTER);
        nicknameMessage.setFont(nicknameMessage.getFont().deriveFont(Font.BOLD,56.0f));
        nicknameMessage.setBounds(0, 100, 500, 60);
        message.add(nicknameMessage);

        JLabel imageMessage = new JLabel(bannerImage);
        imageMessage.setFont(imageMessage.getFont().deriveFont(300.0f));
        imageMessage.setBounds(87, 100, 500, 570);
        message.add(imageMessage);

        message.add(exitGame);
        message.add(newGame);

        this.grid.setVisible(false);
        this.window.add(message);
        this.window.revalidate();
        this.window.repaint();

    }

    /**
     * Validates the Sudoku solution by checking if the number field meets the game's rules.
     * (no repeated numbers in rows, columns, or subgrids)
     *
     * @param numberField 2D array representing the Sudoku board with values to be validated.
     * @return True if the provided number field adheres to Sudoku rules, false otherwise.
     */
    private boolean validator(int[][] numberField) {

        for (int i = 0; i < this.fullSize; i++) {
            for (int j = 0; j < this.fullSize; j++) {

                int value = numberField[i][j];
                numberField[i][j] = -1;

                for (int number : numberField[i]) {

                    if (number == value){
                        return false;
                    }

                }

                for (int k = 0; k < this.fullSize; k++) {
                    int number = numberField[k][j];
                    if (number == value) {
                        return false;
                    }
                }

                int x = j - (j % this.size);
                int y = i - (i % this.size);

                for (int k = 0; k < this.size; k++) {
                    for (int l = 0; l < this.size; l++) {
                        int number = numberField[k + y][l + x];

                        if (number == value) {
                            return false;
                        }

                    }
                }
                numberField[i][j] = value;

            }
        }
       return true;
    }
}