package voxspell.voxspellApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VOXSPELL extends JFrame implements ActionListener {
	private JPanel _mainPanel, _butPanel, _picPanel, _viewPanel;
	private JButton[] _levels;
	private JLabel _picLabel, _label;
	private ImageIcon _image;
	protected static JFrame frame;
	public VOXSPELL() {
		buildGUI();
		for(int i = 0; i < 5; i++) {
			_levels[i].addActionListener(this);
		}
	}
	
	private void buildGUI() {
		_mainPanel = new JPanel();
		_picPanel = new JPanel();
		_picPanel.setPreferredSize(new Dimension(600, 300));
		_viewPanel = new JPanel();
		_viewPanel.setPreferredSize(new Dimension(600, 150));
		_butPanel = new JPanel();
		_butPanel.setPreferredSize(new Dimension(600, 50));
		_image = new ImageIcon("main.jpg");
		_picLabel = new JLabel(_image);
		_picPanel.add(_picLabel);

		_label = new JLabel("<html>Welcome to VOXSPELL<br>Please select the spelling level</html>", SwingConstants.CENTER);
		_viewPanel.add(_label);
		
		_butPanel.setLayout(new GridLayout(1,5));
		_levels = new JButton[5];
		for (int i = 0; i < 5; i++) {
			_levels[i] = new JButton("Level " + Integer.toString(i+1));
			_butPanel.add(_levels[i]);
		}
		_mainPanel.setLayout(new BorderLayout());
		_mainPanel.add(_picPanel, BorderLayout.NORTH);
		_mainPanel.add(_viewPanel, BorderLayout.CENTER);
		_mainPanel.add(_butPanel, BorderLayout.SOUTH);
		add(_mainPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String levelChosen = ((JButton)e.getSource()).getText();
		switch (levelChosen) {
		case ("Level 1"):
			// creates new instance with textfile 1
			_mainPanel.setVisible(false);
		case ("Level 2"):
			// creates new instance with textfile 2
			_mainPanel.setVisible(false);
		case ("Level 3"):
			// creates new instance with textfile 3
			_mainPanel.setVisible(false);
		case ("Level 4"):
			// creates new instance with textfile 4
			_mainPanel.setVisible(false);
		case ("Level 5"):
			// creates new instance with textfile 5
			_mainPanel.setVisible(false);
		}
		
	}
	public static void main(String[] args) {
		frame = new VOXSPELL();
		frame.setSize(600, 450);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("VOXSPELL");
	}
}
