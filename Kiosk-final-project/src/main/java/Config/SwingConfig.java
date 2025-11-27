package Config;

import Domain.*;
import SwingComponent.*;

public class SwingConfig {

    // config 필드
    private SwingGraphic swingGraphic;
    private SwingAction swingAction;
    private SwingController swingController;
    private ShoppingCartConfig shoppingCartConfig;
    
    // Frame 필드
    private StartFrame startFrame;
    private SelectMenuFrame selectMenuFrame;
    private CheckOrderListNewTabFrame checkOrderListNewTabFrame;
    private SelectOptionNewTabFrame selectOptionNewTabFrame;
    private ShoppingCartFrame shoppingCartFrame;
    private OrderFrame orderFrame;
    private ReceiptNewTabFrame receiptNewTabFrame;

    // Order 객체 (전역 관리)
    private Order nowOrder;

    // 공통 제어자
    public SwingGraphic swingGraphic() {
        if (swingGraphic == null) swingGraphic = new SwingGraphicImpl();
        return swingGraphic;
    }

    public SwingAction swingAction() {
        if (swingAction == null) swingAction = new SwingActionImpl();
        return swingAction;
    }

    public SwingController swingController() {
        if (swingController == null) swingController = new SwingControllerImpl();
        return swingController;
    }

    public ShoppingCartConfig shoppingCartConfig() {
        if (shoppingCartConfig == null) shoppingCartConfig = new ShoppingCartConfig();
        return shoppingCartConfig;
    }

    public Order getNowOrder() {
        if (nowOrder == null) {
            nowOrder = new Order();
        }
        return nowOrder;
    }

    // Frame 공유 연결
    public StartFrame startFrame() {
        if (startFrame == null) startFrame = new StartFrame(swingGraphic(), swingController());
        return startFrame;
    }

    public SelectMenuFrame selectMenuFrame() {
        if (selectMenuFrame == null) {
            selectMenuFrame = new SelectMenuFrame(swingGraphic(), swingAction(), swingController(), shoppingCartConfig());
            // Order 객체 설정
            selectMenuFrame.setOrder(getNowOrder());
        }
        return selectMenuFrame;
    }

    public CheckOrderListNewTabFrame checkOrderListNewTabFrame() {
        if (checkOrderListNewTabFrame == null) checkOrderListNewTabFrame = new CheckOrderListNewTabFrame(swingGraphic(), swingAction());
        return checkOrderListNewTabFrame;
    }

    public SelectOptionNewTabFrame selectOptionNewTabFrame() {
        if (selectOptionNewTabFrame == null) {
            selectOptionNewTabFrame = new SelectOptionNewTabFrame(swingGraphic(), swingAction(), swingController(), shoppingCartConfig());
            // Order 객체 설정
            selectOptionNewTabFrame.setOrder(getNowOrder());
        }
        return selectOptionNewTabFrame;
    }

    public ShoppingCartFrame shoppingCartFrame() {
        if (shoppingCartFrame == null) {
            shoppingCartFrame = new ShoppingCartFrame(swingGraphic(), swingController(), shoppingCartConfig());
            // Order 객체 설정
            shoppingCartFrame.setOrder(getNowOrder());
        }
        return shoppingCartFrame;
    }

    public OrderFrame orderFrame() {
        if (orderFrame == null) {
            orderFrame = new OrderFrame(swingGraphic(), swingAction(), swingController(), shoppingCartConfig());
            // Order 객체 설정
            orderFrame.setOrder(getNowOrder());
        }
        return orderFrame;
    }

    public ReceiptNewTabFrame receiptNewTabFrame() {
        if (receiptNewTabFrame == null) receiptNewTabFrame = new ReceiptNewTabFrame(swingGraphic());
        return receiptNewTabFrame;
    }
}