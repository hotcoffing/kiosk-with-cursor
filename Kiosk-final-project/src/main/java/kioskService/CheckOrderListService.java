package kioskService;

import Domain.OrderItem;

import java.util.List;

// 주문 내역 조회 서비스 인터페이스
// 테이블 번호와 고객명으로 주문 내역을 조회하는 기능을 정의
public interface CheckOrderListService {
    public List<OrderItem> getOrderItemsByUserInfo(int tableNumber, String name);
    public int gettotalPriceByUserInfo(int tableNumber, String name);
}
