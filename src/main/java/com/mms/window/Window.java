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

    private JPanel grid;

    private int size;

    private double difficulty;

    public Window() {

        this.size = 3;
        this.difficulty = 0.3;

        this.window = createWindow();
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
            window.setVisible(false);
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        });
        buttonLayout.add(exitButton);

        return buttonLayout;

    }

}