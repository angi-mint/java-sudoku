package main.java.com.mms.window;

import main.java.com.mms.board.Board;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;

public class Window {

    private JFrame window;

    private JPanel grid, menu;
    private JPanel[][] boardFields;

    private int size;
    private int fullSize;

    private double difficulty;

    private Board board;

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
        catch(Exception ex) {

        }

        window.setVisible(true);

        return window;

    }

    private JPanel createGrid() {

        this.board = new Board(size);
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

    private JPanel createMenu() {
        JPanel login = new JPanel();

        login.setLayout(null);

        JLabel nickLabel = new JLabel("Nickname:");
        nickLabel.setBounds(10, 20, 80, 25);
        login.add(nickLabel);

        JTextField nickTextField = new JTextField(20);
        nickTextField.setBounds(100, 20, 100, 25);
        login.add(nickTextField);

        JLabel gridSizeLabel = new JLabel("Grid Size:");
        gridSizeLabel.setBounds(10, 80, 400, 25);
        login.add(gridSizeLabel);

        JButton two = new JButton("2x2");
        two.setBounds(10, 120, 80, 25);
        two.addActionListener(e -> {
            this.size = 2;
            this.fullSize = this.size * this.size;
        });
        login.add(two);

        JButton three = new JButton("3x3");
        three.setBounds(100, 120, 80, 25);
        three.addActionListener(e -> {
            this.size = 3;
            this.fullSize = this.size * this.size;
        });
        login.add(three);

        JLabel difficultyLabel = new JLabel("Difficulty:");
        difficultyLabel.setBounds(10, 160, 400, 25);
        login.add(difficultyLabel);

        JButton easyButton = new JButton("EASY");
        easyButton.setBounds(10, 200, 80, 25);
        easyButton.addActionListener(e -> {
            difficulty = 0.3;
            System.out.println(difficulty);
        });
        login.add(easyButton);

        JButton middleButton = new JButton("MIDDLE");
        middleButton.setBounds(100, 200, 80, 25);
        middleButton.addActionListener(e -> {
            difficulty = 0.5;
            System.out.println(difficulty);
        });
        login.add(middleButton);

        JButton hardButton = new JButton("HARD");
        hardButton.setBounds(190, 200, 80, 25);
        hardButton.addActionListener(e -> {
            difficulty = 0.7;
            System.out.println(difficulty);
        });
        login.add(hardButton);

        JButton startButton = new JButton("START");
        startButton.setBounds(10, 240, 80, 25);
        startButton.addActionListener(e -> {

            this.menu.setVisible(false);
            this.grid = createGrid();
            this.window.add(grid);

            this.grid.setVisible(true);
        });
        login.add(startButton);

        return login;

    }

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

                    if (component instanceof JTextField) {

                        JTextField textField = (JTextField) component;
                        try {

                            numberField[i][j] = Integer.parseInt(textField.getText());

                        } catch (Exception ex) {

                            success = false;
                            break outer_loop;

                        }

                    } else if (component instanceof JLabel) {

                        JLabel label = (JLabel) component;
                        numberField[i][j] = Integer.parseInt(label.getText());

                    }

                }

            }
            if (success) {
                boolean valid = validator(numberField);
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

                    if (component instanceof JTextField) {

                        JTextField textField = (JTextField) component;
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

    private boolean validator(int[][] numberField) {
        //Schreibe den Validator in dieses Gerüst lg Tic tac und toe ;)

       return true;
    }
}