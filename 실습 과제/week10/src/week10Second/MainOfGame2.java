package week10Second;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MainOfGame2 extends JFrame {

	ImageIcon [] gbbImage = {
		new ImageIcon("resource/가위.jpg"),
		new ImageIcon("resource/바위.jpg"),
		new ImageIcon("resource/보.jpg")
	};
	
	MenuPanel menupanel = new MenuPanel();
	GamePanel gamepanel = new GamePanel();
	
	public MainOfGame2() {
		super("가위바위보 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 300);
		setVisible(true);
		
		add(menupanel, BorderLayout.NORTH);
		add(gamepanel, BorderLayout.SOUTH);
	}
	
	class MenuPanel extends JPanel {
		
	}
	
	class GamePanel extends JPanel {
		
	}
	
	public static void main(String[] args) {
		
	}

}
