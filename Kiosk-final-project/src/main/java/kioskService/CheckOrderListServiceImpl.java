package kioskService;

import Domain.*;
import Repository.*;

import java.util.*;

// 주문 내역 조회 서비스 구현 클래스
// 데이터베이스에서 주문 내역을 조회하고 총 금액을 계산하는 기능을 구현
public class CheckOrderListServiceImpl implements CheckOrderListService {
    private final OrderInfoRespositoryImpl orderInfoRespositoryImpl;

    public CheckOrderListServiceImpl(OrderInfoRespositoryImpl orderInfoRespositoryImpl) {
        this.orderInfoRespositoryImpl = orderInfoRespositoryImpl;
    }

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
