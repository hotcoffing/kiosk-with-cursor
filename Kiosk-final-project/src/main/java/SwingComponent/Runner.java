package SwingComponent;

import Config.SwingConfig;
import Config.AppConfig;
import Config.DatabaseInitializer;
import Domain.IdCounter;

// 애플리케이션 실행 클래스
// 데이터베이스 초기화, 프레임 생성, 컨트롤러 설정 등 애플리케이션 초기화를 담당
public class Runner {
    SwingConfig swingConfig;

    public Runner() {
        // 데이터베이스 자동 초기화 (데이터베이스 및 테이블 생성)
        DatabaseInitializer.initializeDatabase();
        
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