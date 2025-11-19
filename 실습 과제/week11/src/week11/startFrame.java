package week11;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;

public class startFrame extends JFrame {
	
	private newFrame nFrame;
	
	public startFrame() {
		setTitle("시작 프레임 띄우기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(120, 60, 400, 400);
		
		nFrame = new newFrame(this);
		
		JPanel sPanel = new JPanel();
		setContentPane(sPanel);
		
		JButton btn = new JButton("newFrame 생성");
		add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 둘 다 설정하면 하나의 프레임만 띄우도록 변경 가능
				// dispose()를 설정하지 않는 프레임은 남아 있음
				nFrame.setVisible(true);
				// dispose();
			}
		});
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new startFrame();
	}
}
