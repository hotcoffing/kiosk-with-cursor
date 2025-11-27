package SwingComponent;

import Config.SwingConfig;
import Config.AppConfig;
import Domain.IdCounter;

public class Runner {
    SwingConfig swingConfig;

    public Runner() {
        // Config 초기화
        swingConfig = new SwingConfig();
        
        // 앱 실행 시 ID 동기화
        try {
            AppConfig appConfig = new AppConfig();
            IdCounter.syncOrderIdFromDatabase(appConfig.dataSource());
        } catch (Exception e) {
            System.err.println("Failed to initialize ID counter: " + e.getMessage());
        }

        // 모든 프레임 생성
        StartFrame startFrame = swingConfig.startFrame();
        SelectMenuFrame selectMenuFrame = swingConfig.selectMenuFrame();
        CheckOrderListNewTabFrame checkOrderListFrame = swingConfig.checkOrderListNewTabFrame();
        SelectOptionNewTabFrame selectOptionNewTabFrame = swingConfig.selectOptionNewTabFrame();
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
                selectOptionNewTabFrame,
                shoppingCartFrame,
                orderFrame,
                receiptFrame,
                swingConfig
        );
    }

    // 프로그램 시작
    public void run() {
        swingConfig.startFrame().setVisible(true);
    }
}