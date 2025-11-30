package Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Domain.OrderItem;

// 장바구니 저장소 구현 클래스
// 메모리 기반 HashMap을 사용한 장바구니 주문 항목 관리 클래스
public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
    private static final Map<Long, OrderItem> store = new HashMap<>();

    // 주문 항목 추가 메서드
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
    
    // 동일한 메뉴와 옵션을 가진 주문 항목 검색 메서드
    private OrderItem findSameMenuAndOptions(OrderItem targetItem) {
        for (OrderItem item : store.values()) {
            if (item.isSameMenuAndOptions(targetItem)) {
                return item;
            }
        }
        return null;
    }

    // 주문 항목 제거 메서드
    @Override
    public void removeOrderItem(OrderItem orderItem) {
        Long orderItemId = orderItem.getId();
        OrderItem existItem = store.get(orderItemId);

        if (existItem == null) {
            System.out.println("No Item ID: " + orderItemId + " is not in storage.");
            return;
        }

        if (existItem.getQuantity() > 1) {
            existItem.setQuantity(existItem.getQuantity() - 1);
        } else {
            store.remove(orderItemId);
        }
    }

    // 모든 주문 항목 제거 메서드
    @Override
    public void removeAllOrderItems() {
        store.clear();
    }

    // ID로 주문 항목 조회 메서드
    @Override
    public OrderItem getOrderItemById(Long id) {
        return store.get(id);
    }

    // 모든 주문 항목 조회 메서드
    @Override
    public List<OrderItem> getAllOrderItems() {
        return new ArrayList<>(store.values());
    }
}
