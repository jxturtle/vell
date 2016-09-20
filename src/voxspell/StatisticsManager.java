package voxspell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import voxspell.voxspellApp.TitleScreen;

public class StatisticsManager {
	public static final File MASTEREDSTATS = new File(".masteredStats");
	public static final File FAULTEDSTATS = new File(".faultedStats");
	public static final File FAILEDSTATS = new File(".failedStats");
	public static final File WORDSUSED = new File(".wordsUsed");
	public static final File MASTERED = new File(".mastered");
	public static final File FAULTED = new File(".faulted");
	public static final File FAILED = new File(".failed");
	private File[] statsFileArray = {MASTEREDSTATS, FAULTEDSTATS, FAILEDSTATS, WORDSUSED, MASTERED, FAULTED, FAILED};
	private List<String> masteredWords, faultedWords, failedWords, wordsUsed;
	private List<List<String>> listOfLists;
	private JTextArea _outputField;
	private int _fileNotFound, _correctCount, _incorrectCount;
	private int[] _percentage = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	private int _thisLevel;
	
	//Constructor adds contents of each of the stats files to a linkedList.
	public StatisticsManager(JTextArea outputField) {
		_outputField = outputField;
		listOfLists = new LinkedList<List<String>>();
		for (int i = 0; i < 4; i++) {
			listOfLists.add(new LinkedList<String>());
		}
		for (int i = 0; i < 4; i++) {
			if (statsFileArray[i].exists()) {
				_fileNotFound += 1;
				Scanner sc = null;
				try {
					sc = new Scanner(statsFileArray[i]);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				while (sc.hasNextLine()) {
					Scanner sc2 = new Scanner(sc.nextLine());
					while (sc2.hasNext()) {
						String s = sc2.next();
						listOfLists.get(i).add(s);
					}
				}	
			}
		}
		for (List<String> contents: listOfLists) {
			Collections.sort(contents);			
		};
		masteredWords = listOfLists.get(0);
		faultedWords = listOfLists.get(1);
		failedWords = listOfLists.get(2);
		wordsUsed = listOfLists.get(3);
	}
	//Gets the View Statistics functionality of the program, and prints it out to the
	//JTextArea. Numbers are stored in Occurrences. 
	public void getStats() {
		_outputField.append(" Game Statistics \n");
		int previousLevel = -1;
		if (_fileNotFound == 0) {
			_outputField.append("=================================\n");
			_outputField.append("There is no game history recorded.\n");
			_outputField.append("Please play a New Game.\n");
		} else {
			for (String word: wordsUsed) {
				_thisLevel = Integer.parseInt(word.split("\\$")[0].substring(1));
				int masteredOccurrences = Collections.frequency(masteredWords, word);
				int faultedOccurrences = Collections.frequency(faultedWords, word);
				int failedOccurrences = Collections.frequency(failedWords, word);				
				if (previousLevel != _thisLevel) {
					_outputField.append("=================================\n");
					_outputField.append("                              Game Level: " + word.split("\\$")[0].substring(1) + "\n");
					_outputField.append("=================================\n");
					_correctCount = 0;
					_incorrectCount = 0;
				}								
				previousLevel = _thisLevel;
				
				_correctCount += masteredOccurrences+faultedOccurrences;
				_incorrectCount += failedOccurrences;
				
				_outputField.append(" " + word.split("\\$")[1]+"\n");
				String str = "\tMastered: " + masteredOccurrences + "\tFaulted: " + faultedOccurrences + "\tFailed: " + failedOccurrences;
				_outputField.append(str+"\n");
				_percentage[previousLevel-1] = ((_correctCount*100/(_correctCount+_incorrectCount)));
			}
			
			_outputField.append("=================================\n");
		}	
	}
	public int[] getLevelPercentage() {
		return _percentage;
	}
	
	//Deals with the clear history component of the program. Asks for confirmation.
	public void clearHistory() {
		for (File file: statsFileArray) {
			file.delete();
		}

	}
}