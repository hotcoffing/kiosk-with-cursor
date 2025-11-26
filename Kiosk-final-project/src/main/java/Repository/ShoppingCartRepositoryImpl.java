package Repository;

import Domain.*;

import java.util.*;

public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
    private static Map<Long, OrderItem> store = new HashMap<>();

    @Override
    public void addOrderItem(OrderItem orderItem) {
        Long orderItemId = orderItem.getId();
        OrderItem existItem = store.get(orderItemId);

        if (existItem != null) {
            existItem.setQuantity(existItem.getQuantity() + orderItem.getQuantity());
        }
        else {
            store.put(orderItemId, orderItem);
        }
    }

    @Override
    public void removeOrderItem(OrderItem orderItem) {
        Long orderItemId = orderItem.getId();
        OrderItem existItem = store.get(orderItemId);

        if  (existItem == null) {
            System.out.println("No Item ID: " + orderItemId + " is not in storage.");
            return;
        }

        if (existItem.getQuantity() > 1) {
            existItem.setQuantity(existItem.getQuantity() - 1);
        }
        else {
            store.remove(orderItemId);
        }
    }

    @Override
    public void removeAllOrderItems() {
        store.clear();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return store.get(id);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return new ArrayList<>(store.values());
    }
}
