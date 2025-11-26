package SwingComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

public interface SwingController {
    // For CheckOrderList (새 창)
    void openCheckOrderList(JFrame currentFrame);

    // For SelectMenu
    void moveSelectMenu(JFrame currentFrame);

    // For SelectMenu -> Start
    void moveStartFrame(JFrame currentFrame);

    // For SelectMenu -> SelectOption
    void moveSelectOption(JFrame currentFrame);

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
    void openReceipt(JFrame currentFrame);
}