package kioskService;

import java.util.List;

import Domain.OrderItem;

// 주문 내역 조회 서비스 인터페이스
public interface CheckOrderListService {
    public List<OrderItem> getOrderItemsByUserInfo(int tableNumber, String name);
    public int gettotalPriceByUserInfo(int tableNumber, String name);
}
