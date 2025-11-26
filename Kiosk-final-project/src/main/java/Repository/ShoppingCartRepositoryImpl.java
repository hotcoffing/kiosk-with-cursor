package Repository;

import Domain.*;

import java.util.*;

public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
    private static Map<Long, OrderItem> store = new HashMap<>();

    @Override
    public void addOrderItem(OrderItem orderItem) {
        // 같은 메뉴와 옵션을 가진 항목이 있는지 찾기
        OrderItem existingItem = findSameMenuAndOptions(orderItem);
        
        if (existingItem != null) {
            // 같은 메뉴+옵션이 있으면 수량만 증가
            existingItem.setQuantity(existingItem.getQuantity() + orderItem.getQuantity());
        } else {
            // 없으면 새로 추가
            store.put(orderItem.getId(), orderItem);
        }
    }
    
    // 같은 메뉴와 옵션을 가진 OrderItem 찾기
    private OrderItem findSameMenuAndOptions(OrderItem targetItem) {
        for (OrderItem item : store.values()) {
            if (item.isSameMenuAndOptions(targetItem)) {
                return item;
            }
        }
        return null;
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
