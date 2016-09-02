package spelling_aid;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class SpellingAid extends JPanel {
	private JButton _newGame;
	private JButton _reviewMistakes;
	private JButton _viewStatistics;
	private JButton _clearStatistics;
	private JTextArea _outputText;
	private JTextField _inputTextField;
	public SpellingAid() {
		buildGameScreen();
		
		//setUpEventHandlers(_outputText);
	}
	private void buildGameScreen() {
		//Creates the main frame for user output
		JTextArea _outputText = new JTextArea(25,40);
		_outputText.append("\n");
		_outputText.append("Welcome to the Spelling Aid!\n");
		_outputText.append("Please select from one of the Game Options Below.\n");
		_outputText.setEditable(false);
		JScrollPane scroll = new JScrollPane(_outputText);
		
		//Creates the text field for user input to play the game
		JTextField _inputTextField = new JTextField();
//		_inputTextField.addActionListener(new ActionListener() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			_outputText.append(_inputTextField.getText());
//			}
//		});
		
		//Creates the Game Options user interface with their
		//corresponding listeners.
		
		JPanel gameOptions = new JPanel(new GridLayout(2,2,10,10));
		gameOptions.setBorder(BorderFactory.createTitledBorder("Game Options"));
		
		//New Spelling Game button
		_newGame = new JButton("New Spelling Quiz");
		registerOptionIfClicked(_newGame, _outputText, _inputTextField);
		
		//Review Mistakes Game Button 
		_reviewMistakes = new JButton("Review Mistakes");
		registerOptionIfClicked(_reviewMistakes, _outputText, _inputTextField);
		//View statistics button
		_viewStatistics = new JButton("View Statistics");
		registerOptionIfClicked(_viewStatistics, _outputText, _inputTextField);
		
		//Clear statistics button
		_clearStatistics = new JButton("Clear Statistics");
		registerOptionIfClicked(_clearStatistics, _outputText, _inputTextField);
		
		//Adding all game options to the game panel
		gameOptions.add(_newGame);
		gameOptions.add(_reviewMistakes);
		gameOptions.add(_viewStatistics);
		gameOptions.add(_clearStatistics);
		
		setLayout(new BorderLayout());
		JPanel gameUI  = new JPanel(new BorderLayout());
		gameUI.add(scroll, BorderLayout.NORTH);
		gameUI.add(_inputTextField, BorderLayout.SOUTH);
		add(gameUI, BorderLayout.NORTH);
		add(gameOptions, BorderLayout.SOUTH);
		
		
	}
	
	
	//This method has a switch statement on the type of button clicked. The buttons are
	//registered as ActionListeners.
	private void registerOptionIfClicked(final JButton btn, final JTextArea outputField, final JTextField inputField) {
		btn.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			outputField.append("\n");

			switch (btn.getText()) {
			case "New Spelling Quiz":
				outputField.append("Starting a new Spelling Quiz Game...\n");
				outputField.append("Please spell out the three words.\n");
				outputField.append("===========================================\n");

				PlayGame newGame = new PlayGame("wordlist", 3, outputField, inputField, "newQuiz");	
				int cnt = 0;
				newGame.playGame();
				break;
			case "Review Mistakes":
				outputField.append("Review Mistakes selected\n");
				outputField.append("Please spell out up to three of your mistakes.\n");
				outputField.append("===========================================\n");

				try {
					BufferedReader reader = new BufferedReader(new FileReader("failed.txt"));
					int lines = 0;
					while (reader.readLine() != null) lines++;
					reader.close();			
					if (lines != 0) {
						if (lines < 3) {
							PlayGame reviewMistakes = new PlayGame("failed.txt", lines, outputField, inputField, "review");
							reviewMistakes.playGame();
						} else {
							PlayGame reviewMistakes = new PlayGame("failed.txt", 3, outputField, inputField, "review");
							reviewMistakes.playGame();
						}
					} else {
						outputField.append("You do not have any mistakes so far.");
					}
				} catch (FileNotFoundException e1) {
					outputField.append("You do not have any mistakes so far.");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			
				break;
			case "View Statistics":
				outputField.append("Showing Statistics\n");

				StatisticsManager statsManager = new StatisticsManager(outputField);
				statsManager.getStats();
				break;
			case "Clear Statistics":		
				StatisticsManager.clearHistory();
				break;
					
			}
			}
		});
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Spelling Aid");
		JComponent newContentPane = new SpellingAid();
		frame.add(newContentPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});

	}



}
