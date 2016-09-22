package voxspell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FilesManager {
	public static final String WORDSASSESSED = ".wordsUsed";
	private int _token, _level;
	private String _word, _fileName, _statsFileName;
	private String[] tokens = {"a1","b2","c3","d4","e5","f6","g7","h8","i9","j10","k11"};
	
	public FilesManager(String word, int level, int token) {
		_word = word;
		_level = level;
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
			_fileName = ".mastered";
			_statsFileName = ".masteredStats";
		} else if (_token == 2) {
			_fileName = ".failed";
			_statsFileName = ".failedStats";
		} else if (_token == 4) {
			_fileName = ".mastered";
			_statsFileName = ".faultedStats";
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
				File failedFile = new File(".failed");
				File faultedFile = new File(".faulted");
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
		File tempFile = new File(".tempWordList");		
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			if (_level != 0) {
				if (trimmedLine.equalsIgnoreCase(tokens[_level-1]+"$"+_word)) continue;
			} else {
				if (trimmedLine.split("\\$")[1].replaceAll("#"," ").equals(_word)) continue;
			}
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
			if (_level != 0) {
				fileWriter.write(tokens[_level-1]+"$"+_word.replaceAll(" ", "#") + "\n");
			}			
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
				fileWriter.write(tokens[_level-1]+"$"+_word.replaceAll(" ", "#") + "\n");
				fileWriter.close();
			} else {
				FileReader fr = new FileReader(fileName);
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(fr);
				String wordFromFile;
				boolean found = false;
				while ((wordFromFile = br.readLine()) != null) {
					if (_level != 0) {
						if (wordFromFile.equals(tokens[_level-1]+"$"+_word.replaceAll(" ", "#"))) {
							found = true;
						} 
					
					}
				}
				if (!found) {
					BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file.getName(), true));
					if (_level != 0) {
						fileWriter.write(tokens[_level-1]+"$"+_word.replaceAll(" ","#") + "\n");
					}					
					fileWriter.close();
				}
			}
		} catch (IOException e ){
			e.printStackTrace();
		}
	}
}