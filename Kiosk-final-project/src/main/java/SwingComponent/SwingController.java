package SwingComponent;

import javax.swing.JFrame;

import Domain.MenuItem;

// 프레임 간 화면 전환 제어 인터페이스
public interface SwingController {
    // 주문 내역 조회 화면 열기 메서드
    void openCheckOrderList(JFrame nowFrame);
    // 메뉴 선택 화면으로 이동 메서드
    void moveSelectMenu(JFrame nowFrame);
    // 시작 화면으로 이동 메서드
    void moveStartFrame(JFrame nowFrame);
    // 옵션 선택 새 탭 열기 메서드
    void openSelectOptionNewTab(JFrame nowFrame, MenuItem menuItem);
    // 장바구니 화면으로 이동 메서드
    void moveShoppingCart(JFrame nowFrame);
    // 주문 화면으로 이동 메서드
    void moveOrder(JFrame nowFrame);
    // 영수증 화면 열기 메서드
    void openReceipt(JFrame nowFrame, Long orderId);
}