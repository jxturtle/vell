package voxspell.voxspellApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class TitleScreen extends JFrame {
	private JPanel _mainPanel, _titleScreenPanel, _emptyPanel, _picPanel, _viewPanel, _buttonPanel;
	private JLabel _picLabel, _label, _welcomeLabel;
	private ImageIcon _welcome;
	private JButton _newQuiz, _reviewMistakes, _viewStats;
	private Font font = new Font("Verdana", Font.PLAIN, 20);
	protected static JFrame frame;
	public TitleScreen() {
		buildGUI();
		setUpListeners();
	}
	private void setUpListeners() {
		_newQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_label.setText("Please select the level of the spelling quiz");
				_newQuiz.setVisible(false);
				_reviewMistakes.setVisible(false);
				_viewStats.setVisible(false);
				LevelPanel level = new LevelPanel();
				_buttonPanel.add(level);
			}
		});

		_reviewMistakes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainPanel.setVisible(false);
				ReviewMistakes review = new ReviewMistakes();
				add(review);
				review.setVisible(true);
			}
		});
		_viewStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainPanel.setVisible(false);
				Statistics statsScreen = new Statistics();
				add(statsScreen);
				statsScreen.setVisible(true);

			}
		});
	}
	private void buildGUI() {
		_mainPanel = new JPanel();
		_titleScreenPanel = new JPanel();

		_emptyPanel = new JPanel();
		_emptyPanel.setPreferredSize(new Dimension(600, 100));
		_picPanel = new JPanel();
		_welcome = new ImageIcon("welcome.jpg");
		_welcomeLabel = new JLabel(_welcome);
		_picPanel.add(_welcomeLabel);
		_picPanel.setPreferredSize(new Dimension(600, 250));
		
		_viewPanel = new JPanel();
		_viewPanel.setPreferredSize(new Dimension(600, 70));
		_label = new JLabel("Please select one of the game options", SwingConstants.CENTER);
		_label.setFont(font);
		_viewPanel.add(_label);

		_titleScreenPanel.setLayout(new BorderLayout());
		_titleScreenPanel.add(_emptyPanel, BorderLayout.NORTH);
		_titleScreenPanel.add(_picPanel, BorderLayout.CENTER);
		_titleScreenPanel.add(_viewPanel, BorderLayout.SOUTH);
		
		_buttonPanel = new JPanel();
		_newQuiz = new JButton("New Spelling Quiz");
		_reviewMistakes = new JButton("Review Mistakes");
		_viewStats = new JButton("View Statistics");
		_buttonPanel.add(_newQuiz);
		_buttonPanel.add(_reviewMistakes);
		_buttonPanel.add(_viewStats);
		_buttonPanel.setPreferredSize(new Dimension(600, 130));
		
		_mainPanel.add(_titleScreenPanel, BorderLayout.NORTH);
		_mainPanel.add(_buttonPanel, BorderLayout.SOUTH);

		add(_mainPanel);
	}
	private static void createAndShowGUI() {
		frame = new TitleScreen();
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("VOXSPELL");
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private class LevelPanel extends JPanel implements ActionListener {
		private JPanel _gameOptionPanel, _basicPanel, _advancedPanel;
		private JButton[] _basicLevels, _advancedLevels;
		private LevelPanel() {
			buildGUI();
			for(int i = 0; i < 6; i++) {
				_basicLevels[i].addActionListener(this);
			}
			for (int i = 0; i < 5; i++) {
				_advancedLevels[i].addActionListener(this);
			}
		}
		private void buildGUI() {
			_basicPanel = new JPanel();
			TitledBorder _basic = BorderFactory.createTitledBorder("Basic Spelling Game");
			_basic.setTitleJustification(TitledBorder.CENTER);
			_basicPanel.setBorder(_basic);
			_advancedPanel = new JPanel();
			TitledBorder _advanced = BorderFactory.createTitledBorder("Advanced Spelling Game");
			_advanced.setTitleJustification(TitledBorder.CENTER);
			_advancedPanel.setBorder(_advanced);		
			_basicLevels = new JButton[6];
			for (int i = 0; i < 6; i++) {
				_basicLevels[i] = new JButton("Level " + Integer.toString(i+1));
				_basicPanel.add(_basicLevels[i]);
			}
			_advancedLevels = new JButton[5];
			for (int i = 0; i < 5; i++) {
				_advancedLevels[i] = new JButton("Level " + Integer.toString(i+7));
				_advancedPanel.add(_advancedLevels[i]);
			}
			_gameOptionPanel = new JPanel();
			_gameOptionPanel.setLayout(new BorderLayout());
			_gameOptionPanel.add(_basicPanel, BorderLayout.NORTH);
			_gameOptionPanel.add(_advancedPanel,BorderLayout.CENTER);
			_gameOptionPanel.setPreferredSize(new Dimension(600, 119));
			add(_gameOptionPanel);
		}
		public void actionPerformed(ActionEvent e) {
			String levelChosen = ((JButton)e.getSource()).getText();
			for (int i = 0; i < 11; i++) {
				if (levelChosen.equals("Level "+Integer.toString(i+1))) {
					GameGUI game = new GameGUI(i+1);
					_mainPanel.setVisible(false);
					frame.add(game);
				}
			}
		}
	}
}