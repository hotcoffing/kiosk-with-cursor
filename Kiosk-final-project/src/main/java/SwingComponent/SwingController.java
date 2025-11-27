package SwingComponent;

import javax.swing.JFrame;

import Domain.MenuItem;

// 프레임 간 이동 제어 인터페이스
// 각 프레임 간의 전환 및 새 창 열기 기능을 정의
public interface SwingController {
    // For CheckOrderList (새 창)
    void openCheckOrderList(JFrame currentFrame);

    // For SelectMenu
    void moveSelectMenu(JFrame currentFrame);

    // For SelectMenu -> Start
    void moveStartFrame(JFrame currentFrame);

    // For SelectMenu -> SelectOption (새 창 생성)
    void openSelectOptionNewTab(JFrame currentFrame, MenuItem menuItem);

    // For SelectMenu -> ShoppingCart
    void moveShoppingCart(JFrame currentFrame);

    // For SelectMenu -> Order
    void moveOrder(JFrame currentFrame);

    /*// For SelectOption -> SelectMenu
    void backToSelectMenuFromOption(JFrame currentFrame);

    // For ShoppingCart -> SelectMenu
    void backToSelectMenuFromCart(JFrame currentFrame);

    // For ShoppingCart -> Order
    void moveOrderFromCart(JFrame currentFrame);*/

    // For Order -> Receipt (새 창)
    void openReceipt(JFrame currentFrame, Long orderId);
}