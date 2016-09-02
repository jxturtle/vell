package spelling_aid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PlayGame {

	private String _file;
	private int _wordCap;
	private String _wordFromFile;
	private JTextArea _outputArea;
	private JTextField _inputField;
	private List<String> words;
	private String userIn;
	private boolean fin;
	private String _gameType;
	private int loopGame = 1;
	private int cnt;
	
	public PlayGame(String file, int wordCap, JTextArea outputArea, JTextField inputField, String gameType) {
		_file = file;
		_wordCap = wordCap;
		_outputArea = outputArea;
		_inputField = inputField;
		_gameType = gameType;
		words = new LinkedList<>();
		setupGame();
		}
	
	private void setupGame() {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(_file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc.hasNextLine()) {
			Scanner sc2 = new Scanner(sc.nextLine());
			while (sc2.hasNext()) {
				String s = sc2.next();
				words.add(s);
			}
		}
	}
	
	private String getRandomWord() {
		Collections.shuffle(words);
		String pickWord = words.get(0);
		return pickWord;
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
					try {
						command = "echo Correct. | festival --tts";
						ProcessBuilder npb = new ProcessBuilder("bash", "-c", command);
						Process process = npb.start();
						process.waitFor();
						output.append("Correct. \n");
						FilesManager manageFiles = new FilesManager(randomWord, cnt);
						fin = true;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					

				} else {
					cnt += 1;
					try {
						if (cnt == 1) {
							if (_gameType.equals("newQuiz")) {
								command = "echo Incorrect. Please try again.... " + randomWord + "... " + randomWord + " | festival --tts";
								output.append("Incorrect. Please try again.\n");
								output.append("Enter your selection: ");
							} else if (_gameType.equals("review")){
								String individualChars = randomWord.replace("", ".. ");
								command = "echo Incorrect. Please try again.... "
										+ "Listen to the words closely... " + randomWord + "... "
										+ individualChars + "| festival --tts";
								output.append("Incorrect. Please try again.\n");
								output.append("Enter your selection: ");
							}
						} else {
							fin = true;
							command = "echo Incorrect. | festival --tts";
							output.append("Incorrect.\n");
							FilesManager manageFiles = new FilesManager(randomWord, cnt);
							

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
					PlayGame experimentalNewGame = new PlayGame(_file, _wordCap-1, _outputArea, _inputField, _gameType);
					experimentalNewGame.playGame();
				}
			}
			}
		});
			
		
	}
	

	public void playGame() {
		String randomWord = getRandomWord();
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
	

	

}
