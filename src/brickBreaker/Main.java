package brickBreaker;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		Gameplay gameplay = new Gameplay();
		
		frame.setBounds(10, 10, 700, 600);
		frame.setTitle("Breakout Ball");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(gameplay);
		frame.setVisible(true);
	}

}
