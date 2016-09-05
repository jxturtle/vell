package voxspell.voxspellApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameConfig {
	//Name of the file, placed in the same location as the source folder
	//of the current directory of the project.
	private static final String FILENAME = "NZCER-spelling-lists.txt";
	private ArrayList<String> _words;
	private ArrayList<String> _wordArray;
	private HashMap<Integer, ArrayList<String>> _wordMap;
	private int _key;
	
	private static GameConfig instance;
	public static GameConfig instance() {
		if (instance == null) {
			instance = new GameConfig();
		}
		return instance;
	}
	
	private GameConfig() {
		try {
			HashMap<Integer, ArrayList<String>> _wordMap = new HashMap<Integer, ArrayList<String>>();
			ArrayList<String> _wordArray = new ArrayList<String>();
			Scanner scanner = new Scanner(new File(FILENAME));
			while (scanner.hasNextLine()) {
				
				String token = scanner.nextLine();
				//System.out.println(_wordArray);
				if (token.contains("%Level")) {
					//System.out.println(_wordMap);
					if (!_wordArray.isEmpty()) {
						_key = Integer.parseInt(token.split(" ")[1]);
						//System.out.println(_key-1 + " " + _wordArray);
						_wordMap.put(_key-1, _wordArray);
						//System.out.println(_wordMap);
						_wordArray = new ArrayList<String>();

					}
					
				} else {
					_wordArray.add(token);
				}
				
			}
				//System.out.println(_key + " " + _wordArray);
				_wordMap.put(_key, _wordArray);
				System.out.println(_wordMap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public ArrayList<String> getLevelWords(int level) { 
		_words = _wordMap.get(level);
		return _words;
	}
	
	public static void main(String[] args) {
		GameConfig config = GameConfig.instance();
		config.getLevelWords(2);
		
	}
}