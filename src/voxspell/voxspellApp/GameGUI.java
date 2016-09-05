package voxspell.voxspellApp;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import voxspell.StatsModel;
import voxspell.StatsModelAdapter;

public class GameGUI extends JPanel {
	private GameComboBoxModel _comboBoxModel;
	private JComboBox _voiceOptions;
	private JButton _submit, _listenAgain, _changeVoice;
	private JPanel _leftPanel, _leftTopPanel, _rightPanel, _optionPanel, _inputPanel, _mainPanel;
	private JTextArea _outputArea;
	private JTextField _inputField;
	private String _voiceSelected;
	private int _level;
	private StatsModel[] _statsModels;
	private JPanel[] _levelPanels, _levelLabelPanels;
	private JLabel[] _levelLabels;
	private int _wordsCorrect = 4;
	private StatsModelAdapter[] _statsModelAdapters;
	
	public GameGUI(int level) {
		_level = level;
		GameConfig config = GameConfig.instance();
		
		_comboBoxModel = new GameComboBoxModel();
		buildGUI();

		// need to be in class where game logic is implemented 
//		_listeners = new ArrayList<GameListener>();

		System.out.println("Building...");
		
		
	}

	private void buildGUI() {
		//Setting up the panels
		_mainPanel = new JPanel();
		//Main frames for the GUI.
		_leftTopPanel = new JPanel();
		_leftPanel = new JPanel();
		_leftPanel.setPreferredSize(new Dimension(330, 555));
		_rightPanel = new JPanel();
		//Main game area
		_leftTopPanel.setPreferredSize(new Dimension(330, 300));
		
		//Stats screen
		_rightPanel.setPreferredSize(new Dimension(440, 555));

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
			_statsModels[i].compute(_wordsCorrect);
			_statsModelAdapters[i] = new StatsModelAdapter(_statsModels[i]);
			_levelPanels[i].add(_statsModels[i], BorderLayout.CENTER);
			_rightPanel.add(_levelPanels[i]);
		}
		
		_leftTopPanel.setLayout(new BorderLayout());
		_leftTopPanel.setBorder(BorderFactory.createTitledBorder("Game Area"));
		
		_listenAgain = new JButton("Listen to the word again");
		_listenAgain.setPreferredSize(new Dimension(200,30));
		
		_outputArea = new JTextArea();
		_outputArea.setEditable(false);
		_outputArea.setPreferredSize(new Dimension(300,410));

		
		_inputField = new JTextField(); //"Enter the word, then press Submit"
		_inputField.setPreferredSize(new Dimension(220,30));
		_submit = new JButton("Submit");
		
		_inputPanel = new JPanel();
		_inputPanel.add(_inputField, BorderLayout.EAST);
		_inputPanel.add(_submit, BorderLayout.WEST);
		
		
		_leftTopPanel.add(_listenAgain, BorderLayout.NORTH);
		_leftTopPanel.add(_outputArea, BorderLayout.CENTER);
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
		
		add(_mainPanel);
		
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
