package SwingComponent;

import javax.swing.*;

public class CheckOrderListNewTabFrame extends JFrame {
    private final SwingGraphic swingGraphic;
    private final SwingAction swingAction;

    public CheckOrderListNewTabFrame(SwingGraphic swingGraphic, SwingAction swingAction) {
        this.swingGraphic = swingGraphic;
        this.swingAction = swingAction;

        setTitle("주문 내역");
        setBounds(900, 0, 800, 800); // 크기 지정
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // 임시 라벨 추가
        add(new JLabel("주문 내역 화면입니다.", SwingConstants.CENTER));
    }
}