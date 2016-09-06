package voxspell.voxspellApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class TitleScreen extends JFrame implements ActionListener {
	private JPanel _mainPanel, _titleScreenPanel, _emptyPanel, _picPanel, _viewPanel, _gameOptionPanel, _basicPanel, _advancedPanel;
	private JButton[] _basicLevels, _advancedLevels;
	private JLabel _picLabel, _label, _welcomeLabel;
	private ImageIcon _welcome;
	private Font font = new Font("Verdana", Font.PLAIN, 20);
	protected static JFrame frame;
	public TitleScreen() {
		buildGUI();
		for(int i = 0; i < 6; i++) {
			_basicLevels[i].addActionListener(this);
		}
		for (int i = 0; i < 5; i++) {
			_advancedLevels[i].addActionListener(this);
		}
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
		_viewPanel.setPreferredSize(new Dimension(600, 75));
		_label = new JLabel("Please select the level of the spelling quiz", SwingConstants.CENTER);
		_label.setFont(font);
		_viewPanel.add(_label);

		_titleScreenPanel.setLayout(new BorderLayout());
		_titleScreenPanel.add(_emptyPanel, BorderLayout.NORTH);
		_titleScreenPanel.add(_picPanel, BorderLayout.CENTER);
		_titleScreenPanel.add(_viewPanel, BorderLayout.SOUTH);

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
		
		_mainPanel.add(_titleScreenPanel, BorderLayout.NORTH);
		_mainPanel.add(_gameOptionPanel, BorderLayout.SOUTH);

		add(_mainPanel);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String levelChosen = ((JButton)e.getSource()).getText();
		for (int i = 0; i < 11; i++) {
			if (levelChosen.equals("Level "+Integer.toString(i+1))) {
				_mainPanel.setVisible(false);
				GameGUI game = new GameGUI(i+1);
				add(game);
				game.setVisible(true);
			}
		}
	}

	public static void main(String[] args) {
		frame = new TitleScreen();
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("VOXSPELL");
	}
}