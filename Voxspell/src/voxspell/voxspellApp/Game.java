package voxspell.voxspellApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import voxspell.StatsModel;

public class Game extends JPanel{
	private StatsModel _statsModel;
	private GameComboBoxModel _comboBoxModel;
	private JComboBox _voiceOptions;
	private JButton _submit, _listenAgain, _changeVoice;
	private JPanel _leftPanel, _leftTopPanel, _rightPanel, _optionPanel, _inputPanel, _mainPanel;
	private JTextArea _outputArea;
	private JTextField _inputField;
	private String _voiceSelected;
	private int _level;
	
	public Game(int level) {
		_level = level;
		GameConfig config = GameConfig.instance();
		_comboBoxModel = new GameComboBoxModel();
		System.out.println("Building...");
		buildGUI();
		
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
		_rightPanel.setLayout(new BorderLayout());
		_rightPanel.setBorder(BorderFactory.createTitledBorder("Game Statistics"));
		
		
		
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
