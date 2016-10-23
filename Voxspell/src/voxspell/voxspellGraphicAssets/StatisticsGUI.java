package voxspell.voxspellGraphicAssets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import voxspell.voxspellApp.HalloweenVoxspell;
import voxspell.voxspellApp.StatisticsManager;
import voxspell.voxspellModel.GlobalStatsModel;

import javax.swing.ScrollPaneConstants;
/**
 * Class that sets up a GUI for Statistics page. There are mainly two GUI components
 * that shows a statistics for the game session: a JTextArea that outputs all the
 * words assessed and their statistics sorted in a order of label, a GlobalStatsModel
 * that outputs the bar chart for percentage by the level. In addition to that
 * includes buttons and associated event handlers.
 * @author CJ Bang
 *
 */
public class StatisticsGUI extends JPanel {
	private static JFrame _frame;
	private JTextArea _outputArea;
	private JButton _clearStats, _back;
	private ImageIcon _backImage;
	private JPanel _mainPanel, _textPanel, _chartPanel, _bottomPanel, _middlePanel, _emptyPanel;
	private JScrollPane _scroll;
	private GlobalStatsModel _globalStatsModel;
	private int[] _percentage;
	/*
	 * Constructor for creating a StatisticsGUI object. Takes JFrame from the main 
	 * application. Sets up the GUI, and creates all the necessary statistics from 
	 * StatisticsManager and add the GlobalStatsModel view to the JPanel. 
	 */
	public StatisticsGUI(JFrame frame) {
		_frame = frame;
		buildGUI();
		setUpListeners();
		StatisticsManager stats = new StatisticsManager(_outputArea);
		stats.getStats();
		_percentage = stats.getLevelPercentage();
		_globalStatsModel = new GlobalStatsModel(_percentage);
		_chartPanel.add(_globalStatsModel);
	}
	/*
	 * Creates and lays out GUI components. It simply builds up a composition of GUI
	 * components and makes use of borders, scroll bars and layout managers. 
	 */
	private void buildGUI() {	
		//panels
		_bottomPanel = new JPanel();
		_bottomPanel.setPreferredSize(new Dimension(50, 55));
		_mainPanel = new JPanel();
		_textPanel = new JPanel();
		_textPanel.setPreferredSize(new Dimension(360, 515));
		_textPanel.setBorder(BorderFactory.createTitledBorder("Statistics by word"));
		_chartPanel = new JPanel();
		_chartPanel.setPreferredSize(new Dimension(410, 515));
		_chartPanel.setBorder(BorderFactory.createTitledBorder("Statistics by level"));
		
		// building up the text panel
		_outputArea = new JTextArea(30,30);
		DefaultCaret caret = (DefaultCaret)_outputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);				
		_textPanel.setLayout(null);
		_outputArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(_outputArea);
		scroll.setBounds(0,15,360,500);
		_textPanel.add(scroll);		
		// building up the bottom panel with buttons
		_bottomPanel = new JPanel();
		_clearStats = new JButton("Clear Statistics");
		_backImage = new ImageIcon("arrow.png");
		_back = new JButton(_backImage);
		_back.setPreferredSize(new Dimension(55,55));
		_back.setOpaque(false);
		_back.setContentAreaFilled(false);
		_back.setBorderPainted(false);
		_middlePanel = new JPanel();
		_middlePanel.add(_clearStats);
		_emptyPanel = new JPanel();
		_emptyPanel.setPreferredSize(new Dimension(62, 55));
		_bottomPanel.setLayout(new BorderLayout());
		_bottomPanel.setPreferredSize(new Dimension(700, 60));
		_bottomPanel.add(_back, BorderLayout.WEST);
		_bottomPanel.add(_middlePanel, BorderLayout.CENTER);
		_bottomPanel.add(_emptyPanel, BorderLayout.EAST);
		// adding panels to create the overall appearance
		_mainPanel.add(_textPanel);
		_mainPanel.add(_chartPanel);
		setLayout(new BorderLayout());
		add(_mainPanel, BorderLayout.NORTH);
		add(_bottomPanel, BorderLayout.CENTER);
	}
	/*
	 * Sets up listeners for the buttons. There are two listeners to be implemented.
	 *  1) _clearStats button call clearHistory() from StatisticsManager object and
	 *     updates the JTextArea and GlobalStatsModel view 
	 *  2) _back button creates a new instance of VOXSPELL application title screen
	 *     and overlays on the current JFrame (as if going back to the main menu)
	 */
	private void setUpListeners() {
		_clearStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StatisticsManager stats = new StatisticsManager(_outputArea);
				stats.clearHistory();
				int p = JOptionPane.showConfirmDialog(null,  "Are you sure you wish to clear the game history?", "Clear History", JOptionPane.YES_NO_OPTION);
				switch(p) {
				case JOptionPane.YES_OPTION:
					_chartPanel.remove(_globalStatsModel);
					int[] clear = {0,0,0,0,0,0,0,0,0,0,0};
					_percentage = clear;
					_chartPanel.add(new GlobalStatsModel(clear));
					_chartPanel.revalidate();
					_chartPanel.repaint();
					_outputArea.setText("  Statistics cleared");					
					break;
				default:
					break;
				}
			}
		});
		_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_frame.getContentPane().removeAll();
				HalloweenVoxspell newTitle = new HalloweenVoxspell();
				_frame.getContentPane().add(newTitle);
				_frame.revalidate();
				_frame.repaint();
			}
		});

	}
}
