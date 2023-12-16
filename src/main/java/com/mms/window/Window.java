package main.java.com.mms.window;

import main.java.com.mms.board.Board;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

public class Window {

    private JFrame window;

    private JPanel grid, menu;

    private int size;

    private double difficulty;

    public Window() {

        this.size = 3;
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
        window.setVisible(true);

        return window;

    }

    private JPanel createGrid(int size) {

        Board board = new Board(size);
        System.out.println("Generating Puzzle");
        board.generateBoard();
        board.generatePuzzle(difficulty);
        System.out.println("Generated");

        int fullSize = size * size;

        JPanel body = new JPanel();
        body.setVisible(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setPreferredSize(new Dimension(500, 570));

        JPanel boardLayout = new JPanel(new GridLayout(size, size, 6, 6));
        boardLayout.setBackground(Color.BLACK);
        boardLayout.setPreferredSize(new Dimension(500, 500));

        JPanel[][] segmentLayouts = new JPanel[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                segmentLayouts[i][j] = new JPanel(new GridLayout(size, size, 2, 2));
                segmentLayouts[i][j].setBackground(Color.BLACK);
            }
        }

        for (int i = 0; i < fullSize; i++) {
            for (int j = 0; j < fullSize; j++) {

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

                    NumberFormatter numberFormatter = new NumberFormatter(intFormat);
                    numberFormatter.setValueClass(Integer.class);
                    numberFormatter.setAllowsInvalid(true);
                    numberFormatter.setMinimum(1);
                    numberFormatter.setMaximum(fullSize);

                    JFormattedTextField input = new JFormattedTextField(numberFormatter);
                    input.setFont(input.getFont().deriveFont(25.0f));
                    input.setForeground(Color.BLUE);
                    input.setBackground(new Color(201, 207, 234));
                    input.setHorizontalAlignment(JTextField.CENTER);
                    fieldLayout.add(input);

                }

                segmentLayouts[i / size][j / size].add(fieldLayout);

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
        gridSizeLabel.setBounds(10,80,400,25);
        login.add(gridSizeLabel);

        JButton two = new JButton("2x2");
        two.setBounds(10, 120, 80,25);
        two.addActionListener(e -> {
            size = 2;
            System.out.println(size);
        });
        login.add(two);

        JButton three = new JButton("3x3");
        three.setBounds(100, 120, 80,25);
        three.addActionListener(e -> {
            size = 3;
            System.out.println(size);
        });
        login.add(three);

        JLabel difficultyLabel = new JLabel("Difficulty:");
        difficultyLabel.setBounds(10,160,400,25);
        login.add(difficultyLabel);

        JButton easyButton = new JButton("EASY");
        easyButton.setBounds(10, 200, 80,25);
        easyButton.addActionListener(e -> {
            difficulty = 0.3;
            System.out.println(difficulty);
        });
        login.add(easyButton);

        JButton middleButton = new JButton("MIDDLE");
        middleButton.setBounds(100, 200, 80,25);
        middleButton.addActionListener(e -> {
            difficulty = 0.5;
            System.out.println(difficulty);
        });
        login.add(middleButton);

        JButton hardButton = new JButton("HARD");
        hardButton.setBounds(190, 200, 80,25);
        hardButton.addActionListener(e -> {
            difficulty = 0.7;
            System.out.println(difficulty);
        });
        login.add(hardButton);

        JButton startButton = new JButton("START");
        startButton.setBounds(10, 240, 80, 25);
        startButton.addActionListener(e -> {

            this.menu.setVisible(false);
            this.grid = createGrid(size);
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
            this.grid = createGrid(size);
            this.window.add(grid);
            this.grid.setVisible(true);
            this.window.revalidate();
            this.window.repaint();

            System.out.println("New Game created");
        });
        buttonLayout.add(newButton);

        JButton submitButton = new JButton();
        submitButton.setText("Submit");
        submitButton.setMnemonic(KeyEvent.VK_S);
        submitButton.addActionListener(e -> System.out.println("Submit button pressed"));
        buttonLayout.add(submitButton);

        JButton clearButton = new JButton();
        clearButton.setText("Clear Field");
        clearButton.setMnemonic(KeyEvent.VK_C);
        clearButton.addActionListener(e -> System.out.println("Clear button pressed"));
        buttonLayout.add(clearButton);

        JButton exitButton = new JButton();
        exitButton.setText("Exit Game");
        exitButton.setMnemonic(KeyEvent.VK_E);
        exitButton.addActionListener(e -> {

            this.grid.setVisible(false);
            this.window.add(menu);
            this.menu.setVisible(true);
        });
        buttonLayout.add(exitButton);

        return buttonLayout;

    }

}