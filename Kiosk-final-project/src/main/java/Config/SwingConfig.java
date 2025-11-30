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

// Swing UI 컴포넌트 및 서비스 의존성 주입 설정 클래스
public class SwingConfig {
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
    private Order nowOrder;
    // Swing UI 컴포넌트 생성 인터페이스 반환 메서드
    public SwingGraphic swingGraphic() {
        if (swingGraphic == null) swingGraphic = new SwingGraphicImpl();
        return swingGraphic;
    }

    // 프레임 간 화면 전환 제어 인터페이스 반환 메서드
    public SwingController swingController() {
        if (swingController == null) swingController = new SwingControllerImpl();
        return swingController;
    }

    // 장바구니 설정 클래스 반환 메서드
    public ShoppingCartConfig shoppingCartConfig() {
        if (shoppingCartConfig == null) shoppingCartConfig = new ShoppingCartConfig();
        return shoppingCartConfig;
    }

    // 주문 정보 설정 클래스 반환 메서드
    public OrderInfoConfig orderInfoConfig() {
        if (orderInfoConfig == null) orderInfoConfig = new OrderInfoConfig();
        return orderInfoConfig;
    }

    // 장바구니 저장소 반환 메서드
    public ShoppingCartRepository shoppingCartRepository() {
        return shoppingCartConfig().shoppingCartRepository();
    }

    // 주문 정보 저장소 반환 메서드
    public OrderInfoRespositoryImpl orderInfoRepository() {
        return orderInfoConfig().orderInfoRepository();
    }

    // 주문 내역 조회 서비스 반환 메서드
    public CheckOrderListService checkOrderListService() {
        if (checkOrderListService == null) {
            checkOrderListService = orderInfoConfig().checkOrderListService();
        }
        return checkOrderListService;
    }

    // 영수증 출력 서비스 반환 메서드
    public PrintReceiptService printReceiptService() {
        if (printReceiptService == null) {
            printReceiptService = orderInfoConfig().printReceiptService();
        }
        return printReceiptService;
    }

    // 결제 서비스 반환 메서드
    public PaymentService paymentService() {
        if (paymentService == null) {
            paymentService = new PaymentServiceImpl(orderInfoRepository());
        }
        return paymentService;
    }

    // 현재 주문 정보 반환 메서드
    public Order getNowOrder() {
        if (nowOrder == null) {
            nowOrder = new Order();
            nowOrder.setOrderState(OrderState.ORDERING);
        }
        return nowOrder;
    }
    
    // 현재 주문 정보 설정 메서드
    public void setNowOrder(Order order) {
        this.nowOrder = order;
    }

    // 시작 화면 프레임 반환 메서드
    public StartFrame startFrame() {
        if (startFrame == null) startFrame = new StartFrame(swingGraphic(), swingController());
        return startFrame;
    }

    // 메뉴 선택 화면 프레임 반환 메서드
    public SelectMenuFrame selectMenuFrame() {
        if (selectMenuFrame == null) {
            selectMenuFrame = new SelectMenuFrame(swingGraphic(), swingController());
            selectMenuFrame.setServices(
                    shoppingCartConfig().selectMenuService(),
                    shoppingCartConfig().shoppingCartService()
            );
            selectMenuFrame.setShoppingCartRepository(shoppingCartRepository());
            selectMenuFrame.setOrder(getNowOrder());
        }
        return selectMenuFrame;
    }

    // 주문 내역 조회 화면 프레임 반환 메서드
    public CheckOrderListNewTabFrame checkOrderListNewTabFrame() {
        if (checkOrderListNewTabFrame == null) {
            checkOrderListNewTabFrame = new CheckOrderListNewTabFrame(
                    swingGraphic(),
                    checkOrderListService()
            );
        }
        return checkOrderListNewTabFrame;
    }

    // 옵션 선택 화면 프레임 반환 메서드
    public SelectOptionNewTabFrame selectOptionNewTabFrame() {
        if (selectOptionNewTabFrame == null) {
            selectOptionNewTabFrame = new SelectOptionNewTabFrame(swingGraphic(), swingController());
            selectOptionNewTabFrame.setServices(
                    shoppingCartConfig().selectOptionService()
            );
            selectOptionNewTabFrame.setShoppingCartRepository(shoppingCartRepository());
            selectOptionNewTabFrame.setOrder(getNowOrder());
        }
        return selectOptionNewTabFrame;
    }

    // 장바구니 화면 프레임 반환 메서드
    public ShoppingCartFrame shoppingCartFrame() {
        if (shoppingCartFrame == null) {
            shoppingCartFrame = new ShoppingCartFrame(swingGraphic(), swingController());
            shoppingCartFrame.setService(shoppingCartConfig().shoppingCartService());
            shoppingCartFrame.setShoppingCartRepository(shoppingCartRepository());
            shoppingCartFrame.setOrder(getNowOrder());
        }
        return shoppingCartFrame;
    }

    // 주문 및 결제 화면 프레임 반환 메서드
    public OrderFrame orderFrame() {
        if (orderFrame == null) {
            orderFrame = new OrderFrame(swingGraphic(), swingController());
            orderFrame.setServices(
                    shoppingCartConfig().orderService(),
                    paymentService()
            );
            orderFrame.setShoppingCartRepository(shoppingCartRepository());
            orderFrame.setOrder(getNowOrder());
        }
        return orderFrame;
    }

    // 영수증 화면 프레임 반환 메서드
    public ReceiptNewTabFrame receiptNewTabFrame() {
        if (receiptNewTabFrame == null) {
            receiptNewTabFrame = new ReceiptNewTabFrame(printReceiptService());
        }
        return receiptNewTabFrame;
    }

    // 새로운 영수증 화면 프레임 생성 메서드
    public ReceiptNewTabFrame createNewReceiptFrame() {
        return new ReceiptNewTabFrame(printReceiptService());
    }
}