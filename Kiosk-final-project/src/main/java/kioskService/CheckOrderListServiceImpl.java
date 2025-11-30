package kioskService;

import Domain.Order;
import Domain.OrderItem;
import Repository.OrderInfoRespositoryImpl;

import java.util.ArrayList;
import java.util.List;

// 주문 내역 조회 서비스 구현 클래스
// 데이터베이스에서 주문 내역 조회 및 총 금액 계산 기능 구현 클래스
public class CheckOrderListServiceImpl implements CheckOrderListService {
    private final OrderInfoRespositoryImpl orderInfoRespositoryImpl;

    // 주문 내역 조회 서비스 생성자
    public CheckOrderListServiceImpl(OrderInfoRespositoryImpl orderInfoRespositoryImpl) {
        this.orderInfoRespositoryImpl = orderInfoRespositoryImpl;
    }

    // 테이블 번호와 주문자명으로 주문 항목 목록 조회 메서드
    @Override
    public List<OrderItem> getOrderItemsByUserInfo(int tableNumber, String name) {
        List<Order> orders = orderInfoRespositoryImpl.getOrdersByUserInfo(tableNumber, name);

        if (orders == null || orders.isEmpty()) {
            return null;
        }

        // 모든 주문의 항목을 합치기
        List<OrderItem> allOrderItems = new ArrayList<>();
        for (Order order : orders) {
            if (order.getOrderItems() != null) {
                allOrderItems.addAll(order.getOrderItems());
            }
        }

        return allOrderItems.isEmpty() ? null : allOrderItems;
    }

    // 테이블 번호와 주문자명으로 총 가격 조회 메서드
    @Override
    public int gettotalPriceByUserInfo(int tableNumber, String name) {
        List<Order> orders = orderInfoRespositoryImpl.getOrdersByUserInfo(tableNumber, name);

        if (orders == null || orders.isEmpty()) {
            return -1;
        }

        // 모든 주문의 총액 합산
        int totalPrice = 0;
        for (Order order : orders) {
            totalPrice += order.getTotalPrice();
        }

        return totalPrice;
    }
}
