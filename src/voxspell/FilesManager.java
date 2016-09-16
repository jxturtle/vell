package voxspell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FilesManager {
	public static final String WORDSASSESSED = "wordsUsed.txt";
	private String _word;
	private int _token;
	private String _fileName;
	private String _statsFileName;
	
	public FilesManager(String word, int token) {
		_word = word;
		_token = token;
		setUpFileNames();		
	}
	
	public void manageFiles() {
		writeToStatsFile();
		writeToFile(_fileName);
		writeToFile(WORDSASSESSED);
	}
	
	private void setUpFileNames() {
		if (_token == 3) {
			_fileName = "mastered.txt";
			_statsFileName = "masteredStats.txt";
		} else if (_token == 2) {
			_fileName = "failed.txt";
			_statsFileName = "failedStats.txt";
		} else if (_token == 4) {
			_fileName = "mastered.txt";
			_statsFileName = "faultedStats.txt";
		}
		//The difference between writeToStatsFile and writeToFile is that writeToFile checks if
		//the word to be added already exists in the file (Word will be added to the
		// _file If and only if it doesn't exist already)
		//writeToStatsFile();
		//writeToFile(_fileName);
		//writeToWordsUsed keeps track of all the words assessed.
		//writeToFile(WORDSASSESSED);
		//Token == 3 indicates that a word has been spelled correctly.
		//, and 4 if the word has been faulted. If either, remove the
		//correct word from failed and faulted. 
		if (_token == 3 || _token == 4) {
			try {
				File failedFile = new File("failed.txt");
				File faultedFile = new File("faulted.txt");
				if (failedFile.exists()) {
					remakeFileWithoutWord(failedFile);
				}
				if (faultedFile.exists()) {
					remakeFileWithoutWord(faultedFile);
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		private void remakeFileWithoutWord(File inputFile) throws IOException {
			File tempFile = new File("tempWordList.txt");
			
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				if (trimmedLine.equalsIgnoreCase(_word)) continue;
				writer.write(currentLine + "\n");
			}
			reader.close();
			writer.close();
			inputFile.delete();
			tempFile.renameTo(inputFile);
		
	}

		private void writeToStatsFile() {
			try {
				File statsFile = new File(_statsFileName);
				
				if (!statsFile.exists()) {
					statsFile.createNewFile();
				}
				
				BufferedWriter fileWriter = new BufferedWriter(new FileWriter(statsFile.getName(), true));
				//BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
				fileWriter.write(_word + "\n");
				fileWriter.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void writeToFile(String fileName) {
		try {
			File file = new File(fileName);
					
			if (!file.exists()) {
				file.createNewFile();
				BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file.getName(), true));
				fileWriter.write(_word + "\n");
				fileWriter.close();
			} else {
				FileReader fr = new FileReader(fileName);
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(fr);
				String wordFromFile;
				boolean found = false;
				while ((wordFromFile = br.readLine()) != null) {
		
					if (wordFromFile.equals(_word)) {
						found = true;
					}
					
				}
				if (!found) {
					BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file.getName(), true));
					fileWriter.write(_word + "\n");
					fileWriter.close();
				}
				
			}
		} catch (IOException e ){
			e.printStackTrace();
		}
		

	}
	
}
