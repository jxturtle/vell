package voxspell.voxspellApp;

import java.util.ArrayList;

import java.util.Collections;

import voxspell.GameListener;
import voxspell.StatsModel;
import voxspell.StatsModelAdapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class GameLogic {
	private ArrayList<GameListener> _listeners;
	private int _wordCap;
	private JTextArea _outputArea;
	private JTextField _inputField;
	private JButton _start;
	private String userIn;
	private boolean fin;
	private int loopGame = 1;
	private int cnt;
	private int _level;
	String _command;
//	private StatsModel _statsModel;
//	private StatsModelAdapter _statsModelAdapter;
	private ArrayList<String> _words;
	private GameConfig _config;
//	private int _numWordCorrect;

	public GameLogic(int level, int wordCap, JTextArea outputArea, JTextField inputField, JButton start, ArrayList<GameListener> listeners) {
		_level = level;
		_listeners = listeners;
		_wordCap = wordCap;
		_outputArea = outputArea;
		_inputField = inputField;
		_start = start;
//		_statsModel = new StatsModel(level);
//		_statsModelAdapter = new StatsModelAdapter(_statsModel, 0);
	}
	
	private void getUserInput(final String randomWord,final JTextField input, final JTextArea output) {
		input.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println(_statsModel.getCorrect() + " " + "correct");
//				System.out.println(_statsModelAdapter.getLength() + " " + "length");
				if (cnt < 2) {
//					String command = "";
					String userInput = input.getText();
					userIn = userInput;
					input.setText("");
					output.append(userIn + "\n");

					if (randomWord.equalsIgnoreCase(userInput)) {
						cnt += 3;
						wordIsCorrect();
					
						
						try {
							_command = "echo Correct. | festival --tts";
							VoiceWorker worker = new VoiceWorker(_command);
							worker.execute();
//							ProcessBuilder npb = new ProcessBuilder("bash", "-c", command);
//							Process process = npb.start();
//							process.waitFor();
							output.append("Correct. \n");
							fin = true;
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						cnt += 1;
						try {
							if (cnt == 1) {
								//System.out.println(_statsModelAdapter.getLength() + " " + "length");
								_command = "echo Incorrect. Please try again.... " + randomWord + "... " + randomWord + " | festival --tts";
								output.append("Incorrect. Please try again.\n");
								output.append("Enter your selection: ");
							} else {
								fin = true;
								_command = "echo Incorrect. | festival --tts";
								output.append("Incorrect.\n");
							}
							VoiceWorker worker = new VoiceWorker(_command);
							worker.execute();
//							ProcessBuilder npb = new ProcessBuilder("bash", "-c", command);
//							Process process = npb.start();
//							process.waitFor();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					//2 is for straight up incorrect
					//3 is for correct
					//4 is for faulted
//					System.out.println(_wordCap);

					System.out.println(_listeners.get(0).getLength() < 9);
					if (fin && _wordCap > 1 && _listeners.get(0).getLength() < 9) {
						GameLogic experimentalNewGame = new GameLogic(_level, _wordCap-1, _outputArea, _inputField, _start, _listeners);
						experimentalNewGame.playGame(_words);
					} else if (fin && _wordCap == 1 || _listeners.get(0).getLength() >= 9) {
						System.out.println(_level);
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
								//_start.setText("Finish the quiz");
								//_start.setVisible(true);
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
//		System.out.println(_statsModelAdapter.getLength() + " " + "length");
//		System.out.println(_statsModel.getCorrect() + " " + "correct");
//		GameConfig config = GameConfig.instance(); 
//		_words = config.getLevelWords(_level); 
		_words = words;
		String randomWord = getRandomWord(_words);
		_outputArea.append("Enter your selection: ");
		getUserInput(randomWord, _inputField, _outputArea);
		System.out.println(randomWord);
		try {			
			_command = "echo Please spell.... " + randomWord + " | festival --tts";
			VoiceWorker worker = new VoiceWorker(_command);
			worker.execute();
//			System.out.println(command);
//			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
//			Process process = pb.start();
//			process.waitFor();		
//			process.destroy();
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