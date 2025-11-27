package SwingComponent;

import javax.swing.JFrame;

import Domain.MenuItem;

// 프레임 간 이동 제어 인터페이스
// 각 프레임 간의 전환 및 새 창 열기 기능을 정의
public interface SwingController {
    // For CheckOrderList (새 창)
    void openCheckOrderList(JFrame nowFrame);

    // For SelectMenu
    void moveSelectMenu(JFrame nowFrame);

    // For SelectMenu -> Start
    void moveStartFrame(JFrame nowFrame);

    // For SelectMenu -> SelectOption (새 창 생성)
    void openSelectOptionNewTab(JFrame nowFrame, MenuItem menuItem);

    // For SelectMenu -> ShoppingCart
    void moveShoppingCart(JFrame nowFrame);

    // For SelectMenu -> Order
    void moveOrder(JFrame nowFrame);

    /*// For SelectOption -> SelectMenu
    void backToSelectMenuFromOption(JFrame nowFrame);

    // For ShoppingCart -> SelectMenu
    void backToSelectMenuFromCart(JFrame nowFrame);

    // For ShoppingCart -> Order
    void moveOrderFromCart(JFrame nowFrame);*/

    // For Order -> Receipt (새 창)
    void openReceipt(JFrame nowFrame, Long orderId);
}