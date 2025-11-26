package SwingComponent;

import javax.swing.*;

public class SelectMenuFrame extends JFrame {
    public SelectMenuFrame() {
        setTitle("메뉴 선택");
        setBounds(0, 0, 800, 800); // 크기 지정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 임시 라벨 추가
        add(new JLabel("메뉴 선택 화면입니다.", SwingConstants.CENTER));
    }
}