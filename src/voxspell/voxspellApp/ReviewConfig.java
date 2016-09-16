package voxspell.voxspellApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReviewConfig {
	private static final String FILENAME = "failed.txt";
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
					_wordArray.add(token);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void printstuff() {
		System.out.println(_wordArray);
	}
	public ArrayList<String> getWords() {
		return _wordArray;
	}
}