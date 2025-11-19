package week11;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class startPanel extends JPanel {
	private JButton btn;
	private JScrollPane scroll;
	private JTextArea textArea;
	private mainFrame win;
	
	public startPanel(mainFrame win) {
		this.win = win;
		setLayout(null);
		
		btn = new JButton("게임시작");
		btn.setBounds(10, 10, 150, 20);
		add(btn);
		
		textArea = new JTextArea();
		scroll = new JScrollPane(textArea);
		scroll.setBounds(10,  40, 300,  150);
		add(scroll);
		
		btn.addActionListener(new myActionListener());
	}
	
	class myActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			win.change("gPanel");
		}
	}
}

class gamePanel extends JPanel {
	private mainFrame win = null;
	
	gamePanel(mainFrame win) {
		this.win = win;
		
		setLayout(null);
		setBackground(Color.yellow);
		
		JButton btn = new JButton("돌아가기");
		btn.setBounds(200, 200, 150, 20);
		add(btn);
		
		btn.addActionListener(new myActionListener());
	}
	
	class myActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			win.change("sPanel");
		}
	}
}

public class mainFrame extends JFrame {
	
	public startPanel sPanel = null;
	public gamePanel gPanel = null;
	
	mainFrame() {
		setTitle("Panel 변경");
		sPanel = new startPanel(this);
		gPanel = new gamePanel(this);
		
		add(sPanel);
		add(gPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}
	
	public void change(String panelName) {
		if (panelName.equals("sPanel")) {
			getContentPane().removeAll();
			getContentPane().add(sPanel);
			revalidate();
			repaint();
		}
		else {
			getContentPane().removeAll();
			getContentPane().add(sPanel);
			revalidate();
			repaint();
		}
	}
	
	public static void main(String[] args) {
		new mainFrame();
	}

}
