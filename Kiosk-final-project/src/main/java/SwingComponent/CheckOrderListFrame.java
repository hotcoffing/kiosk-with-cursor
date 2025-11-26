package SwingComponent;

import javax.swing.*;

public class CheckOrderListFrame extends JFrame {
    public CheckOrderListFrame() {
        setTitle("주문 내역");
        setBounds(0, 0, 800, 800); // 크기 지정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 임시 라벨 추가
        add(new JLabel("주문 내역 화면입니다.", SwingConstants.CENTER));
    }
}