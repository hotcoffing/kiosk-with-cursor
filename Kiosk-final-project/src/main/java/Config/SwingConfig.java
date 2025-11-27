package Config;

import Domain.Order;
import Domain.OrderState;
import Repository.OrderInfoRespositoryImpl;
import Repository.ShoppingCartRepository;
import SwingComponent.CheckOrderListNewTabFrame;
import SwingComponent.OrderFrame;
import SwingComponent.ReceiptNewTabFrame;
import SwingComponent.SelectMenuFrame;
import SwingComponent.SelectOptionNewTabFrame;
import SwingComponent.ShoppingCartFrame;
import SwingComponent.StartFrame;
import SwingComponent.SwingController;
import SwingComponent.SwingControllerImpl;
import SwingComponent.SwingGraphic;
import SwingComponent.SwingGraphicImpl;
import kioskService.CheckOrderListService;
import kioskService.PaymentService;
import kioskService.PaymentServiceImpl;
import kioskService.PrintReceiptService;

// Swing UI 컴포넌트 설정 클래스
// 모든 프레임, 서비스, Repository를 싱글톤 패턴으로 생성 및 관리
public class SwingConfig {

    // config 필드
    private SwingGraphic swingGraphic;
    private SwingController swingController;
    private ShoppingCartConfig shoppingCartConfig;
    private OrderInfoConfig orderInfoConfig;
    
    // Frame 필드
    private StartFrame startFrame;
    private SelectMenuFrame selectMenuFrame;
    private CheckOrderListNewTabFrame checkOrderListNewTabFrame;
    private SelectOptionNewTabFrame selectOptionNewTabFrame;
    private ShoppingCartFrame shoppingCartFrame;
    private OrderFrame orderFrame;
    private ReceiptNewTabFrame receiptNewTabFrame;
    private CheckOrderListService checkOrderListService;
    private PrintReceiptService printReceiptService;
    private PaymentService paymentService;

    // Order 객체 (전역 관리)
    private Order nowOrder;

    // 공통 제어자
    public SwingGraphic swingGraphic() {
        if (swingGraphic == null) swingGraphic = new SwingGraphicImpl();
        return swingGraphic;
    }

    public SwingController swingController() {
        if (swingController == null) swingController = new SwingControllerImpl();
        return swingController;
    }

    public ShoppingCartConfig shoppingCartConfig() {
        if (shoppingCartConfig == null) shoppingCartConfig = new ShoppingCartConfig();
        return shoppingCartConfig;
    }

    public OrderInfoConfig orderInfoConfig() {
        if (orderInfoConfig == null) orderInfoConfig = new OrderInfoConfig();
        return orderInfoConfig;
    }

    // Repository 인스턴스 접근 메서드 추가
    public ShoppingCartRepository shoppingCartRepository() {
        return shoppingCartConfig().shoppingCartRepository();
    }

    public OrderInfoRespositoryImpl orderInfoRepository() {
        return orderInfoConfig().orderInfoRepository();
    }

    public CheckOrderListService checkOrderListService() {
        if (checkOrderListService == null) {
            checkOrderListService = orderInfoConfig().checkOrderListService();
        }
        return checkOrderListService;
    }

    public PrintReceiptService printReceiptService() {
        if (printReceiptService == null) {
            printReceiptService = orderInfoConfig().printReceiptService();
        }
        return printReceiptService;
    }

    public PaymentService paymentService() {
        if (paymentService == null) {
            paymentService = new PaymentServiceImpl(orderInfoRepository());
        }
        return paymentService;
    }

    public Order getNowOrder() {
        if (nowOrder == null) {
            nowOrder = new Order();
            nowOrder.setOrderState(OrderState.ORDERING);
        }
        return nowOrder;
    }
    
    public void setNowOrder(Order order) {
        this.nowOrder = order;
    }

    // Frame 공유 연결
    public StartFrame startFrame() {
        if (startFrame == null) startFrame = new StartFrame(swingGraphic(), swingController());
        return startFrame;
    }

    public SelectMenuFrame selectMenuFrame() {
        if (selectMenuFrame == null) {
            selectMenuFrame = new SelectMenuFrame(swingGraphic(), swingController());
            // 서비스 주입
            selectMenuFrame.setServices(
                    shoppingCartConfig().selectMenuService(),
                    shoppingCartConfig().shoppingCartService()
            );
            // Repository 주입
            selectMenuFrame.setShoppingCartRepository(shoppingCartRepository());
            // Order 객체 설정
            selectMenuFrame.setOrder(getNowOrder());
        }
        return selectMenuFrame;
    }

    public CheckOrderListNewTabFrame checkOrderListNewTabFrame() {
        if (checkOrderListNewTabFrame == null) {
            checkOrderListNewTabFrame = new CheckOrderListNewTabFrame(
                    checkOrderListService()
            );
        }
        return checkOrderListNewTabFrame;
    }

    public SelectOptionNewTabFrame selectOptionNewTabFrame() {
        if (selectOptionNewTabFrame == null) {
            selectOptionNewTabFrame = new SelectOptionNewTabFrame(swingGraphic(), swingController());
            // 서비스 주입
            selectOptionNewTabFrame.setServices(
                    shoppingCartConfig().selectOptionService()
            );
            // Repository 주입
            selectOptionNewTabFrame.setShoppingCartRepository(shoppingCartRepository());
            // Order 객체 설정
            selectOptionNewTabFrame.setOrder(getNowOrder());
        }
        return selectOptionNewTabFrame;
    }

    public ShoppingCartFrame shoppingCartFrame() {
        if (shoppingCartFrame == null) {
            shoppingCartFrame = new ShoppingCartFrame(swingGraphic(), swingController());
            // 서비스 주입
            shoppingCartFrame.setService(shoppingCartConfig().shoppingCartService());
            // Repository 주입
            shoppingCartFrame.setShoppingCartRepository(shoppingCartRepository());
            // Order 객체 설정
            shoppingCartFrame.setOrder(getNowOrder());
        }
        return shoppingCartFrame;
    }

    public OrderFrame orderFrame() {
        if (orderFrame == null) {
            orderFrame = new OrderFrame(swingGraphic(), swingController());
            // 서비스 주입
            orderFrame.setServices(
                    shoppingCartConfig().orderService(),
                    paymentService()
            );
            // Repository 주입
            orderFrame.setShoppingCartRepository(shoppingCartRepository());
            // Order 객체 설정
            orderFrame.setOrder(getNowOrder());
        }
        return orderFrame;
    }

    public ReceiptNewTabFrame receiptNewTabFrame() {
        if (receiptNewTabFrame == null) {
            receiptNewTabFrame = new ReceiptNewTabFrame(swingGraphic(), printReceiptService());
        }
        return receiptNewTabFrame;
    }

    // 매번 새로운 ReceiptNewTabFrame 생성 (주문마다 새 창 띄우기)
    public ReceiptNewTabFrame createNewReceiptFrame() {
        return new ReceiptNewTabFrame(swingGraphic(), printReceiptService());
    }
}