package voxspell.voxspellApp;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import voxspell.GameListener;
import voxspell.StatsModel;
import voxspell.StatsModelAdapter;

public class GameGUI extends JPanel {
	private GameComboBoxModel _comboBoxModel;
	private JComboBox _voiceOptions;
	private JButton _start, _submit, _listenAgain, _changeVoice;
	private JPanel _gamePanel, _beginPanel, _leftPanel, _leftTopPanel, _rightPanel, _optionPanel, _inputPanel, _mainPanel;
	private JTextArea _outputArea;
	private JTextField _inputField;
	private String _voiceSelected;
	private int _level;
	private StatsModel[] _statsModels;
	private JPanel[] _levelPanels, _levelLabelPanels;
	private JLabel[] _levelLabels;
	private StatsModelAdapter[] _statsModelAdapters;
	private GameLogic _game;
	private ArrayList<String> _words;
	private GameConfig _config;
	
	public GameGUI(int level) {
		_level = level;
		_config = new GameConfig();
		buildGUI();
		setUpListeners(_start, _inputField, _listenAgain);
	}
	private void setUpListeners(final JButton start, final JTextField input, final JButton listenAgain) {
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start.setVisible(false);
				setUpNewLevelGame();

			}
		});
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_submit.doClick();
			}
		});
		listenAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String wordToRepeat = _statsModelAdapters[_level-1].getWord();
				String command = "echo " + wordToRepeat + " | festival --tts";
				VoiceWorker worker = new VoiceWorker(0, command, _inputField);
				worker.execute();
			}
		});
	}
	private void setUpNewLevelGame() {	
		_statsModels[_level-1].setCorrect(0);
		_outputArea.append("\n");
		_outputArea.append("Starting a new Spelling Quiz Game...\n");
		_outputArea.append("Please spell out the ten words.\n");
		_outputArea.append("==============================\n");
		if (_start.getText().equals("Begin the next level")) {
			_level++;
			_words = _config.getLevelWords(_level);
		} else {
			_statsModelAdapters[_level-1].setLength(0);
//			_statsModels[_level-1].setCorrect(0);
			
			_config = new GameConfig();
		}
		_words = _config.getLevelWords(_level);
		ArrayList<GameListener> _listeners = new ArrayList<GameListener>();
		_listeners.add(_statsModelAdapters[_level-1]);
		_statsModels[_level-1].compute(0, 0);		
		_game = new GameLogic(_level, 10, _outputArea, _inputField, _start, _submit, _listeners);
		_game.playGame(_words);
	}
	private void buildGUI() {
		_comboBoxModel = new GameComboBoxModel();
		//Setting up the panels
		_gamePanel = new JPanel();
		_gamePanel.setLayout(new BorderLayout());
		_beginPanel = new JPanel();
		_beginPanel.setPreferredSize(new Dimension(50, 60));
		_mainPanel = new JPanel();
		//Main frames for the GUI.
		_leftTopPanel = new JPanel();
		_leftPanel = new JPanel();
		_leftPanel.setPreferredSize(new Dimension(330, 520));
		_rightPanel = new JPanel();
		//Main game area
		_leftTopPanel.setPreferredSize(new Dimension(330, 300));
		
		//Stats screen
		_rightPanel.setPreferredSize(new Dimension(440, 520));

		//Create main stats panel
		_rightPanel.setLayout(new GridLayout(11,1));
		_rightPanel.setBorder(BorderFactory.createTitledBorder("Game Statistics"));
		
		
		_statsModels = new StatsModel[11];
		_levelPanels = new JPanel[11];
		_levelLabels = new JLabel[11];
		_levelLabelPanels = new JPanel[11];
		_statsModelAdapters = new StatsModelAdapter[11];
		for (int i = 0; i < 11; i++) {
			_levelPanels[i] = new JPanel();
			_levelLabelPanels[i] = new JPanel();
			_levelPanels[i].setLayout(new BorderLayout());
			_levelPanels[i].setPreferredSize(new Dimension(440, 50));
			_levelLabels[i] = new JLabel("Level " + Integer.toString(i+1));
			_levelLabelPanels[i].setPreferredSize(new Dimension(100, 50));
			_levelLabelPanels[i].add(_levelLabels[i]);
			_levelPanels[i].add(_levelLabelPanels[i], BorderLayout.WEST);
			_statsModels[i] = new StatsModel(i+1);
			_statsModelAdapters[i] = new StatsModelAdapter(_statsModels[i], 0, 0, "");
//			_statsModels[i].compute(_wordsCorrect);
			_levelPanels[i].add(_statsModels[i], BorderLayout.CENTER);
			_rightPanel.add(_levelPanels[i]);
		}
		
		_leftTopPanel.setLayout(new BorderLayout());
		_leftTopPanel.setBorder(BorderFactory.createTitledBorder("Game Area"));
		_listenAgain = new JButton("Listen to the word again");
		_listenAgain.setPreferredSize(new Dimension(200,30));
		
		_outputArea = new JTextArea(0,20);
		DefaultCaret caret = (DefaultCaret)_outputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		_outputArea.append("\n");
		_outputArea.append("Please press the \"Begin playing\" button\n");
//		_outputArea.append("Starting a new Spelling Quiz Game...\n");
//		_outputArea.append("Please spell out the ten words.\n");
		_outputArea.append("==============================\n");
				
		_outputArea.setEditable(false);
//		_outputArea.setPreferredSize(new Dimension(300,410));

		JScrollPane scroll = new JScrollPane(_outputArea);
		_inputField = new JTextField(); //"Enter the word, then press Submit"
		_inputField.setPreferredSize(new Dimension(220,30));
		_submit = new JButton("Submit");
		
		_inputPanel = new JPanel();
		_inputPanel.add(_inputField, BorderLayout.EAST);
		_inputPanel.add(_submit, BorderLayout.WEST);
		
		
		_leftTopPanel.add(_listenAgain, BorderLayout.NORTH);
		_leftTopPanel.add(scroll, BorderLayout.CENTER);
		_leftTopPanel.add(_inputPanel, BorderLayout.SOUTH);
		
		_optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		_optionPanel.setBorder(BorderFactory.createTitledBorder("Voice Options"));
		_voiceOptions = new JComboBox(_comboBoxModel);
		_changeVoice = new JButton("Confirm voice change");
		
		_optionPanel.add(_voiceOptions);
		_optionPanel.add(_changeVoice);

		_leftPanel.setLayout(new BorderLayout());
		_leftPanel.add(_leftTopPanel, BorderLayout.CENTER);
		_leftPanel.add(_optionPanel, BorderLayout.SOUTH);
		
		_mainPanel.add(_leftPanel);
		_mainPanel.add(_rightPanel);
		
		_start = new JButton("Begin playing");
		_beginPanel.add(_start);
		_gamePanel.add(_mainPanel, BorderLayout.NORTH);
		_gamePanel.add(_beginPanel, BorderLayout.CENTER);
		
		add(_gamePanel);
	}

	private class GameComboBoxModel extends DefaultComboBoxModel {
		public GameComboBoxModel() {
			addElement("Default");
			addElement("Placeholder 1");
			addElement("Placeholder 2");
			//Empty implementation so far, get the voice options
			//What are the voice options?
			//for (String voices : voiceOptions) {
				//addElement(voiceOption); or something
			//}

		}
	}
}