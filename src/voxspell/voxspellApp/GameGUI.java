package voxspell.voxspellApp;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.BorderLayout;
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

public class GameGUI extends JPanel {
	private GameComboBoxModel _comboBoxModel;
	private JComboBox _voiceOptions;
	private JButton _start, _submit, _listenAgain, _changeVoice, _back;
	private ImageIcon _backImage;
	private JPanel _gamePanel, _beginPanel, _leftPanel, _leftTopPanel, _rightPanel, _optionPanel, _inputPanel, _mainPanel, _bottomPanel, _titlePanel;
	private JTextArea _outputArea;
	private JTextField _inputField;
	private String _voiceSelected;
	private int _level, _lines;
	private JPanel[] _levelPanels, _levelLabelPanels;
	private JLabel[] _levelLabels;
	private StatsModel[] _statsModels;
	private StatsModelAdapter[] _statsModelAdapters;
	private StatsModel _reviewStatsModel;
	private StatsModelAdapter _reviewStatsModelAdapter;
	private GameLogic _game;
	private ArrayList<String> _words;
	private GameConfig _config;
	private ReviewConfig _reviewConfig;
	protected static JFrame _frame;
	
	public GameGUI(int level, JFrame frame) {
		_frame = frame;
		_level = level;
		_config = new GameConfig();
		_reviewConfig = new ReviewConfig();
		_words = _reviewConfig.getWords();
		_lines = _words.size();
		buildGUI();
		setUpRightPanel(_level);
		VoiceEvent.makeDefaultVoice();
		setUpListeners();
	}
	private void setUpListeners() {
		_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int backToMain = JOptionPane.showConfirmDialog(null, "<html>Are you sure you want to leave this game session?<br><br>"
						+ "Your game statistics will be saved but you will lose your progress for the current game session if you leave now.<br><br>Press OK to continue, or Cancel to stay "
						+ "on the current session.<br><br>", "Back to main", JOptionPane.OK_CANCEL_OPTION);
				switch(backToMain) {
				case JOptionPane.OK_OPTION:
					_frame.getContentPane().removeAll();				
					TitleScreen newTitle = new TitleScreen();
					_frame.getContentPane().add(newTitle);
					_frame.revalidate();
					_frame.repaint();
					break;
				default:
					break;
				}	
			}
		});
		_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_start.setVisible(false);
//				_back.setVisible(false);
				setUpNewLevelGame(_level);
			}
		});
		_inputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_submit.doClick();
			}
		});
		_listenAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String wordToRepeat = _statsModelAdapters[_level-1].getWord();
				String command = wordToRepeat;
				VoiceWorker worker = new VoiceWorker(0, command);
				worker.execute();
			}
		});
		
		_changeVoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String voiceType = _comboBoxModel.getSelectedItem().toString();
				if (voiceType.equals("British English")) {
					VoiceEvent.makeBritishEnglishVoice();
				} else if (voiceType.equals("Spanish")) {
					VoiceEvent.makeSpanishVoice();
				} else if (voiceType.equals("New Zealand")) {
					VoiceEvent.makeNewZealandVoice();
				} else {
					VoiceEvent.makeDefaultVoice();
				}
			}
		});
	}
	private void setUpNewLevelGame(int level) {	
		if (level == 0) {
			ArrayList<GameListener> _listeners = new ArrayList<GameListener>();
			_listeners.add(_reviewStatsModelAdapter);
			if (_lines < 10) {
				_game = new GameLogic(_level, _lines, _outputArea, _inputField, _start, _back, _submit, _listeners);
			} else {
				_game = new GameLogic(_level, 10, _outputArea, _inputField, _start, _back, _submit, _listeners);
			}		
		} else {
			_statsModels[_level-1].setNumber(0,0,9);
			if (_start.getText().equals("Begin the next level")) {
				_level++;
				_words = _config.getLevelWords(_level);
			} else {
				_statsModelAdapters[_level-1].setNumber(0,0,9);			
				_config = new GameConfig();
			}
			_words = _config.getLevelWords(_level);
			ArrayList<GameListener> _listeners = new ArrayList<GameListener>();
			_listeners.add(_statsModelAdapters[_level-1]);
			_statsModels[_level-1].compute(0, 0);		
			_game = new GameLogic(_level, 10, _outputArea, _inputField, _start, _back, _submit, _listeners);
		}
		_outputArea.append("\n");
		_outputArea.append("Starting a new Spelling Quiz Game...\n");
		_outputArea.append("Please spell out the words.\n");
		_outputArea.append("==============================\n");
		_game.playGame(_words);
	}
	private void setUpRightPanel(int level) {
		if (level == 0) {
			_reviewStatsModel = new StatsModel(0, _lines);
			_reviewStatsModel.setPreferredSize(new Dimension(420,450));
			_reviewStatsModelAdapter = new StatsModelAdapter(_reviewStatsModel, 0, 0, "");
			_rightPanel.add(_reviewStatsModel);
		} else {
			_rightPanel.setLayout(new GridLayout(11,1));
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
				_levelLabelPanels[i].setPreferredSize(new Dimension(90, 50));
				_levelLabelPanels[i].add(_levelLabels[i]);
				_levelPanels[i].add(_levelLabelPanels[i], BorderLayout.WEST);
				_statsModels[i] = new StatsModel(i+1, 9);
				_statsModelAdapters[i] = new StatsModelAdapter(_statsModels[i], 0, 0, "");
				_levelPanels[i].add(_statsModels[i], BorderLayout.CENTER);
				_rightPanel.add(_levelPanels[i]);
			}
		}
	}
	private void buildGUI() {
		_comboBoxModel = new GameComboBoxModel();
		//Setting up the panels
		_bottomPanel = new JPanel();
		_bottomPanel.setPreferredSize(new Dimension(50, 55));
		_mainPanel = new JPanel();
		//Main frames for the GUI.
		_leftTopPanel = new JPanel();
		_leftPanel = new JPanel();
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
		_listenAgain.setPreferredSize(new Dimension(200,30));
		
		_outputArea = new JTextArea(0,20);
		DefaultCaret caret = (DefaultCaret)_outputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		_outputArea.append("\n");
		_outputArea.append("Please press the \"Begin playing\" button\n");
		_outputArea.append("==============================\n");
				
		_outputArea.setEditable(false);

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
		_backImage = new ImageIcon("arrow.png");
		_back = new JButton(_backImage);
		_back.setPreferredSize(new Dimension(55,55));
		_back.setOpaque(false);
		_back.setContentAreaFilled(false);
		_back.setBorderPainted(false);
		_beginPanel.add(_start);
		JPanel _emptyPanel = new JPanel();
		_emptyPanel.setPreferredSize(new Dimension(62, 55));
		_bottomPanel.setLayout(new BorderLayout());
		_bottomPanel.setPreferredSize(new Dimension(700, 60));
		_bottomPanel.add(_back, BorderLayout.WEST);
		_bottomPanel.add(_beginPanel, BorderLayout.CENTER);
		_bottomPanel.add(_emptyPanel, BorderLayout.EAST);

		setLayout(new BorderLayout());
		add(_mainPanel, BorderLayout.NORTH);
		add(_bottomPanel, BorderLayout.CENTER);
	}
	private class GameComboBoxModel extends DefaultComboBoxModel {
		public GameComboBoxModel() {
			addElement("Default");
			addElement("New Zealand");

		}
	}
}