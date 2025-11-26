package Repository;

import Domain.*;

import java.util.*;

public interface ShoppingCartRepository {
    void addOrderItem(OrderItem orderItem);
    void removeOrderItem(OrderItem orderItem);
    void removeAllOrderItems();

    OrderItem getOrderItemById(Long id);
    List<OrderItem> getAllOrderItems();
}
