package voxspell.voxspellGraphicAssets;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import voxspell.*;
import voxspell.voxspellApp.GameConfig;
import voxspell.voxspellApp.GameLogic;
import voxspell.voxspellApp.HalloweenVoxspell;
import voxspell.voxspellApp.ReviewConfig;
import voxspell.voxspellApp.TrickTreat;
import voxspell.voxspellAssets.VoiceEvent;
import voxspell.voxspellAssets.VoiceWorker;
import voxspell.voxspellModel.GameAdapter;
import voxspell.voxspellModel.GameListener;
import voxspell.voxspellModel.StatsModel;

import javax.swing.BoxLayout;
import java.awt.Component;
/**
 * Class that sets up a GUI for a Game session. There are mainly two GUI components
 * for the game session: a JTextArea that outputs all the words assessed and 
 * a StatsModel that outputs the progress bar for each level during the game sessions. 
 * During the review mode, the StatsModel outputs texts not a bar. In addition to the
 * two main GUI components includes buttons and associated event handlers.
 * @author CJ Bang
 *
 */
public class GameGUI extends JPanel {
	private GameComboBoxModel _comboBoxModel;
	private JComboBox _voiceOptions;
	private JButton _start, _submit, _listenAgain, _changeVoice, _back;
	private ImageIcon _backImage;
	private JPanel _gamePanel, _beginPanel, _leftPanel, _leftTopPanel, _rightPanel, _optionPanel, _inputPanel, _mainPanel, _bottomPanel, _titlePanel;
	private JTextArea _outputArea;
	private JTextField _inputField;
	private String _voiceSelected;
	private int _level, _lines, _backToMain;
	private JPanel[] _levelPanels, _levelLabelPanels;
	private JLabel[] _levelLabels;
	private StatsModel[] _statsModels;
	private GameAdapter[] _GameAdapters;
	private StatsModel _reviewStatsModel;
	private GameAdapter _reviewGameAdapter;
	private GameLogic _game;
	private ArrayList<String> _words;
	private GameConfig _config;
	private ReviewConfig _reviewConfig;
	private boolean _isFinished = false, _isCustomFile = false;
	private TrickTreat _tricksAndTreats;
	protected static JFrame _frame;
	private File _inputFile;
	/*
	 * Constructor for GameGUI. takes two parameters: level to specify the lists of words
	 * for the game session. JFrame for the main frame. Creates necessary instances required
	 * for playing game. Build GUI components and sets up with the appropriate StatsModel
	 */
	/**
	 * @wbp.parser.constructor
	 */
	public GameGUI(int level, JFrame frame, TrickTreat tricksAndTreats) {
		_frame = frame;
		_level = level;
		_config = new GameConfig();		
		_reviewConfig = new ReviewConfig();
		_words = _reviewConfig.getWords();
		_lines = _words.size();
		_tricksAndTreats = tricksAndTreats;
		buildGUI();
		setUpRightPanel(_level);
		VoiceEvent.makeDefaultVoice();
		setUpListeners();
	}
	
	public GameGUI(int level, JFrame frame, TrickTreat tricksAndTreats, File inputFile) throws InvalidObjectException {
		_frame = frame;
		_level = level;
		_inputFile = inputFile;
		_config = new GameConfig(_inputFile);
		_reviewConfig = new ReviewConfig();
		_words = _reviewConfig.getWords();
		_isCustomFile=true;
		_lines = _words.size();
		_tricksAndTreats = tricksAndTreats;
		buildGUI();
		setUpRightPanel(_level);
		VoiceEvent.makeDefaultVoice();
		setUpListeners();
	}
	/*
	 * sets up listeners for the buttons. Five buttons to implement
	 */
	private void setUpListeners() {
		// back button shows an appropriate action. if pressed during the game, it will give a 
		// warning message. if the game has not been started or has finished, returns to the main
		_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((_game == null) || (_isFinished)){
					returnToMain(_frame);
				} else if (_level != 0) {
					_backToMain = JOptionPane.showConfirmDialog(null, "<html>Are you sure you want to leave this game session?<br><br>"
							+ "Your overall statistics,tricks and treats will be saved.<br><br>Press OK to continue, or Cancel to stay "
							+ "on the current session.<br><br>", "Back to main", JOptionPane.OK_CANCEL_OPTION);
				} else if (_level == 0) {
					_backToMain = JOptionPane.showConfirmDialog(null, "<html>Are you sure you want to leave this review session?<br><br>"
							+ "Your game statistics will be saved but you will lose your progress for the current review session if you leave now.<br><br>Press OK to continue, or Cancel to stay "
							+ "on the current session.<br><br>", "Back to main", JOptionPane.OK_CANCEL_OPTION);
				}
				switch(_backToMain) {
				case JOptionPane.OK_OPTION:
					returnToMain(_frame);
					break;
				default:
					break;
				}
			}
		});
		// start button begins the new level game
		_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_submit.setEnabled(true);
				_listenAgain.setEnabled(true);
				_inputField.setEnabled(true);
				_start.setVisible(false);
				setUpNewLevelGame(_level);
			}
		});
		// pressing enter in JTextField also allows the submit button
		// to be pressed (for flexibility for user)
		_inputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_submit.doClick();
			}
		});
		// pressing listen again button will get a current word from the adapter
		// and creates a voiceworker to say the word
		_listenAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String wordToRepeat = "";
				if (_level != 0) {
					wordToRepeat = _GameAdapters[_level-1].getWord();
				} else {
					wordToRepeat = _reviewGameAdapter.getWord();
				}
				VoiceWorker worker = new VoiceWorker(0, wordToRepeat);
				worker.execute();
			}
		});
		// confirms a voice change
		_changeVoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String voiceType = _comboBoxModel.getSelectedItem().toString();
				if (voiceType.equals("British English")) {
					VoiceEvent.makeBritishEnglishVoice();
				} else if (voiceType.equals("New Zealand")) {
					VoiceEvent.makeNewZealandVoice();
				} else {
					VoiceEvent.makeDefaultVoice();
				}
			}
		});
	}
	/*
	 * a public method for GameAdapter to execute when there is a gameFinished
	 * event fired. This sets the boolean variable _isFinished to true to show
	 * an appropriate action when the back button is pressed.
	 */
	public void setGameFinished() {
		_isFinished = true;
	}
	/*
	 * a private method to creates a title screen and return to the title screen
	 */
	private void returnToMain(JFrame frame) {
		frame.getContentPane().removeAll();				
		HalloweenVoxspell newTitle = new HalloweenVoxspell();
		frame.getContentPane().add(newTitle);
		frame.revalidate();
		frame.repaint();
	}
	/*
	 * a private method that sets up the actual game session. if level = 0, it first 
	 * checks the number of words to be run. if level is not 0, then word list is generated
	 * and game starts. 
	 */
	private void setUpNewLevelGame(int level) {	
		if (level == 0) {
			ArrayList<GameListener> _listeners = new ArrayList<GameListener>();
			_listeners.add(_reviewGameAdapter);
			if (_lines < 10) {
				_game = new GameLogic(_level, _lines, _outputArea, _inputField, _start, _back, _submit, _listeners, true,_tricksAndTreats);
			} else {
				_game = new GameLogic(_level, 10, _outputArea, _inputField, _start, _back, _submit, _listeners, true,_tricksAndTreats);
			}		
		} else {
			// reset the length and colour of the progress bar and resets the percentage
			_statsModels[_level-1].setNumber(0,0,9);
			// if the level is not repeated but newly started, the gamelogic for the next 
			// level is executed
			if (_start.getText().equals("Begin the next level")) {
				_level++;
				_words = _config.getLevelWords(_level);
			// if the level is repeated, then the progress bar is reset and gets another
			// word list for the level and run the game.
			} else {
				_GameAdapters[_level-1].setNumber(0,0,9);				
				if (!_isCustomFile) {
					_config = new GameConfig();
				} else {			
					try {
						_config = new GameConfig(_inputFile);
					} catch (InvalidObjectException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			_words = _config.getLevelWords(_level);
			ArrayList<GameListener> _listeners = new ArrayList<GameListener>();
			_listeners.add(_GameAdapters[_level-1]);
			_statsModels[_level-1].compute(0, 0);		
			_game = new GameLogic(_level, 10, _outputArea, _inputField, _start, _back, _submit, _listeners, true,_tricksAndTreats);
		}
		_outputArea.append("\n");
		_outputArea.append("Starting a new Spelling Quiz Game...\n");
		_outputArea.append("Please spell out the words.\n");
		_outputArea.append("==============================\n");
		_game.playGame(_words);
	}
	/*
	 * a private method to set up the statsmodel panel. If level is not zero, 
	 * 11 statsmodels and their respective adapters are created and laid out in an 
	 * order from level 1 to level 11. if the level is zero, then there is only 
	 * one panel to be created and one adapter is created.
	 */
	private void setUpRightPanel(int level) {
		if (level == 0) {
			_reviewStatsModel = new StatsModel(0, _lines);
			_reviewStatsModel.setPreferredSize(new Dimension(420,450));
			_reviewGameAdapter = new GameAdapter(_reviewStatsModel, this, 0, 0, "");
			_rightPanel.add(_reviewStatsModel);
		} else {
			_rightPanel.setLayout(new GridLayout(11,1));
			_statsModels = new StatsModel[11];
			_levelPanels = new JPanel[11];
			_levelLabels = new JLabel[11];
			_levelLabelPanels = new JPanel[11];
			_GameAdapters = new GameAdapter[11];
			for (int i = 0; i < 11; i++) {
				_levelPanels[i] = new JPanel();
				_levelLabelPanels[i] = new JPanel();
				_levelPanels[i].setLayout(new BorderLayout());
				_levelPanels[i].setPreferredSize(new Dimension(440, 50));
				_levelLabels[i] = new JLabel("Level " + Integer.toString(i+1));
				_levelLabelPanels[i].setPreferredSize(new Dimension(90, 50));
				_levelLabelPanels[i].add(_levelLabels[i]);
				_levelPanels[i].add(_levelLabelPanels[i], BorderLayout.WEST);
				_statsModels[i] = new StatsModel(i+1, 9);
				_GameAdapters[i] = new GameAdapter(_statsModels[i], this, 0, 0, "");
				_levelPanels[i].add(_statsModels[i], BorderLayout.CENTER);
				_rightPanel.add(_levelPanels[i]);
			}
		}
	}
	
	/*
	 * creates and lays out GUI components. It simply builds up a composition of GUI
	 * components and makes use of borders, scroll bar and layout managers
	 */
	private void buildGUI() {
		_comboBoxModel = new GameComboBoxModel();
		//Setting up the panels
		_bottomPanel = new JPanel();
		_bottomPanel.setPreferredSize(new Dimension(50, 55));
		_mainPanel = new JPanel();
		//Main frames for the GUI.
		_leftTopPanel = new JPanel();
		_leftPanel = new JPanel();
		_leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		_leftPanel.setPreferredSize(new Dimension(330, 515));
		_rightPanel = new JPanel();
		//Main game area
		_leftTopPanel.setPreferredSize(new Dimension(330, 300));
		
		//Stats screen
		_rightPanel.setPreferredSize(new Dimension(440, 515));

		//Create main stats panel
		_rightPanel.setBorder(BorderFactory.createTitledBorder("Game Statistics"));
		
		_leftTopPanel.setLayout(new BorderLayout());
		_leftTopPanel.setBorder(BorderFactory.createTitledBorder("Game Area"));
		_listenAgain = new JButton("Listen to the word again");
		_listenAgain.setEnabled(false);
		_listenAgain.setPreferredSize(new Dimension(200,30));
		
		_outputArea = new JTextArea(0,20);
		DefaultCaret caret = (DefaultCaret)_outputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		_outputArea.append("\n");
		_outputArea.append("Please press the \"Begin playing\" button\n");
		_outputArea.append("======================================\n");
		_mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
		_outputArea.setEditable(false);

		JScrollPane scroll = new JScrollPane(_outputArea);
		_inputField = new JTextField();
		_inputField.setEnabled(false);
		_inputField.setPreferredSize(new Dimension(220,30));
		_submit = new JButton("Submit");
		_submit.setEnabled(false);
		
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
		
		_optionPanel.setLayout(new BorderLayout());
		_optionPanel.add(_voiceOptions, BorderLayout.WEST);
		_optionPanel.add(_changeVoice, BorderLayout.EAST);

		_leftPanel.setLayout(new BorderLayout());
		_leftPanel.add(_leftTopPanel, BorderLayout.CENTER);
		_leftPanel.add(_optionPanel, BorderLayout.SOUTH);
		
		_mainPanel.add(_leftPanel);
		_mainPanel.add(_rightPanel);
		_beginPanel = new JPanel();
		_start = new JButton("Begin playing");
		_start.setBounds(291, 0, 175, 44);

		_beginPanel.setLayout(null);
		_back = new JButton(new ImageIcon("arrow.png"));
		_back.setBounds(21, 0, 55, 55);
		_beginPanel.add(_back);
		_back.setPreferredSize(new Dimension(55,55));
		_back.setOpaque(false);
		_back.setContentAreaFilled(false);
		_back.setBorderPainted(false);
		_beginPanel.add(_start);
		_bottomPanel.setLayout(new BorderLayout());
		_bottomPanel.setPreferredSize(new Dimension(700, 60));
		_bottomPanel.add(_beginPanel, BorderLayout.CENTER);

		setLayout(new BorderLayout());
		setBackground(Color.white);
		add(_mainPanel, BorderLayout.NORTH);
		add(_bottomPanel, BorderLayout.CENTER);
	}
	/*
	 * a nested inner class that creates a new combo box for voice choices
	 */
	private class GameComboBoxModel extends DefaultComboBoxModel {
		public GameComboBoxModel() {
			addElement("Default");
			addElement("New Zealand");
		}
	}
}
