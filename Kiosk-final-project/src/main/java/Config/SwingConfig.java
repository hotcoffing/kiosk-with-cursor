package Config;

import SwingComponent.*;

public class SwingConfig {

    // config 필드
    private SwingGraphic swingGraphic;
    private SwingAction swingAction;
    private SwingController swingController;
    
    // Frame 필드
    private StartFrame startFrame;
    private SelectMenuFrame selectMenuFrame;
    private CheckOrderListNewTabFrame checkOrderListNewTabFrame;
    private SelectOptionFrame selectOptionFrame;
    private ShoppingCartFrame shoppingCartFrame;
    private OrderFrame orderFrame;
    private ReceiptNewTabFrame receiptNewTabFrame;

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

    // Frame 공유 연결
    public StartFrame startFrame() {
        if (startFrame == null) startFrame = new StartFrame(swingGraphic(), swingController());
        return startFrame;
    }

    public SelectMenuFrame selectMenuFrame() {
        if (selectMenuFrame == null) selectMenuFrame = new SelectMenuFrame(swingGraphic(), swingAction(), swingController());
        return selectMenuFrame;
    }

    public CheckOrderListNewTabFrame checkOrderListNewTabFrame() {
        if (checkOrderListNewTabFrame == null) checkOrderListNewTabFrame = new CheckOrderListNewTabFrame(swingGraphic(), swingAction());
        return checkOrderListNewTabFrame;
    }

    public SelectOptionFrame selectOptionFrame() {
        if (selectOptionFrame == null) selectOptionFrame = new SelectOptionFrame(swingGraphic(), swingAction(), swingController());
        return selectOptionFrame;
    }

    public ShoppingCartFrame shoppingCartFrame() {
        if (shoppingCartFrame == null) shoppingCartFrame = new ShoppingCartFrame(swingGraphic(), swingController());
        return shoppingCartFrame;
    }

    public OrderFrame orderFrame() {
        if (orderFrame == null) orderFrame = new OrderFrame(swingGraphic(), swingAction(), swingController());
        return orderFrame;
    }

    public ReceiptNewTabFrame receiptNewTabFrame() {
        if (receiptNewTabFrame == null) receiptNewTabFrame = new ReceiptNewTabFrame(swingGraphic());
        return receiptNewTabFrame;
    }
}