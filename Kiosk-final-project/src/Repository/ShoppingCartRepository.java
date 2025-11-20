package Repository;

import Domain.OrderItem;

import java.util.List;

public interface ShoppingCartRepository {
    void addItem(OrderItem orderItem);
    void removeItem(OrderItem orderItem);

    OrderItem findById(Long orderId);
    List<OrderItem> findAllOrderItems();
}
