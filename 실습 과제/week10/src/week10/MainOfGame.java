package week10;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MainOfGame extends JFrame {
	JLabel [] label = new JLabel[3];
	JLabel result = new JLabel("시작하세요");
	
	JPanel contentPane = new GamePanel();
	
	public MainOfGame() {
		super("Game Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
		
		setContentPane(contentPane);
		
		// 이벤트 부착 필요
		contentPane.addKeyListener(new myKeyListener());
		contentPane.addMouseListener(new myMouseListener());
		getContentPane().requestFocus();
	}
	
	class GamePanel extends JPanel {
		public GamePanel() {
			setLayout(null);
			
			Font labelFont = new Font("Gulim", Font.ITALIC, 30);
			
			for (int i = 0; i < label.length; i++) {
				label[i] = new JLabel("0");
				label[i].setBounds(30 + 80 * i, 50, 60, 30);
				label[i].setFont(labelFont);
				
				// JLabel 배경을 넣기위한 공간 설정
				label[i].setOpaque(true);
				// JLabel 가운데 정렬
				label[i].setHorizontalAlignment(JLabel.CENTER);
				label[i].setBackground(Color.MAGENTA);
				label[i].setForeground(Color.YELLOW);
			
				add(label[i]);
			}
			
			result.setBounds(100, 120, 200, 20);
			add(result);
		}
	}
	
	void run() {
		int [] rdNumber = { 0, 0, 0 };
		
		for (int i = 0; i < rdNumber.length; i++) {
			rdNumber[i] = (int)(Math.random() * 3);
			label[i].setText(Integer.toString(rdNumber[i]));
		}
		
		if (rdNumber[0] == rdNumber[1] && rdNumber[1] == rdNumber[2]) {
			result.setText("잭팟에 성공하였습니다.");
		}
		else {
			result.setText("잭팟에 실패하였습니다.");
		}
	}
	
	class myKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == '\n') {
				run();
			}
		}
	}
	
	class myMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			run(); 
		}
	}
	
	public static void main(String[] args) {
		MainOfGame run = new MainOfGame();
	}
}
