package voxspell.voxspellApp;

import java.awt.BorderLayout;
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

public class TitleScreen extends JPanel {
	private JPanel _titleScreenPanel, _emptyPanel, _picPanel, _viewPanel, _buttonPanel, _backButtonPanel, _emptyPanel2;
	private JLabel _label, _welcomeLabel;
	private ImageIcon _welcome, _backImage;
	private JButton _newQuiz, _reviewMistakes, _viewStats, _back;
	private Font font = new Font("Verdana", Font.PLAIN, 20);
	private ReviewConfig _reviewConfig;
	private ArrayList<String> _words;
	protected static JFrame _frame;
	public TitleScreen() {
		buildGUI();
		setUpListeners();
		_reviewConfig = new ReviewConfig();
//		VideoProcessor video = new VideoProcessor();
	}
	private void setUpListeners() {
		_newQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_back.setVisible(true);
				_label.setText("Please select the level of the spelling quiz");
				_newQuiz.setVisible(false);
				_reviewMistakes.setVisible(false);
				_viewStats.setVisible(false);
				final LevelPanel level = new LevelPanel();
				_buttonPanel.add(level);
				_back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						_label.setText("Please select one of the game options");
						level.setVisible(false);
						_back.setVisible(false);
						_newQuiz.setVisible(true);
						_reviewMistakes.setVisible(true);
						_viewStats.setVisible(true);
					}
				});
			}
		});
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
	private void buildGUI() {
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
		
		_buttonPanel = new JPanel();
		_newQuiz = new JButton("New Spelling Quiz");
		_reviewMistakes = new JButton("Review Mistakes");
		_viewStats = new JButton("View Statistics");
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

		_buttonPanel.add(_newQuiz);
		_buttonPanel.add(_reviewMistakes);
		_buttonPanel.add(_viewStats);

		setLayout(new BorderLayout());
		add(_titleScreenPanel, BorderLayout.NORTH);
		add(_buttonPanel, BorderLayout.CENTER);
		add(_backButtonPanel, BorderLayout.SOUTH);
	}

	private static void createAndShowGUI() {
		_frame = new JFrame();
		_frame.add(new TitleScreen());
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
