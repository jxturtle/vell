package voxspell.voxspellApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game extends JPanel implements ActionListener {
	private JPanel _panel;
	private JLabel _text;
	public Game(int i) {
		buildGUI();
	}
	private void buildGUI() {
		_panel = new JPanel();
		_text = new JLabel("hi");
		_panel.add(_text);
		add(_panel);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
