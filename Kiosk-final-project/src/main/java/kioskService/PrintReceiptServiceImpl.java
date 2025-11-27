package kioskService;

import Domain.Order;
import Domain.OrderState;
import Repository.OrderInfoRespositoryImpl;

// 영수증 출력 서비스 구현 클래스
// 데이터베이스에서 주문 정보를 조회하여 영수증 데이터를 제공하는 기능을 구현
public class PrintReceiptServiceImpl implements PrintReceiptService {
    private final OrderInfoRespositoryImpl orderInfoRespositoryImpl;

    public PrintReceiptServiceImpl(OrderInfoRespositoryImpl orderInfoRespositoryImpl) {
        this.orderInfoRespositoryImpl = orderInfoRespositoryImpl;
    }

    public Order getOrderData(Long orderId) {
        Order order = orderInfoRespositoryImpl.getOrderById(orderId);

        if ((order != null) && (order.getOrderState() == OrderState.ORDERED)) {
            return order;
        }

        return null;
    }
}
