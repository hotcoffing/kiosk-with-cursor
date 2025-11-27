package Repository;

import Domain.*;
import java.util.*;

// 주문 정보 저장소 인터페이스
// 주문의 추가, 삭제, 조회 등 주문 정보 관련 데이터베이스 작업을 정의
public interface OrderInfoRepository {
    void addOrder(Order orderItem);
    void removeOrder(Order orderItem);
    void removeAllOrders();

    Order getOrderById(Long id);
    Order getOrderByUserInfo(int tableNumber, String name);
    List<Order> getOrdersByUserInfo(int tableNumber, String name);
    List<Order> getAllOrder();
    Long getMaxOrderId();
}
