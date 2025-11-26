package SwingComponent;

import Config.SwingConfig;

public class Runner {
    SwingConfig swingConfig;

    public Runner() {
        // Config 초기화
        swingConfig = new SwingConfig();

        // 모든 프레임 생성
        StartFrame startFrame = swingConfig.startFrame();
        SelectMenuFrame selectMenuFrame = swingConfig.selectMenuFrame();
        CheckOrderListNewTabFrame checkOrderListFrame = swingConfig.checkOrderListNewTabFrame();
        SelectOptionFrame selectOptionFrame = swingConfig.selectOptionFrame();
        ShoppingCartFrame shoppingCartFrame = swingConfig.shoppingCartFrame();
        OrderFrame orderFrame = swingConfig.orderFrame();
        ReceiptNewTabFrame receiptFrame = swingConfig.receiptNewTabFrame();

        // Controller 가져오기 및 프레임 주입
        SwingController controller = swingConfig.swingController();

        // 컨트롤러에 프레임 전달 
        ((SwingControllerImpl) controller).setFrames(
                startFrame,
                selectMenuFrame,
                checkOrderListFrame,
                selectOptionFrame,
                shoppingCartFrame,
                orderFrame,
                receiptFrame
        );
    }

    // 프로그램 시작
    public void run() {
        swingConfig.startFrame().setVisible(true);
    }
}