package voxspell.voxspellApp;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Game extends JPanel implements ActionListener {
	private JPanel _leftPanel, _textAreaPanel, _buttonPanel, _rightPanel;
	private JLabel _texts;
	private JTextArea _game;
	private JTextField _text;
	private JButton _submit, _repeat, _voice;
	public Game(int i) {
		buildGUI();
	}
	private void buildGUI() {
		
		_leftPanel = new JPanel();
		_leftPanel.setPreferredSize(new Dimension(400, 600));
		_leftPanel.setLayout(new GridLayout(2,1));

		_textAreaPanel = new JPanel();
		_game = new JTextArea(25, 35);
		_textAreaPanel.add(_game);
		
		_buttonPanel = new JPanel();
		_buttonPanel.setLayout(new GridLayout(4,1));
		_text = new JTextField("      ");
		_submit = new JButton("Submit");
		_repeat = new JButton("Listen again");
		_voice = new JButton("Change voice");
		_buttonPanel.add(_text);
		_buttonPanel.add(_submit);
		_buttonPanel.add(_repeat);
		_buttonPanel.add(_voice);
		
		_leftPanel.add(_textAreaPanel);
		_leftPanel.add(_buttonPanel);
		
		_rightPanel = new JPanel();
		_rightPanel.setPreferredSize(new Dimension(400, 600));
		_texts = new JLabel("This panel is for stats model");
		_rightPanel.add(_texts);
		
		
		setLayout(new GridLayout(1,2));
		add(_leftPanel);
		add(_rightPanel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
