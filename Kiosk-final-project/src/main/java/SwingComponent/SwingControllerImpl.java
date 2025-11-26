package SwingComponent;

import Domain.*;
import javax.swing.*;

public class SwingControllerImpl implements SwingController {

    // 모든 프레임 객체 필드 (config 안됨)
    private StartFrame startFrame;
    private SelectMenuFrame selectMenuFrame;
    private CheckOrderListNewTabFrame checkOrderListFrame;
    private SelectOptionNewTabFrame selectOptionNewTabFrame;
    private ShoppingCartFrame shoppingCartFrame;
    private OrderFrame orderFrame;
    private ReceiptNewTabFrame receiptFrame;

    public SwingControllerImpl() {
        // 생성자는 비워둠 (Config에서 생성 시점에는 프레임이 없으므로)
    }

    // Runner 호출 : 프레임 DI 주입
    public void setFrames(StartFrame startFrame,
                          SelectMenuFrame selectMenuFrame,
                          CheckOrderListNewTabFrame checkOrderListFrame,
                          SelectOptionNewTabFrame selectOptionNewTabFrame,
                          ShoppingCartFrame shoppingCartFrame,
                          OrderFrame orderFrame,
                          ReceiptNewTabFrame receiptFrame) {
        this.startFrame = startFrame;
        this.selectMenuFrame = selectMenuFrame;
        this.checkOrderListFrame = checkOrderListFrame;
        this.selectOptionNewTabFrame = selectOptionNewTabFrame;
        this.shoppingCartFrame = shoppingCartFrame;
        this.orderFrame = orderFrame;
        this.receiptFrame = receiptFrame;
    }

    // startFrame -> checkOrderListNewTabFrame (새 창 생성)
    @Override
    public void openCheckOrderList(JFrame currentFrame) {
        if (checkOrderListFrame != null) {
            checkOrderListFrame.setVisible(true);
            System.out.println("주문 내역 새 창 열기");
        }
    }

    // startFrame -> selectMenuFrame
    @Override
    public void moveSelectMenu(JFrame currentFrame) {
        if (selectMenuFrame != null) {
            currentFrame.setVisible(false);
            selectMenuFrame.setVisible(true);
            // 장바구니 업데이트
            if (selectMenuFrame instanceof SelectMenuFrame) {
                ((SelectMenuFrame) selectMenuFrame).updateTotal();
            }
            System.out.println("메뉴 선택 화면으로 이동");
        }
    }

    // selectMenuFrame -> startFrame
    @Override
    public void moveStartFrame(JFrame currentFrame) {
        if (startFrame != null) {
            currentFrame.setVisible(false);
            startFrame.setVisible(true);
            System.out.println("시작 화면으로 복귀");
        }
    }

    // selectMenuFrame -> selectOptionNewTabFrame (새 창 생성)
    @Override
    public void openSelectOptionNewTab(JFrame currentFrame, MenuItem menuItem) {
        if (selectOptionNewTabFrame != null) {
            selectOptionNewTabFrame.setMenuItem(menuItem);
            selectOptionNewTabFrame.setVisible(true);
            System.out.println("옵션 선택 새 창 열기 (메뉴: " + menuItem.getName() + ")");
        }
    }

    // selectMenuFrame -> shoppingCartFrame
    @Override
    public void moveShoppingCart(JFrame currentFrame) {
        if (shoppingCartFrame != null) {
            // 장바구니 업데이트
            if (shoppingCartFrame instanceof ShoppingCartFrame) {
                ((ShoppingCartFrame) shoppingCartFrame).updateOrderItems();
            }
            currentFrame.setVisible(false);
            shoppingCartFrame.setVisible(true);
            System.out.println("장바구니 화면으로 이동");
        }
    }

    // selectMenuFrame -> OrderFrame
    @Override
    public void moveOrder(JFrame currentFrame) {
        if (orderFrame != null) {
            // 주문 화면 업데이트
            if (orderFrame instanceof OrderFrame) {
                ((OrderFrame) orderFrame).updateOrderItems();
            }
            currentFrame.setVisible(false);
            orderFrame.setVisible(true);
            System.out.println("주문 화면으로 이동");
        }
    }

    // 메뉴 선택 화면 총계 업데이트 (옵션 창에서 호출)
    public void updateSelectMenuTotal() {
        if (selectMenuFrame != null) {
            selectMenuFrame.updateTotal();
        }
    }

    /*// selectOptionFrame -> selectMenuFrame
    @Override
    public void backToSelectMenuFromOption(JFrame currentFrame) {
        if (selectMenuFrame != null) {
            currentFrame.setVisible(false);
            selectMenuFrame.setVisible(true);
            System.out.println("옵션 -> 메뉴 선택 화면 복귀");
        }
    }

    // shoppingCartFrame -> selectMenuFrame
    @Override
    public void backToSelectMenuFromCart(JFrame currentFrame) {
        if (selectMenuFrame != null) {
            currentFrame.setVisible(false);
            selectMenuFrame.setVisible(true);
            System.out.println("장바구니 -> 메뉴 선택 화면 복귀");
        }
    }

    // shoppingCartFrame -> OrderFrame
    @Override
    public void moveOrderFromCart(JFrame currentFrame) {
        if (orderFrame != null) {
            currentFrame.setVisible(false);
            orderFrame.setVisible(true);
            System.out.println("장바구니 -> 주문 화면으로 이동");
        }
    }*/

    // OrderFrame -> receiptNewTabFrame (새 창 생성)
    @Override
    public void openReceipt(JFrame currentFrame) {
        if (receiptFrame != null) {
            receiptFrame.setVisible(true);
            System.out.println("영수증 새 창 열기");
        }
    }
}