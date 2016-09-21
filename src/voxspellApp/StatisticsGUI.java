package voxspell.voxspellApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import voxspell.GlobalStatsModel;
import voxspell.StatisticsManager;

public class StatisticsGUI extends JPanel {
	private static JFrame _frame;
	private JTextArea _outputArea;
	private JButton _clearStats, _back;
	private ImageIcon _backImage;
	private JPanel _mainPanel, _textPanel, _chartPanel, _bottomPanel, _middlePanel, _emptyPanel;
	private JScrollPane _scroll;
	private GlobalStatsModel _globalStatsModel;
	private int[] _percentage;
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
	private void buildGUI() {	
		_bottomPanel = new JPanel();
		_bottomPanel.setPreferredSize(new Dimension(50, 55));
		_mainPanel = new JPanel();
		//Main frames for the GUI.
		_textPanel = new JPanel();
		_textPanel.setPreferredSize(new Dimension(360, 515));
		_textPanel.setBorder(BorderFactory.createTitledBorder("Statistics by word"));

		_chartPanel = new JPanel();
		_chartPanel.setPreferredSize(new Dimension(410, 515));
		_chartPanel.setBorder(BorderFactory.createTitledBorder("Statistics by level"));
		
		_outputArea = new JTextArea(32,30);
		DefaultCaret caret = (DefaultCaret)_outputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);				
		_outputArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(_outputArea);		
		_textPanel.add(scroll);						
		_mainPanel.add(_textPanel);
		_mainPanel.add(_chartPanel);
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

		setLayout(new BorderLayout());
		add(_mainPanel, BorderLayout.NORTH);
		add(_bottomPanel, BorderLayout.CENTER);
	}
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
				TitleScreen newTitle = new TitleScreen();
				_frame.getContentPane().add(newTitle);
				_frame.revalidate();
				_frame.repaint();
			}
		});

	}
}
