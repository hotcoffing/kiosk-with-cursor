package Repository;

import Domain.OrderItem;

import java.util.List;

// 장바구니 저장소 인터페이스
// 장바구니 주문 항목 추가, 삭제, 조회 기능 정의 인터페이스
public interface ShoppingCartRepository {
    void addOrderItem(OrderItem orderItem);
    void removeOrderItem(OrderItem orderItem);
    void removeAllOrderItems();

    OrderItem getOrderItemById(Long id);
    List<OrderItem> getAllOrderItems();
}
