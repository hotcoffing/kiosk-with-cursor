package SwingComponent;

import Config.AppConfig;
import Config.DatabaseInitializer;
import Config.SwingConfig;
import Domain.IdCounter;

// 애플리케이션 초기화 및 실행 관리 클래스
public class Runner {
    SwingConfig swingConfig;

    // 데이터베이스 초기화 및 프레임 설정 생성자
    public Runner() {
        DatabaseInitializer.initializeDatabase();
        swingConfig = new SwingConfig();
        
        try {
            AppConfig appConfig = new AppConfig();
            IdCounter.syncOrderIdFromDatabase(appConfig.dataSource());
        } catch (Exception e) {
            System.err.println("Failed to initialize ID counter: " + e.getMessage());
        }

        StartFrame startFrame = swingConfig.startFrame();
        SelectMenuFrame selectMenuFrame = swingConfig.selectMenuFrame();
        CheckOrderListNewTabFrame checkOrderListFrame = swingConfig.checkOrderListNewTabFrame();
        SelectOptionNewTabFrame selectOptionNewTabFrame = swingConfig.selectOptionNewTabFrame();
        ShoppingCartFrame shoppingCartFrame = swingConfig.shoppingCartFrame();
        OrderFrame orderFrame = swingConfig.orderFrame();
        ReceiptNewTabFrame receiptFrame = swingConfig.receiptNewTabFrame();

        SwingController controller = swingConfig.swingController();
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

    // 시작 화면 표시 메서드
    public void run() {
        swingConfig.startFrame().setVisible(true);
    }
}