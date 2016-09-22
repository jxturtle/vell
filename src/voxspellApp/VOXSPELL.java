package voxspell.voxspellApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
/**
 * Class that represents the main application. Sets up a GUI for the main menu. 
 * Buttons and associated event handlers are included to navigate the user to
 * one of the three sessions: play new game session, play review session, and
 * view statistics. 
 * @author CJ Bang
 *
 */
public class VOXSPELL extends JPanel {
	private JPanel _titleScreenPanel, _emptyPanel, _picPanel, _viewPanel, _buttonPanel, _backButtonPanel, _emptyPanel2;
	private JLabel _label, _welcomeLabel;
	private ImageIcon _welcome, _backImage;
	private JButton _newQuiz, _reviewMistakes, _viewStats, _back;
	private Font font = new Font("Verdana", Font.PLAIN, 20);
	private ReviewConfig _reviewConfig;
	private ArrayList<String> _words;
	protected static JFrame _frame;
	protected LevelPanel _level;
	/*
	 * Constructs a VOXSPELL object and sets up listener. Creates a review config object
	 * to check if there is any failed word saved in the hidden file.
	 */
	public VOXSPELL() {
		buildGUI();
		setUpListeners();
		_reviewConfig = new ReviewConfig();
	}
	/*
	 * sets up listeners for the buttons. Four buttons to implement
	 */
	private void setUpListeners() {
		// clicking on newQuiz button updates the buttonPanel with another panel 
		// called LevelPanel (nested class)
		_newQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_back.setVisible(true);
				_label.setText("Please select the level of the spelling quiz");
				_newQuiz.setVisible(false);
				_reviewMistakes.setVisible(false);
				_viewStats.setVisible(false);
				_level = new LevelPanel();
				_buttonPanel.add(_level);
				// back button is hidden initially and when Level Panel is created
				// this button is set visible
				_back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						_label.setText("Please select one of the game options");
						_level.setVisible(false);
						_back.setVisible(false);
						_newQuiz.setVisible(true);
						_reviewMistakes.setVisible(true);
						_viewStats.setVisible(true);
					}
				});

			}
		});
		// clicking reviewMistakes button opens a new class over the JFrame if 
		// there is any failed word from the previous game sessions
		_reviewMistakes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_words = _reviewConfig.getWords();
				int lines = _words.size();
				if (lines == 0) {
					JOptionPane.showMessageDialog(null, "There are no mistakes to review");
				} else {
					_back.setVisible(true);
					GameGUI game = new GameGUI(0, _frame);
					_frame.getContentPane().removeAll();
					_frame.getContentPane().add(game);
					_frame.revalidate();
					_frame.repaint();
				}
			}
		});
		// clicking the viewstats button opens a new class over the JFrame
		_viewStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_back.setVisible(true);
				StatisticsGUI statsScreen = new StatisticsGUI(_frame);
				_frame.getContentPane().removeAll();
				_frame.getContentPane().add(statsScreen);
				_frame.revalidate();
				_frame.repaint();
			}
		});
	}
	/*
	 * creates and lays out GUI components. It simply builds up a composition of GUI
	 * components and makes use of borders, scroll bar and layout managers
	 */
	private void buildGUI() {
		//setting up the panels. Top empty panel and picpanel, viewpanel are
		//added to titleScreenPanel.
		_titleScreenPanel = new JPanel();
		_titleScreenPanel.setPreferredSize(new Dimension(800, 380));
		_emptyPanel = new JPanel();
		_emptyPanel.setPreferredSize(new Dimension(800, 95));
		_picPanel = new JPanel();
		_welcome = new ImageIcon("welcome.jpg");
		_welcomeLabel = new JLabel(_welcome);
		_picPanel.add(_welcomeLabel);
		_picPanel.setPreferredSize(new Dimension(600, 220));
		_viewPanel = new JPanel();
		_viewPanel.setPreferredSize(new Dimension(600, 60));
		_label = new JLabel("Please select one of the game options", SwingConstants.CENTER);
		_label.setFont(font);
		_viewPanel.add(_label);
		_titleScreenPanel.setLayout(new BorderLayout());
		_titleScreenPanel.add(_emptyPanel, BorderLayout.NORTH);
		_titleScreenPanel.add(_picPanel, BorderLayout.CENTER);
		_titleScreenPanel.add(_viewPanel, BorderLayout.SOUTH);

		// ButtonPanel initially has 3 buttons
		_buttonPanel = new JPanel();
		_newQuiz = new JButton("New Spelling Quiz");
		_reviewMistakes = new JButton("Review Mistakes");
		_viewStats = new JButton("View Statistics");
		_buttonPanel.add(_newQuiz);
		_buttonPanel.add(_reviewMistakes);
		_buttonPanel.add(_viewStats);

		// back button is created and set invisible 
		_backButtonPanel = new JPanel();
		_backButtonPanel.setLayout(new BorderLayout());
		_backButtonPanel.setPreferredSize(new Dimension(800,55));
		_emptyPanel2 = new JPanel();
		_backImage = new ImageIcon("arrow.png");
		_back = new JButton(_backImage);
		_back.setPreferredSize(new Dimension(55,55));
		_back.setVisible(false);
		_back.setOpaque(false);
		_back.setContentAreaFilled(false);
		_back.setBorderPainted(false);
		_backButtonPanel.add(_back, BorderLayout.WEST);
		_backButtonPanel.add(_emptyPanel2, BorderLayout.CENTER);

		// adding panels to create the overall appearance
		setLayout(new BorderLayout());
		add(_titleScreenPanel, BorderLayout.NORTH);
		add(_buttonPanel, BorderLayout.CENTER);
		add(_backButtonPanel, BorderLayout.SOUTH);
	}

	/*
	 * creates and shows a GUI over a JFrame.
	 */
	private static void createAndShowGUI() {
		_frame = new JFrame();
		_frame.add(new VOXSPELL());
		_frame.setSize(800, 600);
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
		_frame.setResizable(false);
		_frame.setTitle("VOXSPELL");
		_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    _frame.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		int close = JOptionPane.showConfirmDialog(null,  "Are you sure you want to exit the program?", "Exit VOXSPELL", JOptionPane.YES_NO_OPTION);
				switch(close) {
				case JOptionPane.YES_OPTION:
					_frame.dispose();
					break;
				default:
					break;
				}	

	    	}
	    });
	}
	/*
	 * main method calls createAndShowGUI using the main EDT.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
	/* 
	 * Nested class that creates a panel for choosing game levels. 
	 */
	private class LevelPanel extends JPanel implements ActionListener {
		private JPanel _gameOptionPanel, _basicPanel, _advancedPanel;
		private JButton[] _basicLevels, _advancedLevels;
		/*
		 * constructor for LevelPanel object. GUI is built and 11 buttons have 
		 * action listeners added 
		 */
		private LevelPanel() {
			buildGUI();
			for(int i = 0; i < 6; i++) {
				_basicLevels[i].addActionListener(this);
			}
			for (int i = 0; i < 5; i++) {
				_advancedLevels[i].addActionListener(this);
			}
		}
		/*
		 * creates and lays out GUI components. It simply builds up a composition of GUI
		 * components and makes use of borders and layout managers
		 */
		private void buildGUI() {
			// setting up two panels - one for basic spelling game, one for advanced spelling game
			_basicPanel = new JPanel();
			TitledBorder _basic = BorderFactory.createTitledBorder("Basic Spelling Game");
			_basic.setTitleJustification(TitledBorder.CENTER);
			_basicPanel.setBorder(_basic);
			_advancedPanel = new JPanel();
			TitledBorder _advanced = BorderFactory.createTitledBorder("Advanced Spelling Game");
			_advanced.setTitleJustification(TitledBorder.CENTER);
			_advancedPanel.setBorder(_advanced);		
			// 11 buttons to add using a for loop.
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
			// adding two panels to the JPanel to create the overall appearance
			setLayout(new BorderLayout());
			add(_basicPanel, BorderLayout.NORTH);
			add(_advancedPanel, BorderLayout.CENTER);
			setPreferredSize(new Dimension(600,119));
		}
		/*
		 * listener action for 11 level buttons. if the button is pressed, an appropriate
		 * gameGUI opens over the JFrame.
		 */
		public void actionPerformed(ActionEvent e) {
			String levelChosen = ((JButton)e.getSource()).getText();
			for (int i = 0; i < 11; i++) {
				if (levelChosen.equals("Level "+Integer.toString(i+1))) {
					GameGUI game = new GameGUI(i+1, _frame);
					_frame.getContentPane().removeAll();
					_frame.getContentPane().add(game);
					_frame.revalidate();
					_frame.repaint();
				}
			}
		}
	}
}
