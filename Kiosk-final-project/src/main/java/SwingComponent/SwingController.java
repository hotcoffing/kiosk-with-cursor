package SwingComponent;

import javax.swing.JFrame;

import Domain.MenuItem;

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
    void openReceipt(JFrame nowFrame);
}