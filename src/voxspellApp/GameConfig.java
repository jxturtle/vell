package voxspell.voxspellApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class sets up the list of words for each level to play a
 * Spelling Game. This class is responsible only to select words
 * for a "New Game" and not "Review Mistakes" ("Review Mistakes" 
 * has its own configuration to obtain words).
 * The name of the words file is NZCER-spelling-lists.txt.
 * @author Andon Xia
 */
public class GameConfig {
	//Name of the file, placed in the same location as the source folder
	//of the current directory of the project.
	private static final String FILENAME = "NZCER-spelling-lists.txt";
	private ArrayList<String> _words;
	private ArrayList<String> _wordArray;
	private ArrayList<String> _wordsCopy;
	private HashMap<Integer, ArrayList<String>> _finalWordMap;
	private int _key, _level;
	
	/*
	* This method reads line by line of the specified file,
	* and puts each word into the level they appear in into a
	* HashMap with the key value being the level of the word,
	* and the value being the actual word.
	*/
	public GameConfig() {
		try {
			HashMap<Integer, ArrayList<String>> _wordMap = new HashMap<Integer, ArrayList<String>>();
			ArrayList<String> _wordArray = new ArrayList<String>();
			Scanner scanner = new Scanner(new File(FILENAME));
			while (scanner.hasNextLine()) {				
				String token = scanner.nextLine();
				String trimmedToken = token.trim();
				if (token.contains("%Level")) {
					if (!_wordArray.isEmpty()) {
						_key = Integer.parseInt(trimmedToken.split(" ")[1]);
						_wordMap.put(_key-1, _wordArray);
						_wordArray = new ArrayList<String>();
					}	
				} else {
					_wordArray.add(trimmedToken);
				}				
			}
				_wordMap.put(_key, _wordArray);
				_finalWordMap = _wordMap;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	* Returns the list of words for the input level parameter.
	*/
	
	public ArrayList<String> getLevelWords(int level) { 	
		_words = _finalWordMap.get(level);
		return _words;
	}
}
