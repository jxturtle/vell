package voxspell.voxspellApp;

import java.util.ArrayList;
import java.util.Collections;

import voxspell.GameEvent;
import voxspell.GameListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameLogic {
	private ArrayList<GameListener> _listeners;
	private int _wordCap;
	private JTextArea _outputArea;
	private JTextField _inputField;
	private JButton _start, _submit;
	private String userIn;
	private boolean fin;
	private int cnt;
	private int _level;
	String _command;
	private ArrayList<String> _words;

	public GameLogic(int level, int wordCap, JTextArea outputArea, JTextField inputField, JButton start, JButton submit, ArrayList<GameListener> listeners) {
		_level = level;
		_listeners = listeners;
		_wordCap = wordCap;
		_outputArea = outputArea;
		_inputField = inputField;
		_start = start;
		_submit = submit;
	}
	
	private void getUserInput(final String randomWord,final JTextField input, final JTextArea output, final JButton submit) {
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cnt < 2) {
					String userInput = input.getText();
					userIn = userInput;
					input.setText("");
					output.append(userIn + "\n");
					if (randomWord.equalsIgnoreCase(userInput)) {
						cnt += 3;
						fire(GameEvent.makeCorrectEvent());
						try {
							_command = "echo Correct. | festival --tts";
							VoiceWorker worker = new VoiceWorker(0, _command);
							worker.execute();
							output.append("Correct. \n");
							fin = true;
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						cnt += 1;
						try {
							if (cnt == 1) {
								_command = "echo Incorrect. Please try again.... " + randomWord + "... " + randomWord + " | festival --tts";
								output.append("Incorrect. Please try again.\n");
								output.append("Enter your selection: ");
							} else {
								fin = true;
								_command = "echo Incorrect. | festival --tts";
								fire(GameEvent.makeIncorrectEvent());
							}
							VoiceWorker worker = new VoiceWorker(0, _command);
							worker.execute();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					//2 is for straight up incorrect
					//3 is for correct
					//4 is for faulted
					if (fin && _wordCap > 1 && _listeners.get(0).getLength() < 9) {
						GameLogic experimentalNewGame = new GameLogic(_level, _wordCap-1, _outputArea, _inputField, _start, _submit, _listeners);
						experimentalNewGame.playGame(_words);
					} else if (fin && _wordCap == 1 || _listeners.get(0).getLength() >= 9) {
						if (_level < 11) {
							int test = JOptionPane.showConfirmDialog(null, "Would you like to move on to the next level?", "Level finished", JOptionPane.YES_NO_OPTION);
							switch(test) {
							case JOptionPane.YES_OPTION:
								_start.setText("Begin the next level");
								_start.setVisible(true);
								break;
							case JOptionPane.NO_OPTION:
								_start.setText("Repeat the same level");
								_start.setVisible(true);
								break;
							default:
								break;
							}
						} else {
							int test = JOptionPane.showConfirmDialog(null, "Would you like to repeat this level?", "Level finished", JOptionPane.YES_NO_OPTION);
							switch(test) {
							case JOptionPane.YES_OPTION:
								_start.setText("Repeat the same level");
								_start.setVisible(true);
								break;
							case JOptionPane.NO_OPTION:
								_outputArea.append("==============================\n");
								_outputArea.append("Game has finished. \n");
								_outputArea.append("==============================\n");
								break;
							default:
								break;
							}
						}
					}
				}
			}
		});
	}
	public void playGame(ArrayList<String> words) {
		_words = words;
		String randomWord = getRandomWord(_words);
		_outputArea.append("Enter your selection: ");
		getUserInput(randomWord, _inputField, _outputArea, _submit);
		System.out.println(randomWord);
		try {			
			_command = "echo Please spell.... " + randomWord + " | festival --tts";
			VoiceWorker worker = new VoiceWorker(1000, _command);
			worker.execute();
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
	public void fire(GameEvent e) {
		for (GameListener listener : _listeners) {
			listener.updateProgressBar(e);
		}
	}
}
