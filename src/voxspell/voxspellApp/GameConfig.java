package voxspell.voxspellApp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GameConfig {
	//Name of the file, placed in the same location as the source folder
	//of the current directory of the project.
	private static final String FILENAME = "NZCER-spelling-lists.txt";
	private ArrayList<String> _words;
	private HashMap<Integer, ArrayList<String>> _wordMap;
	
	private static GameConfig instance;
	public static GameConfig instance() {
		if (instance == null) {
			instance = new GameConfig();
		}
		return instance;
	}
	
	private GameConfig() {
		System.out.println(new File(FILENAME).isFile());
		//Empty implementation. call variable _wordMap
		//Load all of the words into a dictionary/hashtable with
		//the keys being the level, and the ArrayList<String> being
		//the word values for that level. 
		//Subject to change...
	}
	
	
	
	public ArrayList<String> getLevelWords(int level) { 
		//Empty implementation
		//Using the _wordMap, we will get the words for our
		//level, passed in as a parameter to this method.
		return _words;
	}
	
	public static void main(String[] args) {
		GameConfig config = GameConfig.instance();
		
	}
}