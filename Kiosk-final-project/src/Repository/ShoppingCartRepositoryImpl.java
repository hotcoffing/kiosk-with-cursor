package Repository;

import Domain.OrderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
    private static Map<Long, OrderItem> store = new HashMap<>();

    @Override
    public void addItem(OrderItem orderItem) {
        store.put(orderItem.getId(), orderItem);
    }

    @Override
    public void removeItem(OrderItem orderItem) {
        store.remove(orderItem.getId());
    }

    @Override
    public OrderItem findById(Long orderId) {
        return store.get(orderId);
    }

    @Override
    public List<OrderItem> findAllOrderItems() {
        return new ArrayList<>(store.values());
    }
}
