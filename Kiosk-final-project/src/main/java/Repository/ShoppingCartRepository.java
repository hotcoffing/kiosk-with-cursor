package Repository;

import Domain.OrderItem;

import java.util.List;

public interface ShoppingCartRepository {
    void addOrderItem(OrderItem orderItem);
    void removeOrderItem(OrderItem orderItem);
    void removeAllOrderItems();

    OrderItem getOrderItemById(Long id);
    List<OrderItem> getAllOrderItems();
}
