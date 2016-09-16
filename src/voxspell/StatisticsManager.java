package voxspell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class StatisticsManager {
	public static final File MASTEREDSTATS = new File("masteredStats.txt");
	public static final File FAULTEDSTATS = new File("faultedStats.txt");
	public static final File FAILEDSTATS = new File("failedStats.txt");
	public static final File WORDSUSED = new File("wordsUsed.txt");
	public static final File MASTERED = new File("mastered.txt");
	public static final File FAULTED = new File("faulted.txt");
	public static final File FAILED = new File("failed.txt");
	
	private List<String> masteredWords;
	private List<String> faultedWords;
	private List<String> failedWords;
	private List<String> usedWords;
	private JTextArea _outputField;
	private int _fileNotFound;
	
	//Constructor adds contents of each of the stats files to a linkedList.
	public StatisticsManager(JTextArea outputField) {
		masteredWords = new LinkedList<>();
		faultedWords = new LinkedList<>();
		failedWords = new LinkedList<>();
		usedWords = new LinkedList<>();
		_outputField = outputField;
		
		if (MASTEREDSTATS.exists()) {
			_fileNotFound += 1;
			Scanner sc = null;
			try {
				sc = new Scanner(MASTEREDSTATS);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while (sc.hasNextLine()) {
				Scanner sc2 = new Scanner(sc.nextLine());
				while (sc2.hasNext()) {
					String s = sc2.next();
					masteredWords.add(s);
				}
			}
		}
		
		if (FAULTEDSTATS.exists()) {
			_fileNotFound += 1;
			Scanner sc = null;
			try {
				sc = new Scanner(FAULTEDSTATS);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while (sc.hasNextLine()) {
				Scanner sc2 = new Scanner(sc.nextLine());
				while (sc2.hasNext()) {
					String s = sc2.next();
					faultedWords.add(s);
				}
			}
		}
		
		if (FAILEDSTATS.exists()) {
			_fileNotFound += 1;
			Scanner sc = null;
			try {
				sc = new Scanner(FAILEDSTATS);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while (sc.hasNextLine()) {
				Scanner sc2 = new Scanner(sc.nextLine());
				while (sc2.hasNext()) {
					String s = sc2.next();
					failedWords.add(s);
				}
			}
		}
		if (WORDSUSED.exists()) {
			Scanner sc = null;
			try {
				sc = new Scanner(WORDSUSED);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			while (sc.hasNextLine()) {
				Scanner sc2 = new Scanner(sc.nextLine());
				while (sc2.hasNext()) {
					String s = sc2.next();
					usedWords.add(s);
				}
			}
		}
		
	}
	
	//Gets the View Statistics functionality of the program, and prints it out to the
	//JTextArea. Numbers are stored in Occurrences. 
	public void getStats() {
		if (_fileNotFound == 0) {
			_outputField.append("===========================================\n");
			_outputField.append("There is no game history recorded. Please play a New Game.\n");
		} else {
		for (String word:usedWords) {
			int masteredOccurrences = Collections.frequency(masteredWords, word);
			int faultedOccurrences = Collections.frequency(faultedWords, word);
			int failedOccurrences = Collections.frequency(failedWords, word);
			_outputField.append("===========================================\n");
			_outputField.append("Word: " + word + "\n");
			_outputField.append("Mastered " + masteredOccurrences + " times.\n");
			_outputField.append("Faulted " + faultedOccurrences + " times. \n");
			_outputField.append("Failed " + failedOccurrences + " times. \n");

			}
		_outputField.append("===========================================\n");
		}
		
	}

	//Deals with the clear history component of the program. Asks for confirmation.
	public static void clearHistory() {
		int p = JOptionPane.showConfirmDialog(null,  "Are you sure you wish to clear the game history?", "Clear History", JOptionPane.YES_NO_OPTION);
		if (p==0) {
			MASTEREDSTATS.delete();
			FAULTEDSTATS.delete();
			FAILEDSTATS.delete();
			WORDSUSED.delete();
			FAILED.delete();
			FAULTED.delete();
			MASTERED.delete();
		}

		
	}

}
