package voxspell.voxspellApp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.InvalidObjectException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import voxspell.voxspellGraphicAssets.GameGUI;
import voxspell.voxspellGraphicAssets.LevelPanel;
import voxspell.voxspellGraphicAssets.Rewards;
import voxspell.voxspellGraphicAssets.StatisticsGUI;

import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.Icon;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class HalloweenVoxspell extends JPanel {
	private static JFrame _frame;
//	private int _tricks, _treats;
	private LevelPanel _levelPanel;
	private JPanel _advancedPanel;
	private JPanel _basicPanel;
	private JLabel _help;
	private JButton _newQuiz;
	private JButton _reviewMistakes;
	private JButton _viewStats;
	private JButton _trickTreatButton;
	private JButton _back;
	private JPanel _optionPanel;
	private ArrayList<String> _words;
	private ReviewConfig _reviewConfig;
	private TrickTreat _tricksAndTreats;
	private JLabel _currentFile;
	private JPanel _fileChooserPanel;
	private GameConfig _config;
	private boolean _isCustomFile;
	private File _inputFile;
	private JLabel _welcome;
	private JPanel panel_1;
	private JComboBox comboBox;
	private JButton btnNewButton_1;
	private JPanel _rewardPanel;
	private JLabel _tricksLabel;
	private JLabel _treatsLabel;
	/** 
	 * HalloweenVoxspell is the seasonal title screen for Voxspell, and is the entry point of the program.
	 * Halloween Voxspell allows users to navigate through the different game options, as well as see
	 * how well the user is doing in terms of tricks/treats earned for the current game session.
	 */
	public HalloweenVoxspell() {
		buildGUI();
		setupListeners();
		_reviewConfig = new ReviewConfig();
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {			
					setupLookAndFeel();
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//Changing the look and feel of the Swing components to the "Nimbus LAF".
	private static void setupLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	//This private method provides functionality for the JButton GUI components.
	private void setupListeners(){ 
		_newQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				_optionPanel.setVisible(false);
				_levelPanel.setVisible(true);
				_back.setVisible(true);
				_welcome.setVisible(false);
				_fileChooserPanel.setVisible(true);
				// back button is hidden initially and when Level Panel is created
				// this button is set visible
				_back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						_levelPanel.setVisible(false);
						_back.setVisible(false);
						_optionPanel.setVisible(true);
						_fileChooserPanel.setVisible(false);
						_welcome.setVisible(true);
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
					GameGUI game = new GameGUI(0, _frame, _tricksAndTreats);
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
		
		_trickTreatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				_back.setVisible(true);
				_optionPanel.setVisible(false);
				_welcome.setVisible(false);
				_rewardPanel.setVisible(true);
				_back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						_back.setVisible(false);
						_optionPanel.setVisible(true);
						_welcome.setVisible(true);
						_rewardPanel.setVisible(false);
					}
				});
				
			}
		});
	}
	private static void createAndShowGUI() {
		_frame = new JFrame();
		_frame.getContentPane().add(new HalloweenVoxspell());
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
					System.exit(0);
					break;
				default:
					break;
				}	

	    	}
	    });
	}
	
	private void buildGUI() {
		_tricksAndTreats = TrickTreat.getInstance();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setMinimumSize(new Dimension(800, 600));
		setBackground(Color.WHITE);
		setLayout(null);
		ImageIcon icon = new ImageIcon("halloween_background.gif");
		Image scaleImage = icon.getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT);
		

		_back = new JButton(new ImageIcon("arrow.png"));
		_back.setBorder(null);
		_back.setVisible(false);
		
		_fileChooserPanel = new JPanel();
		_fileChooserPanel.setVisible(false);
		
		_fileChooserPanel.setBounds(230, 263, 342, 70);
		add(_fileChooserPanel);
		_fileChooserPanel.setOpaque(false);
		_fileChooserPanel.setLayout(null);
		
		_config = new GameConfig();
		_currentFile = new JLabel("The current quiz file is: " + _config.getFileName());
		_currentFile.setOpaque(true);
		_currentFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		_currentFile.setForeground(Color.BLACK);
		_currentFile.setBackground(Color.WHITE);
		_currentFile.setBounds(0, 0, 342, 23);
		_fileChooserPanel.add(_currentFile);
		
		JButton btnSelectNewFile = new JButton("Change File");
		btnSelectNewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(new JFrame());
				if(returnVal==JFileChooser.APPROVE_OPTION) {
					try {
						_inputFile = chooser.getSelectedFile();
						_config = new GameConfig(chooser.getSelectedFile());
						_currentFile.setText("The current quiz file is: " + _config.getFileName());
						_isCustomFile = true;
						_levelPanel = new LevelPanel(_frame,_tricksAndTreats, _inputFile, _isCustomFile);
					} catch (InvalidObjectException ioe) {
						JOptionPane invalidFilePane = new JOptionPane();
						invalidFilePane.showMessageDialog(new JFrame(), "<html>The file specified does not conform to the file format<br>requirements. Please check the format of the file you wish to play.</html>", "Wrong File Format", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		_tricksLabel = new JLabel(Integer.toString(_tricksAndTreats.getTricks()));
		_tricksLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		_tricksLabel.setOpaque(true);
		_tricksLabel.setBackground(new Color(210, 105, 30));
		_tricksLabel.setForeground(Color.WHITE);
		_tricksLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_tricksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		_tricksLabel.setBorder(new TitledBorder(null, "Tricks", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));
		_tricksLabel.setBounds(637, 53, 142, 52);
		add(_tricksLabel);
		
		
		_treatsLabel = new JLabel(Integer.toString(_tricksAndTreats.getTreats()));
		_treatsLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		_treatsLabel.setOpaque(true);
		_treatsLabel.setBackground(new Color(210, 105, 30));
		_treatsLabel.setForeground(Color.WHITE);
		_treatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_treatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		_treatsLabel.setBorder(new TitledBorder(new CompoundBorder(new LineBorder(new Color(171, 173, 179)), new EmptyBorder(2, 2, 2, 2)), "Treats", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));
		_treatsLabel.setBounds(637, 116, 142, 52);
		add(_treatsLabel);
		_levelPanel = new LevelPanel(_frame,_tricksAndTreats, _inputFile, _isCustomFile);
		_levelPanel.setVisible(false);
		add(_levelPanel);
		_rewardPanel = new Rewards(_tricksLabel, _treatsLabel, _tricksAndTreats);
		_rewardPanel.setBounds(128, 211, 577, 101);
		_rewardPanel.setVisible(false);
		add(_rewardPanel);
		btnSelectNewFile.setBounds(104, 28, 140, 31);
		_fileChooserPanel.add(btnSelectNewFile);
		_back.setOpaque(false);
		_back.setContentAreaFilled(false);
		_back.setBorderPainted(false);
		_back.setBounds(10,506,55,55);
		add(_back);		
		_optionPanel = new JPanel();
		_optionPanel.setLayout(null);
		_optionPanel.setOpaque(false);
		_optionPanel.setBorder(null);
		_optionPanel.setBounds(278, 323, 247, 222);
		add(_optionPanel);
		
		_newQuiz = new JButton((Icon) null);
		_newQuiz.setForeground(new Color(255, 255, 255));
		_newQuiz.setAlignmentX(Component.CENTER_ALIGNMENT);
		_newQuiz.setFont(new Font("Calibri", _newQuiz.getFont().getStyle() | Font.BOLD, _newQuiz.getFont().getSize() + 5));
		_newQuiz.setText("Play New Game");
		_newQuiz.setOpaque(false);
		_newQuiz.setBorder(UIManager.getBorder("Button.border"));
		_newQuiz.setBackground(new Color(107, 142, 35));
		_newQuiz.setBounds(10, 30, 228, 35);
		_optionPanel.add(_newQuiz);
		
		_reviewMistakes = new JButton((Icon) null);
		_reviewMistakes.setText("Review Mistakes");
		_reviewMistakes.setForeground(new Color(255, 255, 255));
		_reviewMistakes.setFont(new Font("Calibri", _reviewMistakes.getFont().getStyle() | Font.BOLD, _reviewMistakes.getFont().getSize() + 5));
		_reviewMistakes.setOpaque(false);
		_reviewMistakes.setBorder(UIManager.getBorder("Button.border"));
		_reviewMistakes.setBackground(new Color(107, 142, 35));
		_reviewMistakes.setBounds(10, 80, 228, 35);
		_optionPanel.add(_reviewMistakes);
		
		_viewStats = new JButton((Icon) null);
		_viewStats.setFont(new Font("Calibri", _viewStats.getFont().getStyle() | Font.BOLD, _viewStats.getFont().getSize() + 5));
		_viewStats.setForeground(new Color(255, 255, 255));
		_viewStats.setText("View Statistics");
		_viewStats.setOpaque(false);
		_viewStats.setBorder(UIManager.getBorder("Button.border"));
		_viewStats.setBackground(new Color(107, 142, 35));
		_viewStats.setBounds(10, 128, 228, 35);
		_optionPanel.add(_viewStats);
		
		_trickTreatButton = new JButton((Icon) null);
		_trickTreatButton.setFont(new Font("Calibri", _trickTreatButton.getFont().getStyle() | Font.BOLD, _trickTreatButton.getFont().getSize() + 5));
		_trickTreatButton.setForeground(new Color(255, 255, 255));
		_trickTreatButton.setText("Tricks and Treats");
		_trickTreatButton.setOpaque(false);
		_trickTreatButton.setBorder(UIManager.getBorder("Button.border"));
		_trickTreatButton.setBackground(new Color(107, 142, 35));
		_trickTreatButton.setBounds(10, 175, 228, 35);
		_optionPanel.add(_trickTreatButton);
		
		_welcome = new JLabel(new ImageIcon("welcomeHa.png"));
		_welcome.setBounds(128, 145, 543, 193);
		add(_welcome);
		
		_tricksLabel = new JLabel(Integer.toString(_tricksAndTreats.getTricks()));
		_tricksLabel.setForeground(Color.WHITE);
		_tricksLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_tricksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		_tricksLabel.setBorder(new TitledBorder(new CompoundBorder(new LineBorder(new Color(171, 173, 179)), new EmptyBorder(2, 2, 2, 2)), "Tricks", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));
		_tricksLabel.setBounds(637, 53, 142, 52);
		add(_tricksLabel);
		
		
		_treatsLabel = new JLabel(Integer.toString(_tricksAndTreats.getTreats()));
		_treatsLabel.setForeground(Color.WHITE);
		_treatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_treatsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		_treatsLabel.setBorder(new TitledBorder(new CompoundBorder(new LineBorder(new Color(171, 173, 179)), new EmptyBorder(2, 2, 2, 2)), "Treats", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));
		_treatsLabel.setBounds(637, 116, 142, 52);
		add(_treatsLabel);
		JLabel bg = new JLabel(new ImageIcon(scaleImage));
		bg.setBounds(0, 0, 800, 576);
		add(bg);
	}
}
