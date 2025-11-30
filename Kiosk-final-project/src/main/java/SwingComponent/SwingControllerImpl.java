package SwingComponent;

import javax.swing.JFrame;

import Config.SwingConfig;
import Domain.MenuItem;

// 프레임 간 화면 전환 제어 구현 클래스
public class SwingControllerImpl implements SwingController {
    private StartFrame startFrame;
    private SelectMenuFrame selectMenuFrame;
    private CheckOrderListNewTabFrame checkOrderListFrame;
    private SelectOptionNewTabFrame selectOptionNewTabFrame;
    private ShoppingCartFrame shoppingCartFrame;
    private OrderFrame orderFrame;
    private ReceiptNewTabFrame receiptFrame;
    private SwingConfig swingConfig;

    // 컨트롤러 생성자
    public SwingControllerImpl() {
    }

    // 프레임 설정 메서드
    public void setFrames(StartFrame startFrame,
                          SelectMenuFrame selectMenuFrame,
                          CheckOrderListNewTabFrame checkOrderListFrame,
                          SelectOptionNewTabFrame selectOptionNewTabFrame,
                          ShoppingCartFrame shoppingCartFrame,
                          OrderFrame orderFrame,
                          ReceiptNewTabFrame receiptFrame,
                          SwingConfig swingConfig) {
        this.startFrame = startFrame;
        this.selectMenuFrame = selectMenuFrame;
        this.checkOrderListFrame = checkOrderListFrame;
        this.selectOptionNewTabFrame = selectOptionNewTabFrame;
        this.shoppingCartFrame = shoppingCartFrame;
        this.orderFrame = orderFrame;
        this.receiptFrame = receiptFrame;
        this.swingConfig = swingConfig;
    }

    // 주문 내역 조회 화면 열기 메서드
    @Override
    public void openCheckOrderList(JFrame nowFrame) {
        if (checkOrderListFrame != null) {
            checkOrderListFrame.setVisible(true);
        }
    }

    // 메뉴 선택 화면으로 이동 메서드
    @Override
    public void moveSelectMenu(JFrame nowFrame) {
        if (selectMenuFrame != null) {
            nowFrame.setVisible(false);
            selectMenuFrame.setVisible(true);
            if (selectMenuFrame instanceof SelectMenuFrame) {
                ((SelectMenuFrame) selectMenuFrame).updateTotal();
            }
        }
    }

    // 시작 화면으로 이동 메서드
    @Override
    public void moveStartFrame(JFrame nowFrame) {
        if (startFrame != null) {
            nowFrame.setVisible(false);
            startFrame.setVisible(true);
        }
    }

    // 옵션 선택 새 탭 열기 메서드
    @Override
    public void openSelectOptionNewTab(JFrame nowFrame, MenuItem menuItem) {
        if (selectOptionNewTabFrame != null) {
            selectOptionNewTabFrame.setMenuItem(menuItem);
            selectOptionNewTabFrame.setVisible(true);
        }
    }

    // 장바구니 화면으로 이동 메서드
    @Override
    public void moveShoppingCart(JFrame nowFrame) {
        if (shoppingCartFrame != null) {
            if (shoppingCartFrame instanceof ShoppingCartFrame) {
                ((ShoppingCartFrame) shoppingCartFrame).updateOrderItems();
            }
            nowFrame.setVisible(false);
            shoppingCartFrame.setVisible(true);
        }
    }

    // 주문 화면으로 이동 메서드
    @Override
    public void moveOrder(JFrame nowFrame) {
        if (orderFrame != null) {
            if (orderFrame instanceof OrderFrame) {
                ((OrderFrame) orderFrame).updateOrderItems();
            }
            nowFrame.setVisible(false);
            orderFrame.setVisible(true);
        }
    }

    // 메뉴 선택 화면 총계 업데이트 메서드
    public void updateSelectMenuTotal() {
        if (selectMenuFrame != null) {
            selectMenuFrame.updateTotal();
        }
    }

    // 영수증 화면 열기 메서드
    @Override
    public void openReceipt(JFrame nowFrame, Long orderId) {
        if (swingConfig != null) {
            ReceiptNewTabFrame newReceiptFrame = swingConfig.createNewReceiptFrame();
            if (newReceiptFrame != null) {
                newReceiptFrame.displayReceipt(orderId);
            }
        }
    }
}