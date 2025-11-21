package kioskService;

import Domain.Order;
import Domain.OrderItem;

public interface ShoppingCartService {
    public void plusOrderItem(Long id);
    public void minusOrderItem(Long id);

    public void goChooseOrder(Order order);
    public void goOrder(Order order);
}
