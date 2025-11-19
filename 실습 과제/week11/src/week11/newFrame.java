package week11;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class newFrame extends JFrame {
	private startFrame sFrame;
	
	newFrame(startFrame sFrame) {
		this.sFrame = sFrame;
		
		JPanel nPanel = new JPanel();
		setContentPane(nPanel);
		
		JLabel label = new JLabel("This is new Frame");
		nPanel.add(label);
		
		JButton btn = new JButton("돌아가기");
		btn.setBounds(0, 0, 150, 20);
		nPanel.add(btn);
		
		setTitle("new Frame");
		setBounds(500, 200, 400, 400);
		//setVisible(true);
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 둘 다 설정하면 하나의 프레임만 띄우도록 변경 가능
				// dispose()를 설정하지 않는 프레임은 남아 있음
				sFrame.setVisible(true);
				dispose();
			}
		});
	}
}
