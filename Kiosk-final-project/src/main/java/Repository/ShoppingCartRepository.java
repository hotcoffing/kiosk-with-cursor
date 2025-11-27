package Repository;

import Domain.*;

import java.util.*;

// 장바구니 저장소 인터페이스
// 장바구니에 주문 항목을 추가, 삭제, 조회하는 기능을 정의
public interface ShoppingCartRepository {
    void addOrderItem(OrderItem orderItem);
    void removeOrderItem(OrderItem orderItem);
    void removeAllOrderItems();

    OrderItem getOrderItemById(Long id);
    List<OrderItem> getAllOrderItems();
}
