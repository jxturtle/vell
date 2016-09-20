package voxspell.voxspellApp;

import java.util.ArrayList;
import java.util.Collections;

import voxspell.FilesManager;
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
	private JButton _start, _submit, _back;
	private String userIn;
	private boolean fin, _isVideoOn = false;
	private int cnt;
	private int _level;
	String _command;
	private ArrayList<String> _words;

	public GameLogic(int level, int wordCap, JTextArea outputArea, JTextField inputField, JButton start, JButton back, JButton submit, ArrayList<GameListener> listeners) {
		_level = level;
		_listeners = listeners;
		_wordCap = wordCap;
		_outputArea = outputArea;
		_inputField = inputField;
		_start = start;
		_back = back;
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
							_command = "Correct.";
							VoiceWorker worker = new VoiceWorker(0, _command);
							worker.execute();
							output.append("Correct. \n");
							FilesManager fileManager = new FilesManager(randomWord, _level, cnt);
							fileManager.manageFiles();
							fin = true;
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						cnt += 1;
						try {
							if (cnt == 1) {
								_command = "Incorrect, please try again......... :" + randomWord + "......... :" + randomWord + ".";
								output.append("Incorrect, please try again.\n");
								output.append("Enter your selection: ");
							} else {
								fin = true;
								_command = "Incorrect.";
								output.append("Incorrect.\n");
								FilesManager fileManager = new FilesManager(randomWord, _level, cnt);
								fileManager.manageFiles();
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
					if (_level == 0) {
						if (fin && _wordCap > 1) {
							GameLogic experimentalNewGame = new GameLogic(_level, _wordCap-1, _outputArea, _inputField, _start, _back, _submit, _listeners);
							experimentalNewGame.playGame(_words);
						} else if (fin && _wordCap == 1){
							_outputArea.append("==============================\n");
							_outputArea.append("Game has finished. \n");
							_outputArea.append("==============================\n");
							_back.setVisible(true);
						}
					} else {
						if (fin && _wordCap > 1 && _listeners.get(0).getLength() < 9) {
							GameLogic experimentalNewGame = new GameLogic(_level, _wordCap-1, _outputArea, _inputField, _start, _back, _submit, _listeners);
							experimentalNewGame.playGame(_words);
						} else if (fin && _wordCap == 1 && _listeners.get(0).getLength() < 9) {
							openOptionPaneWhenComplete(_level, _isVideoOn);
						} else if (_listeners.get(0).getLength() >= 9) {
							openOptionPaneAskingVideo();
						}
					}
				}
			}
		});
	}
	
	public void playGame(ArrayList<String> words) {
		_words = words;
		String randomWord = getRandomWord(_words);
		System.out.println(randomWord);
		_outputArea.append("Enter your selection: ");
		getUserInput(randomWord, _inputField, _outputArea, _submit);
		try {			
			_command = "Please spell: ....... " + randomWord;
			VoiceWorker worker = new VoiceWorker(1000, _command);
			worker.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		fire(GameEvent.makeFestivalEvent(), randomWord);
	}
	
	private String getRandomWord(ArrayList<String> words) {
		Collections.shuffle(words);
		String pickWord = words.get(0);
		words.remove(pickWord);
		return pickWord;
	}
	private void fire(GameEvent e) {
		for (GameListener listener : _listeners) {
			listener.updateProgressBar(e);
		}
	}
	private void fire(GameEvent e, String word) {
		for (GameListener listener : _listeners) {
			listener.setWord(e, word);
		}
	}
	private void openOptionPaneAskingVideo() {
		int playVideo = JOptionPane.showConfirmDialog(null, "Play video reward?", "Level Complete!", JOptionPane.YES_NO_OPTION);
		switch(playVideo) {
		case JOptionPane.YES_OPTION:
			_isVideoOn = true;
			openOptionPaneWhenComplete(_level, _isVideoOn);
			VideoProcessor video = new VideoProcessor();
			break;
		case JOptionPane.NO_OPTION:
			openOptionPaneWhenComplete(_level, _isVideoOn);
		}		
	}
	private void openOptionPaneWhenComplete(int level, boolean isVideoOn) {
		String message, yesMessage;
		if (level == 11) {
			if (isVideoOn) {
				message = "After watching video, would you like to repeat this level?";
			} else {
				message = "Would you like to repeat this level?";				
			}
			yesMessage = "Repeat the same level";
		} else {
			if (isVideoOn) {
				message = "After watching video, would you like to move to the next level?";
			} else {
				message = "Would you like to move to the next level?";				
			}
			yesMessage = "Begin the next level";
		}
		int test = JOptionPane.showConfirmDialog(null, message, "Level Complete!", JOptionPane.YES_NO_OPTION);
		switch(test) {
		case JOptionPane.YES_OPTION:
			_start.setText(yesMessage);
			_start.setVisible(true);
			_back.setVisible(true);
			break;
		case JOptionPane.NO_OPTION:
			if (level == 11) {
				_outputArea.append("==============================\n");
				_outputArea.append("Game has finished. \n");
				_outputArea.append("==============================\n");
			} else {
				_start.setText("Repeat the same level");
				_start.setVisible(true);
			}
			_back.setVisible(true);
			break;
		default:
			break;			
		}
	}
}