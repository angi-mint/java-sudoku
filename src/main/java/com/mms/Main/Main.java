package main.java.com.mms.Main;

import main.java.com.mms.Board.Board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class Main {

    private static Board board;

    private static int size;

    private static int fullSize;

    public static void main(String[] args) {

        board = new Board(3);
        board.generateBoard();
        size = board.getSize();
        fullSize = size * size;

        createWindow();

    }


    private static void createWindow() {

        JFrame window = new JFrame();

        window.setTitle("Debug Window");
        window.setSize(new Dimension(500, 570));
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        JPanel body = new JPanel();
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

                if (board.getVisibility(i, j)) {

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
                    numberFormatter.setMaximum(9);

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

        window.add(body);

        window.setVisible(true);

    }

}