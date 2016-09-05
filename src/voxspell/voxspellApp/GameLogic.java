package voxspell.voxspellApp;

import java.util.ArrayList;

import java.util.Collections;

import voxspell.GameListener;
import voxspell.StatsModel;
import voxspell.StatsModelAdapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameLogic {
	private static ArrayList<GameListener> _listeners;
	private int _wordCap;
	private JTextArea _outputArea;
	private JTextField _inputField;
	private String userIn;
	private boolean fin;
	private int loopGame = 1;
	private int cnt;
	private int _level;
	String _command;
	private StatsModel _statsModel;
	private StatsModelAdapter _statsModelAdapter;
	private ArrayList<String> _words;
	private GameConfig _config;
	private int _numWordCorrect;

	public GameLogic(int level, int wordCap, JTextArea outputArea, JTextField inputField, ArrayList<GameListener> listeners) {
		_level = level;
		_listeners = listeners;
		_wordCap = wordCap;
		_outputArea = outputArea;
		_inputField = inputField;
		_statsModel = new StatsModel(level);
		_statsModelAdapter = new StatsModelAdapter(_statsModel, _numWordCorrect);
	}
	
	private void getUserInput(final String randomWord,final JTextField input, final JTextArea output) {
		input.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cnt < 2) {
					String command = "";
					String userInput = input.getText();
					userIn = userInput;
					input.setText("");
					output.append(userIn + "\n");

					if (randomWord.equalsIgnoreCase(userInput)) {
						cnt += 3;
						wordIsCorrect();
						try {
							command = "echo Correct. | festival --tts";
							ProcessBuilder npb = new ProcessBuilder("bash", "-c", command);
							Process process = npb.start();
							process.waitFor();
							output.append("Correct. \n");
							fin = true;
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						cnt += 1;
						try {
							if (cnt == 1) {
								command = "echo Incorrect. Please try again.... " + randomWord + "... " + randomWord + " | festival --tts";
								output.append("Incorrect. Please try again.\n");
								output.append("Enter your selection: ");
							} else {
								fin = true;
								command = "echo Incorrect. | festival --tts";
								output.append("Incorrect.\n");
							}
							ProcessBuilder npb = new ProcessBuilder("bash", "-c", command);
							Process process = npb.start();
							process.waitFor();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					//2 is for straight up incorrect
					//3 is for correct
					//4 is for faulted
					if (fin && _wordCap > 1) {
						GameLogic experimentalNewGame = new GameLogic(_level, _wordCap-1, _outputArea, _inputField, _listeners);
						experimentalNewGame.playGame();
					}
				}
			}
		});
	}
	public void playGame() {
		GameConfig config = GameConfig.instance(); 
		_words = config.getLevelWords(_level); 
		String randomWord = getRandomWord(_words);
		System.out.println(randomWord);
		_outputArea.append("Enter your selection: ");
		getUserInput(randomWord, _inputField, _outputArea);
		try {			

			String command = "echo Please spell.... " + randomWord + " | festival --tts";
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			Process process = pb.start();
			process.waitFor();		
			process.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getRandomWord(ArrayList<String> words) {
		Collections.shuffle(words);
		String pickWord = words.get(0);
		words.remove(pickWord);
		return pickWord;
	}

	public void wordIsCorrect() {
		for (GameListener listener : _listeners) {
			listener.updateProgressBar();
		}
	}
}
