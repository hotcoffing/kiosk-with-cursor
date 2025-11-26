package SwingComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

// ActionListener 인터페이스 구현은 불필요하므로 제거 (Controller 역할에 집중)
public class SwingControllerImpl implements SwingController {

    public SwingControllerImpl() {}

    @Override
    public void moveSelectMenuFrame(JFrame nowFrame, ActionEvent e) {
        // Start 프레임 숨기기
        nowFrame.setVisible(false);

        // 메뉴 선택 프레임 생성 및 표시
        SelectMenuFrame nextFrame = new SelectMenuFrame();
        nextFrame.setVisible(true);

        System.out.println("-> SelectMenuFrame으로 이동 (Controller 실행)");
    }

    @Override
    public void moveOrderInfoFrame(JFrame nowFrame, ActionEvent e) {
        // Start 프레임 숨기기
        nowFrame.setVisible(false);

        // 주문 내역 프레임 생성 및 표시
        CheckOrderListFrame nextFrame = new CheckOrderListFrame();
        nextFrame.setVisible(true);

        System.out.println("-> CheckOrderListFrame으로 이동 (Controller 실행)");
    }
}