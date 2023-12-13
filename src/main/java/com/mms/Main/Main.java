package main.java.com.mms.Main;

import main.java.com.mms.Board.Board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

//nico
import java.awt.event.ActionEvent;

public class Main {

    private static JFrame window;

    private static Board board;

    private static int size;

    private static int fullSize;

    //Nico
private static JLabel nicknameLabel;
private static JTextField userText;
private static JLabel difficultyLabel;
private static JTextField difficultyText;
private static JLabel difficultylevelLabel;
private static JButton button;
private static JPanel loginPanel;
private static JFrame loginFrame;

    public static void main(String[] args) {
        
        // Nico code   
        loginFrame = new JFrame();
		loginFrame.setSize(350, 220);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginPanel = new JPanel();
		loginFrame.add(loginPanel);

		loginPanel.setLayout(null);

		nicknameLabel = new JLabel("Nickname:");
		nicknameLabel.setBounds(10, 20, 80, 25);
		loginPanel.add(nicknameLabel);

		userText = new JTextField(20);
		userText.setBounds(150, 20, 100, 25);
		loginPanel.add(userText);
		loginFrame.setVisible(true);

		difficultyLabel = new JLabel("Schwierigkeitsgrad:");
		difficultyLabel.setBounds(10, 80, 130, 25);
		loginPanel.add(difficultyLabel);

		difficultylevelLabel = new JLabel("(1)leicht  (2)mittel  (3)schwer");
		difficultylevelLabel.setBounds(10, 140, 180, 25);
		loginPanel.add(difficultylevelLabel);

		difficultyText = new JTextField(20);
		difficultyText.setBounds(150, 80, 100, 25);
		loginPanel.add(difficultyText);
		loginFrame.setVisible(true);

		button = new JButton("START");
		button.setBounds(200, 140, 80, 25);
		loginPanel.add(button);
		button.addActionListener(new Login());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// immer bei Klick des START Knopfs
		String nickname = userText.getText();
		String difficulty = difficultyText.getText();
		

		for (int i =  0; i < 1; i++) {

			String difficultySelection = difficulty;
			if (difficulty.equalsIgnoreCase("leicht")||difficulty.equals("1")||difficulty.equalsIgnoreCase("(1)leicht")){
				difficultySelection = "leichteste Stufe";
				System.out.println("Willkommen bei SUDOKU "+nickname+"!\nDu hast die "+difficultySelection+" ausgewählt, viel Erfolg!");
				loginFrame.setVisible(false);
				break;
			}else if(difficulty.equalsIgnoreCase("mittel")||difficulty.equals("2")||difficulty.equalsIgnoreCase("(2)mittel")) {
				difficultySelection = "mittlere Stufe";
				System.out.println("Willkommen bei SUDOKU "+nickname+"!\nDu hast die "+difficultySelection+" ausgewählt, viel Erfolg!");
				loginFrame.setVisible(false);
				break;
			}else if (difficulty.equalsIgnoreCase("schwer")||difficulty.equalsIgnoreCase("3")||difficulty.equalsIgnoreCase("(3)achwer")) {
				difficultySelection = "schwerste Stufe";
				System.out.println("Willkommen bei SUDOKU "+nickname+"!\nDu hast die "+difficultySelection+" ausgewählt, viel Erfolg!");
				loginFrame.setVisible(false);
				break;
			}else {
				System.out.println("Überprüfe deine Eingabe, einer der folgenden Werte muss enthalten sein: leicht / mittel / schwer");
			}
		}
		
	}
	public void Banner() {
		
	}

    
            
        
        board = new Board(3);
        board.generateBoard();
        size = board.getSize();
        fullSize = size * size;

        createWindow();

    }

    private static void createWindow() {

        window = new JFrame();

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

        JPanel buttonLayout = getjPanel();

        body.add(buttonLayout);

        window.add(body);

        window.setVisible(true);

    }

    private static JPanel getjPanel() {
        JPanel buttonLayout = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonLayout.setPreferredSize(new Dimension(500, 70));

        JButton newButton = new JButton();
        newButton.setText("New Game");
        newButton.setMnemonic(KeyEvent.VK_N);
        newButton.addActionListener(e -> System.out.println("New Game button pressed"));

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