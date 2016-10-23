package voxspell.voxspellApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for getting the words to be played
 * in the "Review Mistakes" game mode. A different implementation is required
 * as we write words to files in different format to that of the NZCER spelling word
 * text file. This class reads from the .failed file if it exists.
 * @author Andon XIa
 */
 
public class ReviewConfig {
	private static final String FILENAME = ".failed";
	private ArrayList<String> _wordArray;
	private int _lines;
	public ReviewConfig() {
		_wordArray = new ArrayList<String>();
		Scanner scanner;
		File failedFile = new File(FILENAME);
		if (failedFile.exists()) {
			try {
				scanner = new Scanner(new File(FILENAME));		
				while (scanner.hasNextLine()) {
					String token = scanner.nextLine();
					String word = token.split("\\$")[1];
					_wordArray.add(word.replaceAll("#", " "));
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public ArrayList<String> getWords() {
		return _wordArray;
	}
}
